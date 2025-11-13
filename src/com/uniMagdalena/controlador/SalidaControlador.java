
package com.uniMagdalena.controlador;

import com.uniMagdalena.recurso.utilidad.Icono;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class SalidaControlador
{
    public static void verificar(Stage miEscenario)
    {
        Alert msg = new Alert(Alert.AlertType.CONFIRMATION);
        msg.setTitle("AMF");
        msg.initOwner(miEscenario);
        msg.setHeaderText(null);
        msg.getDialogPane().setGraphic(Icono.obtenerIcono("iconByeBye.png",40));
        msg.setContentText("¿Desea cerrar la aplicacion?");
        
        if (msg.showAndWait().get() == ButtonType.OK){
            miEscenario.close();
        }
    }
}
