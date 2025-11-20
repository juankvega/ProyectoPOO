
package com.uniMagdalena.controlador.banyo;

import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.servicio.BanyoServicio;
import java.util.List;

public class BanyoControladorListar 
{
    public static List<BanyoDto> arregloBanyos()
    {
        BanyoServicio miDao = new BanyoServicio();
        List<BanyoDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
    public static int cantidadBanyos()
    {
        BanyoServicio miDao = new BanyoServicio();
        int cant = miDao.numRows();
        return cant;
    }
}
