package com.example.shared.utils;

import com.example.shared.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private WebClient webClient;

    @Autowired
    public JwtTokenService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String generateToken(UserDetails userDetails, String userUuid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
        claims.put("userUuid", userUuid);
        return createToken(claims, userDetails.getUsername());
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        // HS256 is a symmetric encryption algorithm, which means the same secret key is used for both signing (on the server-side) and verification (on the client-side). This is in contrast to asymmetric encryption algorithms, where different keys are used for signing and verification.
        // the claims parameter in the createToken method of the JwtTokenService class is used to include additional claims (key-value pairs) in the JWT token. Claims provide a way to include custom information or attributes about the user or the token itself.
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expirationDate).signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token) {
        try {
            String authority = extractAuthority(token);
            // IllegalArgumentException will be thrown when value not existed in enum
            Role.valueOf(authority);
            String userUuid = extractUserUuid(token);
            return isUserValid(userUuid, token) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserValid(String userUuid, String token) {
        // retrieve the response without consuming the response body
        // If the response is empty (no response), we set the default value to false using the defaultIfEmpty operator
        return Boolean.TRUE.equals(webClient.get()
                .uri("http://localhost:8081/users/validity/" + userUuid)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                // retrieve the response without consuming the response body
                .toBodilessEntity()
                .map(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                // If the response is empty (no response), we set the default value to false using the defaultIfEmpty operator
                .defaultIfEmpty(false)
                .block());
    }

    public String extractUserUuid(String token) {
        return (String) extractAllClaims(token).get("userUuid");
    }

    public String extractAuthority(String token) {
        Claims claims = extractAllClaims(token);
        String role = (String) claims.get("role");
        return role;
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

    public String extractJwtTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            // Token will be passed in as "Bearer xxxxxxx", hence it will start with 7th index
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
