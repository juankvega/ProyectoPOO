
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.servicio.SalaServicio;

public class SalaControladorUna 
{
    public static SalaDto obtenerSala(int indice)
    {
        SalaDto obj;
        SalaServicio salaServicio = new SalaServicio();
        obj = salaServicio.getOne(indice);
        
        return obj;
    }
}
