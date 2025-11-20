/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.servicio.SedeServicio;

/**
 *
 * @author macbook
 */
public class SedeControladorEditar {
    
    public static boolean actualizar(int indiceExterno, SedeDto objExterno, String rutaImagen)
    {
        boolean correcto;
        
        SedeServicio sedeServicio = new SedeServicio();
        
        correcto = sedeServicio.updateSet(indiceExterno, objExterno, rutaImagen);
        return correcto;
    }
    
}
