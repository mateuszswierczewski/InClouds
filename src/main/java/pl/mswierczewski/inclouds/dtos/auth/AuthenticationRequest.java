package pl.mswierczewski.inclouds.dtos.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthenticationRequest {

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Email
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 50)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
