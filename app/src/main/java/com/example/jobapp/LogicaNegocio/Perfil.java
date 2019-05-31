package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Perfil implements Serializable {

    private String id;
    private String nombre;
    private String lugar_habitacion;
    private int edad;
    private String correo;
    private String telefono;
    private String descripcion;

    public Perfil() {}

    public Perfil(String id, String nombre, String lugar_habitacion, int edad, String correo, String telefono, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.lugar_habitacion = lugar_habitacion;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
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

    public String getLugar_habitacion() {
        return lugar_habitacion;
    }

    public void setLugar_habitacion(String lugar_habitacion) {
        this.lugar_habitacion = lugar_habitacion;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", lugar_habitacion='" + lugar_habitacion + '\'' +
                ", edad=" + edad +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
