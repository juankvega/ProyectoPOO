
package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.servicio.SedeServicio;


public class SedeControladorGrabar {
    
    public static  Boolean crearSede(SedeDto dto, String laRuta) {
        
        boolean correcto = false;
        SedeServicio sedeServicio = new SedeServicio();
        SedeDto dtoRespuesta = sedeServicio.insertInto(dto, laRuta);
        if (dtoRespuesta != null) 
        {
            correcto = true;
        }
        return correcto;
    }
}
