package com.uniMagdalena.controlador.producto;

import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.servicio.ProductoServicio;

public class ProductoControladorEditar 
{
   public static boolean actualizar(int indiceExterno, ProductoDto objExterno, String rutaImagen)
   {
       boolean correcto;
       
       ProductoServicio clienteServicio = new ProductoServicio();
       
       correcto = clienteServicio.updateSet(indiceExterno, objExterno, rutaImagen);
       return correcto;
   }
}
