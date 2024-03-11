package org.cibertec.edu.pe.model;

public class Ticket {

    private Ciudad ciudadOrigen;  // Cambiado a Ciudad en lugar de String
    private Ciudad ciudadDestino; // Cambiado a Ciudad en lugar de String
    private String fechaSalida;
    private String fechaRetorno;
    private String nombreComprador;
    private int cantidad;
    private Double subTotal;

    public Ticket() {}

    public Ticket(Ciudad ciudadOrigen, Ciudad ciudadDestino, String fechaSalida, String fechaRetorno, String nombreComprador, int cantidad, Double subTotal) {
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.fechaSalida = fechaSalida;
        this.fechaRetorno = fechaRetorno;
        this.nombreComprador = nombreComprador;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(String fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
