
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.vista.sala.VistaSalaAdmin;
import com.uniMagdalena.vista.sala.VistaSalaCarrusel;
import com.uniMagdalena.vista.sala.VistaSalaCrear;
import com.uniMagdalena.vista.sala.VistaSalaEditar;
import com.uniMagdalena.vista.sala.VistaSalaListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SalaControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaSalaCrear ventana = new VistaSalaCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
     public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaSalaListar ventana = new VistaSalaListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
     
          public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaSalaAdmin ventana = new VistaSalaAdmin(miEscenario, princ, pane, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
          
      public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, SalaDto objSala, int posicion)
    {
        VistaSalaEditar ventana = new VistaSalaEditar(miEscenario, princ, pane, anchito, altito, objSala, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
      
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaSalaCarrusel ventana = new VistaSalaCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
}
