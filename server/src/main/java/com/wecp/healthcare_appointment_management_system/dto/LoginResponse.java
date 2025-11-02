package com.wecp.healthcare_appointment_management_system.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private Long userId;

    private String token;

    private String username;

    private String email;

    private String role;

    @JsonCreator
    public LoginResponse(@JsonProperty("token") String token,
                         @JsonProperty("role") String role,
                         @JsonProperty("userId") Long userId) {
        this.token = token;
        this.role = role;
        this.userId = userId;
    }

    @JsonCreator
    public LoginResponse(@JsonProperty("userId") Long userId,
                         @JsonProperty("token") String token,
                         @JsonProperty("username") String username,
                         @JsonProperty("email") String email,
                         @JsonProperty("role") String role) {
        this.userId = userId;
        this.token = token;
        this.username = username;
        this.email = email;
        this.role = role;
    }
    
}
