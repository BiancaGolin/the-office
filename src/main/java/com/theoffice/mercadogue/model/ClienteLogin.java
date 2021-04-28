package com.theoffice.mercadogue.model;

public class ClienteLogin {

    private int idCliente;
    private String nomeCliente;
    private String emailCliente;
    private String senhaCliente;
    private String tokenCliente;


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }

    public String getTokenCliente() {
        return tokenCliente;
    }

    public void setTokenCliente(String tokenCliente) {
        this.tokenCliente = tokenCliente;
    }
}
