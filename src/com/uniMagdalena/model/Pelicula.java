
package com.uniMagdalena.model;


public class Pelicula 
{
    
    // ID PELICULA (INTEGER); NOMBRE DE PELICULA (STRING); IDGENERO (INTEGER); ACTOR PRINCIPAL DE PELICULA (STRING); PRESUPUESTO PELICULA (DOUBLE); BOOLEAN PARA ADULTOS
    private Integer idPelicula;
    private String nombrePelicula;
    private Genero idGeneroPelicula;
    private String actorPPelicula;
    private Double presupuestoPelicula;
    private Boolean esParaNinyosPelicula;
    
    private String nombreImagenPublicoPelicula;
    private String nombreImagenPrivadoPelicula;

    public Pelicula() 
    {
        
    }

    public Pelicula(Integer idPelicula, String nombrePelicula, Genero idGeneroPelicula, String actorPPelicula, Double presupuestoPelicula, Boolean esParaNinyosPelicula, String nombreImagenPublicoPelicula, String nombreImagenPrivadoPelicula) {
        this.idPelicula = idPelicula;
        this.nombrePelicula = nombrePelicula;
        this.idGeneroPelicula = idGeneroPelicula;
        this.actorPPelicula = actorPPelicula;
        this.presupuestoPelicula = presupuestoPelicula;
        this.esParaNinyosPelicula = esParaNinyosPelicula;
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

    public Genero getIdGeneroPelicula() {
        return idGeneroPelicula;
    }

    public void setIdGeneroPelicula(Genero idGeneroPelicula) {
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

    public Boolean getEsParaNinyosPelicula() {
        return esParaNinyosPelicula;
    }

    public void setEsParaNinyosPelicula(Boolean esParaNinyosPelicula) {
        this.esParaNinyosPelicula = esParaNinyosPelicula;
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
        return idPelicula + "-" + nombrePelicula;
    }
    
    
    
    
    
    
    
  
    
    
    
    
}
