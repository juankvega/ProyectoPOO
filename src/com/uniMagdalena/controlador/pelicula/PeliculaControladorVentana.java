
package com.uniMagdalena.controlador.pelicula;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.vista.pelicula.VistaPeliculaCrear;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PeliculaControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaPeliculaCrear ventana = new VistaPeliculaCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
}
