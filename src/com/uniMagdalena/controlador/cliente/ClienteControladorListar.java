
package com.uniMagdalena.controlador.cliente;

import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.servicio.ClienteServicio;
import java.util.List;

public class ClienteControladorListar 
{
    public static List<ClienteDto> arregloClientes()
    {
        ClienteServicio miDao = new ClienteServicio();
        List<ClienteDto> arreglo = miDao.selectFrom();
        return arreglo;
    }
    
    public static int cantidadClientes()
    {
        ClienteServicio miDao = new ClienteServicio();
        int cant = miDao.numRows();
        return cant;
    }
}
