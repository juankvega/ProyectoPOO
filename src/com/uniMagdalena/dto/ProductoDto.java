
package com.uniMagdalena.dto;

public class ProductoDto 
{
    private Integer idProducto;
    private String nombreProducto;
    private Boolean esparaninyosProducto;
    private String advertenciaConsumoProducto;
    private Double precioProducto;
    
    private String nombreImagenPublicoProducto;
    private String nombreImagenPrivadoProducto;

    public ProductoDto() 
    {
        
    }

    public ProductoDto(Integer idProducto, String nombreProducto, Boolean esparaninyosProducto, String advertenciaConsumoProducto, Double precioProducto, String nombreImagenPublicoProducto, String nombreImagenPrivadoProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.esparaninyosProducto = esparaninyosProducto;
        this.advertenciaConsumoProducto = advertenciaConsumoProducto;
        this.precioProducto = precioProducto;
        this.nombreImagenPublicoProducto = nombreImagenPublicoProducto;
        this.nombreImagenPrivadoProducto = nombreImagenPrivadoProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Boolean getEsparaninyosProducto() {
        return esparaninyosProducto;
    }

    public void setEsparaninyosProducto(Boolean esparaninyosProducto) {
        this.esparaninyosProducto = esparaninyosProducto;
    }

    public String getAdvertenciaConsumoProducto() {
        return advertenciaConsumoProducto;
    }

    public void setAdvertenciaConsumoProducto(String advertenciaConsumoProducto) {
        this.advertenciaConsumoProducto = advertenciaConsumoProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getNombreImagenPublicoProducto() {
        return nombreImagenPublicoProducto;
    }

    public void setNombreImagenPublicoProducto(String nombreImagenPublicoProducto) {
        this.nombreImagenPublicoProducto = nombreImagenPublicoProducto;
    }

    public String getNombreImagenPrivadoProducto() {
        return nombreImagenPrivadoProducto;
    }

    public void setNombreImagenPrivadoProducto(String nombreImagenPrivadoProducto) {
        this.nombreImagenPrivadoProducto = nombreImagenPrivadoProducto;
    }

    @Override
    public String toString() 
    {
        return nombreProducto ;
    }
    
    
    
}
