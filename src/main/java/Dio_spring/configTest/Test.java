package Dio_spring.configTest;

import Dio_spring.model.Usuario;
import Dio_spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class Test implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public void run(String... args) throws Exception {
        usuarioRepository.save(new Usuario("Pedro","pedro@gmail.com","1234"));
    }
}
