package com.theoffice.mercadogue.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tb_compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    private String enderecoEntrega;
    private int formaPagamento; //0 cartão, 1 boleto
    private int nParcelas;
    private String statusPedido; //ao concluir uma compra será gravado no BD como "aguardando pagamento"
    private float valorTotal;
    private float frete;
    private long numeroPedido;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCompra = new java.sql.Date(System.currentTimeMillis());

    @ManyToMany
    @JoinTable(
            name = "compra_produto",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    Set<Produto> produtosVinculados = new HashSet<>();;

    public Set<Produto> getProdutosVinculados() {
        return produtosVinculados;
    }

    public void setProdutosVinculados(Set<Produto> produtosVinculados) {
        this.produtosVinculados = produtosVinculados;
    }


    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public float getFrete() {
        return frete;
    }

    public void setFrete(float frete) {
        this.frete = frete;
    }

    public String isStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public long getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setFormaPagamento(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getFormaPagamento() {
        return formaPagamento;
    }


}


