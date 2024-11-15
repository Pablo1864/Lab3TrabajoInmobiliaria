package com.app.lab3trabajoinmobiliaria.model;



import java.io.Serializable;




import java.io.Serializable;

public class Pagos implements Serializable {

    private int id;
    private int nroPago;
    private String fechaPago;
    private String detallePago;
    private double importe;
    private int contratoId;
    private String fechaPagoFormateada;
    private String error;  // Campo para almacenar el mensaje de error

    // Constructor completo para casos sin errores
    public Pagos(int id, int nroPago, String fechaPago, String detallePago, double importe, int contratoId, String fechaPagoFormateada) {
        this.id = id;
        this.nroPago = nroPago;
        this.fechaPago = fechaPago;
        this.detallePago = detallePago;
        this.importe = importe;
        this.contratoId = contratoId;
        this.fechaPagoFormateada = fechaPagoFormateada;
        this.error = null;  // No hay error
    }

    // Constructor para casos de error
    public Pagos(String error) {
        this.error = error;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNroPago() {
        return nroPago;
    }

    public void setNroPago(int nroPago) {
        this.nroPago = nroPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(String detallePago) {
        this.detallePago = detallePago;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    public String getFechaPagoFormateada() {
        return fechaPagoFormateada;
    }

    public void setFechaPagoFormateada(String fechaPagoFormateada) {
        this.fechaPagoFormateada = fechaPagoFormateada;
    }

    // Método para obtener el mensaje de error
    public String getError() {
        return error;
    }

    // Método toString actualizado para incluir el error
    @Override
    public String toString() {
        return "Pagos{" +
                "id=" + id +
                ", nroPago=" + nroPago +
                ", fechaPago='" + fechaPago + '\'' +
                ", detallePago='" + detallePago + '\'' +
                ", importe=" + importe +
                ", contratoId=" + contratoId +
                ", fechaPagoFormateada='" + fechaPagoFormateada + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
