
package com.uniMagdalena.controlador.trabajador;

import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.servicio.TrabajadorServicio;

public class TrabajadorControladorEditar
{
    public static boolean actualizar(int indiceExterno, TrabajadorDto objExterno, String rutaImagen)
    {
        boolean correcto;
        
        TrabajadorServicio trabajadorServicio = new TrabajadorServicio();
        
        correcto = trabajadorServicio.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
}
