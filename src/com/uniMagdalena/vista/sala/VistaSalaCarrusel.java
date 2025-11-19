
package com.uniMagdalena.vista.sala;

import com.uniMagdalena.controlador.sala.SalaControladorEliminar;
import com.uniMagdalena.controlador.sala.SalaControladorListar;
import com.uniMagdalena.controlador.sala.SalaControladorUna;
import com.uniMagdalena.controlador.sala.SalaControladorVentana;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VistaSalaCarrusel extends SubScene
{
    private static final String ARCHIVO_MEMORIA = "carrusel_Sala_posicion.txt";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    private int indiceActual;
    private int totalSalas;
    private SalaDto objCargado;
    
    private StringProperty SalaTitulo;
    private StringProperty SalaNombre;
    private ObjectProperty<Image> SalaImagen;
    private IntegerProperty SalaAsientos;
    private BooleanProperty Sala4d;
    private ObjectProperty<SedeDto> SalaSede;
    
    public VistaSalaCarrusel (Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
        super(new BorderPane(), anchoPanel, altoPanel);
        
        indiceActual = cargarIndiceGuardado();
        
        totalSalas = SalaControladorListar.cantidadSalas();
        if (indiceActual < 0 || indiceActual >= totalSalas) {
            indiceActual = indice;
        }
        objCargado = SalaControladorUna.obtenerSala(indiceActual);
        
        miBorderPane = (BorderPane) this.getRoot();
        
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        miCajaVertical = new VBox();
        configurarMiCajaVertical();
        crearTitulo();
        
        construirPanelIzquiero(0.14);
        construirPanelDerecho(0.14);
        construirPanelCentro();
        
        laVentanaPrincipal.setOnCloseRequest(event -> guardarIndiceActual());
        
    }
    
     public BorderPane getMiBorderPane()
    {
        return miBorderPane;
    }
    
    
    
    private void configurarMiCajaVertical() {
        miCajaVertical.setSpacing(10);
        miCajaVertical.setAlignment(Pos.TOP_CENTER);
        miCajaVertical.prefWidthProperty().bind(laVentanaPrincipal.widthProperty());
        miCajaVertical.prefHeightProperty().bind(laVentanaPrincipal.heightProperty());
    }
    
          private void guardarIndiceActual() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            Files.writeString(rutaArchivo, String.valueOf(indiceActual));
        } catch (IOException ex) {
            Logger.getLogger(VistaSalaCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo guardar la posición del carrusel", ex);
        }
    }
    
    
    
    private int cargarIndiceGuardado() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            if (Files.exists(rutaArchivo)) {
                String contenido = Files.readString(rutaArchivo);
                return Integer.parseInt(contenido.trim());
            }
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(VistaSalaCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo cargar la posición del carrusel", ex);
        }
        return -1;
    }
    
    
    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        miCajaVertical.getChildren().add(0, bloqueSeparador);

        totalSalas = SalaControladorListar.cantidadSalas();
        SalaTitulo = new SimpleStringProperty("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalSalas + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(SalaTitulo);
        lblTitulo.setTextFill(Color.web("#E82E68"));
        lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        miCajaVertical.getChildren().add(lblTitulo);
    }
    
    private void construirPanelIzquiero(double porcentaje)
    {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 80));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);
        
        btnAnterior.setOnAction(e -> 
        {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalSalas);
            objCargado = SalaControladorUna.obtenerSala(indiceActual);
            
            guardarIndiceActual();
            
            SalaTitulo.set("Detalle de Sala (" + (indiceActual + 1) + "/" + totalSalas + ")");
            SalaNombre.set(objCargado.getNombreSala());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoSala();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                SalaImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaSalaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        SalaSede.set(objCargado.getSedeSala());
        SalaAsientos.set(objCargado.getAsientosSala());
        Sala4d.set(objCargado.getSala4d());               
        });
        
        StackPane panelIzquierdo = new StackPane();
        // panelIzquierdo.setStyle(borderPanel);
        panelIzquierdo.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(porcentaje));
        panelIzquierdo.getChildren().add(btnAnterior);
        miBorderPane.setLeft(panelIzquierdo);
    }
    
    private void construirPanelDerecho(double porcentaje)
    {
        Button btnSiguiente = new Button();
        btnSiguiente.setGraphic(Icono.obtenerIcono("btnSiguiente.png", 80));
        btnSiguiente.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnSiguiente.setCursor(Cursor.HAND);
        
        btnSiguiente.setOnAction(e -> 
        {
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalSalas);
            objCargado = SalaControladorUna.obtenerSala(indiceActual);
            
            guardarIndiceActual();
            
            SalaTitulo.set("Detalle de Sala (" + (indiceActual + 1) + "/" + totalSalas + ")");
            SalaNombre.set(objCargado.getNombreSala());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoSala();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                SalaImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaSalaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        SalaSede.set(objCargado.getSedeSala());
        SalaAsientos.set(objCargado.getAsientosSala());
        Sala4d.set(objCargado.getSala4d());               
        });
        
        StackPane panelDerecho = new StackPane();
        // panelIzquierdo.setStyle(borderPanel);
        panelDerecho.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        miBorderPane.setRight(panelDerecho);
    }
    
    
    private void panelOpciones()
    {
        int anchoBoton = 40;
        int tamanioIcono = 18;
        
        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(
                Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono)
        
        );
        
        btnEliminar.setOnAction((e) -> {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay película para eliminar");
        } else {
            if(objCargado.getSalaVentas() == 0)
            {
            String msg1, msg2, msg3, msg4;
            
            msg1 = "¿Estás seguro mi vale?";
            msg2 = "\nCódigo: " + objCargado.getIdSala();
            msg3 = "\nNombre: " + objCargado.getNombreSala();
            msg4 = "\nSi se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(laVentanaPrincipal);
            
            if (mensajito.showAndWait().get() == ButtonType.OK) {
                if (SalaControladorEliminar.borrar(indiceActual)) {
                    totalSalas = SalaControladorListar.cantidadSalas();
                    
                    // Ajustar el índice después de eliminar
                    if (indiceActual >= totalSalas && totalSalas > 0) {
                        indiceActual = totalSalas - 1;
                    } else if (totalSalas == 0) {
                        indiceActual = 0;
                    }
                    
                    guardarIndiceActual();
                    
                    // Actualizar el título
                    SalaTitulo.set("Detalle de la película (" + 
                        (indiceActual + 1) + " / " + totalSalas + ")");
                    
                    // Cargar la nueva película si hay disponibles
                    if (totalSalas > 0) {
                        objCargado = SalaControladorUna.obtenerSala(indiceActual);
                        actualizarDatosCarrusel();
                    } else {
                        objCargado = null;
                    }
                    
                    Mensaje.mostrar(Alert.AlertType.INFORMATION, 
                        laVentanaPrincipal, "ÉXITO", "Que buen inglés, lo borré");
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR, 
                        laVentanaPrincipal, "Pailas", "No lo pude borrar!");
                }
            }
        }else{
                Mensaje.mostrar(
                            Alert.AlertType.ERROR,
                            laVentanaPrincipal, "Ey",
                            "Ya tiene ventas");
            }
        }
    });        
          
        
        Button btnActualizar = new Button();
    btnActualizar.setPrefWidth(anchoBoton);
    btnActualizar.setCursor(Cursor.HAND);
    btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));
    btnActualizar.setOnAction((ActionEvent e) -> {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay sala para editar");
        } else {
            panelCuerpo = SalaControladorVentana.editar(
                laVentanaPrincipal, 
                panelPrincipal, 
                panelCuerpo, 
                Configuracion.ANCHO_APP, 
                Configuracion.ALTO_APP, 
                objCargado, 
                indiceActual
            );
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        }
    });
    
        HBox panelHorizontalBotones = new HBox(4);
    panelHorizontalBotones.setAlignment(Pos.CENTER);
    panelHorizontalBotones.getChildren().addAll(btnEliminar, btnActualizar);

    miCajaVertical.getChildren().add(panelHorizontalBotones);
 
    }
    
    
    private void construirPanelCentro()
{
    StackPane centerPane = new StackPane();

    // Fondo
    Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
    centerPane.setBackground(fondo);
    // *********************************************************************

    // Marco
    Rectangle miMarco = Marco.pintar(laVentanaPrincipal, 0.55, 0.75,
            Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
    centerPane.getChildren().addAll(miMarco, miCajaVertical);
    
    // Panel de opciones (botones eliminar y actualizar)
    panelOpciones();
    
    // Nombre de la sala
    SalaNombre = new SimpleStringProperty(objCargado.getNombreSala());
    
    int tamanioFuente = 18;
    Label lblNombreSala = new Label();
    lblNombreSala.textProperty().bind(SalaNombre);
    lblNombreSala.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
    lblNombreSala.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblNombreSala);
    
    // Imagen de la sala
    SalaImagen = new SimpleObjectProperty<>();
    
    FileInputStream imgArchivo;
    
    try {
        String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
            + Persistencia.SEPARADOR_CARPETAS 
            + objCargado.getNombreImagenPrivadoSala();
        imgArchivo = new FileInputStream(rutaNuevaImagen);
        Image imgNueva = new Image(imgArchivo);
        SalaImagen.set(imgNueva);
        
        ImageView imgMostrar = new ImageView(imgNueva);
        imgMostrar.setFitHeight(250);
        imgMostrar.setSmooth(true);
        imgMostrar.setPreserveRatio(true);
        
        imgMostrar.imageProperty().bind(SalaImagen);
        miCajaVertical.getChildren().add(imgMostrar);
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(VistaSalaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Sede de la sala
    SalaSede = new SimpleObjectProperty<>(objCargado.getSedeSala());
    
    Label lblSede = new Label();
    lblSede.textProperty().bind(Bindings.createStringBinding(
        () -> "Sede: " + (SalaSede.get() != null ? SalaSede.get().getNombreSede() : "N/A"),
        SalaSede
    ));
    lblSede.setFont(Font.font("Verdana", tamanioFuente));
    lblSede.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblSede);
    
    // Cantidad de asientos
    SalaAsientos = new SimpleIntegerProperty(objCargado.getAsientosSala());
    
    Label lblAsientos = new Label();
    lblAsientos.textProperty().bind(Bindings.concat("Capacidad: ", SalaAsientos.asString(), " asientos"));
    lblAsientos.setFont(Font.font("Verdana", tamanioFuente));
    lblAsientos.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblAsientos);
    
    // Tecnología 4D
    Sala4d = new SimpleBooleanProperty(objCargado.getSala4d());
    
    Label lblTecnologia = new Label();
    lblTecnologia.textProperty().bind(
        Bindings.when(Sala4d)
            .then("Tecnología: Sala 4D ")
            .otherwise("Tecnología: Sala Estándar")
    );
    lblTecnologia.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
    lblTecnologia.styleProperty().bind(
        Sala4d.map(es4d -> es4d.equals(true) 
            ? "-fx-text-fill: #E82E68;" 
            : "-fx-text-fill: #6C3483;")
    );
    miCajaVertical.getChildren().add(lblTecnologia);
    
    miBorderPane.setCenter(centerPane);
}
    
    
    
     private static Integer obtenerIndice(String opcion, int indice, int numCarros) {
        Integer nuevoIndice, limite;

        nuevoIndice = indice;
        limite = numCarros - 1;
        switch (opcion.toLowerCase()) {
            case "anterior" -> {
                if (indice == 0) {
                    nuevoIndice = limite;
                } else {
                    nuevoIndice = indice - 1;
                }
            }
            case "siguiente" -> {
                if (indice == limite) {
                    nuevoIndice = 0;
                } else {
                    nuevoIndice = indice + 1;
                }
            }
        }
        return nuevoIndice;
    }
     
     
     private void actualizarDatosCarrusel()
     {
         if(objCargado != null)
         {
             SalaNombre.set(objCargado.getNombreSala());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoSala();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                SalaImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaSalaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        SalaSede.set(objCargado.getSedeSala());
        SalaAsientos.set(objCargado.getAsientosSala());
        Sala4d.set(objCargado.getSala4d());    
         }
     }
    
}
