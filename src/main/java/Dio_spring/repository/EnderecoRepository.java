package Dio_spring.repository;

import Dio_spring.model.Endereco;
import Dio_spring.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, String> {

}
