/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniMagdalena.vista.sede;

import com.uniMagdalena.controlador.sede.SedeControladorEliminar;
import com.uniMagdalena.controlador.sede.SedeControladorListar;
import com.uniMagdalena.controlador.sede.SedeControladorUna;
import com.uniMagdalena.controlador.sede.SedeControladorVentana;
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
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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


public class VistaSedeCarrusel extends SubScene
{
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    private int indiceActual;
    private int totalSedes;
    private SedeDto objCargado;

    private StringProperty SedeTitulo;
    private StringProperty SedeNombre;
    private ObjectProperty<Image> SedeImagen;
    private StringProperty SedeCiudad;
    private BooleanProperty Sede24horas;
    private StringProperty SedeUbicacion;
    private IntegerProperty SedeCantSalas;

    private static final String ARCHIVO_MEMORIA = "carrusel_Sede_posicion.txt";
    
    public VistaSedeCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
    
        super(new BorderPane(), anchoPanel, altoPanel);
        
        // IMPORTANTE PARA LA MEMORIA
        indiceActual = cargarIndiceGuardado();
        
        // Si el índice cargado es -1 (no existe) o está fuera de rango, usar el parámetro
        totalSedes = SedeControladorListar.cantidadSedes();
        if (indiceActual < 0 || indiceActual >= totalSedes) {
            indiceActual = indice;
        }
        //******************************************************************
        
        
        objCargado = SedeControladorUna.obtenerSede(indiceActual);
        
        miBorderPane = (BorderPane) this.getRoot();
        
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        miCajaVertical = new VBox();
        configurarMiCajaVertical();
        crearTitulo();
        
        construirPanelIzquierdo(0.14);
        construirPanelDerecho(0.14);
        construirPanelCentro();
        
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
    
    
    /**
     * Guarda el índice actual en un archivo para recordar la posición
     */
    private void guardarIndiceActual() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            Files.writeString(rutaArchivo, String.valueOf(indiceActual));
        } catch (IOException ex) {
            Logger.getLogger(VistaSedeCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo guardar la posición del carrusel", ex);
        }
    }
    
    /**
     * Carga el índice guardado desde el archivo
     * @return el índice guardado o -1 si no existe
     */
    private int cargarIndiceGuardado() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            if (Files.exists(rutaArchivo)) {
                String contenido = Files.readString(rutaArchivo);
                return Integer.parseInt(contenido.trim());
            }
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(VistaSedeCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo cargar la posición del carrusel", ex);
        }
        return -1;
    }
    
    
    
    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        miCajaVertical.getChildren().add(0, bloqueSeparador);

        totalSedes = SedeControladorListar.cantidadSedes();
        SedeTitulo = new SimpleStringProperty("Detalle de la sede (" + (indiceActual + 1) + " / " + totalSedes + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(SedeTitulo);
        lblTitulo.setTextFill(Color.web("#E82E68"));
        lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        miCajaVertical.getChildren().add(lblTitulo);
    }
    
    private void construirPanelIzquierdo(double porcentaje)
    {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 80));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);
        btnAnterior.setOnAction(e ->{
        indiceActual = obtenerIndice("Anterior", indiceActual, totalSedes);
        objCargado = SedeControladorUna.obtenerSede(indiceActual);
        guardarIndiceActual();                                  // IMPORTANTE PARA LA MEMORIA
        
        SedeTitulo.set("Detalle de la sede (" +(indiceActual + 1) + "/" + totalSedes + ")");
        
        SedeNombre.set(objCargado.getNombreSede());
        
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoSede();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                SedeImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaSedeCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            SedeNombre.set(objCargado.getNombreSede());
            SedeCiudad.set(objCargado.getCiudadSede());
            SedeUbicacion.set(objCargado.getUbicacionSede());
            Sede24horas.set(objCargado.getEs24horasSede());
            SedeCantSalas.set(objCargado.getSalasSede());
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
        btnSiguiente.setOnAction(e ->{
        indiceActual = obtenerIndice("Siguiente", indiceActual, totalSedes);
        objCargado = SedeControladorUna.obtenerSede(indiceActual);
        guardarIndiceActual();                      //IMPORTANTE PARA LA MEMORIA
        
        SedeTitulo.set("Detalle de la sede (" +(indiceActual + 1) + "/" + totalSedes + ")");
        
        SedeNombre.set(objCargado.getNombreSede());
        
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoSede();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                SedeImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaSedeCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//            SedeNombre.set(objCargado.getNombreSede());
            SedeCiudad.set(objCargado.getCiudadSede());
            SedeUbicacion.set(objCargado.getUbicacionSede());
            Sede24horas.set(objCargado.getEs24horasSede());
            SedeCantSalas.set(objCargado.getSalasSede());
            });
        
        StackPane panelDerecho = new StackPane();
        // panelIzquierdo.setStyle(borderPanel);
        panelDerecho.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        miBorderPane.setRight(panelDerecho);
    }
    
    
    private void panelOpciones() {
    int anchoBoton = 40;
    int tamanioIcono = 18;

    // Botón para eliminar
    // ***************************************************
    Button btnEliminar = new Button();
    btnEliminar.setPrefWidth(anchoBoton);
    btnEliminar.setCursor(Cursor.HAND);
    btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));

    btnEliminar.setOnAction((e) -> {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay sede para eliminar");
        } else {
            if (objCargado.getSalasSede() == 0) {
                String msg1, msg2, msg3, msg4;
                msg1 = "¿Estás seguro mi vale?";
                msg2 = "\nCódigo: " + objCargado.getIdSede();
                msg3 = "\nSede: " + objCargado.getNombreSede();
                msg4 = "\nSi se fue, se fue!";

                Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
                mensajito.setTitle("Te lo advierto");
                mensajito.setHeaderText(null);
                mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
                mensajito.initOwner(laVentanaPrincipal);

                if (mensajito.showAndWait().get() == ButtonType.OK) {
                    if (SedeControladorEliminar.borrar(indiceActual)) {
                        totalSedes = SedeControladorListar.cantidadSedes();
                        
                        // Ajustar el índice después de eliminar
                        if (indiceActual >= totalSedes && totalSedes > 0) {
                            indiceActual = totalSedes - 1;
                        } else if (totalSedes == 0) {
                            indiceActual = 0;
                        }
                        guardarIndiceActual();                    // IMPORTANTE PARA LA MEMORIA
                        
                        // Actualizar el título
                        SedeTitulo.set("Detalle de la sede (" + 
                            (indiceActual + 1) + " / " + totalSedes + ")");
                        
                        // Cargar el nuevo género si hay disponibles
                        if (totalSedes > 0) {
                            objCargado = SedeControladorUna.obtenerSede(indiceActual);
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
            } else {
                Mensaje.mostrar(Alert.AlertType.ERROR, laVentanaPrincipal, 
                    "Ey", "Ya tiene salas");
            }
        }
    });
    
    // ***************************************************

    // Botón para actualizar
    // ***************************************************
    Button btnActualizar = new Button();
    btnActualizar.setPrefWidth(anchoBoton);
    btnActualizar.setCursor(Cursor.HAND);
    btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));

    btnActualizar.setOnAction((ActionEvent e) -> {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay género para editar");
        } else {
            panelCuerpo = SedeControladorVentana.editar(
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
    // ***************************************************

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
        
        panelOpciones();
        
        SedeNombre = new SimpleStringProperty(objCargado.getNombreSede());
        
        int tamanioFuente = 18;
        Label lblNombreSede = new Label();
        lblNombreSede.textProperty().bind(SedeNombre);
        lblNombreSede.setFont(Font.font("Verdana", tamanioFuente));
        lblNombreSede.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblNombreSede);
        
        
        SedeImagen = new SimpleObjectProperty<>();
        
        FileInputStream imgArchivo;
        
        try {
            String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoSede();
            imgArchivo = new FileInputStream(rutaNuevaImagen);
            Image imgNueva = new Image(imgArchivo);
            SedeImagen.set(imgNueva);
            
            ImageView imgMostrar;
            imgMostrar = new ImageView(imgNueva);

            imgMostrar.setFitHeight(250);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            ImageView imgSede = imgMostrar;
            
            imgSede.imageProperty().bind(SedeImagen);
            miCajaVertical.getChildren().add(imgSede);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaSedeCarrusel.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
        SedeCiudad = new SimpleStringProperty(objCargado.getCiudadSede());              
        
        Label lblCiudad = new Label();
        lblCiudad.textProperty().bind(Bindings.concat("Ciudad: ", SedeCiudad));
        lblCiudad.setFont(Font.font("Verdana", tamanioFuente));
        lblCiudad.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblCiudad);
       
                
        SedeUbicacion = new SimpleStringProperty(objCargado.getUbicacionSede());              
        
        Label lblUbicacion = new Label();
        lblUbicacion.textProperty().bind(Bindings.concat("Ubicacion: ", SedeUbicacion));
        lblUbicacion.setFont(Font.font("Verdana", tamanioFuente));
        lblUbicacion.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblUbicacion);
        
        
        Sede24horas = new SimpleBooleanProperty(objCargado.getEs24horasSede());                 // OJO AQUI!
        
        Label lbl24horas = new Label();
        lbl24horas.textProperty().bind(Bindings.concat("Es 24 horas: ", 
            Bindings.when(Sede24horas).then("Sí").otherwise("No")));
        lbl24horas.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
        lbl24horas.setTextFill(Color.web("#E82E68"));

        miCajaVertical.getChildren().add(lbl24horas);
        
        
        SedeCantSalas = new SimpleIntegerProperty(objCargado.getSalasSede());
            
        Label lblCantSalas = new Label("Salas: " + objCargado.getSalasSede());
        lblCantSalas.textProperty().bind(Bindings.concat("Salas: ", SedeCantSalas.asString()));
        lblCantSalas.setFont(Font.font("Verdana", tamanioFuente));
        lblCantSalas.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblCantSalas);
        
        miBorderPane.setCenter(centerPane);
        
    }
    
    
    private void actualizarDatosCarrusel() {
    if (objCargado != null) {
        SedeNombre.set(objCargado.getNombreSede());
        
        try {
            String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
                + Persistencia.SEPARADOR_CARPETAS 
                + objCargado.getNombreImagenPrivadoSede();
            FileInputStream imgArchivo = new FileInputStream(rutaNuevaImagen);
            Image imgNueva = new Image(imgArchivo);
            SedeImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaSedeCarrusel.class.getName())
                .log(Level.SEVERE, null, ex);
        }
        
        SedeCiudad.set(objCargado.getCiudadSede());
        SedeUbicacion.set(objCargado.getUbicacionSede());
        Sede24horas.set(objCargado.getEs24horasSede());
        SedeCantSalas.set(objCargado.getSalasSede());
    }
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

    
}