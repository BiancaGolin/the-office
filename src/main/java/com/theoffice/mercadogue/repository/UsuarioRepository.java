package com.theoffice.mercadogue.repository;

import com.theoffice.mercadogue.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    public List<Usuario> findAllByNomeUsuarioContainingIgnoreCase(String nomeUsuario);

    public Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE email = ?1")
    public List<Optional<Usuario>> findByEmailList(String email);
}
