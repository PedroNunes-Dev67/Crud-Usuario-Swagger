package Dio_spring.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class RedefinirSenha implements Serializable {

    @NotBlank
    private String senha;

    public String getSenha() {
        return senha;
    }
}
