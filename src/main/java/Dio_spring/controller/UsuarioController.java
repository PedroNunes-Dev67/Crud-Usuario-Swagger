package Dio_spring.controller;


import Dio_spring.model.Usuario;
import Dio_spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(usuarioService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findALl(){
        List<Usuario> allUsers = usuarioService.findAll();
        return ResponseEntity.ok(allUsers);
    }

}
