package com.pooespol.pronosticodepartidos.modelo;

import java.io.Serializable;

public class Partido implements Serializable {

    private String idPartido;
    private Fase faseTorneo;
    private String fecha;
    private String hora;
    private String estadio;
    private String seleccion1;
    private String seleccion2;
    private EstadoPartido estadoPartido;

    public Partido(String idPartido,
                   Fase faseTorneo,
                   String fecha,
                   String hora,
                   String estadio,
                   String seleccion1,
                   String seleccion2,
                   EstadoPartido estadoPartido) {

        this.idPartido = idPartido;
        this.faseTorneo = faseTorneo;
        this.fecha = fecha;
        this.hora = hora;
        this.estadio = estadio;
        this.seleccion1 = seleccion1;
        this.seleccion2 = seleccion2;
        this.estadoPartido = estadoPartido;
    }

    public void cerrarPronosticos() {
        if (estadoPartido == EstadoPartido.ABIERTO) {
            estadoPartido = EstadoPartido.CERRADO;
        }
    }

    public void finalizarPartido() {
        estadoPartido = EstadoPartido.FINALIZADO;
    }

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public Fase getFaseTorneo() {
        return faseTorneo;
    }

    public void setFaseTorneo(Fase faseTorneo) {
        this.faseTorneo = faseTorneo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getSeleccion1() {
        return seleccion1;
    }

    public void setSeleccion1(String seleccion1) {
        this.seleccion1 = seleccion1;
    }

    public String getSeleccion2() {
        return seleccion2;
    }

    public void setSeleccion2(String seleccion2) {
        this.seleccion2 = seleccion2;
    }

    public EstadoPartido getEstadoPartido() {
        return estadoPartido;
    }

    public void setEstadoPartido(EstadoPartido estadoPartido) {
        this.estadoPartido = estadoPartido;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido='" + idPartido + '\'' +
                ", faseTorneo=" + faseTorneo +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", estadio='" + estadio + '\'' +
                ", seleccion1='" + seleccion1 + '\'' +
                ", seleccion2='" + seleccion2 + '\'' +
                ", estadoPartido=" + estadoPartido +
                '}';
    }
}
