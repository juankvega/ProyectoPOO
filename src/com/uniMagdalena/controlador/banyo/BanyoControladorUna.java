
package com.uniMagdalena.controlador.banyo;

import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.servicio.BanyoServicio;

public class BanyoControladorUna 
{
    public static BanyoDto obtenerBanyo(int indice)
    {
        BanyoDto obj;
        BanyoServicio banyoServicio = new BanyoServicio();
        obj = banyoServicio.getOne(indice);
        
        return obj;
    }
}
