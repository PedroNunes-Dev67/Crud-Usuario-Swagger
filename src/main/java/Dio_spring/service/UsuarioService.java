package Dio_spring.service;

import Dio_spring.dto.RedefinirSenha;
import Dio_spring.dto.UsuarioDtoRequest;
import Dio_spring.dto.UsuarioDtoResponse;
import Dio_spring.exception.ExceptionConflitoUsuario;
import Dio_spring.exception.ExceptionEmptyUsuario;
import Dio_spring.exception.ExceptionUsuarioNaoEncontrado;
import Dio_spring.model.Usuario;
import Dio_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getUser(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuário não encontrado."));
        return usuario;
    }

    public List<Usuario> findAll(){
        List<Usuario> list = usuarioRepository.findAll();
        if (list.isEmpty()){
            throw new ExceptionEmptyUsuario("Nenhum usuário cadastrado.");
        }
        return list;
    }

    public void delete(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuario não encontrado."));
        usuarioRepository.delete(usuario);
    }

    public UsuarioDtoResponse addUser(UsuarioDtoRequest usuarioDtoRequest){

        if (usuarioRepository.findByEmail(usuarioDtoRequest.getEmail()).isPresent()){
            throw new ExceptionConflitoUsuario("Usuario já cadastrado.");
        }

        Usuario novoUsuario = new Usuario(
                usuarioDtoRequest.getNome(),
                usuarioDtoRequest.getEmail(),
                usuarioDtoRequest.getSenha()
        );

        usuarioRepository.save(novoUsuario);

        return new UsuarioDtoResponse(
                novoUsuario.getId(),
                novoUsuario.getNome(),
                novoUsuario.getEmail()
        );
    }

    public UsuarioDtoResponse updateUser(Long id, RedefinirSenha novaSenha){

        return usuarioRepository.findById(id)
                .filter(user -> !user.getSenha().equals(novaSenha.getSenha()))
                .map(user -> {

                    user.setSenha(novaSenha.getSenha());
                    usuarioRepository.save(user);
                    UsuarioDtoResponse usuarioDtoResponse = new UsuarioDtoResponse(
                            user.getId(),
                            user.getNome(),
                            user.getEmail()
                    );
                    return usuarioDtoResponse;
                }).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuário não encontrado ou senha não pode ser a mesma que a anterior."));
    }
}
