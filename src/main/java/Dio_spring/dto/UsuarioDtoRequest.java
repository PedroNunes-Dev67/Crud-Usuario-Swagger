package Dio_spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class UsuarioDtoRequest implements Serializable {

    @NotBlank
    @Schema(example = "pedro")
    private String nome;
    @Schema(example = "pedro@gmail.com")
    @Email
    @NotBlank
    private String email;
    @Schema(example = "1234")
    @NotBlank
    private String senha;
    @NotBlank
    @Schema(example = "01000000")
    private String cep;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
