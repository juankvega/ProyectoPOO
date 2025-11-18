
package com.uniMagdalena.model;

public class Cliente
{
   private Integer idCliente;
   private String nombreCliente;
   private Boolean generoCliente;
   private String tipoDocumentoCliente;
   private Integer numeroDocumentoCliente;
   private String tipoCliente;
  
   
   private String nombreImagenPublicoCliente;
   private String nombreImagenPrivadoCliente;

    public Cliente() 
    {
        
    }

    public Cliente(Integer idCliente, String nombreCliente, Boolean generoCliente, String tipoDocumentoCliente, Integer numeroDocumentoCliente, String tipoCliente, String nombreImagenPublicoCliente, String nombreImagenPrivadoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.generoCliente = generoCliente;
        this.tipoDocumentoCliente = tipoDocumentoCliente;
        this.numeroDocumentoCliente = numeroDocumentoCliente;
        this.tipoCliente = tipoCliente;
        this.nombreImagenPublicoCliente = nombreImagenPublicoCliente;
        this.nombreImagenPrivadoCliente = nombreImagenPrivadoCliente;
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
    public String toString() 
    {
        return idCliente + "-" + nombreCliente;
    }
    
    

    
    
    
   
   
}
