
package com.uniMagdalena.model;

public class Genero 
{
    private Integer idGenero;
    private String nombreGenero;
    private Boolean estadoGenero;
    private Short popularidadGenero;
    private Boolean esClasicoGenero;
    private Short peliculasGenero;
    
    
    private String nombreImagenPublicoGenero;
    private String nombreImagenPrivadoGenero;

    public Genero() 
    {
        
    }

    public Genero(Integer idGenero, String nombreGenero, Boolean estadoGenero, Short popularidadGenero, Boolean esClasicoGenero, Short peliculasGenero, String nombreImagenPublicoGenero, String nombreImagenPrivadoGenero) {
        this.idGenero = idGenero;
        this.nombreGenero = nombreGenero;
        this.estadoGenero = estadoGenero;
        this.popularidadGenero = popularidadGenero;
        this.esClasicoGenero = esClasicoGenero;
        this.peliculasGenero = peliculasGenero;
        this.nombreImagenPublicoGenero = nombreImagenPublicoGenero;
        this.nombreImagenPrivadoGenero = nombreImagenPrivadoGenero;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
    }

    public Boolean getEstadoGenero() {
        return estadoGenero;
    }

    public void setEstadoGenero(Boolean estadoGenero) {
        this.estadoGenero = estadoGenero;
    }

    public Short getPopularidadGenero() {
        return popularidadGenero;
    }

    public void setPopularidadGenero(Short popularidadGenero) {
        this.popularidadGenero = popularidadGenero;
    }

    public Boolean getEsClasicoGenero() {
        return esClasicoGenero;
    }

    public void setEsClasicoGenero(Boolean esClasicoGenero) {
        this.esClasicoGenero = esClasicoGenero;
    }

    public Short getPeliculasGenero() {
        return peliculasGenero;
    }

    public void setPeliculasGenero(Short peliculasGenero) {
        this.peliculasGenero = peliculasGenero;
    }

    public String getNombreImagenPublicoGenero() {
        return nombreImagenPublicoGenero;
    }

    public void setNombreImagenPublicoGenero(String nombreImagenPublicoGenero) {
        this.nombreImagenPublicoGenero = nombreImagenPublicoGenero;
    }

    public String getNombreImagenPrivadoGenero() {
        return nombreImagenPrivadoGenero;
    }

    public void setNombreImagenPrivadoGenero(String nombreImagenPrivadoGenero) {
        this.nombreImagenPrivadoGenero = nombreImagenPrivadoGenero;
    }
    
     @Override
    public String toString() 
    {
        return idGenero + "-" + nombreGenero;
    }
    
    
}
