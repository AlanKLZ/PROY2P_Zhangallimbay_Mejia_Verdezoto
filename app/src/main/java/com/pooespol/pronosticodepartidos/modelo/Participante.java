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
     * Registra un pronóstico para un partido.
     *
     * @param partido partido sobre el cual se realizará el pronóstico
     * @param golesSeleccion1 goles pronosticados para la selección 1
     * @param golesSeleccion2 goles pronosticados para la selección 2
     * @return pronóstico registrado
     * @throws PronosticoFueraDeTiempoException si el partido no está abierto
     * @throws DatosIncompletosException si los datos ingresados no son válidos
     * @author Naomi
     */
    public Pronostico registrarPronostico(
            Partido partido,
            int golesSeleccion1,
            int golesSeleccion2)
            throws PronosticoFueraDeTiempoException, DatosIncompletosException {

        if (partido == null) {
            throw new DatosIncompletosException(
                    "No se han ingresado todos los datos necesarios para registrar el pronóstico."
            );
        }

        if (golesSeleccion1 < 0 || golesSeleccion2 < 0) {
            throw new DatosIncompletosException(
                    "Los goles deben ser números enteros mayores o iguales a cero."
            );
        }

        if (partido.getEstadoPartido() != EstadoPartido.ABIERTO) {
            throw new PronosticoFueraDeTiempoException(
                    "El período para registrar pronósticos de este partido ya ha finalizado."
            );
        }

        String idPronostico =
                this.getIdUsuario() + "_" + partido.getIdPartido();

        return new Pronostico(
                idPronostico,
                this.getIdUsuario(),
                partido.getIdPartido(),
                golesSeleccion1,
                golesSeleccion2,
                0
        );
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
