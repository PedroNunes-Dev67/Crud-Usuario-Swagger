package Dio_spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class RedefinirSenha implements Serializable {

    @NotBlank
    @Schema(example = "java123")
    private String senha;

    public String getSenha() {
        return senha;
    }
}
