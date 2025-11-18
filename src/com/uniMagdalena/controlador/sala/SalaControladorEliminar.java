
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.servicio.SalaServicio;

public class SalaControladorEliminar 
{
    public static Boolean borrar(Integer indice)
    {
        Boolean correcto;
        SalaServicio salaServicio = new SalaServicio();
        correcto = salaServicio.deleteFrom(indice);
        return correcto;
    }
}
