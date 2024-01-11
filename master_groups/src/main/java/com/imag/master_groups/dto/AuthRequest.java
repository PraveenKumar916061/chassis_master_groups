package com.imag.master_groups.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull(message = "the username is mandatory should not be null")
    @NotBlank(message = "the username is mandatory should not be empty")
    private String username;

    @NotNull(message = "the password is mandatory should not be null")
    @NotBlank(message = "the password is mandatory should not be empty")
    private String password;
}





