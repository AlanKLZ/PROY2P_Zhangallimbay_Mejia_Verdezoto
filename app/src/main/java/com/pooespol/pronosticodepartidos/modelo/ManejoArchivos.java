package com.pooespol.pronosticodepartidos.modelo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.OutputStreamWriter;

/**
 * Clase encargada de manejar la lectura de archivos utilizados por la aplicación.
 * Permite leer y almacenar la información de usuarios, participantes, administradores, partidos y resultados.
 * Los archivos que pueden modificarse durante la ejecución se copian
 * inicialmente desde assets al almacenamiento interno de la aplicación.
 * @author andreaverdezotolung
 */

public class ManejoArchivos {
    //nombre de los archivos utilizados para cargar la información de los usuarios
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_PARTICIPANTES = "participantes.txt";
    private static final String ARCHIVO_ADMINISTRADORES = "administradores.txt";
    private static final String ARCHIVO_PARTIDOS = "partidos.txt";
    private static final String ARCHIVO_RESULTADOS = "resultados.txt";


    /**
     * Busca el cargo asociado a un administrador a partir de su id.
     * La información se obtiene del archivo administradores.txt
     * @param idUsuarioBuscado id del administrador que se desea buscar
     * @param context contexto de la aplicación necesario para acceder a assets
     * @return cargo del administrador si se encuentra, caso contrario null.
     */
 
    private static String buscarCargoAdministrador(String idUsuarioBuscado, Context context ) {

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
     * Busca el puntaje acumulado de un participante a partir de su identificador.
     * La información se obtiene de la copia interna de participantes.txt.
     *
     * @param idUsuarioBuscado identificador del participante cuyo puntaje se desea obtener
     * @param context contexto de la aplicación necesario para acceder al almacenamiento interno
     * @return puntaje acumulado del participante si se encuentra; caso contrario, -1
     */
    private static int buscarPuntaje(String idUsuarioBuscado, Context context ) {
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
     *
     * Si el archivo todavía no existe, copia su contenido inicial desde assets.
     * Si ya existe en el almacenamiento interno, conserva la información
     * almacenada previamente.
     *
     * @param nombreArchivo nombre del archivo que se desea inicializar
     * @param context contexto de la aplicación necesario para acceder a assets
     * y al almacenamiento interno
     */
   private static void inicializarArchivo(String nombreArchivo, Context context) {
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
     * Participante o Administrador según el tipo de usuario registrado.
     *
     * Para los participantes recupera el puntaje acumulado desde participantes.txt.
     * Para los administradores recupera el cargo desde administradores.txt.
     *
     * @param context contexto de la aplicación necesario para acceder a los archivos
     * @return lista de usuarios cargados desde los archivos
     */
    public static ArrayList<Usuario> leerUsuarios(Context context) {
        inicializarArchivo(ARCHIVO_PARTICIPANTES, context);
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
                    int puntaje = buscarPuntaje(idUsuario,context);

                    if (puntaje != -1) {
                        participante.setPuntajeAcumulado(puntaje);
                    }

                    usuarios.add(participante);
                } else if (tipoUsuario.equals("ADMINISTRADOR")) {
                    String cargo = buscarCargoAdministrador(idUsuario,context);

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
     * Guarda los puntajes acumulados de todos los participantes
     * en el archivo participantes.txt del almacenamiento interno
     * El contenido anterior del archivo es reemplazado por los
     * puntajes actualizados recibidos en la lista.
     * @param participantes lista de participantes cuyos puntajes
     *                      se desean guardar
     * @param context contexto de la aplicación necesario para
     *                acceder al almacenamiento interno
     */
    public static void guardarParticipantes(ArrayList<Participante>participantes, Context context){
        try(BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(ARCHIVO_PARTICIPANTES, Context.MODE_PRIVATE)))){
            bf.write("idUsuario;puntaje");
            bf.newLine();
            for(Participante participante : participantes){
                bf.write(participante.getIdUsuario() + ";"+ participante.getPuntajeAcumulado());
                bf.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Lee los partidos almacenados en la copia interna de partidos.txt
     * y crea los objetos Partido correspondientes.
     *
     * @param context contexto de la aplicación necesario para acceder
     * al almacenamiento interno
     * @return lista de partidos cargados desde el archivo
     */
    public static ArrayList<Partido> leerPartidos(Context context) {
        inicializarArchivo(ARCHIVO_PARTIDOS, context);
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
     * Registra un nuevo partido al final del archivo partidos.txt
     * almacenado internamente por la aplicación.
     *
     * @param partido partido que se desea registrar
     * @param context contexto de la aplicación necesario para acceder
     * al almacenamiento interno
     */
    public static void registrarPartido(Partido partido, Context context ) {
        inicializarArchivo(ARCHIVO_PARTIDOS, context);
        try (BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(context.openFileOutput(ARCHIVO_PARTIDOS,Context.MODE_APPEND )))) {
            bw.write(partido.getIdPartido() + ";" + partido.getFaseTorneo() + ";" + partido.getFecha() + ";" + partido.getHora() + ";" + partido.getEstadio() + ";" + partido.getSeleccion1() + ";" + partido.getSeleccion2() + ";" + partido.getEstadoPartido());
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Guarda la información actualizada de todos los partidos
     * en el archivo partidos.txt del almacenamiento interno.
     * El contenido anterior del archivo se reemplaza por los datos
     * actuales de los partidos recibidos
     * @param partidos lista de partidos por guardar
     * @param context contexto de la aplicación
     */
    public static void guardarPartidos(ArrayList<Partido>partidos, Context context){
        try(BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(ARCHIVO_PARTIDOS, Context.MODE_PRIVATE)))){
            bf.write("idPartido;fase;fecha;horaUTC;estadio;seleccion1;seleccion2;estado");
            bf.newLine();
            for (Partido p: partidos){
                bf.write(p.getIdPartido() + ";" + p.getFaseTorneo() + ";" + p.getFecha() + ";" + p.getHora() + ";" + p.getEstadio() + ";" + p.getSeleccion1() + ";" + p.getSeleccion2() + ";" + p.getEstadoPartido());
                bf.newLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Registra un nuevo resultado oficial al final del archivo resultados.txt
     * almacenado internamente por la aplicación.
     *
     * @param resultado resultado que se desea registrar
     * @param context contexto de la aplicación necesario para acceder
     * al almacenamiento interno
     */
    public static void registrarResultado(Resultado resultado, Context context ) {
        inicializarArchivo(ARCHIVO_RESULTADOS, context);
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(ARCHIVO_RESULTADOS,Context.MODE_APPEND)))
        ) {
            bw.write(resultado.getIdResultado() + ";" + resultado.getIdPartido() + ";" + resultado.getGolesSeleccion1() + ";" + resultado.getGolesSeleccion2());
            bw.newLine();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca y recupera el resultado oficial asociado a un partido.
     * La búsqueda se realiza en la copia interna de resultados.txt.
     *
     * @param idPartido identificador del partido cuyo resultado se desea obtener
     * @param context contexto de la aplicación necesario para acceder
     *                al almacenamiento interno
     * @return resultado correspondiente al partido si existe; caso contrario, null
     */
    public static Resultado obtenerResultado(String idPartido, Context context) {
        inicializarArchivo(ARCHIVO_RESULTADOS, context);
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

    /**
     * Lee los resultados oficiales almacenados
     * en la copia interna del archivo resultados.txt
     * y crea los objetos correspondientes
     * @param context contexto de la aplicación necesario
     *                para acceder al almacenamiento interno
     * @return lista de resultados
     */
    public static ArrayList<Resultado> leerResultados(Context context){
        inicializarArchivo(ARCHIVO_RESULTADOS, context);
        ArrayList<Resultado>resultados = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(context.openFileInput(ARCHIVO_RESULTADOS)))){
            String linea;
            bf.readLine();
            while((linea = bf.readLine())!= null){
                String[]datos = linea.split(";");
                String idResultado = datos[0];
                String idPartido = datos[1];
                int golesSeleccion1 = Integer.parseInt(datos[2]);
                int golesSeleccion2 = Integer.parseInt(datos[3]);
                Resultado resultado = Resultado.desdeArchivo(idResultado, idPartido, golesSeleccion1, golesSeleccion2);
                resultados.add(resultado);
            }
        }catch (IOException i){
            i.printStackTrace();
        }
        return resultados;
    }
    private static String obtenerNombreArchivoPronosticos(String idParticipante, Fase fase) {
        return "pronostico_" + idParticipante + "_" + fase.name().toLowerCase() + ".dat";
    }
    /**
     * Guarda la lista de pronósticos registrados en el archivo
     * pronosticos.dat del almacenamiento interno
     * Los objetos Pronostico son almacenados mediante serialización
     * y el contenido anterior del archivo es reemplazado por la lista recibida
     * @param pronosticos lista de pronósticos
     * @param context contexto de la aplicación
     */
    public static void guardarPronosticos(ArrayList<Pronostico> pronosticos, String idParticipante, Fase fase, Context context){
        String nombreArchivo = obtenerNombreArchivoPronosticos(idParticipante, fase);
        try(ObjectOutputStream salida = new ObjectOutputStream(context.openFileOutput(nombreArchivo, Context.MODE_PRIVATE))){
            salida.writeObject(pronosticos);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Lee la lista de pronósticos del almacenamiento interno.
     * Si el archivo todavía no existe, retorna lista vacía
     * @param context contexto de la aplicación
     * @return lista de pronósticos almacenados
     */
    public static ArrayList<Pronostico> leerPronosticos(String idParticipante, Fase fase, Context context){
        ArrayList<Pronostico> pronosticos = new ArrayList<>();
        String nombreArchivo = obtenerNombreArchivoPronosticos(idParticipante, fase);
        try(ObjectInputStream entrada = new ObjectInputStream(context.openFileInput(nombreArchivo))){
            pronosticos = (ArrayList<Pronostico>) entrada.readObject();
        }catch (FileNotFoundException e){
            //El archivo no existe, se retorna lista vacía
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return pronosticos;
    }
    public static ArrayList<Pronostico> leerPronosticosParticipante(String idParticipante, Context context) {
        ArrayList<Pronostico> todos = new ArrayList<>();
        ArrayList<Fase> fases = new ArrayList<>();
        fases.add(Fase.FASE_DE_GRUPOS);
        fases.add(Fase.DIECISEISAVOS_DE_FINAL);
        fases.add(Fase.OCTAVOS_DE_FINAL);
        fases.add(Fase.CUARTOS_DE_FINAL);
        fases.add(Fase.SEMIFINALES);
        fases.add(Fase.TERCER_LUGAR);
        fases.add(Fase.FINAL);

        for (Fase fase : fases) {
            ArrayList<Pronostico> pronosticosFase = leerPronosticos(idParticipante, fase, context);
            for (Pronostico pronostico : pronosticosFase) {
                todos.add(pronostico);
            }
        }

        return todos;
    }
    public static ArrayList<Pronostico> leerTodosPronosticos(Context context) {
        ArrayList<Pronostico> todos = new ArrayList<>();
        ArrayList<Usuario> usuarios = leerUsuarios(context);
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Participante) {
                ArrayList<Pronostico> pronosticosParticipante = leerPronosticosParticipante(usuario.getIdUsuario(), context);
                for (Pronostico pronostico : pronosticosParticipante) {
                    todos.add(pronostico);
                }
            }
        }

        return todos;
    }

    /**
     *
     * @param pronostico
     * @param context
     */
    public static void registrarPronostico(Pronostico pronostico, Fase fase, Context context){
        ArrayList<Pronostico>pronosticos = leerPronosticos(pronostico.getIdParticipante(), fase, context);
        boolean encontrado = false;
        for(int i=0; i<pronosticos.size(); i++){
            Pronostico pronosticoGuardado = pronosticos.get(i);
            if (pronosticoGuardado.getIdPronostico().equals(pronostico.getIdPronostico())){
                pronosticos.set(i, pronostico);
                encontrado= true;
            }
        }
        if(!encontrado){
            pronosticos.add(pronostico);
        }
        guardarPronosticos(pronosticos, pronostico.getIdParticipante(), fase, context);
    }
}


    




