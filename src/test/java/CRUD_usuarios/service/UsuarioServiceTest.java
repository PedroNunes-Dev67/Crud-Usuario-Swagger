package CRUD_usuarios.service;

import CRUD_usuarios.dto.UsuarioDtoRequest;
import CRUD_usuarios.dto.UsuarioDtoResponse;
import CRUD_usuarios.exception.ExceptionApiViaCep;
import CRUD_usuarios.exception.ExceptionUsuarioNaoEncontrado;
import CRUD_usuarios.model.Endereco;
import CRUD_usuarios.model.Usuario;
import CRUD_usuarios.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private ApiViaCep apiViaCep;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Teste da API Via Cep do método de criar usuário Sucesso")
    void addUsuarioTestSucesso(){

        Endereco endereco = new Endereco("55130000", " ", " ", "PE", "Centro", "São Caitano", "PE", "Pernambuco", "Nordeste", " ", " ","81"," ");

        Mockito.when(apiViaCep.buscarDaddosDeUmCep("55130000")).thenReturn(endereco);

        Mockito.when(usuarioRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.empty());

        UsuarioDtoRequest usuarioDtoRequest = new UsuarioDtoRequest("Pedro","pedro@gmail.com","1234","55130000");

        UsuarioDtoResponse usuario = usuarioService.addUsuario(usuarioDtoRequest);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(ArgumentMatchers.any());

        Assertions.assertEquals(endereco, usuario.getEndereco());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CEP for inválido")
    void addUsuarioTestErro(){

        Mockito.when(apiViaCep.buscarDaddosDeUmCep("00000000")).thenThrow(ExceptionApiViaCep.class);

        UsuarioDtoRequest usuarioDtoRequest = new UsuarioDtoRequest("Pedro","pedro@gmail.com","1234","00000000");

        Assertions.assertThrows(ExceptionApiViaCep.class, () -> usuarioService.addUsuario(usuarioDtoRequest));

        Mockito.verify(usuarioRepository, Mockito.times(0)).save(ArgumentMatchers.any());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findByEmail(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Deve retornar dto do usuário quando id for válido e lançar exceção quando não existir usuário com id")
    void getUsuarioSucesso(){

        Usuario usuario = new Usuario("pedro","pedro@gmail.com","1234",null);

        Mockito.when(usuarioRepository.findById(Long.parseLong("1"))).thenReturn(Optional.of(usuario));

        UsuarioDtoResponse usuarioDtoResponse = usuarioService.getUser(Long.parseLong("1"));

        Assertions.assertEquals(usuario.getId(),usuarioDtoResponse.getId());

        Mockito.when(usuarioRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ExceptionUsuarioNaoEncontrado.class, () -> usuarioService.getUser(Long.parseLong("5")));
    }
}
