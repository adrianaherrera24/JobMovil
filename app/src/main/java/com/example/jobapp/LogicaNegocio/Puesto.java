package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Puesto implements Serializable {

    String id;
    String nombre;
    String empresa_id;
    String area;
    String descripcion;
    String requisitos;
    String horario;
    String nombre_empresa;
    String locacion_empresa;
    int vigente;

    public Puesto(){
        id= new String();
        nombre = new String();
        empresa_id = new String();
        area = new String();
        descripcion = new String();
        requisitos = new String();
        horario = new String();
        vigente = 0;
        nombre_empresa = new String();
        locacion_empresa = new String();
    }
    public Puesto(String nombre_empresa, String nombre,String area,String locacion_empresa, String descripcion, String requisitos, String horario){
        this.nombre = nombre;
        this.area = area;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.horario = horario;
        this.nombre_empresa = nombre_empresa;
        this.locacion_empresa = locacion_empresa;
    }
    public Puesto(String id, String nombre,String area,String descripcion, String requisitos, String horario, int vigente,String nombre_empresa, String locacion_empresa){
        this.id = id;
        this.nombre = nombre;
        this.area = area;
        this.descripcion = descripcion;
        this.requisitos = requisitos;
        this.horario = horario;
        this.vigente = vigente;
        this.nombre_empresa = nombre_empresa;
        this.locacion_empresa = locacion_empresa;
    }


    public String getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getVigente() {
        return vigente;
    }

    public void setVigente(int vigente) {
        this.vigente = vigente;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getLocacion_empresa() {
        return locacion_empresa;
    }

    public void setLocacion_empresa(String locacion_empresa) {
        this.locacion_empresa = locacion_empresa;
    }

    @Override
    public String toString() {
        return "Puesto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", empresa_id='" + empresa_id + '\'' +
                ", area='" + area + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", requisitos='" + requisitos + '\'' +
                ", horario='" + horario + '\'' +
                ", nombre_empresa='" + nombre_empresa + '\'' +
                ", locacion_empresa='" + locacion_empresa + '\'' +
                ", vigente=" + vigente +
                '}';
    }
}
