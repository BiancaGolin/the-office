package com.theoffice.mercadogue.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 3, max = 280)
	private String nomeProduto;
	
	@Size(max = 2000)
	private String nomeExtenso;

	@NotNull
	private float qtdEstrelas;

	@NotNull
	private boolean status;

	@NotNull
	private int qtdEstoque;

	@NotNull
	private float preco;

//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
//	@JoinColumn(name="imagem")
//	@JsonIgnoreProperties("produto")

	@OneToMany(mappedBy = "produto", cascade=CascadeType.ALL)
	private List<Imagem> imagem;

	public List<Compra> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Compra> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	//	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JoinColumn(name = "id")
	@ManyToMany
	@JoinTable(
		name="produto_liked_compras",
		joinColumns = @JoinColumn(name = "tb_produto_id"),
		inverseJoinColumns = @JoinColumn(name = "tb_compra_id")
	)
	public List<Compra> listaProdutos = new ArrayList<Compra>();
//	Set<Compra> likedCompras;

	// Getters e Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getNomeExtenso() {
		return nomeExtenso;
	}

	public void setNomeExtenso(String nomeExtenso) {
		this.nomeExtenso = nomeExtenso;
	}

	public float getQtdEstrelas() {
		return qtdEstrelas;
	}

	public void setQtdEstrelas(float qtdEstrelas) {
		this.qtdEstrelas = qtdEstrelas;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

//	public Set<Compra> getLikedCompras() {
//		return likedCompras;
//	}
//
//	public void setLikedCompras(Set<Compra> likedCompras) {
//		this.likedCompras = likedCompras;
//	}

	public List<Imagem> getImagem() {
		return imagem;
	}

	public void setImagem(List<Imagem> imagem) {

		this.imagem = imagem;
		imagem.forEach(entity -> entity.setProduto(this));
	}
}
