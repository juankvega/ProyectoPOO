
package com.uniMagdalena.controlador.banyo;

import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.servicio.BanyoServicio;

public class BanyoControladorGrabar 
{
    public static Boolean crearBanyo(BanyoDto dto, String laRuta)
    {
        boolean correcto = false;
        BanyoServicio banyoServicio = new BanyoServicio();
        BanyoDto dtoRespuesta = banyoServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
