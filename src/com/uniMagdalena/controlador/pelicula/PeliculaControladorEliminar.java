
package com.uniMagdalena.controlador.pelicula;

import com.uniMagdalena.servicio.PeliculaServicio;



public class PeliculaControladorEliminar 
{
    public static Boolean borrar(Integer indice)
    {
        Boolean correcto;
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        correcto = peliculaServicio.deleteFrom(indice);
        return correcto;
    }
}
