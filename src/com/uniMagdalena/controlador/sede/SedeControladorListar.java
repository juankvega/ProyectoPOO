
package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.servicio.SedeServicio;
import java.util.List;

public class SedeControladorListar 
{
    public static List<SedeDto> arregloSedes()
    {
        SedeServicio miDao = new SedeServicio();
        List<SedeDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    public static int cantidadSedes()
    {
        SedeServicio miDao = new SedeServicio();
        int cant = miDao.numRows();
        return cant;
    }
}
