package CRUD_usuarios.service;

import CRUD_usuarios.dto.RedefinirSenhaDTO;
import CRUD_usuarios.dto.UsuarioDtoRequest;
import CRUD_usuarios.dto.UsuarioDtoResponse;
import CRUD_usuarios.exception.ExceptionConflitoUsuario;
import CRUD_usuarios.exception.ExceptionUsuarioNaoEncontrado;
import CRUD_usuarios.model.Endereco;
import CRUD_usuarios.model.Usuario;
import CRUD_usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ApiViaCep apiViaCep;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional
    public void delete(Long id){

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ExceptionUsuarioNaoEncontrado("Usuario não encontrado."));

        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioDtoResponse addUsuario(UsuarioDtoRequest usuarioDtoRequest){

            //Não deve salvar o mesmo usuário com mesmo email
            if (usuarioRepository.findByEmail(usuarioDtoRequest.getEmail()).isPresent()) throw new ExceptionConflitoUsuario("Usuario já cadastrado.");

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

    @Transactional
    public UsuarioDtoResponse updateUser(Long id, RedefinirSenhaDTO novaSenha){

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
