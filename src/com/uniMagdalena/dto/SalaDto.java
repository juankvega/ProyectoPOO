
package com.uniMagdalena.dto;

public class SalaDto
{
    private Integer idSala;
    private String nombreSala;
    private Integer asientosSala;
    private Boolean sala4d;
    private SedeDto sedeSala;
    
    private String nombreImagenPublicoSala;
    private String nombreImagenPrivadoSala;

    public SalaDto() 
    {
        
    }

    public SalaDto(Integer idSala, String nombreSala, Integer asientosSala, Boolean sala4d, SedeDto sedeSala, String nombreImagenPublicoSala, String nombreImagenPrivadoSala) {
        this.idSala = idSala;
        this.nombreSala = nombreSala;
        this.asientosSala = asientosSala;
        this.sala4d = sala4d;
        this.sedeSala = sedeSala;
        this.nombreImagenPublicoSala = nombreImagenPublicoSala;
        this.nombreImagenPrivadoSala = nombreImagenPrivadoSala;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public Integer getAsientosSala() {
        return asientosSala;
    }

    public void setAsientosSala(Integer asientosSala) {
        this.asientosSala = asientosSala;
    }

    public Boolean getSala4d() {
        return sala4d;
    }

    public void setSala4d(Boolean sala4d) {
        this.sala4d = sala4d;
    }

    public SedeDto getSedeSala() {
        return sedeSala;
    }

    public void setSedeSala(SedeDto sedeSala) {
        this.sedeSala = sedeSala;
    }

    public String getNombreImagenPublicoSala() {
        return nombreImagenPublicoSala;
    }

    public void setNombreImagenPublicoSala(String nombreImagenPublicoSala) {
        this.nombreImagenPublicoSala = nombreImagenPublicoSala;
    }

    public String getNombreImagenPrivadoSala() {
        return nombreImagenPrivadoSala;
    }

    public void setNombreImagenPrivadoSala(String nombreImagenPrivadoSala) {
        this.nombreImagenPrivadoSala = nombreImagenPrivadoSala;
    }

    @Override
    public String toString() 
    {
        return nombreSala;
    }
    
    
    
}
