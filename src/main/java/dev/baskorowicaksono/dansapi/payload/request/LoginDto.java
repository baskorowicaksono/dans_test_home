package dev.baskorowicaksono.dansapi.payload.request;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
