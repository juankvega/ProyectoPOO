
package com.uniMagdalena.model;

public class Banyo
{
   private Integer idBanyo;
   private Sede sedeBanyo;
   private String ubicacionBanyo;
   private Boolean generoBanyo;
   private Boolean usoBanyo;
   private Trabajador encargadoBanyo;
   
   private String nombreImagenPublicoBanyo;
   private String nombreImagenPrivadoBanyo;

    public Banyo() 
    {
        
    }

    public Banyo(Integer idBanyo, Sede sedeBanyo, String ubicacionBanyo, Boolean generoBanyo, Boolean usoBanyo, Trabajador encargadoBanyo, String nombreImagenPublicoBanyo, String nombreImagenPrivadoBanyo) {
        this.idBanyo = idBanyo;
        this.sedeBanyo = sedeBanyo;
        this.ubicacionBanyo = ubicacionBanyo;
        this.generoBanyo = generoBanyo;
        this.usoBanyo = usoBanyo;
        this.encargadoBanyo = encargadoBanyo;
        this.nombreImagenPublicoBanyo = nombreImagenPublicoBanyo;
        this.nombreImagenPrivadoBanyo = nombreImagenPrivadoBanyo;
    }

    public Integer getIdBanyo() {
        return idBanyo;
    }

    public void setIdBanyo(Integer idBanyo) {
        this.idBanyo = idBanyo;
    }

    public Sede getSedeBanyo() {
        return sedeBanyo;
    }

    public void setSedeBanyo(Sede sedeBanyo) {
        this.sedeBanyo = sedeBanyo;
    }

    public String getUbicacionBanyo() {
        return ubicacionBanyo;
    }

    public void setUbicacionBanyo(String ubicacionBanyo) {
        this.ubicacionBanyo = ubicacionBanyo;
    }

    public Boolean getGeneroBanyo() {
        return generoBanyo;
    }

    public void setGeneroBanyo(Boolean generoBanyo) {
        this.generoBanyo = generoBanyo;
    }

    public Boolean getUsoBanyo() {
        return usoBanyo;
    }

    public void setUsoBanyo(Boolean usoBanyo) {
        this.usoBanyo = usoBanyo;
    }

    public Trabajador getEncargadoBanyo() {
        return encargadoBanyo;
    }

    public void setEncargadoBanyo(Trabajador encargadoBanyo) {
        this.encargadoBanyo = encargadoBanyo;
    }

    public String getNombreImagenPublicoBanyo() {
        return nombreImagenPublicoBanyo;
    }

    public void setNombreImagenPublicoBanyo(String nombreImagenPublicoBanyo) {
        this.nombreImagenPublicoBanyo = nombreImagenPublicoBanyo;
    }

    public String getNombreImagenPrivadoBanyo() {
        return nombreImagenPrivadoBanyo;
    }

    public void setNombreImagenPrivadoBanyo(String nombreImagenPrivadoBanyo) {
        this.nombreImagenPrivadoBanyo = nombreImagenPrivadoBanyo;
    }
    
    

    

    @Override
    public String toString() 
    {
        return  idBanyo + "-" + sedeBanyo;
    }
    
    
   
}
