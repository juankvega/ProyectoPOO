
package com.uniMagdalena.controlador.sala;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.vista.sala.VistaSalaCrear;
import com.uniMagdalena.vista.sala.VistaSalaListar;
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
}
