
package com.uniMagdalena.vista.sede;

import com.uniMagdalena.controlador.sede.SedeControladorEliminar;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.controlador.sede.SedeControladorListar;
import com.uniMagdalena.controlador.sede.SedeControladorVentana;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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


public class VistaSedeAdmin extends SubScene {
    
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<SedeDto> miTabla;
    private final Rectangle miMarco;
    private HBox miCajaHorizontal;
    
    private Text miTitulo;
    
    
        private final ObservableList<SedeDto> datosTabla
            = FXCollections.observableArrayList();
        
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
        

    public VistaSedeAdmin(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto) {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);
        
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        miTabla = new TableView<>();
        miCajaVertical = new VBox();
        
        miMarco = Marco.pintar(laVentanaPrincipal, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
        
        configurarmiCajaVertical();
        armarTitulo();
        crearTabla();
        mostrarIconosAdministrar();
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
    miTitulo = new Text("Listado de sedes - (" + canti + ") ");
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
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoSede"));
        columna.setCellFactory(column -> new TableCell<SedeDto, String>()
        {
            @Override
            protected void updateItem(String nombreImagen, boolean bandera) {
                super.updateItem(nombreImagen, bandera);
                if (bandera || nombreImagen == null) {
                    setGraphic(null);
                } else {
                    setGraphic(Icono.obtenerIconoExterno(nombreImagen, 100));
                }
            }
            
        });
        
        
        
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private void configurarColumnas() {
        miTabla.getColumns().addAll(
                List.of(
                        crearColumnaCodigo(), crearColumnaNombre(), crearColumnaCiudad(), crearColumnaUbicacion(), crearColumna24horas(), crearColumnaCantidadSalas(),crearColumnaCantidadBanyos() ,crearColumnaImagen()
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
    
    
    private void mostrarIconosAdministrar() {
        int anchoBoton = 40;
        int tamanioIcono = 18;
        // Botón para eliminar
        // ***************************************************
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(
                Icono.obtenerIcono(
                        Configuracion.ICONO_BORRAR,
                        tamanioIcono)
        );
        btnEliminar.setOnAction((e) -> {
            if (miTabla.getSelectionModel()
                    .getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING,
                        laVentanaPrincipal, "Te veo", "MAL ! agarra algo");
            } else {
                SedeDto objSede = miTabla
                        .getSelectionModel()
                        .getSelectedItem();
                if (objSede.getSalasSede() == 0 && objSede.getBanyosSede() == 0) {
                    String msg1, msg2, msg3, msg4;
                    msg1 = "¿Estás seguro mi vale?";
                    msg2 = "\nCódigo: " + objSede.getIdSede();
                    msg3 = "\nNombre: " + objSede.getNombreSede();
                    msg4 = "\nSi se fue, se fue!";

                    Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
                    mensajito.setTitle("Telo advierto");
                    mensajito.setHeaderText(null);
                    mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
                    mensajito.initOwner(null);

                    if (mensajito.showAndWait().get() == ButtonType.OK) {
                        int fila = miTabla.getSelectionModel().getSelectedIndex();
                        if (SedeControladorEliminar.borrar(fila)) {
                                int cant = SedeControladorListar.cantidadSedes();
                            miTitulo.setText("Administrador de sedes - (" + cant + ") ");
                            List<SedeDto> quedaron =  SedeControladorListar.arregloSedes();
                            datosTabla.setAll(quedaron);
                            miTabla.refresh();
                            
                            Mensaje.mostrar(
                                    Alert.AlertType.INFORMATION,
                                    laVentanaPrincipal, "EXITO",
                                    "Que buen inglés, lo borré");
                        } else {
                            Mensaje.mostrar(
                                    Alert.AlertType.ERROR,
                                    laVentanaPrincipal, "Pailas",
                                    "No lo pude borrar!");
                        }

                    } else {
                        miTabla.getSelectionModel().clearSelection();
                    }

                } else {
                    Mensaje.mostrar(
                            Alert.AlertType.ERROR,
                            laVentanaPrincipal, "Ey",
                            "Ya tiene salas y/o baños");
                }
            }
        });
    
        // Botón para actualizar
        // ***************************************************
        Button btnActualizar = new Button();
        btnActualizar.setPrefWidth(anchoBoton);
        btnActualizar.setCursor(Cursor.HAND);
        btnActualizar.setGraphic(
                Icono.obtenerIcono(
                        Configuracion.ICONO_EDITAR,
                        tamanioIcono)
        );
        btnActualizar.setOnAction((ActionEvent e) -> 
        {
            // Acá va el código de actualizar
                if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "No ha seleccionado una sede para editar");
                }else
                {
                    SedeDto objSede = miTabla.getSelectionModel().getSelectedItem();
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    
                    panelCuerpo = SedeControladorVentana.editar(laVentanaPrincipal, panelPrincipal, panelCuerpo, Configuracion.ANCHO_APP, Configuracion.ALTO_APP, objSede, posicion);
                    panelPrincipal.setCenter(null);
                    panelPrincipal.setCenter(panelCuerpo);
                }
           
        });
        // ***************************************************

        // Botón para cancelar
        // ***************************************************
        Button btnCancelar = new Button();
        btnCancelar.setPrefWidth(anchoBoton);
        btnCancelar.setCursor(Cursor.HAND);
        btnCancelar.setGraphic(
                Icono.obtenerIcono(
                        Configuracion.ICONO_CANCELAR,
                        tamanioIcono)
        );
        btnCancelar.setOnAction((e) -> {
            miTabla.getSelectionModel().clearSelection();
        });
        // ***************************************************

        miCajaHorizontal = new HBox(6);
        miCajaHorizontal.setAlignment(Pos.CENTER);
        miCajaHorizontal.getChildren()
                .addAll(btnEliminar, btnActualizar, btnCancelar);
        miCajaVertical.getChildren().add(miCajaHorizontal);
        
    }
        
}
