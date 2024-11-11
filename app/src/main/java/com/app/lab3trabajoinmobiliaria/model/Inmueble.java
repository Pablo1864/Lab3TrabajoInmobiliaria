package com.app.lab3trabajoinmobiliaria.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Inmueble implements Serializable {


    private int idInmueble;


    private int idTipoInmueble;
    private String uso;
    private double precio;
    private String direccion;
    private int ambientes;


    private String estado;
    private String img;

    public Inmueble() {
    }

    public Inmueble(int idInmueble, int idTipoInmueble, String uso, double precio, String direccion, int ambientes, String estado, String img) {
        this.idInmueble = idInmueble;
        this.idTipoInmueble = idTipoInmueble;
        this.uso = uso;
        this.precio = precio;
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.estado = estado;
        this.img = img;
    }



    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdTipoInmueble() {
        return idTipoInmueble;
    }

    public void setIdTipoInmueble(int idTipoInmueble) {
        this.idTipoInmueble = idTipoInmueble;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "idInmueble=" + idInmueble +
                ", idTipoInmueble=" + idTipoInmueble +
                ", uso='" + uso + '\'' +
                ", precio=" + precio +
                ", direccion='" + direccion + '\'' +
                ", ambientes=" + ambientes +
                ", estado='" + estado + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}