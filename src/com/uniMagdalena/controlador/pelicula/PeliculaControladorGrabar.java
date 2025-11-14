
package com.uniMagdalena.controlador.pelicula;

import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.servicio.PeliculaServicio;



public class PeliculaControladorGrabar 
{
    public static Boolean crearPelicula(PeliculaDto dto, String laRuta)
    {
        boolean correcto = false;
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        PeliculaDto dtoRespuesta = peliculaServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
