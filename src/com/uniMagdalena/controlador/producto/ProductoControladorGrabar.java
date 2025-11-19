package com.uniMagdalena.controlador.producto;

import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.servicio.ProductoServicio;

public class ProductoControladorGrabar 
{
    public static Boolean crearProducto(ProductoDto dto, String laRuta)
    {
        boolean correcto = false;
        ProductoServicio clienteServicio = new ProductoServicio();
        ProductoDto dtoRespuesta = clienteServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
