
package com.uniMagdalena.controlador.cliente;

import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.servicio.ClienteServicio;

public class ClienteControladorEditar 
{
   public static boolean actualizar(int indiceExterno, ClienteDto objExterno, String rutaImagen)
   {
       boolean correcto;
       
       ClienteServicio clienteServicio = new ClienteServicio();
       
       correcto = clienteServicio.updateSet(indiceExterno, objExterno, rutaImagen);
       return correcto;
   }
}
