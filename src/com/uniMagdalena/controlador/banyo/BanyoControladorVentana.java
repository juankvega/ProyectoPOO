
package com.uniMagdalena.controlador.banyo;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.vista.banyo.VistaBanyoAdmin;
import com.uniMagdalena.vista.banyo.VistaBanyoCarrusel;
import com.uniMagdalena.vista.banyo.VistaBanyoCrear;
import com.uniMagdalena.vista.banyo.VistaBanyoEditar;
import com.uniMagdalena.vista.banyo.VistaBanyoListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BanyoControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaBanyoCrear ventana = new VistaBanyoCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaBanyoListar ventana = new VistaBanyoListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaBanyoAdmin ventana = new VistaBanyoAdmin(miEscenario, princ, pane, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
     
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, BanyoDto objbanyo, int posicion)
    {
         VistaBanyoEditar ventana = new VistaBanyoEditar(miEscenario, princ, pane, anchito, altito, objbanyo, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
        public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaBanyoCarrusel ventana = new VistaBanyoCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
    
}
