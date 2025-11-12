
package com.uniMagdalena.vista.gestor;

import com.unimagdalena.recurso.constante.Configuracion;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class VistaAdmin 
{
    private final Scene miEscena;
    private final Stage miEscenario;
    
    private final Pane miPanelCuerpo;
    private final BorderPane miPanelPrincipal;

    public VistaAdmin(Stage escenario) 
    {
        miEscenario = escenario;
        miPanelCuerpo = new Pane();
        miPanelPrincipal = new BorderPane();
        
        VistaCabecera cabecera = new VistaCabecera(escenario, miPanelPrincipal, Configuracion.ALTO_CABECERA);
        
        miPanelPrincipal.setTop(cabecera);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        
        miEscena = new Scene(miPanelPrincipal, Configuracion.ANCHO_APP, Configuracion.ALTO_APP);
        
        miEscenario.setScene(miEscena);
    }
    
    public void configurarSalida(Runnable accion)
    {
        miEscenario.setOnCloseRequest((e)->{
            e.consume();
            accion.run();
        });
    }
    
    
}
