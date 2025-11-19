
package com.uniMagdalena.dto;

public class ProductoDto 
{
    private Integer idProducto;
    private String nombreProducto;
    private Boolean tipoProducto;
    private String tamanioProducto;
    private Double precioProducto;
    private Short productoVentas;
    
    private String nombreImagenPublicoProducto;
    private String nombreImagenPrivadoProducto;

    public ProductoDto() 
    {
        
    }

    public ProductoDto(Integer idProducto, String nombreProducto, Boolean tipoProducto, String tamanioProducto, Double precioProducto, Short productoVentas, String nombreImagenPublicoProducto, String nombreImagenPrivadoProducto) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
        this.tamanioProducto = tamanioProducto;
        this.precioProducto = precioProducto;
        this.productoVentas = productoVentas;
        this.nombreImagenPublicoProducto = nombreImagenPublicoProducto;
        this.nombreImagenPrivadoProducto = nombreImagenPrivadoProducto;
    }

    public Short getProductoVentas() {
        return productoVentas;
    }

    public void setProductoVentas(Short productoVentas) {
        this.productoVentas = productoVentas;
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

    public String getTamanioProducto() {
        return tamanioProducto;
    }

    public void setTamanioProducto(String tamanioProducto) {
        this.tamanioProducto = tamanioProducto;
    }

    public Boolean getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(Boolean tipoProducto) {
        this.tipoProducto = tipoProducto;
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
