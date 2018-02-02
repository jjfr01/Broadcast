package com.example.superordenata.broadcast;

public class Registro {

    private int id;
    private String tipo;//Entrante, Saliente y Perdida
    private String numero;
    private String fechahora;

    public Registro() {
    }

    public Registro(int id, String tipo, String numero, String fechahora) {
        this.id = id;
        this.tipo = tipo;
        this.numero = numero;
        this.fechahora = fechahora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {return numero;}

    public void setNumero(String numero) {this.numero = numero;}

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }
}
