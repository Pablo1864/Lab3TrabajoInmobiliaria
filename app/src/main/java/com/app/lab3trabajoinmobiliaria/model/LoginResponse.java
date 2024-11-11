package com.app.lab3trabajoinmobiliaria.model;

public class LoginResponse {
    private String token;
    private Propietario propietario;

    // Constructor vacío (opcional)
    public LoginResponse() {}

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
