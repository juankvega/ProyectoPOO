
package com.uniMagdalena.model;

public class Sede 
{
    private Integer idSede;
    private String nombreSede;
    private String ciudadSede;
    private String ubicacionSede;
    private Boolean es24horasSede;
    private Short salasSede;
    
    private String nombreImagenPublicoSede;
    private String nombreImagenPrivadoSede;

    public Sede() 
    {
        
    }

    public Sede(Integer idSede, String nombreSede, String ciudadSede, String ubicacionSede, Boolean es24horasSede, Short salasSede, String nombreImagenPublicoSede, String nombreImagenPrivadoSede) {
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.ciudadSede = ciudadSede;
        this.ubicacionSede = ubicacionSede;
        this.es24horasSede = es24horasSede;
        this.salasSede = salasSede;
        this.nombreImagenPublicoSede = nombreImagenPublicoSede;
        this.nombreImagenPrivadoSede = nombreImagenPrivadoSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public Boolean getEs24horasSede() {
        return es24horasSede;
    }

    public void setEs24horasSede(Boolean es24horasSede) {
        this.es24horasSede = es24horasSede;
    }
    
    

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

 

    public String getCiudadSede() {
        return ciudadSede;
    }

    public void setCiudadSede(String ciudadSede) {
        this.ciudadSede = ciudadSede;
    }

    public String getUbicacionSede() {
        return ubicacionSede;
    }

    public void setUbicacionSede(String ubicacionSede) {
        this.ubicacionSede = ubicacionSede;
    }

    public Boolean getes24horasSede() {
        return es24horasSede;
    }

    public void setes24horasSede(Boolean es24horasSede) {
        this.es24horasSede = es24horasSede;
    }

    public Short getSalasSede() {
        return salasSede;
    }

    public void setSalasSede(Short salasSede) {
        this.salasSede = salasSede;
    }

    public String getNombreImagenPublicoSede() {
        return nombreImagenPublicoSede;
    }

    public void setNombreImagenPublicoSede(String nombreImagenPublicoSede) {
        this.nombreImagenPublicoSede = nombreImagenPublicoSede;
    }

    public String getNombreImagenPrivadoSede() {
        return nombreImagenPrivadoSede;
    }

    public void setNombreImagenPrivadoSede(String nombreImagenPrivadoSede) {
        this.nombreImagenPrivadoSede = nombreImagenPrivadoSede;
    }

    @Override
    public String toString() 
    {
        return idSede + "-" + ubicacionSede;
    }
    
    
    
}
