
package com.uniMagdalena.controlador.trabajador;

import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.servicio.TrabajadorServicio;
import java.util.List;

public class TrabajadorControladorListar 
{
    public static List<TrabajadorDto> arregloTrabajadores()
    {
        TrabajadorServicio miDao = new TrabajadorServicio();
        List<TrabajadorDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
     public static List<TrabajadorDto> arregloTrabajadoresdeAseo()
    {
        TrabajadorServicio miDao = new TrabajadorServicio();
        List<TrabajadorDto> arreglo = miDao.selectFromWhereTrabajadorAseo();
        return arreglo;
    }
     
    public static int cantidadTrabajadores()
    {
        TrabajadorServicio miDao = new TrabajadorServicio();
        int cant = miDao.numRows();
        return cant;
    }
    
}
