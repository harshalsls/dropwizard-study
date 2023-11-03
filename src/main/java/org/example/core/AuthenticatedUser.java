package org.example.core;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.security.Principal;

@Getter
@Setter
public class AuthenticatedUser implements Principal {

    private String role;
    private String name;
    private String email;
    private String password;

    public AuthenticatedUser(String username) {
        this.email = username;
        this.role = "Admin";
    }
}
