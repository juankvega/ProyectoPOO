
package com.uniMagdalena.controlador.venta;

import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.servicio.VentaServicio;

public class VentaControladorUna 
{
        public static VentaDto obtenerVenta(int indice)
    {
        VentaDto obj;
        
        VentaServicio ventaServicio = new VentaServicio();
        obj = ventaServicio.getOne(indice);
        
        return obj;
    }
}
