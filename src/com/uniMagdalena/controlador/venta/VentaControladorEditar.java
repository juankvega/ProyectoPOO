
package com.uniMagdalena.controlador.venta;

import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.servicio.VentaServicio;

public class VentaControladorEditar 
{
       public static boolean actualizar(int indiceExterno, VentaDto objExterno, String rutaImagen)
   {
       boolean correcto;
       
       VentaServicio clienteServicio = new VentaServicio();
       
       correcto = clienteServicio.updateSet(indiceExterno, objExterno, rutaImagen);
       return correcto;
   }
}
