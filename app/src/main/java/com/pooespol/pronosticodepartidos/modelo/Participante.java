package com.pooespol.pronosticodepartidos.modelo;

/**
 * @author Alan
 */
public class Participante extends Usuario implements Comparable<Participante>{
    private int puntajeAcumulado;

    /**
     * Constructor del participante
     * @param idUsuario identificador del usuario
     * @param nombreDeUsuario credencial para ingresar a la app
     * @param contraseña contraseña del usario para ingresar a la app
     * @param nombreCompleto nombre del usuario
     */
    public Participante(String idUsuario, String nombreDeUsuario, String contraseña, String nombreCompleto){
        super(idUsuario, nombreDeUsuario, contraseña, nombreCompleto);
        this.tipoUsuario = TipoUsuario.PARTICIPANTE;
        this.puntajeAcumulado = 0;
    }

    //getter del puntaje
    public int getPuntajeAcumulado() {
        return puntajeAcumulado;
    }

    //Pendiente implementar
    @Override
    public int compareTo(Participante participante) {
        return 0;
    }
}
