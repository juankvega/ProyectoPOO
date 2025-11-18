
package com.uniMagdalena.controlador.trabajador;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.vista.trabajador.VistaTrabajadorAdmin;
import com.uniMagdalena.vista.trabajador.VistaTrabajadorCarrusel;
import com.uniMagdalena.vista.trabajador.VistaTrabajadorCrear;
import com.uniMagdalena.vista.trabajador.VistaTrabajadorEditar;
import com.uniMagdalena.vista.trabajador.VistaTrabajadorListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TrabajadorControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaTrabajadorCrear ventana = new VistaTrabajadorCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaTrabajadorListar ventana = new VistaTrabajadorListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaTrabajadorAdmin ventana = new VistaTrabajadorAdmin(miEscenario, princ, pane, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
     
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, TrabajadorDto objTrabajador, int posicion)
    {
        VistaTrabajadorEditar ventana = new VistaTrabajadorEditar(miEscenario, princ, pane, anchito, altito, objTrabajador, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaTrabajadorCarrusel ventana = new VistaTrabajadorCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
}
