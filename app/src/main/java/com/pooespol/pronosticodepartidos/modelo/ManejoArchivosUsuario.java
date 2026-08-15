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

/**
 * Clase encargada de manejar la lectura de archivos relacionados
 * con los usuarios de la aplicación
 * Permite obtener la información de usuarios, participantes y administradores
 * correspondientes.
 */

public class ManejoArchivosUsuario {
    //nombre de los archivos utilizados para cargar la información de los usuarios
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_PARTICIPANTES = "participantes.txt";
    private static final String ARCHIVO_ADMINISTRADORES = "administradores.txt";

    /**
     * Busca el cargo asociado a un administrador a partir de su id.
     * La información se obtiene del archivo administradores.txt
     * @param context contexto de la aplicación necesario para acceder a assets
     * @param idUsuarioBuscado id del administrador que se quiere obtener
     * @return cargo del administrador si se encuentra, caso contrario null.
     */
    private static String buscarCargoAdministrador(Context context, String idUsuarioBuscado) {

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
     * La información se obtiene de la copia interna de participantes.txt
     * @param context contexto de la aplicación necesario para acceder a assets
     * @param idUsuarioBuscado id del participante cuyo puntaje se desea obtener
     * @return puntaje acumulado del participante si se encuentra, caso contrario -1
     */
    private static int buscarPuntaje(Context context, String idUsuarioBuscado) {
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
     * Inicializa el archivo participantes.txt en el almacenamiento interno
     * de la aplicación para manejar un flujo de entrada/salida
     * Si el archivo todavía no existe, copia su contenido inicial desde assets
     * Si ya existe, conserva la información almacenada previamente
     * @param context contexto de la aplicación necesario para acceder a assets y al
     *                almacenamiento interno
     */
    public static void inicializarArchivoParticipantes(Context context) {
        File archivo = new File(context.getFilesDir(), ARCHIVO_PARTICIPANTES);
        if (!archivo.exists()) {
            try (InputStream entrada = context.getAssets().open(ARCHIVO_PARTICIPANTES); OutputStream salida = context.openFileOutput(ARCHIVO_PARTICIPANTES, Context.MODE_PRIVATE)) {
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
     * Lee los usuarios almacenados en usuarios.txt y crea los objetos Participante o Administrador
     * según el tipo de usuario indicado
     * Para los participantes recupera sus puntajes de participantes.txt
     * Para los administradores recupera sus cargos de administradores.txt
     * @param context contexto de la aplicación necesario para acceder a assets
     * @return retorna una lista de usuarios cargados desde los archivos
     */
    public static ArrayList<Usuario> leerUsuarios(Context context) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        inicializarArchivoParticipantes(context);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(ARCHIVO_USUARIOS)))) {
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String idUsuario = datos[0];
                String nombreUsuario = datos[1];
                String contraseña = datos[2];
                String nombreCompleto = datos[3];
                String tipoUsuario = datos[4];
                if (tipoUsuario.equals("PARTICIPANTE")){
                    Participante participante = new Participante(idUsuario, nombreUsuario, contraseña, nombreCompleto);
                    int puntaje = buscarPuntaje(context, idUsuario);
                    if (puntaje != -1){
                        participante.setPuntajeAcumulado(puntaje);
                    }
                    usuarios.add(participante);
                }else if (tipoUsuario.equals("ADMINISTRADOR")){
                    String cargo = buscarCargoAdministrador(context, idUsuario);
                    if (cargo != null){
                        Administrador administrador = new Administrador(idUsuario, nombreUsuario, contraseña, nombreCompleto, cargo);
                        usuarios.add(administrador);
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return usuarios;
    }

    }




