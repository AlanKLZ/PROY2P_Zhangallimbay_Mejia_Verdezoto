package com.pooespol.pronosticodepartidos.modelo;

/**
 * Excepción verificada que se lanza cuando los datos
 * necesarios para registrar un pronóstico o resultado
 * están incompletos o son inválidos.
 *
 * @author Naomi
 */
public class DatosIncompletosException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje mensaje que describe la causa del error
     */

    public DatosIncompletosException(String mensaje) {
        super(mensaje);
    }
}
