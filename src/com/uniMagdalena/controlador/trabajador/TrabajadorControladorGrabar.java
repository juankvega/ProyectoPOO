
package com.uniMagdalena.controlador.trabajador;

import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.servicio.TrabajadorServicio;

public class TrabajadorControladorGrabar 
{
    public static Boolean crearTrabajador(TrabajadorDto dto, String laRuta)
    {
        boolean correcto = false;
        TrabajadorServicio trabajadorServicio = new TrabajadorServicio();
        TrabajadorDto dtoRespuesta = trabajadorServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
