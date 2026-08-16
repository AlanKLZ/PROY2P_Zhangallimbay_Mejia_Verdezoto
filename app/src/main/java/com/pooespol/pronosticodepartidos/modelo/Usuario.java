package com.pooespol.pronosticodepartidos.modelo;

import java.io.Serializable;

/**
 * Clase padre del que se heredarán los usuarios
 * @author Alan
 */
public abstract class Usuario implements Serializable {
    protected String idUsuario;
    protected String nombreDeUsuario;
    protected String contraseña;
    protected String nombreCompleto;
    protected TipoUsuario tipoUsuario;

    /**
     * Constructor
     * @param idUsuario
     * @param nombreDeUsuario
     * @param contraseña
     * @param nombreCompleto
     */
    public Usuario(String idUsuario, String nombreDeUsuario, String contraseña, String nombreCompleto){
        this.idUsuario = idUsuario;
        this.nombreDeUsuario = nombreDeUsuario;
        this.contraseña = contraseña;
        this.nombreCompleto = nombreCompleto;
    }
    //Getters
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    @Override
    public String toString(){
        return "Usuario: "+nombreDeUsuario;
    }
}