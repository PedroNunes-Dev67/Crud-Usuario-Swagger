package Dio_spring.controller;


import Dio_spring.model.Usuario;
import Dio_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/users")
    public List<Usuario> findALl(){
        return usuarioRepository.findALl();
    }

    @PostMapping("users")
    public String save(@RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/users/{email}")
    public Usuario getUserEmail(@PathVariable("email") String email){
        return usuarioRepository.findByEmail(email);
    }
}
