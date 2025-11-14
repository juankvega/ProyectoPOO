package com.uniMagdalena.vista.sede;

import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Marco;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VistaSedeCrear extends StackPane {
    
    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    private static final int TAMANIO_FUENTE = 18;
    private static final double AJUSTE_ARRIBA = -0.10;

    private final GridPane miGrilla;
    private final Rectangle miMarco;
    
    private TextField cajaNombreSede;
    private ComboBox<String> cbmDepartamentoSede;
    private TextField cajaCiudadSede;
    private TextField cajaUbicacionSede;
    private RadioButton rButtonUbicacionSede;
    
    // Para las imagenes
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    public VistaSedeCrear(Stage esce, double ancho, double alto) {
        setAlignment(Pos.CENTER);
        miGrilla = new GridPane();
        miMarco = Marco.pintar(esce, Configuracion.MARCO_ALTO_PORCENTAJE,
                Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO,
                Configuracion.COLOR_BORDE);
        getChildren().addAll(miMarco);
        
        reUbicarFormulario();
        pintarTitulo();
        
    }
    
    private void reUbicarFormulario() {
        Runnable organizar = () -> {
            double altoDelFrm = miMarco.getHeight() * AJUSTE_ARRIBA;
            if (altoDelFrm > 0) {
                miGrilla.setTranslateY(altoDelFrm / 2 + altoDelFrm);
            }
        };
        organizar.run();
        miMarco.heightProperty().addListener(
                ((obs, antes, despues) -> {
                    organizar.run();
                }));
    }
    
    private void configurarLaGrilla(double anchito, double altito) {
        double porcentaje_ancho = 0.7;
        double anchoMarco = anchito * porcentaje_ancho;

        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setPrefSize(anchoMarco, altito);
        miGrilla.setMinSize(anchoMarco, altito);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        col2.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);

        miGrilla.getColumnConstraints().addAll(col1, col2, col3);

        // Cambiado a 7 filas para acomodar todos los elementos
        for (int i = 0; i < 7; i++) {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }
    private void pintarTitulo() {
        Text titulo = new Text("Formulario creacion de pelicula");
        titulo.setFill(Color.web(Configuracion.COLOR1));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(25, 0, 0, 0));
        // orden: columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void pintarFormulario(){
    
        // Nombre sede
        Label lblNombreSede = new Label("Nombre de la sede");
        lblNombreSede.setFont(Font.font("Verdana", TAMANIO_FUENTE));
        miGrilla.add(lblNombreSede, 0, 1);

        cajaNombreSede = new TextField();
        cajaNombreSede.setPromptText("Escribe el nombre de la sede");
        GridPane.setHgrow(cajaNombreSede, Priority.ALWAYS);
        cajaNombreSede.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaNombreSede, 1, 1);
        
        // Departamento sede
        Label lblDepartamentoSede = new Label("Departamento de la Sede");
        lblDepartamentoSede.setFont(Font.font("Verdana", TAMANIO_FUENTE));
        miGrilla.add(lblDepartamentoSede, 0, 2);
        
        cbmDepartamentoSede = new ComboBox<>();
        cbmDepartamentoSede.setMaxWidth(Double.MAX_VALUE);
        cbmDepartamentoSede.setPrefHeight(ALTO_CAJA);
        cbmDepartamentoSede.getItems().addAll("Seleccionar departamento",
                "Atlantico", "Cesar","Magdalena");
        cbmDepartamentoSede.getSelectionModel().select(0);
        miGrilla.add(cbmDepartamentoSede, 1, 2);
    }
    
    
    
}
