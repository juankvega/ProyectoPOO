package com.uniMagdalena.vista.venta;

import com.uniMagdalena.controlador.venta.VentaControladorEliminar;
import com.uniMagdalena.controlador.venta.VentaControladorListar;
import com.uniMagdalena.controlador.venta.VentaControladorVentana;
import com.uniMagdalena.dto.VentaDto;
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

public class VistaVentaAdmin extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<VentaDto> miTabla;
    private final Rectangle miMarco;
    private HBox miCajaHorizontal;
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    
    private Text miTitulo;
    
    private final ObservableList<VentaDto> datosTabla = FXCollections.observableArrayList();
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    public VistaVentaAdmin(Stage ventanaPadre,BorderPane princ, Pane pane ,double ancho, double alto)
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
    
    private void armarTitulo()
    {
        Region separadorTitulo = new Region();
        separadorTitulo.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.05));
        int cantidad = VentaControladorListar.cantidadVentas();
        miTitulo = new Text("Listado de Ventas - (" + cantidad + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
    private TableColumn<VentaDto, Integer> crearColumnaCodigo()
    {
        TableColumn<VentaDto, Integer> columna = new TableColumn<>("Código");
        columna.setCellValueFactory(new PropertyValueFactory<>("idVenta"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.08));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaCliente()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Cliente");
        columna.setCellValueFactory(cellData -> {
            String nombreCliente = cellData.getValue().getClienteVenta().getNombreCliente() + " " + 
                                  cellData.getValue().getClienteVenta().getNumeroDocumentoCliente();
            return new SimpleStringProperty(nombreCliente);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaPelicula()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Película");
        columna.setCellValueFactory(cellData -> {
            String nombrePelicula = cellData.getValue().getPeliculaVenta().getNombrePelicula();
            return new SimpleStringProperty(nombrePelicula);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaSede()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Sede");
        columna.setCellValueFactory(cellData -> {
            String nombreSede = cellData.getValue().getSedeVenta().getNombreSede();
            return new SimpleStringProperty(nombreSede);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaSala()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Sala");
        columna.setCellValueFactory(cellData -> {
            String nombreSala = cellData.getValue().getSalaVenta().getNombreSala();
            return new SimpleStringProperty(nombreSala);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaProducto()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Producto");
        columna.setCellValueFactory(cellData -> {
            String nombreProducto = cellData.getValue().getProductoVenta().getNombreProducto();
            return new SimpleStringProperty(nombreProducto);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaTipoAsiento()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Tipo Asiento");
        columna.setCellValueFactory(new PropertyValueFactory<>("tipoAsientoVenta"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaFecha()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Fecha");
        columna.setCellValueFactory(cellData -> {
            String fecha = cellData.getValue().getFechaVenta().toString();
            return new SimpleStringProperty(fecha);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<VentaDto, Double> crearColumnaValor()
    {
        TableColumn<VentaDto, Double> columna = new TableColumn<>("Valor");
        columna.setCellValueFactory(new PropertyValueFactory<>("valorVenta"));
        
        // Formato personalizado para el valor
        columna.setCellFactory(column -> new TableCell<VentaDto, Double>() {
            @Override
            protected void updateItem(Double valor, boolean empty) {
                super.updateItem(valor, empty);
                if (empty || valor == null) {
                    setText(null);
                } else {
                    setText(String.format("$%,.2f", valor));
                }
                setStyle(ESTILO_CENTRAR);
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.12));
        return columna;
    }
    
    private TableColumn<VentaDto, String> crearColumnaImagen()
    {
        TableColumn<VentaDto, String> columna = new TableColumn<>("Imagen");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoVenta"));
        columna.setCellFactory(column -> new TableCell<VentaDto, String>()
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
        
        
        
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll(
            List.of(
                crearColumnaCodigo(),
                crearColumnaCliente(),
                crearColumnaPelicula(),
                crearColumnaSede(),
                crearColumnaSala(),
                crearColumnaProducto(),
                crearColumnaTipoAsiento(),
                crearColumnaFecha(),
                crearColumnaValor(),
                crearColumnaImagen()
            )
        );
    }
    
    private void crearTabla()
    {
        configurarColumnas();
        List<VentaDto> arrVentas = VentaControladorListar.arregloVentas();
        datosTabla.setAll(arrVentas);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay ventas registradas"));
        
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        miTabla.maxWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.9));
        miTabla.maxHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.6));
        
        laVentanaPrincipal.heightProperty().addListener((o, oldVal, newVal) 
             -> miTabla.setPrefHeight(newVal.doubleValue())
        );
        
        VBox.setVgrow(miTabla, Priority.ALWAYS);
        
        miCajaVertical.getChildren().add(miTabla);
        miFormulario.getChildren().add(miCajaVertical);
    }
    
    
    private void mostrarIconosAdministrar()
    {
        int anchoBoton = 40;
        int tamanioIcono = 18;
        
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(
                Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono)
        
        );
        btnEliminar.setOnAction((e)-> {
        if(miTabla.getSelectionModel().getSelectedItem() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING,laVentanaPrincipal , "Te veo", "MAL ! agarra algo");
            
        }
        else
        {
            VentaDto objVenta = miTabla.getSelectionModel().getSelectedItem();
            String msg1, msg2, msg3, msg4;
            
            msg1 = "Estás seguro mi vale?";
            msg2 = "\n Código: "+objVenta.getIdVenta();
            msg3 = "\n Cliente: "+ objVenta.getClienteVenta().getNombreCliente();
            msg4 = "\n Si se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(null);
            
            if(mensajito.showAndWait().get() == ButtonType.OK)
            {
                int fila = miTabla.getSelectionModel().getSelectedIndex();
                if(VentaControladorEliminar.borrar(fila))
                {
                    int cant = VentaControladorListar.cantidadVentas();
                    miTitulo.setText("Administrador de peliculas - ("+ cant + ")");
                    List<VentaDto> quedaron =  VentaControladorListar.arregloVentas();
                    datosTabla.setAll(quedaron);
                    miTabla.refresh();
                    
                    Mensaje.mostrar(Alert.AlertType.INFORMATION, laVentanaPrincipal, "Exito", "Lo logré borrar");
                    
                }
                else
                {
                    Mensaje.mostrar(
                                    Alert.AlertType.ERROR,
                                    laVentanaPrincipal, "Pailas",
                                    "No lo pude borrar!");
                }
            }
            else
            {
                miTabla.getSelectionModel().clearSelection();
            }
        }
        });
        
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
            
            if (miTabla.getSelectionModel().getSelectedItem() == null) {
                Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "No ha seleccionado una categoría para editar");
                }else
                {
                    VentaDto objVenta = miTabla.getSelectionModel().getSelectedItem();
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    
                    panelCuerpo = VentaControladorVentana.editar(laVentanaPrincipal, panelPrincipal, panelCuerpo, Configuracion.ANCHO_APP, Configuracion.ALTO_APP, objVenta, posicion);
                    panelPrincipal.setCenter(null);
                    panelPrincipal.setCenter(panelCuerpo);
                }
        
        });
        
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
