
package com.uniMagdalena.controlador.venta;

import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.servicio.VentaServicio;
import java.util.List;

public class VentaControladorListar 
{
     public static List<VentaDto> arregloVentas()
    {
        VentaServicio miDao = new VentaServicio();
        List<VentaDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
    public static int cantidadVentas()
    {
        VentaServicio miDao = new VentaServicio();
        int cant = miDao.numRows();
        return cant;
    }
    
    
}
