package com.br.model;

public abstract class Pessoa {
    
    //@ spec_public
    private int id;
    
    //@ spec_public nullable
    private String nome;
    
    //@ spec_public nullable
    private String cpf;
    
    //@ spec_public nullable
    private String telefone;
    
    //@ spec_public nullable
    private String email;
    
    /*@ 
      @ ensures nome == null;
      @ ensures cpf == null;
      @ ensures telefone == null;
      @ ensures email == null;
      @*/
    public Pessoa() {
    }
    
    /*@ 
      @ requires nome != null && cpf != null && telefone != null && email != null;
      @ ensures this.id == id;
      @ ensures this.nome == nome;
      @ ensures this.cpf == cpf;
      @ ensures this.telefone == telefone;
      @ ensures this.email == email;
      @*/
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

    //@ assignable this.id;
    public void setId(int id) {
        this.id = id;
    }
    
    /*@ pure nullable @*/
    public String getNome() {
        return nome;
    }
    
    //@ assignable this.nome;
    public void setNome(String nome) {
        this.nome = nome;
    }

    /*@ pure nullable @*/
    public String getCpf() {
        return cpf;
    }
    
    //@ assignable this.cpf;
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /*@ pure nullable @*/
    public String getTelefone() {
        return telefone;
    }
    
    //@ assignable this.telefone;
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /*@ pure nullable @*/
    public String getEmail() {
        return email;
    }

    //@ assignable this.email;
    public void setEmail(String email) {
        this.email = email;
    }
    
    //@ skipesc
    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", email=" + email + '}';
    }
}