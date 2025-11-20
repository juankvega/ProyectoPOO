/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.servicio.SedeServicio;

/**
 *
 * @author macbook
 */
public class SedeControladorEliminar {
    
    public static Boolean borrar(Integer indice) {
        Boolean correcto;
        SedeServicio sedeServicio = new SedeServicio();
        correcto = sedeServicio.deleteFrom(indice);
        return correcto;
    }
    
}
