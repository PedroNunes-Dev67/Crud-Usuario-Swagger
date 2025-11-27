package Dio_spring.service;

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
}
