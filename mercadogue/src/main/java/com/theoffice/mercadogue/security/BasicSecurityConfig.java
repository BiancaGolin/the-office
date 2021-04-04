package com.theoffice.mercadogue.security;

import com.theoffice.mercadogue.model.Usuario;
import com.theoffice.mercadogue.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Usuario usuario = new Usuario();

//        http.authorizeRequests()
//                .antMatchers("/usuario/logar").permitAll()
//                .antMatchers("/usuario/cadastrar").permitAll()
//                .anyRequest().authenticated()
//                .and().httpBasic()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().cors()
//                .and().csrf().disable();

        //ADMIN
//        if (usuario.getTipoUsuario() == 0) {
//            http.authorizeRequests()
//                    .antMatchers("/usuario/logar").permitAll()
//                    .antMatchers("/usuario/cadastrar").permitAll()
//                    .anyRequest().authenticated()
//                    .and().httpBasic()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().cors()
//                    .and().csrf().disable();
//        }
//        //ESTOQUISTA
//        if (usuario.getTipoUsuario() == 1) {
//            http.authorizeRequests()
//                    .antMatchers("/usuario/logar").authenticated()
//                    .antMatchers("/usuario/cadastrar").authenticated()
//                    .anyRequest().authenticated()
//                    .and().httpBasic()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().cors()
//                    .and().csrf().disable();
//        }

        //USUARIO DESATIVADO
//        if (usuario.isStatusUsuario() == false) {
//            http.authorizeRequests()
//                    //não permite nenhum acesso
//                    .anyRequest().denyAll()
//                    .and().httpBasic()
//                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().cors()
//                    .and().csrf().disable();
//        }

    }
}
