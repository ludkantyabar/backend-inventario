package com.phegondev.InventoryManagementSystem.dto;

import com.phegondev.InventoryManagementSystem.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Nombre is reqquerido")
    private String name;
    @NotBlank(message = "Email es requerido")
    private String email;
    @NotBlank(message = "Password es requerido")
    private String password;
    @NotBlank(message = "Numero de teléfono es requerido")
    private String phoneNumber;
    private UserRole role;

}
