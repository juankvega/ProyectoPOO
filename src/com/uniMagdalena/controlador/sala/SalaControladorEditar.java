
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.servicio.SalaServicio;

public class SalaControladorEditar
{
    public static boolean actualizar(int indiceExterno, SalaDto objExterno,String rutaImagen )
    {
        boolean correcto;
        
        SalaServicio salaServicio = new SalaServicio();
        correcto = salaServicio.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
}
