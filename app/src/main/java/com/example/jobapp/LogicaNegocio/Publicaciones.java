package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Publicaciones implements Serializable {

    private String titulo_publicacion;
    private String descripcion_publicacion;
    private String usuario;

    public Publicaciones(){
        this.titulo_publicacion = "";
        this.descripcion_publicacion = "";
    }

    public Publicaciones(String titulo_publicacion, String descripcion_publicacion, String usuario){
        this.titulo_publicacion = titulo_publicacion;
        this.descripcion_publicacion = descripcion_publicacion;
        this.usuario = usuario;
    }

    public void setTitulo_publicacion(String titulo_publicacion){
        this.titulo_publicacion = titulo_publicacion;
    }

    public String getTitulo_publicacion(){
        return titulo_publicacion;
    }

    public void setDescripcion_publicacion(String descripcion_publicacion) {
        this.descripcion_publicacion = descripcion_publicacion;
    }

    public String getDescripcion_publicacion() {
        return descripcion_publicacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
