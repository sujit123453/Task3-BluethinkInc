package com.bluethinkInc.authentication.authorization_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Pattern(regexp = "^\\d{10}$", message = "Phone must be 10 digits")
    @NotBlank(message = "Phone is required")
    private String phone;

    // optional: if client already has a token, it may send it here to be validated and reused
    private String existingToken;
}
