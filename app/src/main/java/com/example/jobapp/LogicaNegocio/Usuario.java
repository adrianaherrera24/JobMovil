package com.example.jobapp.LogicaNegocio;

import java.io.Serializable;

public class Usuario implements Serializable {

    String uemail, upassword, uid, uprivilegio, unombre;

    public Usuario() {
    }

    public Usuario(String uemail, String upassword, String uid, String uprivilegio, String unombre) {
        this.uemail = uemail;
        this.upassword = upassword;
        this.uid = uid;
        this.uprivilegio = uprivilegio;
        this.unombre = unombre;
    }

    public String getUNombre() {
        return unombre;
    }

    public void setUNombre(String unombre) {
        this.unombre = unombre;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUprivilegio() {
        return uprivilegio;
    }

    public void setUprivilegio(String uprivilegio) {
        this.uprivilegio = uprivilegio;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uemail='" + uemail + '\'' +
                ", upassword='" + upassword + '\'' +
                ", uid='" + uid + '\'' +
                ", uprivilegio='" + uprivilegio + '\'' +
                '}';
    }
}
