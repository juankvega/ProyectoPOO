
package com.uniMagdalena.controlador.trabajador;

import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.servicio.TrabajadorServicio;

public class TrabajadorControladorUna 
{
    public static TrabajadorDto obtenerTrabajador(int indice)
    {
        TrabajadorDto obj;
        
        TrabajadorServicio trabajadorServicio = new TrabajadorServicio();
        obj = trabajadorServicio.getOne(indice);
        
        return obj;
    }
}
