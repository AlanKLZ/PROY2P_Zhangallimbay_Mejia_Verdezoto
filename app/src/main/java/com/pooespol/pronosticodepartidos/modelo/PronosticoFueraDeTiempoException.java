package com.pooespol.pronosticodepartidos.modelo;

/**
 * Excepción verificada que se lanza cuando un participante
 * intenta registrar o modificar un pronóstico fuera del
 * período permitido.
 *
 * Esta situación ocurre cuando el partido se encuentra
 * en estado CERRADO o FINALIZADO.
 *
 * @author Naomi
 */
public class PronosticoFueraDeTiempoException extends Exception {

    /**
     * Constructor de la excepción.
     *
     * @param mensaje mensaje que describe la causa del error
     */
    public PronosticoFueraDeTiempoException(String mensaje) {
        super(mensaje);
    }
}
