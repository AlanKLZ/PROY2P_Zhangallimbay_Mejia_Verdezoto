package com.pooespol.pronosticodepartidos.modelo;

import java.io.Serializable;

/**
 * Representa un partido del Mundial FIFA 2026.
 *
 * Contiene la información general del encuentro, como su fase,
 * fecha, hora, estadio, selecciones participantes y estado.
 *
 * También permite cerrar el período de pronósticos y marcar
 * el partido como finalizado.
 *
 * @author Naomi
 */
public class Partido implements Serializable {

    private String idPartido;
    private Fase faseTorneo;
    private String fecha;
    private String hora;
    private String estadio;
    private String seleccion1;
    private String seleccion2;
    private EstadoPartido estadoPartido;

    /**
     * Constructor de la clase Partido.
     *
     * @param idPartido identificador único del partido
     * @param faseTorneo fase del torneo a la que pertenece el partido
     * @param fecha fecha programada del partido
     * @param hora hora programada del encuentro
     * @param estadio estadio donde se disputará el partido
     * @param seleccion1 primera selección participante
     * @param seleccion2 segunda selección participante
     * @param estadoPartido estado actual del partido
     */
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

    /**
     * Cierra el período para registrar o modificar pronósticos.
     *
     * Si el partido se encuentra en estado ABIERTO,
     * cambia su estado a CERRADO.
     */
    public void cerrarPronosticos() {
        if (estadoPartido == EstadoPartido.ABIERTO) {
            estadoPartido = EstadoPartido.CERRADO;
        }
    }

    /**
     * Marca el partido como finalizado.
     *
     * Cambia el estado actual del partido a FINALIZADO.
     */
    public void finalizarPartido() {
        estadoPartido = EstadoPartido.FINALIZADO;
    }

    // getter del id del partido
    public String getIdPartido() {
        return idPartido;
    }

    // setter del id del partido
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    // getter de la fase del torneo
    public Fase getFaseTorneo() {
        return faseTorneo;
    }

    // setter de la fase del torneo
    public void setFaseTorneo(Fase faseTorneo) {
        this.faseTorneo = faseTorneo;
    }

    // getter de la fecha
    public String getFecha() {
        return fecha;
    }

    // setter de la fecha
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // getter de la hora
    public String getHora() {
        return hora;
    }

    // setter de la hora
    public void setHora(String hora) {
        this.hora = hora;
    }

    // getter del estadio
    public String getEstadio() {
        return estadio;
    }

    // setter del estadio
    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    // getter de la selección 1
    public String getSeleccion1() {
        return seleccion1;
    }

    // setter de la selección 1
    public void setSeleccion1(String seleccion1) {
        this.seleccion1 = seleccion1;
    }

    // getter de la selección 2
    public String getSeleccion2() {
        return seleccion2;
    }

    // setter de la selección 2
    public void setSeleccion2(String seleccion2) {
        this.seleccion2 = seleccion2;
    }

    // getter del estado del partido
    public EstadoPartido getEstadoPartido() {
        return estadoPartido;
    }

    // setter del estado del partido
    public void setEstadoPartido(EstadoPartido estadoPartido) {
        this.estadoPartido = estadoPartido;
    }

    /**
     * Devuelve una representación en texto del partido.
     *
     * @return información principal del partido
     */
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
