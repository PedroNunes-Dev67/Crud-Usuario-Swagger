package Dio_spring.service;

import Dio_spring.dto.RedefinirSenha;
import Dio_spring.dto.UsuarioDtoRequest;
import Dio_spring.dto.UsuarioDtoResponse;
import Dio_spring.exception.ExceptionConflitoUsuario;
import Dio_spring.exception.ExceptionUsuarioNaoEncontrado;
import Dio_spring.model.Endereco;
import Dio_spring.model.Usuario;
import Dio_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ApiViaCep apiViaCep;

    public UsuarioDtoResponse getUser(Long id){
        return usuarioRepository.findById(id).map(usuario -> {
            return new UsuarioDtoResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getEndereco_usuario()
            );
        }).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuário não encontrado"));
    }

    public List<UsuarioDtoResponse> findAll(){

        List<UsuarioDtoResponse> listResponse = new ArrayList<>();
        usuarioRepository.findAll().forEach(usuario -> {
            listResponse.add(new UsuarioDtoResponse(usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getEndereco_usuario()
            )
            );
        });
        return listResponse;
    }

    public void delete(Long id){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuario não encontrado."));
        usuarioRepository.delete(usuario);
    }

    public UsuarioDtoResponse addUsuario(UsuarioDtoRequest usuarioDtoRequest){

            if (usuarioRepository.findByEmail(usuarioDtoRequest.getEmail()).isPresent()){
                throw new ExceptionConflitoUsuario("Usuario já cadastrado.");
            }

            Endereco endereco = apiViaCep.buscarDaddosDeUmCep(usuarioDtoRequest.getCep());

            Usuario novoUsuario = new Usuario(
                    usuarioDtoRequest.getNome(),
                    usuarioDtoRequest.getEmail(),
                    usuarioDtoRequest.getSenha(),
                    endereco
            );

            usuarioRepository.save(novoUsuario);

            return new UsuarioDtoResponse(
                    novoUsuario.getId(),
                    novoUsuario.getNome(),
                    novoUsuario.getEmail(),
                    novoUsuario.getEndereco_usuario()
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
                    usuario.getEmail(),
                    usuario.getEndereco_usuario()
            );
    }
}
