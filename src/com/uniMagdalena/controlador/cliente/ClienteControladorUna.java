
package com.uniMagdalena.controlador.cliente;

import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.servicio.ClienteServicio;

public class ClienteControladorUna 
{
    public static ClienteDto obtenerCliente(int indice)
    {
        ClienteDto obj;
        
        ClienteServicio clienteServicio = new ClienteServicio();
        obj = clienteServicio.getOne(indice);
        
        return obj;
    }
}
