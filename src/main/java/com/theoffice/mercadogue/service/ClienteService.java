package com.theoffice.mercadogue.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.theoffice.mercadogue.model.EnderecoDTO;
import com.theoffice.mercadogue.model.Cliente;
import com.theoffice.mercadogue.model.ClienteLogin;
import com.theoffice.mercadogue.repository.ClienteRepository;
import com.theoffice.mercadogue.service.exception.CPFJaCadastradorException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Cliente cadastrarCliente(Cliente cliente) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(cliente.getSenhaCliente());
        cliente.setSenhaCliente(senhaEncoder);

        Optional<Cliente> userByCPF = repository.findByCpf(cliente.getCpf());

        try {
            if (userByCPF.isPresent()) {
                throw new CPFJaCadastradorException("CPF já cadastrado");
            }
        } catch (CPFJaCadastradorException ex) {
            Cliente userException = new Cliente();
            userException.setNomeCliente(ex.getMessage());
            return userException;
        }

        return repository.save(cliente);
    }

    public Cliente alterarCliente(Cliente cliente) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(cliente.getSenhaCliente());
        cliente.setSenhaCliente(senhaEncoder);

        return repository.save(cliente);
    }

    public Optional<ClienteLogin> logarCliente(Optional<ClienteLogin> user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Cliente> cliente = repository.findByEmailCliente(user.get().getEmailCliente());

        System.out.println("metodo logar inicio");
        if (cliente.isPresent()) {
            System.out.println("primeira condicao para ver se o usuario esta presente");
            if (encoder.matches(user.get().getSenhaCliente(), cliente.get().getSenhaCliente())) {
                System.out.println("segunda condicao que verifica se se a senha do usuario match");

                String auth = user.get().getEmailCliente() + ":" + user.get().getSenhaCliente();
                byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodeAuth);


                user.get().setTokenCliente(authHeader);
                user.get().setNomeCliente(cliente.get().getNomeCliente());
                user.get().setEmailCliente(cliente.get().getEmailCliente());
                user.get().setSenhaCliente(cliente.get().getSenhaCliente());
                user.get().setIdCliente(cliente.get().getId());
                System.out.println("retonar o usuario: " + user);
                return user;
            }
        }
        System.out.println("vai retornar um valor nulo");
        return null;
    }

    public boolean validaNome(String nome) {
        if (nome.equals("undefined")) {
            return false;
        }

        if (!nome.contains(" ")) {
            return false;
        }

        return true;

    }

    public boolean validaCPF(String cpf) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            return false;
        }
    }

    public boolean consultaCep(String cep) {
        try {

            EnderecoDTO enderecoDTO = restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json", EnderecoDTO.class);
            if (enderecoDTO.getCep() == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
