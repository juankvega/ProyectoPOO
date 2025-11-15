/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniMagdalena.vista.pelicula;

import com.uniMagdalena.controlador.pelicula.PeliculaControladorListar;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.dto.PeliculaDto;
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

public class VistaPeliculaListar extends SubScene
{
    private final StackPane miFormulario;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    private final TableView<PeliculaDto> miTabla;
    private final Rectangle miMarco;
    
    private static final String ESTILO_CENTRAR = "-fx-alignment: CENTER;";
    private static final String ESTILO_IZQUIERDA = "-fx-alignment: CENTER-LEFT;";
    private static final String ESTILO_DERECHA = "-fx-alignment: CENTER-RIGHT;";
    private static final String ESTILO_ELROJO = "-fx-text-fill: red; " + ESTILO_DERECHA;
    private static final String ESTILO_ELVERDE = "-fx-text-fill: green; " + ESTILO_DERECHA; 
    
    public VistaPeliculaListar(Stage ventanaPadre, double ancho, double alto)
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
        int canti = PeliculaControladorListar.cantidadPeliculas();
        Text miTitulo = new Text("Listado de peliculas - (" + canti + ") ");
        miTitulo.setFill(Color.web("#54e8b7"));
        miTitulo.setFont(Font.font("Verdana", FontWeight.BOLD, 26));
        miCajaVertical.getChildren().addAll(separadorTitulo, miTitulo);
    }
    
    private TableColumn<PeliculaDto, Integer> crearColumnaCodigo()
    {
        TableColumn<PeliculaDto, Integer> columna = new TableColumn<>("Codigo");
        columna.setCellValueFactory(new PropertyValueFactory<>("idPelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<PeliculaDto, String> crearColumnaNombre()
    {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Nombre");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombrePelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.3));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    private TableColumn<PeliculaDto, String> crearColumnaGenero()
    {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Genero");
        
        columna.setCellValueFactory(cellData -> 
        {
            String nombreGenero = cellData.getValue().getIdGeneroPelicula().getNombreGenero();
            return new javafx.beans.property.SimpleStringProperty(nombreGenero);
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.1));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<PeliculaDto, String> crearColumnaActor()
    {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Actor");
        columna.setCellValueFactory(new PropertyValueFactory<>("actorPPelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        columna.setStyle(ESTILO_CENTRAR);
        return columna;
    }
    
    private TableColumn<PeliculaDto, Double> crearColumnaPresupuesto()
    {
        TableColumn<PeliculaDto, Double> columna = new TableColumn<>("Presupuesto");
        columna.setCellValueFactory(new PropertyValueFactory<>("presupuestoPelicula"));
        
        columna.setStyle(ESTILO_DERECHA);        
        // Formato personalizado para el presupuesto
        columna.setCellFactory(column -> new TableCell<PeliculaDto, Double>() {
            @Override
            protected void updateItem(Double precio, boolean empty) {
                super.updateItem(precio, empty);
                if (empty || precio == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", precio));
                }
                setStyle(ESTILO_DERECHA);
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.2));
        return columna;
    }
    
    private TableColumn<PeliculaDto, String> crearColumnaRestriccion()
    {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("¿menor de 15 años?");
        columna.setCellValueFactory(obj ->
        {
            String Restriccion = obj.getValue().getEsParaNinyosPelicula()? "Sí":"No";
            return new SimpleStringProperty(Restriccion);
        });
        
        columna.setCellFactory(col -> new TableCell<>()
        {
            @Override
            protected void updateItem(String restriccionTxt, boolean empty)
            {
                super.updateItem(restriccionTxt, empty);
                if(empty || restriccionTxt == null)
                {
                    setText(null);
                    setStyle("");
                }
                else
                {
                    setText(restriccionTxt);
                    setStyle("Sí".equals(restriccionTxt) ? ESTILO_ELVERDE: ESTILO_ELROJO);
                }
            }
        });
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        return columna;
        
        
    }
    
    private TableColumn<PeliculaDto, String> crearColumnaImagen()
    {
        TableColumn<PeliculaDto, String> columna = new TableColumn<>("Nombre img");
        columna.setCellValueFactory(new PropertyValueFactory<>("nombreImagenPublicoPelicula"));
        columna.prefWidthProperty().bind(miTabla.widthProperty().multiply(0.15));
        columna.setStyle(ESTILO_IZQUIERDA);
        return columna;
    }
    
    
    
    private void configurarColumnas()
    {
        miTabla.getColumns().addAll
        (
          List.of(crearColumnaCodigo(), crearColumnaNombre(), crearColumnaGenero(),crearColumnaActor(), crearColumnaPresupuesto(), crearColumnaRestriccion(), crearColumnaImagen())
        );
    }
    
    private void crearTabla()
    {
        configurarColumnas();
        List<PeliculaDto> arrPeliculas = PeliculaControladorListar.arregloPeliculas();
        ObservableList<PeliculaDto> datosTabla = FXCollections.observableArrayList(arrPeliculas);
        miTabla.setItems(datosTabla);
        miTabla.setPlaceholder(new Text("No hay peliculas registradas"));
        
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
