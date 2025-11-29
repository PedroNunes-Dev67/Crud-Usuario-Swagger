package Dio_spring.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

public class UsuarioDtoResponse implements Serializable {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "pedro")
    private String nome;
    @Schema(example = "pedro@gmail.com")
    private String email;

    public UsuarioDtoResponse() {
    }

    public UsuarioDtoResponse(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
