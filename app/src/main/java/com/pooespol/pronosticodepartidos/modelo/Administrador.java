package com.pooespol.pronosticodepartidos.modelo;

/**
 * @author Alan
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
}
