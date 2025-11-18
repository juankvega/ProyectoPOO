
package com.uniMagdalena.controlador.trabajador;

import com.uniMagdalena.servicio.TrabajadorServicio;

public class TrabajadorControladorEliminar 
{
    public static Boolean borrar(Integer indice)
   {
       Boolean correcto;
       TrabajadorServicio trabajadorServicio = new TrabajadorServicio();
       correcto = trabajadorServicio.deleteFrom(indice);
       return correcto;
   }
}
