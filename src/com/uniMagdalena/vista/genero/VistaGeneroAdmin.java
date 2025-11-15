
package com.uniMagdalena.vista.genero;

import com.uniMagdalena.controlador.genero.GeneroControladorEliminar;
import com.uniMagdalena.controlador.genero.GeneroControladorListar;
import com.uniMagdalena.controlador.genero.GeneroControladorVentana;
import com.uniMagdalena.dto.GeneroDto;
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

public class VistaGeneroAdmin extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<GeneroDto> miTabla;
    private final Rectangle miMarco;
    private HBox miCajaHorizontal;
    
    private Text miTitulo;
    
    
        private final ObservableList<GeneroDto> datosTabla
            = FXCollections.observableArrayList();
        
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    public VistaGeneroAdmin(Stage ventanaPadre,BorderPane princ, Pane pane ,double ancho, double alto)
    {
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
    
    public StackPane getMiFormulario()
    {
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
        int canti = GeneroControladorListar.cantidadGeneros();
        miTitulo = new Text("Listado de géneros - (" + canti + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
        private TableColumn<GeneroDto, Integer> crearColumnaCodigo() {
        TableColumn<GeneroDto, Integer> columna = new TableColumn<>("Codigo");
        columna.setCellValueFactory(new PropertyValueFactory<>("idGenero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<GeneroDto, String> crearColumnaNombre() {
        TableColumn<GeneroDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }

    private TableColumn<GeneroDto, String> crearColumnaEstado() {
        TableColumn<GeneroDto, String> columna = new TableColumn<>("Estado");
        columna.setCellValueFactory(obj -> {
            String estado = obj.getValue().getEstadoGenero() ? "Activo" : "Inactivo";
            return new SimpleStringProperty(estado);
        });

        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String estadoTxT, boolean empty) {
                super.updateItem(estadoTxT, empty);
                if (empty || estadoTxT == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estadoTxT);
                    setStyle("Activo".equals(estadoTxT) ? ESTILO_ELVERDE : ESTILO_ELROJO);
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }
    
    private TableColumn<GeneroDto, Short> crearColumnaPopularidadGenero()
    {
        TableColumn<GeneroDto, Short> columna = new TableColumn<>("Popularidad");
        columna.setCellValueFactory(new PropertyValueFactory<>("popularidadGenero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna; 
    }
    
    private TableColumn<GeneroDto, String> crearColumnaClasico() {
        TableColumn<GeneroDto, String> columna = new TableColumn<>("Clásico");
        columna.setCellValueFactory(obj -> {
            String clasico = obj.getValue().getEsClasicoGenero() ? "Si" : "No";
            return new SimpleStringProperty(clasico);
        });

        columna.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String estadoTxT, boolean empty) {
                super.updateItem(estadoTxT, empty);
                if (empty || estadoTxT == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(estadoTxT);
                    setStyle("Si".equals(estadoTxT) ? ESTILO_ELVERDE : ESTILO_ELROJO);
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }

    private TableColumn<GeneroDto, Short> crearColumnaCantidadPeliculas() {
        TableColumn<GeneroDto, Short> columna = new TableColumn<>("Pelis");
        columna.setCellValueFactory(new PropertyValueFactory<>("peliculasGenero"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<GeneroDto, String> crearColumnaImagen()
    {
        TableColumn<GeneroDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoGenero"));
        columna.setCellFactory(column -> new TableCell<GeneroDto, String> ()
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
        
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.35));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private void configurarColumnas() {
        miTabla.getColumns().addAll(
                List.of(
                        crearColumnaCodigo(), crearColumnaNombre(), crearColumnaEstado(),crearColumnaPopularidadGenero(),crearColumnaClasico() ,crearColumnaCantidadPeliculas(), crearColumnaImagen()
                )
        );
    }
    
    private void crearTabla() {
        configurarColumnas();

        List<GeneroDto> arrGeneros = GeneroControladorListar.arregloGeneros();
        datosTabla.setAll(arrGeneros);
        
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay géneros registrados"));

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
                GeneroDto objGenero = miTabla
                        .getSelectionModel()
                        .getSelectedItem();
                if (objGenero.getPeliculasGenero() == 0) {
                    String msg1, msg2, msg3, msg4;
                    msg1 = "¿Estás seguro mi vale?";
                    msg2 = "\nCódigo: " + objGenero.getIdGenero();
                    msg3 = "\nGénero: " + objGenero.getNombreGenero();
                    msg4 = "\nSi se fue, se fue!";

                    Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
                    mensajito.setTitle("Telo advierto");
                    mensajito.setHeaderText(null);
                    mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
                    mensajito.initOwner(null);

                    if (mensajito.showAndWait().get() == ButtonType.OK) {
                        int fila = miTabla.getSelectionModel().getSelectedIndex();
                        if (GeneroControladorEliminar.borrar(fila)) {
                            int cant = GeneroControladorListar.cantidadGeneros();
                            miTitulo.setText("Administrador de géneros - (" + cant + ") ");
                            List<GeneroDto> quedaron =  GeneroControladorListar.arregloGeneros();
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
                            "Ya tiene peliculas");
                }
            }
        });
        // ***************************************************

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
                Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "No ha seleccionado una categoría para editar");
                }else
                {
                    GeneroDto objGen = miTabla.getSelectionModel().getSelectedItem();
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    
                    panelCuerpo = GeneroControladorVentana.editar(laVentanaPrincipal, panelPrincipal, panelCuerpo, Configuracion.ANCHO_APP, Configuracion.ALTO_APP, objGen, posicion);
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
