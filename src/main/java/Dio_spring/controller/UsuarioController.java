package Dio_spring.controller;


import Dio_spring.dto.RedefinirSenha;
import Dio_spring.dto.UsuarioDtoRequest;
import Dio_spring.dto.UsuarioDtoResponse;
import Dio_spring.model.Usuario;
import Dio_spring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> delete(@PathVariable("id") Long id){
        Usuario usuario = usuarioService.delete(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> addUser(@RequestBody UsuarioDtoRequest usuarioDtoRequest){

        UsuarioDtoResponse usuarioDtoResponse = usuarioService.addUser(usuarioDtoRequest);

        return ResponseEntity.created(currentUri(usuarioDtoResponse.getId())).body(usuarioDtoResponse);
    }

    private URI currentUri(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> updateUser(@PathVariable("id") Long id, @RequestBody RedefinirSenha senha){
        return ResponseEntity.ok(usuarioService.updateUser(id,senha));
    }

}
