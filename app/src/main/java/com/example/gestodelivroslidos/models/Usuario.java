package com.example.gestodelivroslidos.models;

public class Usuario {
    private int id;
    private String nome;
    private String apelido;
    private String email;
    private String morada;

    private String password;



    public Usuario() {
    }

    public Usuario(String nome, String apelido, String email, String morada, String password) {
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.morada = morada;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
