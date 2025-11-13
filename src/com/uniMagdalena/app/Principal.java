
package com.uniMagdalena.app;

import com.uniMagdalena.controlador.SalidaControlador;
import com.uniMagdalena.recurso.constante.IconoNombre;
import com.uniMagdalena.vista.gestor.VistaAdmin;
import com.uniMagdalena.recurso.constante.Persistencia;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Principal extends Application
{
     private VistaAdmin adminVista;

    public Principal() {
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        String iconoApp = Persistencia.RUTA_IMAGENES_INTERNAS + IconoNombre.ICONO_APP;
        Image miImagen = new Image(getClass().getResourceAsStream(iconoApp));

        adminVista = new VistaAdmin();
        stage = adminVista.getMiEscenario();
        stage.setTitle("CineMAR");
        stage.show();
        stage.getIcons().add(miImagen);
        habilitarEquisCerrar(stage);

    }

    private void habilitarEquisCerrar(Stage miEscenario) {
        miEscenario.setOnCloseRequest((event)
                -> {
            event.consume();
            SalidaControlador.verificar(miEscenario);
        });

    }
}
