package com.pooespol.pronosticodepartidos.modelo;

/**
 * Excepcion si existe algun dato faltante en el registro del pronóstico
 */
public class DatosIncompletosException extends Exception {
    public DatosIncompletosException(String message) {
        super(message);
    }
}
