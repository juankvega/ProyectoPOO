
package com.uniMagdalena.controlador.Producto;

import com.uniMagdalena.controlador.varios.ControladorEfecto;
import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.vista.producto.VistaProductoAdmin;
import com.uniMagdalena.vista.producto.VistaProductoCrear;
import com.uniMagdalena.vista.producto.VistaProductoListar;
import com.uniMagdalena.vista.producto.VistaProductoEditar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ProductoControladorVentana 
{
    public static StackPane crear(Stage miEscenario, double anchito, double altito)
    {
        VistaProductoCrear ventana = new VistaProductoCrear(miEscenario, anchito, altito);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
    
    public static StackPane listar(Stage miEscenario, double anchito, double altito)
    {
        VistaProductoListar ventana = new VistaProductoListar(miEscenario, anchito, altito);
       StackPane contenedor = ventana.getMiFormulario();
       
       ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
       return contenedor;
   }
   
   public static StackPane administrar(Stage miEscenario, BorderPane princ, Pane pane ,double anchito, double altito)
   {
       VistaProductoAdmin ventana = new VistaProductoAdmin(miEscenario, princ, pane, anchito, altito);
       StackPane contenedor = ventana.getMiFormulario();
       
       ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
       return contenedor;
   }
     
    public static StackPane editar(Stage miEscenario, BorderPane princ, Pane pane,double anchito, double altito, ProductoDto objProducto, int posicion)
    {
        VistaProductoEditar ventana = new VistaProductoEditar(miEscenario, princ, pane, anchito, altito, objProducto, posicion);
        StackPane contenedor = ventana.getMiFormulario();
        
        ControladorEfecto.aplicarEfecto(contenedor, anchito, altito);
        return contenedor;
    }
}    
//    public static BorderPane carrusel(Stage miEscenario, BorderPane princ, Pane pane, double anchito, double altio, int indice )
//    {
//        VistaProductoCarrusel ventana = new VistaProductoCarrusel(miEscenario, princ, pane, anchito, altio, indice);
//        BorderPane contenedor = ventana.getMiBorderPane();
//        
//        ControladorEfecto.aplicarEfecto(contenedor, anchito, altio);
//        return contenedor;
//    }
//}
