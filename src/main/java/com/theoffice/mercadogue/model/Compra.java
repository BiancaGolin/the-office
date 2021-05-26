package com.theoffice.mercadogue.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_compra")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "likedCompras")
    Set<Produto> listaDeProdutos;


//    @ManyToOne
//    @JoinColumn(name="id_cliente")
//    @JsonIgnoreProperties("compra")
//    private Cliente cliente;
//
//    @OneToMany(cascade=CascadeType.ALL)
//    @JoinColumn(name="id_produto")
//    @JsonIgnoreProperties("compra")
//    private List<Produto> produto;


    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }
    public Set<Produto> getListaDeProdutos() {
        return listaDeProdutos;
    }

    public void setListaDeProdutos(Set<Produto> listaDeProdutos) {
//        listaDeProdutos.forEach(entity -> entity.setLikedCompras(this));
        this.listaDeProdutos = listaDeProdutos;
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


