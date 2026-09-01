package com.pooespol.pronosticodepartidos.modelo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

/**
 * Clase encargada de manejar la lectura de archivos relacionados
 * con los usuarios de la aplicación
 *
 */

public class ManejoArchivos {
    //nombre de los archivos utilizados para cargar la información de los usuarios
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_PARTICIPANTES = "participantes.txt";
    private static final String ARCHIVO_ADMINISTRADORES = "administradores.txt";
    private static final String ARCHIVO_PARTIDOS = "partidos.txt";
    private static final String ARCHIVO_RESULTADOS = "resultados.txt";
    
   

     private final Context context;

     /**
     * Constructor de ManejoArchivos.
     *
     * @param context contexto de la aplicación
     */
    public ManejoArchivos(Context context) {
        this.context = context.getApplicationContext();

        inicializarArchivo(ARCHIVO_PARTICIPANTES);
        inicializarArchivo(ARCHIVO_PARTIDOS);
        inicializarArchivo(ARCHIVO_RESULTADOS);
    }

    /**
     * Busca el cargo asociado a un administrador.
     *
     * @param idUsuarioBuscado identificador del administrador
     * @return cargo encontrado o null
     */
 
    private String buscarCargoAdministrador(String idUsuarioBuscado) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(ARCHIVO_ADMINISTRADORES)))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String idUsuario = datos[0];
                String cargo = datos[1];
                if (idUsuario.equals(idUsuarioBuscado)) {
                    return cargo;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
 * Busca el puntaje acumulado de un participante a partir de su id de usuario.
 * La información se obtiene de la copia interna de participantes.txt.
 *
 * @param idUsuarioBuscado id del participante cuyo puntaje se desea obtener
 * @return puntaje acumulado del participante si se encuentra, caso contrario -1
 */
    private int buscarPuntaje(String idUsuarioBuscado) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(ARCHIVO_PARTICIPANTES)))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String idUsuario = datos[0];
                int puntaje = Integer.parseInt(datos[1]);
                if (idUsuario.equals(idUsuarioBuscado)) {
                    return puntaje;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
 * Inicializa un archivo en el almacenamiento interno de la aplicación.
 * Si el archivo todavía no existe, copia su contenido inicial desde assets.
 * Si ya existe, conserva la información almacenada previamente.
 *
 * @param nombreArchivo nombre del archivo que se desea inicializar
 */
   private void inicializarArchivo(String nombreArchivo) {
    File archivo = new File(context.getFilesDir(), nombreArchivo);

    if (!archivo.exists()) {
        try (InputStream entrada = context.getAssets().open(nombreArchivo);
             OutputStream salida = context.openFileOutput(nombreArchivo,Context.MODE_PRIVATE)) {
            byte[] buffer = new byte[1024];
            int cantidadBytes;

            while ((cantidadBytes = entrada.read(buffer)) != -1) {
                salida.write(buffer, 0, cantidadBytes);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    /**
 * Lee los usuarios almacenados en usuarios.txt y crea los objetos
 * Participante o Administrador según el tipo de usuario indicado.
 * Para los participantes recupera sus puntajes de participantes.txt.
 * Para los administradores recupera sus cargos de administradores.txt.
 *
 * @return lista de usuarios cargados desde los archivos
 */

    public ArrayList<Usuario> leerUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try ( BufferedReader br =new BufferedReader(new InputStreamReader(context.getAssets().open(ARCHIVO_USUARIOS)) )
        ) {
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String idUsuario = datos[0];
                String nombreUsuario = datos[1];
                String contraseña = datos[2];
                String nombreCompleto = datos[3];
                String tipoUsuario = datos[4];

                if (tipoUsuario.equals("PARTICIPANTE")) {
                    Participante participante =new Participante(idUsuario, nombreUsuario,contraseña,nombreCompleto );
                    int puntaje = buscarPuntaje(idUsuario);

                    if (puntaje != -1) {
                        participante.setPuntajeAcumulado(puntaje);
                    }

                    usuarios.add(participante);
                } else if (tipoUsuario.equals("ADMINISTRADOR")) {
                    String cargo = buscarCargoAdministrador(idUsuario);

                    if (cargo != null) {
                        Administrador administrador =new Administrador(idUsuario,nombreUsuario,contraseña,nombreCompleto,cargo );
                        usuarios.add(administrador);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Lee los partidos almacenados en partidos.txt.
     *
     * @return lista de partidos
     */
    public ArrayList<Partido> leerPartidos() {
        ArrayList<Partido> partidos = new ArrayList<>();
        try (BufferedReader br =new BufferedReader(new InputStreamReader(context.openFileInput(ARCHIVO_PARTIDOS )) )
        ) {
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String idPartido = datos[0];
                Fase fase = Fase.valueOf(datos[1]);
                String fecha = datos[2];
                String hora = datos[3];
                String estadio = datos[4];
                String seleccion1 = datos[5];
                String seleccion2 = datos[6];
                EstadoPartido estado = EstadoPartido.valueOf(datos[7]);

                Partido partido = new Partido(idPartido,fase,fecha,hora,estadio,seleccion1,seleccion2, estado);
                partidos.add(partido);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return partidos;
    }


     /**
     * Registra un partido al final de partidos.txt.
     *
     * @param partido partido que se desea registrar
     */
    public void registrarPartido(Partido partido) {
        try (BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(context.openFileOutput(ARCHIVO_PARTIDOS,Context.MODE_APPEND )))) {
            bw.newLine();
            bw.write(partido.getIdPartido() + ";" + partido.getFaseTorneo() + ";" + partido.getFecha() + ";" + partido.getHora() + ";" + partido.getEstadio() + ";" + partido.getSeleccion1() + ";" + partido.getSeleccion2() + ";" + partido.getEstadoPartido()
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registra un resultado en resultados.txt.
     *
     * @param resultado resultado que se desea registrar
     */
    public void registrarResultado(Resultado resultado) {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(ARCHIVO_RESULTADOS,Context.MODE_APPEND)))
        ) {

            bw.newLine();
            bw.write(resultado.getIdResultado() + ";" + resultado.getIdPartido() + ";" + resultado.getGolesSeleccion1() + ";" + resultado.getGolesSeleccion2()
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el resultado correspondiente a un partido.
     *
     * @param idPartido identificador del partido
     * @return resultado encontrado o null si no existe
     */
    public Resultado obtenerResultado(String idPartido) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(ARCHIVO_RESULTADOS)))) {
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String idResultado = datos[0];
                String idPartidoArchivo = datos[1];
                int golesSeleccion1 = Integer.parseInt(datos[2]);
                int golesSeleccion2 = Integer.parseInt(datos[3]);

                if (idPartidoArchivo.equals(idPartido)) {
                    return Resultado.desdeArchivo(idResultado, idPartidoArchivo,golesSeleccion1, golesSeleccion2
                    );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}


    




