
package com.uniMagdalena.dto;


public class PeliculaDto 
{
    private Integer idPelicula;
    private String nombrePelicula;
    private GeneroDto idGeneroPelicula;
    private String actorPPelicula;
    private Double presupuestoPelicula;
    private Boolean esParaAdultosPelicula;
    
    private String nombreImagenPublicoPelicula;
    private String nombreImagenPrivadoPelicula;

    public PeliculaDto()
    {
        
    }

    public PeliculaDto(Integer idPelicula, String nombrePelicula, GeneroDto idGeneroPelicula, String actorPPelicula, Double presupuestoPelicula, Boolean esParaAdultosPelicula, String nombreImagenPublicoPelicula, String nombreImagenPrivadoPelicula) {
        this.idPelicula = idPelicula;
        this.nombrePelicula = nombrePelicula;
        this.idGeneroPelicula = idGeneroPelicula;
        this.actorPPelicula = actorPPelicula;
        this.presupuestoPelicula = presupuestoPelicula;
        this.esParaAdultosPelicula = esParaAdultosPelicula;
        this.nombreImagenPublicoPelicula = nombreImagenPublicoPelicula;
        this.nombreImagenPrivadoPelicula = nombreImagenPrivadoPelicula;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public GeneroDto getIdGeneroPelicula() {
        return idGeneroPelicula;
    }

    public void setIdGeneroPelicula(GeneroDto idGeneroPelicula) {
        this.idGeneroPelicula = idGeneroPelicula;
    }

    public String getActorPPelicula() {
        return actorPPelicula;
    }

    public void setActorPPelicula(String actorPPelicula) {
        this.actorPPelicula = actorPPelicula;
    }

    public Double getPresupuestoPelicula() {
        return presupuestoPelicula;
    }

    public void setPresupuestoPelicula(Double presupuestoPelicula) {
        this.presupuestoPelicula = presupuestoPelicula;
    }

    public Boolean getEsParaAdultosPelicula() {
        return esParaAdultosPelicula;
    }

    public void setEsParaAdultosPelicula(Boolean esParaAdultosPelicula) {
        this.esParaAdultosPelicula = esParaAdultosPelicula;
    }

    public String getNombreImagenPublicoPelicula() {
        return nombreImagenPublicoPelicula;
    }

    public void setNombreImagenPublicoPelicula(String nombreImagenPublicoPelicula) {
        this.nombreImagenPublicoPelicula = nombreImagenPublicoPelicula;
    }

    public String getNombreImagenPrivadoPelicula() {
        return nombreImagenPrivadoPelicula;
    }

    public void setNombreImagenPrivadoPelicula(String nombreImagenPrivadoPelicula) {
        this.nombreImagenPrivadoPelicula = nombreImagenPrivadoPelicula;
    }
    
    
    
    @Override
    public String toString() 
    {
        return nombrePelicula;
    }

    
    
    
}
