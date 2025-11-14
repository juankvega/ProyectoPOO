
package com.uniMagdalena.controlador.genero;


import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.servicio.GeneroServicio;
import java.util.List;

public class GeneroControladorListar
{
    public static List<GeneroDto> arregloGeneros()
    {
        GeneroServicio miDao = new GeneroServicio();
        List<GeneroDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
        public static List<GeneroDto> arregloGenerosActivos()
    {
        GeneroServicio miDao = new GeneroServicio();
        List<GeneroDto> arreglo = miDao.selectFromWhereEstadoTrue();
        return arreglo;
    }
    public static int cantidadGeneros()
    {
        GeneroServicio miDao = new GeneroServicio();
        int cant = miDao.numRows();
        return cant;
    }
}
