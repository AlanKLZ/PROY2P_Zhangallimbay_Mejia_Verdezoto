package com.pooespol.pronosticodepartidos.modelo;

import java.util.ArrayList;

/**
 * Clase que representa el administrador
 * @author Alan
 * @author andreaverdezotolung
 */
public class Administrador extends Usuario{
    private String cargo;

    /**
     * Constructor del Administrador
     * @param idUsuario identificador del usuario
     * @param nombreDeUsuario credencial para ingresar a la app
     * @param contraseña contraseña del usario para ingresar a la app
     * @param nombreCompleto nombre del usuario
     * @param cargo cargo del administrador
     */
    public Administrador(String idUsuario, String nombreDeUsuario, String contraseña, String nombreCompleto, String cargo){
        super(idUsuario, nombreDeUsuario, contraseña, nombreCompleto);
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;
        this.cargo = cargo;
    }

    //getter del cargo
    public String getCargo() {
        return cargo;
    }

    /**
     * Metodo que permite calcular los puntos
     * obtenidos por cada pronóstico empleando
     * las reglas establecidas
     * @param p una instancia de la clase Pronostico
     * @param r una instancia de la clase Resultado
     * @return un entero que representa el puntaje
     */

    private int calcularPuntos(Pronostico p, Resultado r){
        int pronostico1 = p.getGolesSeleccion1();
        int pronostico2= p.getGolesSeleccion2();
        int resultado1 = r.getGolesSeleccion1();
        int resultado2= r.getGolesSeleccion2();

        if ((pronostico1 == resultado1)&& (pronostico2 == resultado2)){
            return 3;
        }
        if (pronostico1 == pronostico2 && resultado1==resultado2){
            return 2;
        }
        if (pronostico1 > pronostico2 && resultado1 > resultado2){
            int diferenciaPronostico = pronostico1 - pronostico2;
            int diferenciaResultado = resultado1 - resultado2;
            if (diferenciaResultado == diferenciaPronostico){
                return 2;
            }
            return 1;
        }
        if (pronostico2 > pronostico1 && resultado2 > resultado1){
            int diferenciaPronostico = pronostico2 - pronostico1;
            int diferenciaResultado = resultado2 - resultado1;
            if (diferenciaResultado == diferenciaPronostico){
                return 2;
            }
            return 1;
        }
        return 0;
    }

    /**
     * El Administrador revisa los resultados y actualiza el atributo PuntajeAcumulado
     * se recorre todos los pronosticos con sus resultados para cuando el partido se
     * encuentre finalizado, se efectua el cambio a los participantes
     * @param participantes lista de participantes
     * @param pronosticos lista de pronosticos
     * @param partidos lista de partidos
     * @param resultados lista de resultados
     */
    public void actualizarPuntajes(ArrayList<Participante> participantes, ArrayList<Pronostico>pronosticos, ArrayList<Partido> partidos, ArrayList<Resultado>resultados){
        for (Participante participante: participantes){
            participante.setPuntajeAcumulado(0);
        }
        for (Pronostico pronostico: pronosticos){
            for (Partido partido: partidos){
                if(partido.getIdPartido().equals(pronostico.getIdPartido())){
                    if (partido.getEstadoPartido()== EstadoPartido.FINALIZADO){
                        for (Resultado resultado: resultados){
                            if (resultado.getIdPartido().equals(partido.getIdPartido())){
                                int puntos = calcularPuntos(pronostico, resultado);
                                pronostico.setPuntosObtenidos(puntos);
                                for (Participante participante: participantes){
                                    if (participante.getIdUsuario().equals(pronostico.getIdParticipante())){
                                        int puntajeActual = participante.getPuntajeAcumulado();
                                        participante.setPuntajeAcumulado(puntajeActual + puntos);
                                    }
                                }

                            }
                        }
                    }
                }

            }
        }
    }

}
