/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniMagdalena.controlador.sede;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.vista.sede.VistaSedeAdmin;
import com.uniMagdalena.vista.sede.VistaSedeCarrusel;
import com.uniMagdalena.vista.sede.VistaSedeCrear;
import com.uniMagdalena.vista.sede.VistaSedeEditar;
import com.uniMagdalena.vista.sede.VistaSedeListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaSedeAdmin ventana = new VistaSedeAdmin(miEscenario,princ, pane ,anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, SedeDto objSede, int posicion)
    {
        VistaSedeEditar ventana = new VistaSedeEditar(miEscenario, princ, pane, anchito, altito, objSede, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaSedeCarrusel ventana = new VistaSedeCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
}
