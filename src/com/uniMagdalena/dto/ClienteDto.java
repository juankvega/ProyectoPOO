
package com.uniMagdalena.dto;

public class ClienteDto 
{
   private Integer idCliente;
   private String nombreCliente;
   private Boolean generoCliente;
   private String tipoDocumentoCliente;
   private Integer numeroDocumentoCliente;
   private String tipoCliente;
   private Short clienteVentas;
   
   
   private String nombreImagenPublicoCliente;
   private String nombreImagenPrivadoCliente;

    public ClienteDto() 
    {
        
    }

    public ClienteDto(Integer idCliente, String nombreCliente, Boolean generoCliente, String tipoDocumentoCliente, Integer numeroDocumentoCliente, String tipoCliente, Short clienteVentas, String nombreImagenPublicoCliente, String nombreImagenPrivadoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.generoCliente = generoCliente;
        this.tipoDocumentoCliente = tipoDocumentoCliente;
        this.numeroDocumentoCliente = numeroDocumentoCliente;
        this.tipoCliente = tipoCliente;
        this.clienteVentas = clienteVentas;
        this.nombreImagenPublicoCliente = nombreImagenPublicoCliente;
        this.nombreImagenPrivadoCliente = nombreImagenPrivadoCliente;
    }

    public Short getClienteVentas() {
        return clienteVentas;
    }

    public void setClienteVentas(Short clienteVentas) {
        this.clienteVentas = clienteVentas;
    }

  
    

    

    public Integer getNumeroDocumentoCliente() {
        return numeroDocumentoCliente;
    }

    public void setNumeroDocumentoCliente(Integer numeroDocumentoCliente) {
        this.numeroDocumentoCliente = numeroDocumentoCliente;
    }
    
    

    

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Boolean getGeneroCliente() {
        return generoCliente;
    }

    public void setGeneroCliente(Boolean generoCliente) {
        this.generoCliente = generoCliente;
    }

    public String getTipoDocumentoCliente() {
        return tipoDocumentoCliente;
    }

    public void setTipoDocumentoCliente(String tipoDocumentoCliente) {
        this.tipoDocumentoCliente = tipoDocumentoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }


    public String getNombreImagenPublicoCliente() {
        return nombreImagenPublicoCliente;
    }

    public void setNombreImagenPublicoCliente(String nombreImagenPublicoCliente) {
        this.nombreImagenPublicoCliente = nombreImagenPublicoCliente;
    }

    public String getNombreImagenPrivadoCliente() {
        return nombreImagenPrivadoCliente;
    }

    public void setNombreImagenPrivadoCliente(String nombreImagenPrivadoCliente) {
        this.nombreImagenPrivadoCliente = nombreImagenPrivadoCliente;
    }

    @Override
    public String toString() {
        return nombreCliente;
    }
    
    
    
    
   
}
