
package com.uniMagdalena.controlador.genero;

import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.servicio.GeneroServicio;

public class GeneroControladorUna
{
    public static GeneroDto obtenerGenero(int indice)
    {
        GeneroDto obj;
        
        GeneroServicio generoServicio = new GeneroServicio();
        obj = generoServicio.getOne(indice);
        
        return obj;
    }
}
