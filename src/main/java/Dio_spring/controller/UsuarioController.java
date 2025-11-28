package Dio_spring.controller;


import Dio_spring.dto.RedefinirSenha;
import Dio_spring.dto.UsuarioDtoRequest;
import Dio_spring.dto.UsuarioDtoResponse;
import Dio_spring.model.Usuario;
import Dio_spring.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/usuarios")
@Tag(name = "Usuário", description = "Crud de usuários em Java Springboot")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Busca os dados do usuários por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(usuarioService.getUser(id));
    }


    @Operation(summary = "Busca lista de usuários cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public ResponseEntity<List<Usuario>> findALl(){
        List<Usuario> allUsers = usuarioService.findAll();
        return ResponseEntity.ok(allUsers);
    }


    @Operation(summary = "Deleta os usuários pelo id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário encontrado e deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Cadastra usuários", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
            @ApiResponse(responseCode = "400", description = "Validation ativa, parâmetro incorreto"),
            @ApiResponse(responseCode = "409", description = "Conflito , usuário já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> addUser(@RequestBody @Valid UsuarioDtoRequest usuarioDtoRequest){

        UsuarioDtoResponse usuarioDtoResponse = usuarioService.addUser(usuarioDtoRequest);

        return ResponseEntity.created(currentUri(usuarioDtoResponse.getId())).body(usuarioDtoResponse);
    }

    private URI currentUri(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }


    @Operation(summary = "Atualiza senha do usuário",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "400", description = "Validation usado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito , usuário já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> updateUser(@PathVariable("id") Long id,@RequestBody @Valid RedefinirSenha senha){
        return ResponseEntity.ok(usuarioService.updateUser(id,senha));
    }
}
