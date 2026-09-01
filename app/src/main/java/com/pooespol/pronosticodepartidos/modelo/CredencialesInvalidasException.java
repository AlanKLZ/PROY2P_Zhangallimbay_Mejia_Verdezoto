package com.pooespol.pronosticodepartidos.modelo;

/**
 * Excepción verificada que se lanza cuando los datos
 * que ingresa el usuario no son los correctos
 * @author andreaverdezotolung
 */
public class CredencialesInvalidasException extends Exception {
    /**
     * Constructor de la excepción.
     *
     * @param message que describe la causa del error
     */
    public CredencialesInvalidasException(String message) {
        super(message);
    }
}
