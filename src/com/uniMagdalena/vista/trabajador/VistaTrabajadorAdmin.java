
package com.uniMagdalena.vista.trabajador;

import com.uniMagdalena.controlador.trabajador.TrabajadorControladorEliminar;
import com.uniMagdalena.vista.cliente.*;
import com.uniMagdalena.controlador.trabajador.TrabajadorControladorListar;
import com.uniMagdalena.controlador.trabajador.TrabajadorControladorVentana;
import com.uniMagdalena.dto.TrabajadorDto;
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

public class VistaTrabajadorAdmin extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<TrabajadorDto> miTabla;
    private final Rectangle miMarco;
    private HBox miCajaHorizontal;
    
    private Text miTitulo;
    
    private final ObservableList<TrabajadorDto> datosTabla
            = FXCollections.observableArrayList();
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA; 
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal; 
    
    public VistaTrabajadorAdmin(Stage ventanaPadre, BorderPane princ, Pane pane ,double ancho, double alto)
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
        int canti = TrabajadorControladorListar.cantidadTrabajadores();
        miTitulo = new Text("Listado de trabajadores - (" + canti + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
     private TableColumn<TrabajadorDto, Integer> crearColumnaCodigo()
    {
        TableColumn<TrabajadorDto, Integer> columna = new TableColumn<>("Codigo");
        columna.setCellValueFactory(new PropertyValueFactory<>("idTrabajador"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
     
    private TableColumn<TrabajadorDto, String> crearColumnaNombre()
    {
        TableColumn<TrabajadorDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreTrabajador"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
     private TableColumn<TrabajadorDto, String> crearColumnaGenero()
    {
        TableColumn<TrabajadorDto, String> columna = new TableColumn<>("Género");
        columna.setCellValueFactory(obj ->{
        String Genero = obj.getValue().getGeneroTrabajador()? "Masculino": "Femenino";
        return new SimpleStringProperty(Genero);
        });
        
        columna.setCellFactory(col -> new TableCell<>()
        {
           @Override
           protected void updateItem(String GeneroTxT, boolean empty)
           {
               super.updateItem(GeneroTxT, empty);
               if(empty || GeneroTxT == null)
               {
                   setText(null);
                   setStyle("");
               }
               else
               {
                   setText(GeneroTxT);
                   setStyle("Masculino".equals(GeneroTxT) ? ESTILO_ELVERDE: ESTILO_ELROJO);
               }
           }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }
     
    private TableColumn<TrabajadorDto, String> crearColumnaTipoDocumento()
    {
        TableColumn<TrabajadorDto, String> columna = new TableColumn<>("Tipo documento");
        columna.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoTrabajador"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<TrabajadorDto, Long> crearColumnaNumDocumento() {
    TableColumn<TrabajadorDto, Long> columna = new TableColumn<>("Número documento");
    columna.setCellValueFactory(new PropertyValueFactory<>("numDocumentoTrabajador"));
    columna.setStyle(ESTILO_IZQUIERDA);

    columna.setCellFactory(column -> new TableCell<TrabajadorDto, Long>() {
        @Override
        protected void updateItem(Long numero, boolean empty) {
            super.updateItem(numero, empty);
            if (empty || numero == null) {
                setText(null);
            } else {
                // Mostrar solo la parte entera
                setText(String.valueOf(numero));
            }
            setStyle(ESTILO_IZQUIERDA);
        }
    });

    columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
    return columna;
}
    
        private TableColumn<TrabajadorDto, String> crearColumnaTipoTrabajador()
    {
        TableColumn<TrabajadorDto, String> columna = new TableColumn<>("Tipo de Trabajador");
        columna.setCellValueFactory(new PropertyValueFactory<>("tipoTrabajador"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
        
    private TableColumn<TrabajadorDto, String> crearColumnaImagen()
    {
        TableColumn<TrabajadorDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoTrabajador"));
        columna.setCellFactory(column -> new TableCell<TrabajadorDto, String>()
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
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll
        (
                List.of(crearColumnaCodigo(), crearColumnaNombre(), crearColumnaGenero(), crearColumnaTipoDocumento(), crearColumnaNumDocumento(), crearColumnaTipoTrabajador(), crearColumnaImagen())
        );
    }
    
    private void crearTabla()
    {
        configurarColumnas();
        List<TrabajadorDto> arrTrabajadors = TrabajadorControladorListar.arregloTrabajadores();
        datosTabla.setAll(arrTrabajadors);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay trabajadores registrados"));
        
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
            TrabajadorDto objTrabajador = miTabla.getSelectionModel().getSelectedItem();
            if(objTrabajador.getCantidadBanyosAseo() == null)
            {
            String msg1, msg2, msg3, msg4;
            
            msg1 = "Estás seguro mi vale?";
            msg2 = "\n número docuemnto: "+objTrabajador.getNumDocumentoTrabajador();
            msg3 = "\n Nombre: "+ objTrabajador.getNombreTrabajador();
            msg4 = "\n Si se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(null);
            
            if(mensajito.showAndWait().get() == ButtonType.OK)
            {
                int fila = miTabla.getSelectionModel().getSelectedIndex();
                if(TrabajadorControladorEliminar.borrar(fila))
                {
                    int cant = TrabajadorControladorListar.cantidadTrabajadores();
                    miTitulo.setText("Administrador de Trabajadors - ("+ cant + ")");
                    List<TrabajadorDto> quedaron =  TrabajadorControladorListar.arregloTrabajadores();
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
            }else{
                Mensaje.mostrar(
                            Alert.AlertType.ERROR,
                            laVentanaPrincipal, "Ey",
                            "Está encargado de baño");
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
                    TrabajadorDto objTrabajador = miTabla.getSelectionModel().getSelectedItem();
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    
                    panelCuerpo = TrabajadorControladorVentana.editar(laVentanaPrincipal, panelPrincipal, panelCuerpo, Configuracion.ANCHO_APP, Configuracion.ALTO_APP, objTrabajador, posicion);
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
