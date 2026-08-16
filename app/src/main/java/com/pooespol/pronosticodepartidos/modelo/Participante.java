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
    //setter
    public void setPuntajeAcumulado(int puntajeAcumulado) {
        this.puntajeAcumulado = puntajeAcumulado;
    }

    /**
     *
     * @param otroParticipante the object to be compared.
     * @return Retorna un numero, para luego ordenarlos.
     */
    @Override
    public int compareTo(Participante otroParticipante) {
        int comparacion = Integer.compare(
                otroParticipante.getPuntajeAcumulado(),
                this.puntajeAcumulado
        );
        if (comparacion != 0){
            return comparacion;
        }
        return this.getNombreCompleto().compareToIgnoreCase(otroParticipante.getNombreCompleto());
    }
}
