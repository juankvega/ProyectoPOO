
package com.uniMagdalena.vista.producto;

import com.uniMagdalena.controlador.producto.ProductoControladorListar;
import com.uniMagdalena.dto.ProductoDto;
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

public class VistaProductoListar extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<ProductoDto> miTabla;
    private final Rectangle miMarco;
    
    private Text miTitulo;
    
    private final ObservableList<ProductoDto> datosTabla
            = FXCollections.observableArrayList();
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA; 
    
    public VistaProductoListar(Stage ventanaPadre, double ancho, double alto)
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
        int canti = ProductoControladorListar.cantidadProductos();
        miTitulo = new Text("Listado de productos - (" + canti + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
     private TableColumn<ProductoDto, Integer> crearColumnaCodigo()
    {
        TableColumn<ProductoDto, Integer> columna = new TableColumn<>("Codigo");
        columna.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
     
    private TableColumn<ProductoDto, String> crearColumnaNombre()
    {
        TableColumn<ProductoDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
     private TableColumn<ProductoDto, String> crearColumnaTipo()
    {
        TableColumn<ProductoDto, String> columna = new TableColumn<>("Tipo");
        columna.setCellValueFactory(obj ->{
        String Tipo = obj.getValue().getTipoProducto()? "Comida": "Bebida";
        return new SimpleStringProperty(Tipo);
        });
        
        columna.setCellFactory(col -> new TableCell<>()
        {
           @Override
           protected void updateItem(String ProductoTxT, boolean empty)
           {
               super.updateItem(ProductoTxT, empty);
               if(empty || ProductoTxT == null)
               {
                   setText(null);
                   setStyle("");
               }
               else
               {
                   setText(ProductoTxT);
                   setStyle("Comida".equals(ProductoTxT) ? ESTILO_ELVERDE: ESTILO_ELROJO);
               }
           }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
    }
    
    private TableColumn<ProductoDto, Integer> crearColumnaPrecio()
    {
        TableColumn<ProductoDto, Integer> columna = new TableColumn<>("Precio");
        columna.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
        private TableColumn<ProductoDto, String> crearColumnaTamanio()
    {
        TableColumn<ProductoDto, String> columna = new TableColumn<>("Tamaño del producto");
        columna.setCellValueFactory(new PropertyValueFactory<>("tamanioProducto"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
        
    private TableColumn<ProductoDto, String> crearColumnaImagen()
    {
        TableColumn<ProductoDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoProducto"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll
        (
                List.of(crearColumnaCodigo(), crearColumnaNombre(), crearColumnaTipo(), crearColumnaTamanio(), crearColumnaPrecio(), crearColumnaImagen())
        );
    }
    
    private void crearTabla()
    {
        configurarColumnas();
        List<ProductoDto> arrProductos = ProductoControladorListar.arregloProductos();
        datosTabla.setAll(arrProductos);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay productos registrados"));
        
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
