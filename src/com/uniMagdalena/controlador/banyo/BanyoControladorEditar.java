
package com.uniMagdalena.controlador.banyo;

import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.servicio.BanyoServicio;

public class BanyoControladorEditar 
{
    public static boolean actualizar(int indiceExterno, BanyoDto objExterno, String rutaImagen)
    {
        boolean correcto;
        
        BanyoServicio banyoServicio = new BanyoServicio();
        correcto = banyoServicio.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
}
