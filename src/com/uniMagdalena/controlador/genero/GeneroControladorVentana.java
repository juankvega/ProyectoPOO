
package com.uniMagdalena.controlador.genero;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.vista.genero.VistaGeneroAdmin;
import com.uniMagdalena.vista.genero.VistaGeneroCarrusel;
import com.uniMagdalena.vista.genero.VistaGeneroCrear;
import com.uniMagdalena.vista.genero.VistaGeneroEditar;
import com.uniMagdalena.vista.genero.VistaGeneroListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GeneroControladorVentana
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaGeneroCrear ventana = new VistaGeneroCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaGeneroListar ventana = new VistaGeneroListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaGeneroAdmin ventana = new VistaGeneroAdmin(miEscenario,princ, pane ,anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, GeneroDto objGenero, int posicion)
    {
        VistaGeneroEditar ventana = new VistaGeneroEditar(miEscenario, princ, pane, anchito, altito, objGenero, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaGeneroCarrusel ventana = new VistaGeneroCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
}
