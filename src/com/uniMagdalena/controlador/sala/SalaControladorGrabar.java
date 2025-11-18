
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.servicio.SalaServicio;

public class SalaControladorGrabar 
{
    public static Boolean crearSala(SalaDto dto, String laRuta)
    {
        boolean correcto = false;
        SalaServicio salaServicio = new SalaServicio();
        SalaDto dtoRespuesta = salaServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
