package com.example.user.common;

import com.example.user.common.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtTokenService {

    // The @Value("${jwt.secret}") annotation is used to inject a value from a property file into a variable in Spring.
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails, String userUuid){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities());
        claims.put("userUuid", userUuid);
        return createToken(claims, userDetails.getUsername());
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Map<String, Object> claims, String subject){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        // HS256 is a symmetric encryption algorithm, which means the same secret key is used for both signing (on the server-side) and verification (on the client-side). This is in contrast to asymmetric encryption algorithms, where different keys are used for signing and verification.
        // the claims parameter in the createToken method of the JwtTokenService class is used to include additional claims (key-value pairs) in the JWT token. Claims provide a way to include custom information or attributes about the user or the token itself.
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expirationDate).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token) {
        try {
            Collection<? extends GrantedAuthority> authorities = extractAuthorities(token);
            for (GrantedAuthority authority: authorities){
                // IllegalArgumentException will be thrown when value not existed in enum
                Role.valueOf(authority.getAuthority());
            }
            return !isTokenExpired(token);
        } catch (Exception e){
            return false;
        }

    }

    public String extractUserUuid(String token){
        return (String)extractAllClaims(token).get("userUuid");
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token){
        Claims claims = extractAllClaims(token);
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>)claims.get("role");
        return authorities;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
