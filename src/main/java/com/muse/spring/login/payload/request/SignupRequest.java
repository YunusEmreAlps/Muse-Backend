package com.muse.spring.login.payload.request;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Email is mandatory")
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 40)
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
