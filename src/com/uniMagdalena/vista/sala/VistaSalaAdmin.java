
package com.uniMagdalena.vista.sala;

import com.uniMagdalena.controlador.sala.SalaControladorEliminar;
import com.uniMagdalena.controlador.sala.SalaControladorListar;
import com.uniMagdalena.controlador.sala.SalaControladorVentana;
import com.uniMagdalena.dto.SalaDto;
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

public class VistaSalaAdmin extends SubScene
{
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<SalaDto> miTabla;
    private HBox miCajaHorizontal;
    
    private Text miTitulo;
    private final ObservableList<SalaDto> datosTabla = FXCollections.observableArrayList();
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; "+ ESTILO_DERECHA;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaSalaAdmin(Stage esce,BorderPane princ, Pane pane ,double ancho, double alto) 
    {
         
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);
        
        laVentanaPrincipal = esce;
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
        separadorTitulo.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        int canti = SalaControladorListar.cantidadSalas();
        miTitulo = new Text("Listado de salas - ("+ canti+") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
    private TableColumn<SalaDto, Integer> crearColumnaCodigo()
    {
       TableColumn<SalaDto, Integer> columna = new TableColumn<>("Codigo");
       columna.setCellValueFactory(new PropertyValueFactory<>("idSala"));
       columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
       columna.setStyle(ESTILO_CENTRAR);
       return columna;
    }
    
    private TableColumn<SalaDto, String> crearColumnaNombre()
    {
       TableColumn<SalaDto, String> columna = new TableColumn<>("Nombre");
       columna.setCellValueFactory(new PropertyValueFactory<>("nombreSala"));
       columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
       columna.setStyle(ESTILO_CENTRAR);
       return columna;
    }
    
    private TableColumn<SalaDto, Integer> crearColumnaAsientos()
    {
       TableColumn<SalaDto, Integer> columna = new TableColumn<>("Asientos");
       columna.setCellValueFactory(new PropertyValueFactory<>("asientosSala"));
       columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
       columna.setStyle(ESTILO_CENTRAR);
       return columna;
    }
    
    private TableColumn<SalaDto, String> crearColumna4d()
    {
        TableColumn<SalaDto, String> columna = new TableColumn<>("¿Es 4d?");
        columna.setCellValueFactory(obj ->{
        String es4d = obj.getValue().getSala4d() ? "Sí": "No";
        return new SimpleStringProperty(es4d);
        });
        
        columna.setCellFactory(col -> new TableCell<>()
        {
           @Override
           protected void updateItem(String es4dTxT, boolean empty)
           {
               super.updateItem(es4dTxT, empty);
               if(empty || es4dTxT == null)
               {
                   setText(null);
                   setStyle("");
               }
               else
               {
                   setText(es4dTxT);
                   setStyle("Sí".equals(es4dTxT) ? ESTILO_ELVERDE: ESTILO_ELROJO);
               }
           }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }
    
    private TableColumn<SalaDto, String> crearColumnaSede()
    {
        TableColumn<SalaDto, String> columna = new TableColumn<>("Sede");
        
         columna.setCellValueFactory(cellData -> 
        {
            String nombreSede = cellData.getValue().getSedeSala().getNombreSede();
            return new javafx.beans.property.SimpleStringProperty(nombreSede);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
        
    }
    
    
    private TableColumn<SalaDto, String> crearColumnaImagen()
    {
       TableColumn<SalaDto, String> columna = new TableColumn<>("img");
       columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPrivadoSala"));
       columna.setCellFactory(column -> new TableCell<SalaDto, String>()
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
              
       columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.25));
       columna.setStyle(ESTILO_CENTRAR);
       return columna;
    }
    
    
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll(List.of(crearColumnaCodigo(), crearColumnaNombre(), crearColumnaAsientos(), crearColumna4d(), crearColumnaSede(), crearColumnaImagen()));
    }
    
    
    
       
    private void crearTabla() 
    {
       configurarColumnas();
       
       List<SalaDto> arrSalas = SalaControladorListar.arregloSalas();
       datosTabla.setAll(arrSalas);
       
        miTabla.setItems(datosTabla);
         miTabla.setPlaceholder(new Text("No hay salas registradas"));
         
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
            SalaDto objSala = miTabla.getSelectionModel().getSelectedItem();
            String msg1, msg2, msg3, msg4;
            
            msg1 = "Estás seguro mi vale?";
            msg2 = "\n ID sala: "+objSala.getIdSala();
            msg3 = "\n Nombre: "+ objSala.getNombreSala();
            msg4 = "\n Si se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(null);
            
            if(mensajito.showAndWait().get() == ButtonType.OK)
            {
                int fila = miTabla.getSelectionModel().getSelectedIndex();
                if(SalaControladorEliminar.borrar(fila))
                {
                    int cant = SalaControladorListar.cantidadSalas();
                    miTitulo.setText("Administrador de Salas - ("+ cant + ")");
                    List<SalaDto> quedaron =  SalaControladorListar.arregloSalas();
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
                    SalaDto objSala = miTabla.getSelectionModel().getSelectedItem();
                    int posicion = miTabla.getSelectionModel().getSelectedIndex();
                    
                    panelCuerpo = SalaControladorVentana.editar(laVentanaPrincipal, panelPrincipal, panelCuerpo, Configuracion.ANCHO_APP, Configuracion.ALTO_APP, objSala, posicion);
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
