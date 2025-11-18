
package com.uniMagdalena.controlador.cliente;

import com.uniMagdalena.servicio.ClienteServicio;

public class ClienteControladorEliminar 
{
   public static Boolean borrar(Integer indice)
   {
       Boolean correcto;
       ClienteServicio clienteServicio = new ClienteServicio();
       correcto = clienteServicio.deleteFrom(indice);
       return correcto;
   }
}
