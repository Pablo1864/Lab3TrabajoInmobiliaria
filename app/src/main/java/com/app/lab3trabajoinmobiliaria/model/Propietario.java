package com.app.lab3trabajoinmobiliaria.model;

public class Propietario {
    private int idPropietario;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String clave;
    private String domicilio;



    public Propietario(int id, String nombre, String apellido, String email, String telefono, String domicilio, String dni) {
        this.idPropietario = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.dni = dni;
    }


    public int getId() {
        return idPropietario;
    }

    public void setId(int id) {
        this.idPropietario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + idPropietario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
