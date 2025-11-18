
package com.uniMagdalena.controlador.cliente;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.vista.cliente.VistaClienteAdmin;
import com.uniMagdalena.vista.cliente.VistaClienteCarrusel;
import com.uniMagdalena.vista.cliente.VistaClienteCrear;
import com.uniMagdalena.vista.cliente.VistaClienteEditar;
import com.uniMagdalena.vista.cliente.VistaClienteListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClienteControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaClienteCrear ventana = new VistaClienteCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaClienteListar ventana = new VistaClienteListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
     public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaClienteAdmin ventana = new VistaClienteAdmin(miEscenario, princ, pane, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
     
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, ClienteDto objCliente, int posicion)
    {
        VistaClienteEditar ventana = new VistaClienteEditar(miEscenario, princ, pane, anchito, altito, objCliente, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
        public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaClienteCarrusel ventana = new VistaClienteCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
}
