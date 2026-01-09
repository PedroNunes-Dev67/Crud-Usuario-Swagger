package Dio_spring.repository;

import Dio_spring.model.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar usuário quando email existir e vazio quando não existir")
    void validarMetodoFindByEmail(){

        Usuario usuario = new Usuario("pedro","pedro@gmail.com","1234",null);

        createUsuario(usuario);

        Optional<Usuario> usuarioExiste = usuarioRepository.findByEmail("pedro@gmail.com");

        Assertions.assertTrue(usuarioExiste.isPresent());

        Optional<Usuario> usuarioNaoExiste = usuarioRepository.findByEmail("naoExiste@gmail.com");

        Assertions.assertTrue(usuarioNaoExiste.isEmpty());
    }

    private Usuario createUsuario(Usuario usuario){

        entityManager.persist(usuario);
        return usuario;
    }
}
