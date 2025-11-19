
package com.uniMagdalena.controlador.producto;

import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.servicio.ProductoServicio;

public class ProductoControladorUna 
{
    public static ProductoDto obtenerProducto(int indice)
    {
        ProductoDto obj;
        
        ProductoServicio productoServicio = new ProductoServicio();
        obj = productoServicio.getOne(indice);
        
        return obj;
    }
}
