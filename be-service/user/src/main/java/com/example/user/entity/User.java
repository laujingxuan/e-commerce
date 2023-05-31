package com.example.user.entity;

import com.example.shared.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//The UserDetails interface in Spring Security is an essential part of the authentication process. By implementing this interface, you provide necessary information about the user to Spring Security, allowing it to perform authentication and authorization checks effectively.
@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private String id;

    // if want to enforce uniqueness across combinations of columns, can use @UniqueConstraint at table level instead
    // example: @Table(name = "user", uniqueConstraints = {
    //        @UniqueConstraint(columnNames = "email"),
    //        @UniqueConstraint(columnNames = "username")
    //})
    @NotEmpty
    @Size(min=4, max=20)
    @Column(name="username", unique = true)
    private String username;

    @NotEmpty
    @Size(min=6, max=64)
    @Column(name="password")
    private String password;

    @NotEmpty
    @Size(min=6, max=20)
    @Column(name="email", unique = true)
    private String email;

    // The @Enumerated(EnumType.STRING) annotation in JPA (Java Persistence API) is used to specify the mapping of an enum type attribute to its corresponding database representation.
    // @Enumerated(EnumType.STRING) indicates that the enum values of the role field should be stored as strings in the database
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // The SimpleGrantedAuthority class simplifies the creation of GrantedAuthority instances by providing a straightforward constructor that takes a String parameter representing the authority.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.role.toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
