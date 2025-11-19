
package com.uniMagdalena.controlador.venta;

import com.uniMagdalena.servicio.VentaServicio;


public class VentaControladorEliminar 
{
     public static Boolean borrar(Integer indice)
   {
       Boolean correcto;
       VentaServicio ventaServicio = new VentaServicio();
       correcto = ventaServicio.deleteFrom(indice);
       return correcto;
   }
}
