package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Referencia implements Serializable {

    private int id;
    private String usuario;
    private String nombre;
    private String telefono;
    private String email;
    private String descripcion;

    public Referencia() {
        this.id = 0;
        this.usuario = "";
        this.nombre = "";
        this.telefono = "";
        this.email = "";
        this.descripcion = "";
    }

    public Referencia(int id, String usuario, String nombre, String telefono, String email, String descripcion) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.descripcion = descripcion;
    }

    public Referencia(String usuario, String nombre, String telefono, String email, String descripcion) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Referencia{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
