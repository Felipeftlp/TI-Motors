package com.br.model;

public abstract class Pessoa {
    private int id;
    //@ nullable
    private String nome;
    //@ nullable
    private String cpf;
    //@ nullable
    private String telefone;
    //@ nullable
    private String email;
    
    public Pessoa() {
    }
    
    //@ requires nome != null && cpf != null && telefone != null && email != null;
    public Pessoa(int id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    /*@ pure @*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    /*@ pure nullable @*/
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    /*@ pure nullable @*/
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /*@ pure nullable @*/
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /*@ pure nullable @*/
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 
}
