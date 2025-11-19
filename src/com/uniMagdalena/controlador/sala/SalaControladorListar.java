
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.servicio.SalaServicio;
import java.util.List;

public class SalaControladorListar 
{
    public static List<SalaDto> arregloSalas()
    {
        SalaServicio miDao = new SalaServicio();
        List<SalaDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
    public static int cantidadSalas()
    {
        SalaServicio miDao = new SalaServicio();
        int cant = miDao.numRows();
        return cant;
    }
    
    public static List<SalaDto> arregloSalasPorSede(Integer idSede)
    {
        SalaServicio miDao = new SalaServicio();
        List<SalaDto> arreglo = miDao.selectFromSede(idSede);
        return arreglo;
    }
}
