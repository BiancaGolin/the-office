package com.theoffice.mercadogue.security;

import com.theoffice.mercadogue.model.Usuario;
import com.theoffice.mercadogue.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<Usuario> user = usuarioRepository.findByEmail(userEmail);
        user.orElseThrow(() -> new UsernameNotFoundException(userEmail + " Not Found."));

        return user.map(UserDetailsImpl::new).get();
    }
}
