
package com.uniMagdalena.controlador.genero;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.vista.genero.VistaGeneroCrear;
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
}
