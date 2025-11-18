
package com.uniMagdalena.controlador.pelicula;

import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.servicio.PeliculaServicio;

public class PeliculaControladorUna 
{
    public static PeliculaDto obtenerPelicula(int indice)
    {
        PeliculaDto obj;
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        obj = peliculaServicio.getOne(indice);
        
        return obj;
    }
}
