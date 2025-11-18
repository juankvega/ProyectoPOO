package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.servicio.SedeServicio;


public class SedeControladorUna {
    
    public static SedeDto obtenerSede(int indice)
    {
        SedeDto obj;
        
        SedeServicio sedeServicio = new SedeServicio();
        obj = sedeServicio.getOne(indice);
        
        return obj;
    }
}
