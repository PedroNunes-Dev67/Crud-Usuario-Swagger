package CRUD_usuarios.service;

import CRUD_usuarios.exception.ExceptionApiViaCep;
import CRUD_usuarios.model.Endereco;
import CRUD_usuarios.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiViaCep {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco buscarDaddosDeUmCep(String cep){

        return enderecoRepository.findById(cep).orElseGet(() -> {

            try {
                Endereco novoEndereco = restTemplate.getForObject("https://viacep.com.br/ws/"+ cep +"/json/", Endereco.class);

                return enderecoRepository.save(novoEndereco);
            } catch (Exception e) {

                throw new ExceptionApiViaCep("Cep inválido: "+cep);
            }

        });
    }
}
