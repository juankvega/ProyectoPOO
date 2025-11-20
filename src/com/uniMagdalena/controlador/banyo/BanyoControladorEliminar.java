
package com.uniMagdalena.controlador.banyo;

import com.uniMagdalena.servicio.BanyoServicio;

public class BanyoControladorEliminar 
{
    public static Boolean borrar(Integer indice)
    {
        Boolean correcto;
        BanyoServicio banyoServicio = new BanyoServicio();
        correcto = banyoServicio.deleteFrom(indice);
        return correcto;
    }
}
