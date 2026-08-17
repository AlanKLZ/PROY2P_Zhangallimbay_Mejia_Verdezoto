package com.pooespol.pronosticodepartidos.modelo;

import java.io.Serializable;

/**
 * Representa el pronóstico realizado por un participante
 * para un partido del Mundial FIFA 2026.
 *
 * Los objetos de esta clase pueden ser serializados para
 * almacenarse en los archivos correspondientes de cada usuario.
 *
 * @author Naomi
 */
public class Pronostico implements Serializable {

    private String idPronostico;
    private String idParticipante;
    private String idPartido;
    private int golesSeleccion1;
    private int golesSeleccion2;
    private int puntosObtenidos;

    /**
     * Constructor de la clase Pronostico.
     *
     * @param idPronostico identificador único del pronóstico
     * @param idParticipante identificador del participante que realizó el pronóstico
     * @param idPartido identificador del partido pronosticado
     * @param golesSeleccion1 goles pronosticados para la selección 1
     * @param golesSeleccion2 goles pronosticados para la selección 2
     * @param puntosObtenidos puntos obtenidos por el pronóstico
     */
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

    // getter del id del pronóstico
    public String getIdPronostico() {
        return idPronostico;
    }

    // setter del id del pronóstico
    public void setIdPronostico(String idPronostico) {
        this.idPronostico = idPronostico;
    }

    // getter del id del participante
    public String getIdParticipante() {
        return idParticipante;
    }

    // setter del id del participante
    public void setIdParticipante(String idParticipante) {
        this.idParticipante = idParticipante;
    }

    // getter del id del partido
    public String getIdPartido() {
        return idPartido;
    }

    // setter del id del partido
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    // getter de los goles pronosticados para la selección 1
    public int getGolesSeleccion1() {
        return golesSeleccion1;
    }

    // setter de los goles pronosticados para la selección 1
    public void setGolesSeleccion1(int golesSeleccion1) {
        this.golesSeleccion1 = golesSeleccion1;
    }

    // getter de los goles pronosticados para la selección 2
    public int getGolesSeleccion2() {
        return golesSeleccion2;
    }

    // setter de los goles pronosticados para la selección 2
    public void setGolesSeleccion2(int golesSeleccion2) {
        this.golesSeleccion2 = golesSeleccion2;
    }

    // getter de los puntos obtenidos
    public int getPuntosObtenidos() {
        return puntosObtenidos;
    }

    // setter de los puntos obtenidos
    public void setPuntosObtenidos(int puntosObtenidos) {
        this.puntosObtenidos = puntosObtenidos;
    }

    /**
     * Devuelve una representación en texto del pronóstico.
     *
     * @return información completa del pronóstico
     */
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
