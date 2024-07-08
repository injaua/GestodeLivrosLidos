package com.example.gestodelivroslidos.models;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private int numeroPaginas;
    private String edicao;
    private int ano;
    private String local;

    private  int notasId;



    public Livro() {
    }

    public Livro(int id, String titulo, String autor, String genero, int numeroPaginas, String edicao, int ano, String local, int notasId) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.numeroPaginas = numeroPaginas;
        this.edicao = edicao;
        this.ano = ano;
        this.local = local;
        this.notasId = notasId;
    }


    public Livro(int id, String titulo, String autor, String genero, int numeroPaginas, String edicao, int ano, String local) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.numeroPaginas = numeroPaginas;
        this.edicao = edicao;
        this.ano = ano;
        this.local = local;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getNotasId() {
        return notasId;
    }

    public void setNotasId(int notasId) {
        this.notasId = notasId;
    }
}
