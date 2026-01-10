package CRUD_usuarios.controller;


import CRUD_usuarios.dto.RedefinirSenhaDTO;
import CRUD_usuarios.dto.UsuarioDtoRequest;
import CRUD_usuarios.dto.UsuarioDtoResponse;
import CRUD_usuarios.exception.model.ExceptionResponse;
import CRUD_usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDtoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                                    ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> getUser(
            @Parameter(description = "Id do usuário", example = "1")
            @PathVariable("id") Long id){

        UsuarioDtoResponse usuario = usuarioService.getUser(id);

        return ResponseEntity.ok(usuario);
    }


    @Operation(summary = "Lista todos os usuários cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDtoResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDtoResponse>> findAllUsers(){

        List<UsuarioDtoResponse> list = usuarioService.findAll();

        return ResponseEntity.ok(list);
    }


    @Operation(summary = "Deleta os usuários pelo id", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário encontrado e deletado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)
                    ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "id do usuário que vai ser deletado",example = "1")
            @PathVariable("id") Long id){

        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Cadastra usuários", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoResponse.class)
            )),
            @ApiResponse(responseCode = "400", description = "Validation ativa, parâmetro incorreto",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito , usuário já cadastrado",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> createUser(@RequestBody @Valid UsuarioDtoRequest usuarioDtoRequest){

        UsuarioDtoResponse usuarioDtoResponse = usuarioService.addUsuario(usuarioDtoRequest);

        return ResponseEntity.created(currentUri(usuarioDtoResponse.getId())).body(usuarioDtoResponse);
    }

    private URI currentUri(Object id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }


    @Operation(summary = "Atualiza senha do usuário",method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado",
                        content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = UsuarioDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation usado",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflito , senha não pode ser a mesma que a anterior",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDtoResponse> updateUserPassword(
            @Parameter(description = "Id do usuário a ter a senha atualizada",example = "1")
            @PathVariable("id") Long id,
            @RequestBody @Valid RedefinirSenhaDTO senha){

        UsuarioDtoResponse usuario = usuarioService.updateUser(id, senha);

        return ResponseEntity.ok(usuario);
    }
}
