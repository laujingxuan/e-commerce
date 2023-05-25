package com.example.user.DTO;

import com.example.user.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDTO {

    private String id;

    @NotEmpty
    @Size(min=4, max=20)
    private String username;

    @NotEmpty
    @Size(min=6, max=20)
    private String email;

    @NotNull
    private Role role;

    List<UserActionDTO> userActionDTOList;

    public UserDetailsDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<UserActionDTO> getUserActionDTOList() {
        return userActionDTOList;
    }

    public void setUserActionDTOList(List<UserActionDTO> userActionDTOList) {
        this.userActionDTOList = userActionDTOList;
    }
}
