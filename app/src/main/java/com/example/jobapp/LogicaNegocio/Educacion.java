package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Educacion implements Serializable {

    private String usuario;
    private String institucion;
    private String carrera;
    private String titulo;
    private String anno;

    public Educacion(){
        this.usuario = "";
        this.institucion = "";
        this.carrera = "";
        this.titulo = "";
        this.anno = "";
    }

    public Educacion(String usuario, String institucion, String carrera, String titulo, String anno){
        this.usuario = usuario;
        this.institucion = institucion;
        this.carrera = carrera;
        this.titulo = titulo;
        this.anno = anno;
    }

    public String getUsuario(){  return usuario; }

    public void setUsuario(String usuario){ this.usuario = usuario; }

    public String getInstitucion(){  return institucion; }

    public void setInstitucion(String carrera){ this.institucion = institucion; }

    public String getCarrera(){  return carrera; }

    public void setCarrera(String carrera){ this.carrera = carrera; }

    public String getTitulo(){  return titulo; }

    public void setTitulo(String titulo){ this.titulo = titulo; }

    public String getAnno(){  return anno; }

    public void setAnno(String anno){ this.anno = anno; }

    @Override
    public String toString() {
        return "Educacion { " +
                "cedula='" + usuario + '\'' +
                ", institucion='" + institucion + '\'' +
                ", carrera=" + carrera +
                ", titulo='" + titulo + '\'' +
                ", año='" + anno + '\'' + '}';
    }

}
