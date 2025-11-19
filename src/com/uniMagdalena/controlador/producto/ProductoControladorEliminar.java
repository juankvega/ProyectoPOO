package com.uniMagdalena.controlador.producto;

import com.uniMagdalena.servicio.ProductoServicio;

public class ProductoControladorEliminar 
{
   public static Boolean borrar(Integer indice)
   {
       Boolean correcto;
       ProductoServicio productoServicio = new ProductoServicio();
       correcto = productoServicio.deleteFrom(indice);
       return correcto;
   }
}
