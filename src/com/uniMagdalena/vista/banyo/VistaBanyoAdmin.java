package com.uniMagdalena.vista.banyo;

import com.uniMagdalena.controlador.banyo.BanyoControladorEliminar;
import com.uniMagdalena.controlador.banyo.BanyoControladorListar;
import com.uniMagdalena.controlador.banyo.BanyoControladorVentana;
import com.uniMagdalena.dto.BanyoDto;
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

public class VistaBanyoAdmin extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<BanyoDto> miTabla;
    private final Rectangle miMarco;
    private HBox miCajaHorizontal;
    
    private Text miTitulo;
    
    private final ObservableList<BanyoDto> datosTabla
            = FXCollections.observableArrayList();
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA; 
    
   private Pane panelCuerpo;
    private final BorderPane panelPrincipal; 
    
    public VistaBanyoAdmin(Stage ventanaPadre,BorderPane princ, Pane pane ,double ancho, double alto)
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
        int canti = BanyoControladorListar.cantidadBanyos();
        miTitulo = new Text("Listado de baños - (" + canti + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
    private TableColumn<BanyoDto, Integer> crearColumnaCodigo()
    {
        TableColumn<BanyoDto, Integer> columna = new TableColumn<>("Codigo");
        columna.setCellValueFactory(new PropertyValueFactory<>("idBanyo"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<BanyoDto, String> crearColumnaSede()
    {
        TableColumn<BanyoDto, String> columna = new TableColumn<>("Sede");
        
        columna.setCellValueFactory(cellData -> 
        {
            String nombreSede = cellData.getValue().getSedeBanyo().getNombreSede();
            return new javafx.beans.property.SimpleStringProperty(nombreSede);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<BanyoDto, String> crearColumnaUbicacion()
    {
        TableColumn<BanyoDto, String> columna = new TableColumn<>("Ubicación");
        columna.setCellValueFactory(new PropertyValueFactory<>("ubicacionBanyo"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<BanyoDto, String> crearColumnaGenero()
    {
        TableColumn<BanyoDto, String> columna = new TableColumn<>("Género");
        columna.setCellValueFactory(obj ->
        {
            String genero = obj.getValue().getGeneroBanyo()? "Masculino":"Femenino";
            return new SimpleStringProperty(genero);
        });
        
        columna.setCellFactory(col -> new TableCell<>()
        {
            @Override
            protected void updateItem(String generoTxt, boolean empty)
            {
                super.updateItem(generoTxt, empty);
                if(empty || generoTxt == null)
                {
                    setText(null);
                    setStyle("");
                }
                else
                {
                    setText(generoTxt);
                    setStyle("Masculino".equals(generoTxt) ? ESTILO_ELVERDE: ESTILO_ELROJO);
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }
    
    private TableColumn<BanyoDto, String> crearColumnaUso()
    {
        TableColumn<BanyoDto, String> columna = new TableColumn<>("En uso");
        columna.setCellValueFactory(obj ->
        {
            String uso = obj.getValue().getUsoBanyo()? "Sí":"No";
            return new SimpleStringProperty(uso);
        });
        
        columna.setCellFactory(col -> new TableCell<>()
        {
            @Override
            protected void updateItem(String usoTxt, boolean empty)
            {
                super.updateItem(usoTxt, empty);
                if(empty || usoTxt == null)
                {
                    setText(null);
                    setStyle("");
                }
                else
                {
                    setText(usoTxt);
                    setStyle("Sí".equals(usoTxt) ? ESTILO_ELVERDE: ESTILO_ELROJO);
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        return columna;
    }
    
    private TableColumn<BanyoDto, String> crearColumnaEncargado()
    {
        TableColumn<BanyoDto, String> columna = new TableColumn<>("Encargado");
        
        columna.setCellValueFactory(cellData -> 
        {
            String nombreEncargado = cellData.getValue().getEncargadoBanyo().getNombreTrabajador();
            return new javafx.beans.property.SimpleStringProperty(nombreEncargado);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<BanyoDto, String> crearColumnaImagen()
    {
        TableColumn<BanyoDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoBanyo"));
        columna.setCellFactory(column -> new TableCell<BanyoDto, String>()
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
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll
        (
          List.of(crearColumnaCodigo(), crearColumnaSede(), crearColumnaUbicacion(), crearColumnaGenero(), crearColumnaUso(), crearColumnaEncargado(), crearColumnaImagen())
        );
    }
    
    private void crearTabla()
    {
        configurarColumnas();
        List<BanyoDto> arrBanyos = BanyoControladorListar.arregloBanyos();
        datosTabla.setAll(arrBanyos);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay baños registrados"));
        
        miTabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        miTabla.maxWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.6));
        miTabla.maxHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.5));
        
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
            BanyoDto objBanyo = miTabla.getSelectionModel().getSelectedItem();
            String msg1, msg2, msg3, msg4;
            
            msg1 = "Estás seguro mi vale?";
            msg2 = "\n Código: "+objBanyo.getIdBanyo();
            msg3 = "\n Sede: "+ objBanyo.getSedeBanyo().getNombreSede();
            msg4 = "\n Si se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(null);
            
            if(mensajito.showAndWait().get() == ButtonType.OK)
            {
                int fila = miTabla.getSelectionModel().getSelectedIndex();
                if(BanyoControladorEliminar.borrar(fila))
                {
                    int cant = BanyoControladorListar.cantidadBanyos();
                    miTitulo.setText("Administrador de baños - ("+ cant + ")");
                    List<BanyoDto> quedaron =  BanyoControladorListar.arregloBanyos();
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
                    BanyoDto objBanyo = miTabla.getSelectionModel().getSelectedItem();
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    
                    panelCuerpo = BanyoControladorVentana.editar(laVentanaPrincipal, panelPrincipal, panelCuerpo, Configuracion.ANCHO_APP, Configuracion.ALTO_APP, objBanyo, posicion);
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
