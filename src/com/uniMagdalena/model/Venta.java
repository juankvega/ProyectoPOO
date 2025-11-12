
package com.uniMagdalena.model;

import java.time.LocalDate;

public class Venta 
{
    private Integer idVenta;
    private Cliente clienteVenta;
    private Pelicula peliculaVenta;
    private Sede sedeVenta;
    private Sala salaVenta;
    private Producto productoVenta;
    private String tipoAsientoVenta;
    private LocalDate fechaVenta;
    private Double valorVenta;
    
    private String nombreImagenPublicoVenta;
    private String nombreImagenPrivadoVenta;

    public Venta() 
    {
        
    }

    public Venta(Integer idVenta, Cliente clienteVenta, Pelicula peliculaVenta, Sede sedeVenta, Sala salaVenta, Producto productoVenta, String tipoAsientoVenta, LocalDate fechaVenta, Double valorVenta, String nombreImagenPublicoVenta, String nombreImagenPrivadoVenta) {
        this.idVenta = idVenta;
        this.clienteVenta = clienteVenta;
        this.peliculaVenta = peliculaVenta;
        this.sedeVenta = sedeVenta;
        this.salaVenta = salaVenta;
        this.productoVenta = productoVenta;
        this.tipoAsientoVenta = tipoAsientoVenta;
        this.fechaVenta = fechaVenta;
        this.valorVenta = valorVenta;
        this.nombreImagenPublicoVenta = nombreImagenPublicoVenta;
        this.nombreImagenPrivadoVenta = nombreImagenPrivadoVenta;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Cliente getClienteVenta() {
        return clienteVenta;
    }

    public void setClienteVenta(Cliente clienteVenta) {
        this.clienteVenta = clienteVenta;
    }

    public Pelicula getPeliculaVenta() {
        return peliculaVenta;
    }

    public void setPeliculaVenta(Pelicula peliculaVenta) {
        this.peliculaVenta = peliculaVenta;
    }

    public Sede getSedeVenta() {
        return sedeVenta;
    }

    public void setSedeVenta(Sede sedeVenta) {
        this.sedeVenta = sedeVenta;
    }

    public Sala getSalaVenta() {
        return salaVenta;
    }

    public void setSalaVenta(Sala salaVenta) {
        this.salaVenta = salaVenta;
    }

    public Producto getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(Producto productoVenta) {
        this.productoVenta = productoVenta;
    }

    public String getTipoAsientoVenta() {
        return tipoAsientoVenta;
    }

    public void setTipoAsientoVenta(String tipoAsientoVenta) {
        this.tipoAsientoVenta = tipoAsientoVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(Double valorVenta) {
        this.valorVenta = valorVenta;
    }

    public String getNombreImagenPublicoVenta() {
        return nombreImagenPublicoVenta;
    }

    public void setNombreImagenPublicoVenta(String nombreImagenPublicoVenta) {
        this.nombreImagenPublicoVenta = nombreImagenPublicoVenta;
    }

    public String getNombreImagenPrivadoVenta() {
        return nombreImagenPrivadoVenta;
    }

    public void setNombreImagenPrivadoVenta(String nombreImagenPrivadoVenta) {
        this.nombreImagenPrivadoVenta = nombreImagenPrivadoVenta;
    }

    @Override
    public String toString() 
    {
        return  idVenta + "-" + clienteVenta;
    }

    
    
    
    
    
    
}
