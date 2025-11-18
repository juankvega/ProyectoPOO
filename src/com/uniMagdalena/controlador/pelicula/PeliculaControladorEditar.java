
package com.uniMagdalena.controlador.pelicula;

import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.servicio.PeliculaServicio;

public class PeliculaControladorEditar 
{
    public static boolean actualizar(int indiceExterno, PeliculaDto objExterno, String rutaImagen)
    {
        boolean correcto;
        
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        correcto = peliculaServicio.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
}
