
package com.uniMagdalena.vista.gestor;

import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Icono;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VistaAcerca 
{
    public final static String LBL_TEXTO = "#2E2E2E";
    public final static String ACERCA_NOMBRE1 = "Juan Vega";
    public final static String ACERCA_NOMBRE2 = "Lucio Linares";
    public final static String ACERCA_NOMBRE3 = "Meudy Lugo";
    public final static String ACERCA_FOTO1 = "Juan.png";
    public final static String ACERCA_FOTO2 = "Lucio.png";
    public final static String ACERCA_FOTO3 = "Meudy.png";
    public final static String ACERCA_CODIGO1 = "2025114035";
    public final static String ACERCA_CODIGO2 = "2025114017";
    public final static String ACERCA_CODIGO3 = "2025114042";
    public final static String ACERCA_CORREO1 = "jcvegaf@unimagdalena.edu.co";
    public final static String ACERCA_CORREO2 = "ltlinares@unimagdalena.edu.co";
    public final static String ACERCA_CORREO3 = "mjlugo@unimagdalena.edu.co";
    
  public static void mostrar(Stage escenarioPadre, double anchoPanel, double altoPanel)
    {
        Stage escenarioModal = new Stage();
        VBox miPanel = new VBox(24);
        miPanel.setAlignment(Pos.CENTER);
        miPanel.setPadding(new Insets(10, 0, 0, 0));
        miPanel.setStyle(Configuracion.CABECERA_COLOR_FONDO);
        
        HBox contenedorPersonas = new HBox(40);
        contenedorPersonas.setAlignment(Pos.CENTER);
        
        VBox persona1 = crearPanelPersona(ACERCA_FOTO1, ACERCA_NOMBRE1, ACERCA_CORREO1, ACERCA_CODIGO1);
        VBox persona2 = crearPanelPersona(ACERCA_FOTO2, ACERCA_NOMBRE2, ACERCA_CORREO2, ACERCA_CODIGO2);
        VBox persona3 = crearPanelPersona(ACERCA_FOTO3, ACERCA_NOMBRE3, ACERCA_CORREO3, ACERCA_CODIGO3);
        
        contenedorPersonas.getChildren().addAll(persona1, persona2, persona3);
        
        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setPrefWidth(160);
        btnCerrar.setTextFill(Color.web("#E82E68"));
        btnCerrar.setOnAction(event -> escenarioModal.close());
        
        miPanel.getChildren().addAll(contenedorPersonas, btnCerrar);
        
        Scene nuevaEscena = new Scene(miPanel, anchoPanel, altoPanel);
        escenarioModal.setScene(nuevaEscena);
        escenarioModal.initModality(Modality.APPLICATION_MODAL);
        escenarioModal.initStyle(StageStyle.UTILITY);
        escenarioModal.setTitle("Acerca de ...");
        escenarioModal.show();
        
        escenarioPadre.getScene().getRoot().setOpacity(0.2);
        escenarioModal.setOnHidden(event -> escenarioPadre.getScene().getRoot().setOpacity(1.0));
    }
    
    private static VBox crearPanelPersona(String rutaFoto, String nombre, String correo, String codigo)
    {
        VBox panel = new VBox(6);
        panel.setAlignment(Pos.CENTER);
        
        ImageView foto = Icono.obtenerIcono(rutaFoto, 200);
        foto.setPreserveRatio(true);
        
        Label lblNombre = new Label(nombre);
        lblNombre.setAlignment(Pos.CENTER);
        lblNombre.setTextFill(Color.web(LBL_TEXTO));
        lblNombre.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        
        Label lblCorreo = new Label(correo);
        lblCorreo.setAlignment(Pos.CENTER);
        lblCorreo.setTextFill(Color.web(LBL_TEXTO));
        lblCorreo.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        
        Label lblCodigo = new Label(codigo);
        lblCodigo.setAlignment(Pos.CENTER);
        lblCodigo.setTextFill(Color.web(LBL_TEXTO));
        lblCodigo.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        
        panel.getChildren().addAll(foto, lblNombre, lblCorreo, lblCodigo);
        
        return panel;
    }
}
