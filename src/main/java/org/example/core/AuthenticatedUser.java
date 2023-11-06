package org.example.core;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.security.Principal;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticatedUser implements Principal {

    private String role;
    private String name;
    private String email;
    private String password;

    public AuthenticatedUser(String username, String role) {
        this.email = username;
        this.role = "Admin";
    }
}
