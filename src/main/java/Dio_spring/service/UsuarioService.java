package Dio_spring.service;

import Dio_spring.dto.RedefinirSenha;
import Dio_spring.dto.UsuarioDtoRequest;
import Dio_spring.dto.UsuarioDtoResponse;
import Dio_spring.exception.ExceptionConflitoUsuario;
import Dio_spring.exception.ExceptionUsuarioNaoEncontrado;
import Dio_spring.model.Usuario;
import Dio_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDtoResponse getUser(Long id){
        return usuarioRepository.findById(id).map(usuario -> {
            return new UsuarioDtoResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            );
        }).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuário não encontrado"));
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
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
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuário não encontrado"));

            if (usuario.getSenha().equals(novaSenha.getSenha())){
                throw new ExceptionConflitoUsuario("A senha não pode ser a mesma que a anterior");
            }

            usuario.setSenha(novaSenha.getSenha());
            usuarioRepository.save(usuario);

            return new UsuarioDtoResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            );
    }
}
