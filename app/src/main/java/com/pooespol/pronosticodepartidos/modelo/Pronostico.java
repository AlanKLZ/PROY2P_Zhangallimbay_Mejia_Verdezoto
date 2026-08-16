package com.pooespol.pronosticodepartidos.modelo;

import java.io.Serializable;

public class Pronostico implements Serializable {

    private String idPronostico;
    private String idParticipante;
    private String idPartido;
    private int golesSeleccion1;
    private int golesSeleccion2;
    private int puntosObtenidos;

    public Pronostico(String idPronostico,
                      String idParticipante,
                      String idPartido,
                      int golesSeleccion1,
                      int golesSeleccion2,
                      int puntosObtenidos) {

        this.idPronostico = idPronostico;
        this.idParticipante = idParticipante;
        this.idPartido = idPartido;
        this.golesSeleccion1 = golesSeleccion1;
        this.golesSeleccion2 = golesSeleccion2;
        this.puntosObtenidos = puntosObtenidos;
    }

    public String getIdPronostico() {
        return idPronostico;
    }

    public void setIdPronostico(String idPronostico) {
        this.idPronostico = idPronostico;
    }

    public String getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public int getGolesSeleccion1() {
        return golesSeleccion1;
    }

    public void setGolesSeleccion1(int golesSeleccion1) {
        this.golesSeleccion1 = golesSeleccion1;
    }

    public int getGolesSeleccion2() {
        return golesSeleccion2;
    }

    public void setGolesSeleccion2(int golesSeleccion2) {
        this.golesSeleccion2 = golesSeleccion2;
    }

    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

    @Override
    public String toString() {
        return "Pronostico{" +
                "idPronostico='" + idPronostico + '\'' +
                ", idParticipante='" + idParticipante + '\'' +
                ", idPartido='" + idPartido + '\'' +
                ", golesSeleccion1=" + golesSeleccion1 +
                ", golesSeleccion2=" + golesSeleccion2 +
                ", puntosObtenidos=" + puntosObtenidos +
                '}';
    }
}
