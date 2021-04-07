package com.theoffice.mercadogue.service;

import com.theoffice.mercadogue.model.Usuario;
import com.theoffice.mercadogue.model.UsuarioLogin;
import com.theoffice.mercadogue.repository.UsuarioRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senhaEncoder = encoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoder);

        Usuario usu = new Usuario();

        try {
            List<Optional<Usuario>> listUsuarioByEmail = repository.findByEmailList(usuario.getEmail());
            System.out.println(listUsuarioByEmail);

            if (listUsuarioByEmail.isEmpty()) {
                repository.save(usuario);
                return repository.save(usuario);
            } else {
                usu.setEmail("email ja cadastrado");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usu;

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
}
