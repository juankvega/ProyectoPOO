
package com.uniMagdalena.app;

import com.uniMagdalena.controlador.SalidaControlador;
import com.uniMagdalena.vista.gestor.VistaAdmin;
import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application
{
    private VistaAdmin vistaAdmin;
    
    public static void main(String[] args) 
    {
        launch();
    }
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        vistaAdmin = new VistaAdmin(stage);
        stage.setTitle("CineMar");
        vistaAdmin.configurarSalida(()-> SalidaControlador.verificar(stage));
        stage.show();
    }
}
