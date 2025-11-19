/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.vista.sede.VistaSedeCrear;
import com.uniMagdalena.vista.sede.VistaSedeListar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author macbook
 */
public class SedeControladorVentana {
    
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaSedeCrear ventana = new VistaSedeCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaSedeListar ventana = new VistaSedeListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
}
