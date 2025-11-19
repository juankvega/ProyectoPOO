
package com.uniMagdalena.controlador.venta;

import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.servicio.VentaServicio;

public class VentaControladorGrabar 
{
        public static Boolean crearVenta(VentaDto dto, String laRuta)
    {
        boolean correcto = false;
        VentaServicio ventaServicio = new VentaServicio();
        VentaDto dtoRespuesta = ventaServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
