package CRUD_usuarios.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @JoinColumn
    @ManyToOne
    private Endereco endereco_usuario;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, Endereco endereco_usuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco_usuario = endereco_usuario;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco_usuario() {
        return endereco_usuario;
    }

    public void setEndereco_usuario(Endereco endereco_usuario) {
        this.endereco_usuario = endereco_usuario;
    }
}
