package com.theoffice.mercadogue.service;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.theoffice.mercadogue.model.EnderecoDTO;
import com.theoffice.mercadogue.model.Usuario;
import com.theoffice.mercadogue.model.UsuarioLogin;
import com.theoffice.mercadogue.repository.UsuarioRepository;
import com.theoffice.mercadogue.service.exception.CPFJaCadastradorException;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Usuario cadastrarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoder);

        Optional<Usuario> userByCPF = repository.findByCpf(usuario.getCpf());

        try {
            if (userByCPF.isPresent()) {
                throw new CPFJaCadastradorException("CPF já cadastrado");
            }
        } catch (CPFJaCadastradorException ex) {
            Usuario userException = new Usuario();
            userException.setNomeUsuario(ex.getMessage());
            return userException;
        }

        return repository.save(usuario);
    }

    public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Usuario> usuario = repository.findByEmail(user.get().getEmail());

        System.out.println("metodo logar inicio");
        if (usuario.isPresent()) {
            System.out.println("primeira condicao para ver se o usuario esta presente");
            if (encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {
                System.out.println("segunda condicao que verifica se se a senha do usuario match");

                String auth = user.get().getEmail() + ":" + user.get().getSenha();
                byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodeAuth);


                user.get().setToken(authHeader);
                user.get().setNomeUsuario(usuario.get().getNomeUsuario());
                user.get().setEmail(usuario.get().getEmail());
                user.get().setSenha(usuario.get().getSenha());
                user.get().setTipoUsuario(usuario.get().getTipoUsuario());
                user.get().setStatusUsuario(usuario.get().isStatusUsuario());
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
