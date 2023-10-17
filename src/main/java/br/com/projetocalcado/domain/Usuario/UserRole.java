package br.com.projetocalcado.domain.Usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");
    @NotBlank
    private String role;

    UserRole(String role){
        this.role = role;
    }
}
