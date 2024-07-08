package com.example.gestodelivroslidos.models;

public class Notas {
    private int id;
    private String dataInicio;
    private int paginasLidas;
    private String anotacoes;
    private int livroId;

    public Notas() {
    }

    public Notas(String dataInicio, int paginasLidas, String anotacoes, int livroId) {
        this.dataInicio = dataInicio;
        this.paginasLidas = paginasLidas;
        this.anotacoes = anotacoes;
        this.livroId = livroId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public int getPaginasLidas() {
        return paginasLidas;
    }

    public void setPaginasLidas(int paginasLidas) {
        this.paginasLidas = paginasLidas;
    }

    public String getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(String anotacoes) {
        this.anotacoes = anotacoes;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }
}
