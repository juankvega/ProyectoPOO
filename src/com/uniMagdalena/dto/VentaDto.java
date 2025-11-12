
package com.uniMagdalena.dto;

import java.time.LocalDate;

public class VentaDto 
{
     private Integer idVenta;
    private ClienteDto clienteVenta;
    private PeliculaDto peliculaVenta;
    private SedeDto sedeVenta;
    private SalaDto salaVenta;
    private ProductoDto productoVenta;
    private String tipoAsientoVenta;
    private LocalDate fechaVenta;
    private Double valorVenta;
    
    private String nombreImagenPublicoVenta;
    private String nombreImagenPrivadoVenta;

    public VentaDto() 
    {
        
    }

    public VentaDto(Integer idVenta, ClienteDto clienteVenta, PeliculaDto peliculaVenta, SedeDto sedeVenta, SalaDto salaVenta, ProductoDto productoVenta, String tipoAsientoVenta, LocalDate fechaVenta, Double valorVenta, String nombreImagenPublicoVenta, String nombreImagenPrivadoVenta) {
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

    public ClienteDto getClienteVenta() {
        return clienteVenta;
    }

    public void setClienteVenta(ClienteDto clienteVenta) {
        this.clienteVenta = clienteVenta;
    }

    public PeliculaDto getPeliculaVenta() {
        return peliculaVenta;
    }

    public void setPeliculaVenta(PeliculaDto peliculaVenta) {
        this.peliculaVenta = peliculaVenta;
    }

    public SedeDto getSedeVenta() {
        return sedeVenta;
    }

    public void setSedeVenta(SedeDto sedeVenta) {
        this.sedeVenta = sedeVenta;
    }

    public SalaDto getSalaVenta() {
        return salaVenta;
    }

    public void setSalaVenta(SalaDto salaVenta) {
        this.salaVenta = salaVenta;
    }

    public ProductoDto getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoDto productoVenta) {
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
        return idVenta + "-" + clienteVenta;
    }
    
    
    
}
