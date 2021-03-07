package com.theoffice.mercadogue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theoffice.mercadogue.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	public List<Produto> findAllByNomeProdutoContainingIgnoreCase(String nomeProduto);
}
