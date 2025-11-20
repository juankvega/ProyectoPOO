
package com.uniMagdalena.vista.genero;

import com.uniMagdalena.controlador.genero.GeneroControladorEliminar;
import com.uniMagdalena.controlador.genero.GeneroControladorListar;
import com.uniMagdalena.controlador.genero.GeneroControladorUna;
import com.uniMagdalena.controlador.genero.GeneroControladorVentana;
import com.uniMagdalena.dto.GeneroDto;
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

public class VistaGeneroCarrusel extends SubScene
{
    private static final String ARCHIVO_MEMORIA = "carrusel_Genero_Posicion.txt";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    private int indiceActual;
    private int totalGeneros;
    private GeneroDto objCargado;

    private StringProperty GeneroTitulo;
    private StringProperty GeneroNombre;
    private ObjectProperty<Image> GeneroImagen;
    private BooleanProperty GeneroEstado;
    private IntegerProperty GeneroPopularidad;
    private BooleanProperty GeneroClasico;
    private IntegerProperty GeneroCantPel;
    
    public VistaGeneroCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
        super(new BorderPane(), anchoPanel, altoPanel);
        
        indiceActual = cargarIndiceGuardado();
        
        totalGeneros = GeneroControladorListar.cantidadGeneros();
        
         if (totalGeneros == 0) {
        // No hay objetos, mostrar mensaje y crear vista vacía
        miBorderPane = (BorderPane) this.getRoot();
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        miCajaVertical = new VBox();
        
        mostrarMensajeCarruselVacio();
        return; // Salir del constructor
    }
        
        if (indiceActual < 0 || indiceActual >= totalGeneros) {
            indiceActual = indice;
        }
        objCargado = GeneroControladorUna.obtenerGenero(indiceActual);
        
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
    
    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        miCajaVertical.getChildren().add(0, bloqueSeparador);

        totalGeneros = GeneroControladorListar.cantidadGeneros();
        GeneroTitulo = new SimpleStringProperty("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalGeneros + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(GeneroTitulo);
        lblTitulo.setTextFill(Color.web("#E82E68"));
        lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 25));
        miCajaVertical.getChildren().add(lblTitulo);
    }
    
              private void guardarIndiceActual() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            Files.writeString(rutaArchivo, String.valueOf(indiceActual));
        } catch (IOException ex) {
            Logger.getLogger(VistaGeneroCarrusel.class.getName())
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
            Logger.getLogger(VistaGeneroCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo cargar la posición del carrusel", ex);
        }
        return -1;
    }
    
    private void construirPanelIzquierdo(double porcentaje)
    {
        Button btnAnterior = new Button();
        btnAnterior.setGraphic(Icono.obtenerIcono("btnAtras.png", 80));
        btnAnterior.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        btnAnterior.setCursor(Cursor.HAND);
        btnAnterior.setOnAction(e ->{
        indiceActual = obtenerIndice("Anterior", indiceActual, totalGeneros);
        objCargado = GeneroControladorUna.obtenerGenero(indiceActual);
        
        guardarIndiceActual();
        
        GeneroTitulo.set("Detalle del género (" +(indiceActual + 1) + "/" + totalGeneros + ")");
        
        GeneroNombre.set(objCargado.getNombreGenero());
        
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoGenero();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                GeneroImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaGeneroCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            GeneroEstado.set(objCargado.getEstadoGenero());
            GeneroPopularidad.set(objCargado.getPopularidadGenero());
            GeneroClasico.set(objCargado.getEsClasicoGenero());
            GeneroCantPel.set(objCargado.getPeliculasGenero());  
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
        indiceActual = obtenerIndice("Siguiente", indiceActual, totalGeneros);
        objCargado = GeneroControladorUna.obtenerGenero(indiceActual);
        
        guardarIndiceActual();
        
        GeneroTitulo.set("Detalle del género (" +(indiceActual + 1) + "/" + totalGeneros + ")");
        
        GeneroNombre.set(objCargado.getNombreGenero());
        
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoGenero();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                GeneroImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaGeneroCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            GeneroEstado.set(objCargado.getEstadoGenero());
            GeneroPopularidad.set(objCargado.getPopularidadGenero());
            GeneroClasico.set(objCargado.getEsClasicoGenero());
            GeneroCantPel.set(objCargado.getPeliculasGenero());  
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
                "Advertencia", "No hay género para eliminar");
        } else {
            if(totalGeneros > 1)
            {
            if (objCargado.getPeliculasGenero() == 0) {
                String msg1, msg2, msg3, msg4;
                msg1 = "¿Estás seguro mi vale?";
                msg2 = "\nCódigo: " + objCargado.getIdGenero();
                msg3 = "\nGénero: " + objCargado.getNombreGenero();
                msg4 = "\nSi se fue, se fue!";

                Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
                mensajito.setTitle("Te lo advierto");
                mensajito.setHeaderText(null);
                mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
                mensajito.initOwner(laVentanaPrincipal);

                if (mensajito.showAndWait().get() == ButtonType.OK) {
                    if (GeneroControladorEliminar.borrar(indiceActual)) {
                        totalGeneros = GeneroControladorListar.cantidadGeneros();
                        
                        // Ajustar el índice después de eliminar
                        if (indiceActual >= totalGeneros && totalGeneros > 0) {
                            indiceActual = totalGeneros - 1;
                        } else if (totalGeneros == 0) {
                            indiceActual = 0;
                        }
                        
                        guardarIndiceActual();
                        
                        // Actualizar el título
                        GeneroTitulo.set("Detalle de la categoría (" + 
                            (indiceActual + 1) + " / " + totalGeneros + ")");
                        
                        // Cargar el nuevo género si hay disponibles
                        if (totalGeneros > 0) {
                            objCargado = GeneroControladorUna.obtenerGenero(indiceActual);
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
                    "Ey", "Ya tiene películas");
            }
        } else{
                Mensaje.mostrar(Alert.AlertType.ERROR, 
                        laVentanaPrincipal, "Pailas", "No lo puedo borrar! despues se explota el carrusel");
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
            panelCuerpo = GeneroControladorVentana.editar(
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
        
        GeneroNombre = new SimpleStringProperty(objCargado.getNombreGenero());
        
        int tamanioFuente = 18;
        Label lblNombreGen = new Label();
        lblNombreGen.textProperty().bind(GeneroNombre);
        lblNombreGen.setFont(Font.font("Verdana", tamanioFuente));
        lblNombreGen.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblNombreGen);
        
        GeneroImagen = new SimpleObjectProperty<>();
        
        FileInputStream imgArchivo;
        
        try {
            String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoGenero();
            imgArchivo = new FileInputStream(rutaNuevaImagen);
            Image imgNueva = new Image(imgArchivo);
            GeneroImagen.set(imgNueva);
            
            ImageView imgMostrar;
            imgMostrar = new ImageView(imgNueva);

            imgMostrar.setFitHeight(250);
            imgMostrar.setSmooth(true);
            imgMostrar.setPreserveRatio(true);
            ImageView imgGenero = imgMostrar;
            
            imgGenero.imageProperty().bind(GeneroImagen);
            miCajaVertical.getChildren().add(imgGenero);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaGeneroCarrusel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GeneroEstado = new SimpleBooleanProperty(objCargado.getEstadoGenero());
        Label lblEstado = new Label();
        lblEstado.textProperty().bind(Bindings.when(GeneroEstado).then("Activo").otherwise("Inactivo"));
        lblEstado.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
        lblEstado.styleProperty().bind(
        GeneroEstado.map(dato -> dato.equals(true) ? "-fx-text-fill: #6C3483;" : "-fx-text-fill: red;"));
        miCajaVertical.getChildren().add(lblEstado);
        
        GeneroPopularidad = new SimpleIntegerProperty(objCargado.getPopularidadGenero());
        
        Label lblPopularidad = new Label();
        lblPopularidad.textProperty().bind(Bindings.concat("Popularidad: ", GeneroPopularidad.asString()));
        lblPopularidad.setFont(Font.font("Verdana", tamanioFuente));
        lblPopularidad.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblPopularidad);
        
        GeneroClasico = new SimpleBooleanProperty(objCargado.getEsClasicoGenero());
        Label lblClasico = new Label();
        lblClasico.textProperty().bind(Bindings.when(GeneroClasico).then("Clásico").otherwise("No Clásico"));
        lblClasico.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
        lblClasico.styleProperty().bind(
        GeneroClasico.map(dato -> dato.equals(true) ? "-fx-text-fill: #6C3483;" : "-fx-text-fill: red;"));
        miCajaVertical.getChildren().add(lblClasico);
        
        GeneroCantPel = new SimpleIntegerProperty(objCargado.getPeliculasGenero());
        
        Label lblCantPeliculas = new Label("Peliculas: " + objCargado.getPeliculasGenero());
        lblCantPeliculas.textProperty().bind(Bindings.concat("Peliculas: ", GeneroCantPel.asString()));
        lblCantPeliculas.setFont(Font.font("Verdana", tamanioFuente));
        lblCantPeliculas.setTextFill(Color.web("#6C3483"));
        miCajaVertical.getChildren().add(lblCantPeliculas);
        
        miBorderPane.setCenter(centerPane);
    }
    
    
    
    
    
    private void actualizarDatosCarrusel() {
    if (objCargado != null) {
        GeneroNombre.set(objCargado.getNombreGenero());
        
        try {
            String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
                + Persistencia.SEPARADOR_CARPETAS 
                + objCargado.getNombreImagenPrivadoGenero();
            FileInputStream imgArchivo = new FileInputStream(rutaNuevaImagen);
            Image imgNueva = new Image(imgArchivo);
            GeneroImagen.set(imgNueva);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VistaGeneroCarrusel.class.getName())
                .log(Level.SEVERE, null, ex);
        }
        
        GeneroEstado.set(objCargado.getEstadoGenero());
        GeneroPopularidad.set(objCargado.getPopularidadGenero());
        GeneroClasico.set(objCargado.getEsClasicoGenero());
        GeneroCantPel.set(objCargado.getPeliculasGenero());
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
    
    private void mostrarMensajeCarruselVacio() {
    configurarMiCajaVertical();
    
    // Crear el panel central con el mensaje
    StackPane centerPane = new StackPane();
    
    // Fondo
    Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
    centerPane.setBackground(fondo);
    
    // Marco
    Rectangle miMarco = Marco.pintar(laVentanaPrincipal, 0.55, 0.75,
            Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
    
    // Contenedor para el mensaje
    VBox contenedorMensaje = new VBox(20);
    contenedorMensaje.setAlignment(Pos.CENTER);
    contenedorMensaje.prefWidthProperty().bind(laVentanaPrincipal.widthProperty());
    contenedorMensaje.prefHeightProperty().bind(laVentanaPrincipal.heightProperty());
    
    // Título
    Label lblTitulo = new Label("No hay Generos registrados");
    lblTitulo.setTextFill(Color.web("#E82E68"));
    lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 30));
    
    // Mensaje descriptivo
    Label lblMensaje = new Label("Aún no se han creado Generos en el sistema.\nPor favor, crea uno nuevo para comenzar.");
    lblMensaje.setTextFill(Color.web("#6C3483"));
    lblMensaje.setFont(Font.font("Verdana", 18));
    lblMensaje.setAlignment(Pos.CENTER);
    lblMensaje.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
    
    // Ícono opcional (si tienes uno disponible)
    ImageView iconoVacio = null;
    try {
        iconoVacio = Icono.obtenerIcono("imgNoDisponible.png", 100);
    } catch (Exception e) {
        // Si no hay ícono, continuar sin él
    }
    
    // Agregar elementos al contenedor
    if (iconoVacio != null) {
        contenedorMensaje.getChildren().add(iconoVacio);
    }
    contenedorMensaje.getChildren().addAll(lblTitulo, lblMensaje);
    
    centerPane.getChildren().addAll(miMarco, contenedorMensaje);
    miBorderPane.setCenter(centerPane);
    
    // Mostrar también un mensaje de alerta
    Mensaje.mostrar(Alert.AlertType.INFORMATION, laVentanaPrincipal, 
        "Carrusel vacío", "No hay Generos registrados en el sistema.");
}
     
}
    
    
    
  

