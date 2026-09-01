package com.pooespol.pronosticodepartidos.modelo;

import java.io.Serializable;

/**
 * Representa el resultado oficial de un partido.
 *
 * Contiene el identificador del resultado, el partido
 * correspondiente y los goles obtenidos por cada selección.
 *
 * @author Naomi
 */
public class Resultado implements Serializable {

    private static int contadorResultados = 1;

    private String idResultado;
    private String idPartido;
    private int golesSeleccion1;
    private int golesSeleccion2;

    /**
     * Constructor de la clase Resultado.
     * El identificador se genera automáticamente.
     *
     * @param idPartido identificador del partido al que pertenece
     * @param golesSeleccion1 goles oficiales de la selección 1
     * @param golesSeleccion2 goles oficiales de la selección 2
     */
    public Resultado(String idPartido,
                     int golesSeleccion1,
                     int golesSeleccion2) {

        this.idResultado = String.valueOf(contadorResultados++);
        this.idPartido = idPartido;
        this.golesSeleccion1 = golesSeleccion1;
        this.golesSeleccion2 = golesSeleccion2;
    }

    /**
     * Constructor utilizado para recuperar un resultado
     * previamente almacenado en el archivo.
     *
     * @param idResultado identificador almacenado del resultado
     * @param idPartido identificador del partido
     * @param golesSeleccion1 goles de la selección 1
     * @param golesSeleccion2 goles de la selección 2
     */
    private Resultado(String idResultado,String idPartido,int golesSeleccion1,int golesSeleccion2) {

        this.idResultado = idResultado;
        this.idPartido = idPartido;
        this.golesSeleccion1 = golesSeleccion1;
        this.golesSeleccion2 = golesSeleccion2;
    }

    /**
     * Crea un resultado a partir de la información
     * almacenada en resultados.txt.
     *
     * @param idResultado identificador almacenado del resultado
     * @param idPartido identificador del partido
     * @param golesSeleccion1 goles de la selección 1
     * @param golesSeleccion2 goles de la selección 2
     * @return resultado recuperado del archivo
     */
    public static Resultado desdeArchivo(String idResultado, String idPartido,  int golesSeleccion1,  int golesSeleccion2) {

        return new Resultado(idResultado,idPartido,golesSeleccion1, golesSeleccion2
        );
    }

    // getter del id del resultado
    public String getIdResultado() {
        return idResultado;
    }

    // setter del id del resultado
    public void setIdResultado(String idResultado) {
        this.idResultado = idResultado;
    }

    // getter del id del partido
    public String getIdPartido() {
        return idPartido;
    }

    // setter del id del partido
    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    // getter de los goles de la selección 1
    public int getGolesSeleccion1() {
        return golesSeleccion1;
    }

    // setter de los goles de la selección 1
    public void setGolesSeleccion1(int golesSeleccion1) {
        this.golesSeleccion1 = golesSeleccion1;
    }

    // getter de los goles de la selección 2
    public int getGolesSeleccion2() {
        return golesSeleccion2;
    }

    // setter de los goles de la selección 2
    public void setGolesSeleccion2(int golesSeleccion2) {
        this.golesSeleccion2 = golesSeleccion2;
    }

    /**
     * Devuelve la información del resultado en formato de texto.
     *
     * @return información del resultado oficial
     */
    @Override
    public String toString() {
        return "Resultado{" +
                "idResultado='" + idResultado + '\'' +
                ", idPartido='" + idPartido + '\'' +
                ", golesSeleccion1=" + golesSeleccion1 +
                ", golesSeleccion2=" + golesSeleccion2 +
                '}';
    }
}
