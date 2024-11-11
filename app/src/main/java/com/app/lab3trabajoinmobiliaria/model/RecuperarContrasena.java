package com.app.lab3trabajoinmobiliaria.model;

public class RecuperarContrasena {
    private String token;
    private String nuevaContrasena;
    private String confirmarContrasena;

    // Constructor
    public RecuperarContrasena(String token, String nuevaContrasena, String confirmarContrasena) {
        this.token = token;
        this.nuevaContrasena = nuevaContrasena;
        this.confirmarContrasena = confirmarContrasena;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }

    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }
}

