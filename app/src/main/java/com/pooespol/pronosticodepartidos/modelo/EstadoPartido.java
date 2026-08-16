package com.pooespol.pronosticodepartidos.modelo;

/**
 * Enum que representa los posibles estados de un partido.
 *
 * ABIERTO: los participantes pueden registrar o modificar pronósticos.
 * CERRADO: ya no se pueden modificar pronósticos.
 * FINALIZADO: el partido terminó y ya puede tener un resultado oficial.
 *
 * @author Naomi
 */
public enum EstadoPartido {
    ABIERTO,
    CERRADO,
    FINALIZADO
}
