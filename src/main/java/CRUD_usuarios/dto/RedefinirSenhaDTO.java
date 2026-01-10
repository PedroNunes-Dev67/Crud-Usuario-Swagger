package CRUD_usuarios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class RedefinirSenhaDTO implements Serializable {

    @NotBlank
    @Schema(example = "java123")
    private String senha;

    public RedefinirSenhaDTO(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
}
