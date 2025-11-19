
package com.uniMagdalena.controlador.producto;

import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.servicio.ProductoServicio;
import java.util.List;

public class ProductoControladorListar 
{
    public static List<ProductoDto> arregloProductos()
    {
        ProductoServicio miDao = new ProductoServicio();
        List<ProductoDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
    public static int cantidadProductos()
    {
        ProductoServicio miDao = new ProductoServicio();
        int cant = miDao.numRows();
        return cant;
    }
}
