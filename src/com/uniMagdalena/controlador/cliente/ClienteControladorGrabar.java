
package com.uniMagdalena.controlador.cliente;

import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.servicio.ClienteServicio;

public class ClienteControladorGrabar 
{
    public static Boolean crearCliente(ClienteDto dto, String laRuta)
    {
        boolean correcto = false;
        ClienteServicio clienteServicio = new ClienteServicio();
        ClienteDto dtoRespuesta = clienteServicio.insertInto(dto, laRuta);
        if(dtoRespuesta != null)
        {
            correcto = true;
        }
        return correcto;
    }
}
