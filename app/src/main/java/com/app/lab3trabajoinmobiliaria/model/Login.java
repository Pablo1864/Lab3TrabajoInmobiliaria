package com.app.lab3trabajoinmobiliaria.model;

public class Login {
    private String Email;
    private String Password;

    public Login() {
    }

    public Login(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}