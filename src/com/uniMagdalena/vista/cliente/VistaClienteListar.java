
package com.uniMagdalena.vista.cliente;

import com.uniMagdalena.controlador.cliente.ClienteControladorListar;
import com.uniMagdalena.dto.ClienteDto;
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

public class VistaClienteListar extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<ClienteDto> miTabla;
    private final Rectangle miMarco;
    
    private Text miTitulo;
    
    private final ObservableList<ClienteDto> datosTabla
            = FXCollections.observableArrayList();
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA; 
    
    public VistaClienteListar(Stage ventanaPadre, double ancho, double alto)
    {
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
        int canti = ClienteControladorListar.cantidadClientes();
        miTitulo = new Text("Listado de clientes - (" + canti + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
     private TableColumn<ClienteDto, Integer> crearColumnaCodigo()
    {
        TableColumn<ClienteDto, Integer> columna = new TableColumn<>("Codigo");
        columna.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
     
    private TableColumn<ClienteDto, String> crearColumnaNombre()
    {
        TableColumn<ClienteDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
     private TableColumn<ClienteDto, String> crearColumnaGenero()
    {
        TableColumn<ClienteDto, String> columna = new TableColumn<>("Género");
        columna.setCellValueFactory(obj ->{
        String Genero = obj.getValue().getGeneroCliente()? "Masculino": "Femenino";
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
     
    private TableColumn<ClienteDto, String> crearColumnaTipoDocumento()
    {
        TableColumn<ClienteDto, String> columna = new TableColumn<>("Tipo documento");
        columna.setCellValueFactory(new PropertyValueFactory<>("tipoDocumentoCliente"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<ClienteDto, Integer> crearColumnaNumDocumento()
    {
        TableColumn<ClienteDto, Integer> columna = new TableColumn<>("Número documento");
        columna.setCellValueFactory(new PropertyValueFactory<>("numeroDocumentoCliente"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
        private TableColumn<ClienteDto, String> crearColumnaTipoCliente()
    {
        TableColumn<ClienteDto, String> columna = new TableColumn<>("Tipo de cliente");
        columna.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
        
    private TableColumn<ClienteDto, String> crearColumnaImagen()
    {
        TableColumn<ClienteDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoCliente"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll
        (
                List.of(crearColumnaCodigo(), crearColumnaNombre(), crearColumnaGenero(), crearColumnaTipoDocumento(), crearColumnaNumDocumento(), crearColumnaTipoCliente(), crearColumnaImagen())
        );
    }
    
    private void crearTabla()
    {
        configurarColumnas();
        List<ClienteDto> arrClientes = ClienteControladorListar.arregloClientes();
        datosTabla.setAll(arrClientes);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay clientes registrados"));
        
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
    
    
    
}
