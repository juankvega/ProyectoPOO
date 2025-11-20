
package com.uniMagdalena.model;

public class Trabajador 
{
    private Integer idTrabajador;
    private String nombreTrabajador;
    private Boolean generoTrabajador;
    private String tipoDocumentoTrabajador;
    private Long numDocumentoTrabajador;
    private String tipoTrabajador;
    private Short cantidadBanyosAseo;
    
    private String nombreImagenPublicoTrabajador;
    private String nombreImagenPrivadoTrabajador;

    public Trabajador() 
    {
        
    }

    public Trabajador(Integer idTrabajador, String nombreTrabajador, Boolean generoTrabajador, String tipoDocumentoTrabajador, Long numDocumentoTrabajador, String tipoTrabajador, Short cantidadBanyosAseo, String nombreImagenPublicoTrabajador, String nombreImagenPrivadoTrabajador) {
        this.idTrabajador = idTrabajador;
        this.nombreTrabajador = nombreTrabajador;
        this.generoTrabajador = generoTrabajador;
        this.tipoDocumentoTrabajador = tipoDocumentoTrabajador;
        this.numDocumentoTrabajador = numDocumentoTrabajador;
        this.tipoTrabajador = tipoTrabajador;
        this.cantidadBanyosAseo = cantidadBanyosAseo;
        this.nombreImagenPublicoTrabajador = nombreImagenPublicoTrabajador;
        this.nombreImagenPrivadoTrabajador = nombreImagenPrivadoTrabajador;
    }

 



    public Short getCantidadBanyosAseo() {
        return cantidadBanyosAseo;
    }

    public void setCantidadBanyosAseo(Short cantidadBanyosAseo) {
        this.cantidadBanyosAseo = cantidadBanyosAseo;
    }

    public Long getNumDocumentoTrabajador() {
        return numDocumentoTrabajador;
    }

    public void setNumDocumentoTrabajador(Long numDocumentoTrabajador) {
        this.numDocumentoTrabajador = numDocumentoTrabajador;
    }


   
   

    
    

    

    public Integer getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Integer idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombreTrabajador() {
        return nombreTrabajador;
    }

    public void setNombreTrabajador(String nombreTrabajador) {
        this.nombreTrabajador = nombreTrabajador;
    }

    public Boolean getGeneroTrabajador() {
        return generoTrabajador;
    }

    public void setGeneroTrabajador(Boolean generoTrabajador) {
        this.generoTrabajador = generoTrabajador;
    }

    public String getTipoDocumentoTrabajador() {
        return tipoDocumentoTrabajador;
    }

    public void setTipoDocumentoTrabajador(String tipoDocumentoTrabajador) {
        this.tipoDocumentoTrabajador = tipoDocumentoTrabajador;
    }

    public String getTipoTrabajador() {
        return tipoTrabajador;
    }

    public void setTipoTrabajador(String tipoTrabajador) {
        this.tipoTrabajador = tipoTrabajador;
    }

    public String getNombreImagenPublicoTrabajador() {
        return nombreImagenPublicoTrabajador;
    }

    public void setNombreImagenPublicoTrabajador(String nombreImagenPublicoTrabajador) {
        this.nombreImagenPublicoTrabajador = nombreImagenPublicoTrabajador;
    }

    public String getNombreImagenPrivadoTrabajador() {
        return nombreImagenPrivadoTrabajador;
    }

    public void setNombreImagenPrivadoTrabajador(String nombreImagenPrivadoTrabajador) {
        this.nombreImagenPrivadoTrabajador = nombreImagenPrivadoTrabajador;
    }

    @Override
    public String toString() 
    {
        return idTrabajador + "-" + nombreTrabajador;
    }
    
    
    
}
