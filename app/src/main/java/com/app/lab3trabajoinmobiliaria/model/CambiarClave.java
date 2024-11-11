package com.app.lab3trabajoinmobiliaria.model;

public class CambiarClave {
    private String claveActual;
    private String claveNueva;

    public CambiarClave(String claveActual, String claveNueva) {
        this.claveActual = claveActual;
        this.claveNueva = claveNueva;
    }

    // Getters y setters
    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }
}