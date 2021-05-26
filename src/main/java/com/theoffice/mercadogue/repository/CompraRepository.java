package com.theoffice.mercadogue.repository;

import com.theoffice.mercadogue.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    public List<Compra> findAllById(int id);
}
