package com.uniMagdalena.vista.sede;

import com.uniMagdalena.controlador.genero.GeneroControladorListar;
import com.uniMagdalena.controlador.sede.SedeControladorListar;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Marco;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VistaSedeListar extends SubScene{
    
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<SedeDto> miTabla;
    private final Rectangle miMarco;
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA;

    public VistaSedeListar(Stage ventanaPadre, double ancho, double alto) {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);
        
        laVentanaPrincipal = ventanaPadre;
        
        miTabla = new TableView<>();
        miCajaVertical = new VBox();
        
        miMarco = Marco.pintar(laVentanaPrincipal, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
        
        configurarmiCajaVertical();
        armarTitulo();
        crearTabla();
    }
    
    public StackPane getMiFormulario() {
        return miFormulario;
    }
    
    private void configurarmiCajaVertical(){
        miCajaVertical.setSpacing(20);
        miCajaVertical.setAlignment(Pos.TOP_CENTER);
        miCajaVertical.prefWidthProperty().bind(laVentanaPrincipal.widthProperty());
        miCajaVertical.prefHeightProperty().bind(laVentanaPrincipal.heightProperty());

    }
    
    private void armarTitulo() {
    Region separadorTitulo = new Region();
    separadorTitulo.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.05));
    int canti = SedeControladorListar.cantidadSedes();
    Text miTitulo = new Text("Listado de sedes - (" + canti + ") ");
    miTitulo.setFill(Color.web("#54e8b7"));
    miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
    miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
    private TableColumn<SedeDto, Integer> crearColumnaCodigo() {
    TableColumn<SedeDto, Integer> columna = new TableColumn<>("Codigo");
    columna.setCellValueFactory(new PropertyValueFactory<>("idSede"));
    columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
    columna.setStyle(ESTILO_CENTRAR);
    return columna;
    } 
    
    private TableColumn<SedeDto, String> crearColumnaNombre() {
        TableColumn<SedeDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreSede"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<SedeDto, String> crearColumnaCiudad() {
        TableColumn<SedeDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("ciudadSede"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<SedeDto, String> crearColumnaUbicacion() {
        TableColumn<SedeDto, String> columna = new TableColumn<>("Ubicacion");
        columna.setCellValueFactory(new PropertyValueFactory<>("ubicacionSede"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    
    
    private TableColumn<SedeDto, String> crearColumna24horas() {
            TableColumn<SedeDto, String> columna = new TableColumn<>("Es 24 horas");
        columna.setCellValueFactory(obj -> {
            String venticuatroHoras = obj.getValue().getEs24horasSede() ? "Si" : "No";
            return new SimpleStringProperty(venticuatroHoras);
        });

        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String venticuatroHorasTxT, boolean empty) {
                super.updateItem(venticuatroHorasTxT, empty);
                if (empty || venticuatroHorasTxT == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(venticuatroHorasTxT);
                    setStyle("Si".equals(venticuatroHorasTxT) ? ESTILO_ELVERDE : ESTILO_ELROJO);
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }
    
    private TableColumn<SedeDto, Short> crearColumnaCantidadSalas() {
        TableColumn<SedeDto, Short> columna = new TableColumn<>("Salas");
        columna.setCellValueFactory(new PropertyValueFactory<>("salasSede"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
        private TableColumn<SedeDto, Short> crearColumnaCantidadBanyos() {
        TableColumn<SedeDto, Short> columna = new TableColumn<>("Baños");
        columna.setCellValueFactory(new PropertyValueFactory<>("banyosSede"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<SedeDto, String> crearColumnaImagen()
    {
        TableColumn<SedeDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoSede"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private void configurarColumnas() {
        miTabla.getColumns().addAll(
                List.of(
                        crearColumnaCodigo(), crearColumnaNombre(), crearColumnaCiudad(), crearColumnaUbicacion(), crearColumna24horas(), crearColumnaCantidadSalas(), crearColumnaCantidadBanyos() ,crearColumnaImagen()
                )
        );
    }
    
    private void crearTabla() {
        configurarColumnas();

        List<SedeDto> arrSedes = SedeControladorListar.arregloSedes();
        ObservableList<SedeDto> datosTabla = FXCollections.observableArrayList(arrSedes);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay sedes registradas"));

        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        miTabla.maxWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        miTabla.maxHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.5));

        laVentanaPrincipal.heightProperty().addListener((o, oldval, newval)
                -> miTabla.setPrefHeight(newval.doubleValue())
        );
        VBox.setVgrow(miTabla, Priority.ALWAYS);

        miCajaVertical.getChildren().add(miTabla);
        miFormulario.getChildren().add(miCajaVertical);
    }
    
}
