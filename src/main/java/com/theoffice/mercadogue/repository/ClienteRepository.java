package com.theoffice.mercadogue.repository;

import com.theoffice.mercadogue.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public List<Cliente> findAllByNomeClienteContainingIgnoreCase(String nomeCliente);

    public Optional<Cliente> findByEmailCliente(String emailCliente);

    Optional<Cliente> findByCpf(String cpfCliente);
}
