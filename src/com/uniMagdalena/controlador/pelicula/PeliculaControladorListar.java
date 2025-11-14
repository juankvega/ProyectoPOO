
package com.uniMagdalena.controlador.pelicula;


import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.servicio.PeliculaServicio;
import java.util.List;

public class PeliculaControladorListar 
{
    public static List<PeliculaDto> arregloPeliculas()
    {
        PeliculaServicio miDao = new PeliculaServicio();
        List<PeliculaDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
    public static int cantidadPeliculas()
    {
        PeliculaServicio miDao = new PeliculaServicio();
        int cant = miDao.numRows();
        return cant;
    }
}
