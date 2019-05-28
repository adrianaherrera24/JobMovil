package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Trabajo implements Serializable {

    private String usuario;
    private String empresa;
    private String puesto;
    private String descripcion;
    private int anno_inicio;
    private int anno_final;

    public Trabajo(){
        this.usuario = "";
        this.empresa = "";
        this.puesto = "";
        this.descripcion = "";
        this.anno_inicio = 0;
        this.anno_final = 0;
    }

    public Trabajo(String usuario, String empresa, String puesto, String descripcion, int a_inicio, int a_final){
        this.usuario = usuario;
        this.empresa = empresa;
        this.puesto = puesto;
        this.descripcion = descripcion;
        this.anno_inicio = a_inicio;
        this.anno_final = a_final;
    }

    public String getUsuario(){  return usuario; }

    public void setUsuario(){ this.usuario = usuario; }

    public String getEmpresa(){  return empresa; }

    public void setEmpresa(String empresa){ this.empresa = empresa; }

    public String getPuesto(){  return puesto; }

    public void setPuesto(String puesto){ this.puesto = puesto; }

    public String getDescripcion(){  return descripcion; }

    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    public int getAnno_inicio(){  return anno_inicio; }

    public void setAnno_inicio(int anno_inicio){ this.anno_inicio = anno_inicio; }

    public int getAnno_final(){  return anno_final; }

    public void setAnno_final(int anno_final){ this.anno_final = anno_final; }

    @Override
    public String toString() {
        return "Trabajo { " +
                "usuario='" + usuario + '\'' +
                ", empresa='" + empresa + '\'' +
                ", puesto=" + puesto +
                ", descripcion='" + descripcion + '\'' +
                ", año inicio='" + anno_inicio + '\'' +
                ", año final='" + anno_final + '\'' + '}';
    }
}