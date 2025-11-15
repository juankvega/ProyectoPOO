
package com.uniMagdalena.controlador.genero;

import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.servicio.GeneroServicio;

public class GeneroControladorEditar 
{
    public static boolean actualizar(int indiceExterno, GeneroDto objExterno, String rutaImagen)
    {
        boolean correcto;
        
        GeneroServicio generoServicio = new GeneroServicio();
        
        correcto = generoServicio.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
}
