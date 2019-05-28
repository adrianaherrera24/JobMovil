package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Skill implements Serializable {

    private int id;
    private String usuario;
    private String nombre;
    private String descripcion;

    public Skill(){
        this.id = 0;
        this.usuario = "";
        this.nombre = "";
        this.descripcion = "";
    }

    public Skill(String usuario, String nombre, String descripcion){
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Skill(int id, String usuario, String nombre, String descripcion){
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId(){  return id; }

    public void setId(int id){ this.id = id; }

    public String getUsuario(){  return usuario; }

    public void setUsuario(String usuario){ this.usuario = usuario; }

    public String getNombre(){  return nombre; }

    public void setNombre(String nombre){ this.nombre = nombre; }

    public String getDescripcion(){  return descripcion; }

    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
