package com.app.lab3trabajoinmobiliaria.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Contrato implements Serializable {
    @SerializedName("contratoId")
    private int contratoId;
    private int inmuebleId;
    private int inquilinoId;
    private double monto;
    private String fechaDesde;
    private String fechaHasta;
    private Inmueble inmueble;
    private Inquilino inquilino;

    public Contrato(int contratoId, int inmuebleId, int inquilinoId, double monto, String fechaDesde, String fechaHasta, Inmueble inmueble, Inquilino inquilino) {
        this.contratoId = contratoId;
        this.inmuebleId = inmuebleId;
        this.inquilinoId = inquilinoId;
        this.monto = monto;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.inmueble = inmueble;
        this.inquilino = inquilino;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    public int getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(int inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public int getInquilinoId() {
        return inquilinoId;
    }

    public void setInquilinoId(int inquilinoId) {
        this.inquilinoId = inquilinoId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }


    @Override
    public String toString() {
        return "Contrato{" +
                "contratoId=" + contratoId +
                ", inmuebleId=" + inmuebleId +
                ", inquilinoId=" + inquilinoId +
                ", monto=" + monto +
                ", fechaDesde='" + fechaDesde + '\'' +
                ", fechaHasta='" + fechaHasta + '\'' +
                ", inmueble=" + inmueble +
                ", inquilino=" + inquilino +
                '}';
    }
}