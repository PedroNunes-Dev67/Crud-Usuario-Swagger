package Dio_spring.repository;

import Dio_spring.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    private List<Usuario> list = new ArrayList<>();

    public String save(Usuario usuario){
        if (list.contains(usuario)){
            return "Usuario atualizado!";
        }
        else{
            return "Usuario salvo!";
        }
    }

    public List<Usuario> findALl(){
        list.add(new Usuario("pedro@gmail.com","12324"));
        list.add(new Usuario("thiago@gmail.com","1234"));
        return list;
    }

    public Usuario findByEmail(String email){
        for (Usuario u : list){
            if (email.equals(u.getLogin())){
                return u;
            }
        }
        return null;
    }
}
