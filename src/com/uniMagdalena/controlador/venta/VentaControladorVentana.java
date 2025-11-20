
package com.uniMagdalena.controlador.venta;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.vista.venta.VistaVentaAdmin;
import com.uniMagdalena.vista.venta.VistaVentaCarrusel;
import com.uniMagdalena.vista.venta.VistaVentaCrear;
import com.uniMagdalena.vista.venta.VistaVentaEditar;
import com.uniMagdalena.vista.venta.VistaVentaListar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class VentaControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaVentaCrear ventana = new VistaVentaCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
     public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaVentaListar ventana = new VistaVentaListar(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
     
       public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
    {
        VistaVentaAdmin ventana = new VistaVentaAdmin(miEscenario, princ, pane, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
     
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, VentaDto objVenta, int posicion)
    {
         VistaVentaEditar ventana = new VistaVentaEditar(miEscenario, princ, pane, anchito, altito, objVenta, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
    {
        VistaVentaCarrusel ventana = new VistaVentaCarrusel(miEscenario, princ, pane, anchito, altio, indice);
        BorderPane contenedor = ventana.getMiBorderPane();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
        return contenedor;
    }
}
