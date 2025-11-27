package Dio_spring.service;

import Dio_spring.dto.UsuarioDtoRequest;
import Dio_spring.dto.UsuarioDtoResponse;
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
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.get();
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario delete(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        usuarioRepository.delete(usuario.get());
        return usuario.get();
    }

    public UsuarioDtoResponse addUser(UsuarioDtoRequest usuarioDtoRequest){

        if (usuarioRepository.findByEmail(usuarioDtoRequest.getEmail()).isPresent()){
            throw new RuntimeException("Error!");
        }

        Usuario novoUsuario = new Usuario(
                usuarioDtoRequest.getNome(),
                usuarioDtoRequest.getEmail(),
                usuarioDtoRequest.getSenha()
        );

        usuarioRepository.save(novoUsuario);

        UsuarioDtoResponse usuarioDtoResponse = new UsuarioDtoResponse(
                novoUsuario.getId(),
                novoUsuario.getNome(),
                novoUsuario.getEmail()
        );
        return usuarioDtoResponse;
    }
}
