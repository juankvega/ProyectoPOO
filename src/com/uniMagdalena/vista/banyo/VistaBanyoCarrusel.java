
package com.uniMagdalena.vista.banyo;

import com.uniMagdalena.controlador.banyo.BanyoControladorEliminar;
import com.uniMagdalena.controlador.banyo.BanyoControladorListar;
import com.uniMagdalena.controlador.banyo.BanyoControladorUna;
import com.uniMagdalena.controlador.banyo.BanyoControladorVentana;
import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.dto.TrabajadorDto;
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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

public class VistaBanyoCarrusel extends SubScene
{
    private static final String ARCHIVO_MEMORIA = "carrusel_Banyo_posicion.txt";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    private int indiceActual;
    private int totalBanyos;
    private BanyoDto objCargado;
    
    private StringProperty BanyoTitulo;
    private StringProperty BanyoUbicacion;
    private ObjectProperty<Image> BanyoImagen;
    private ObjectProperty<SedeDto> BanyoSede;
    private ObjectProperty<TrabajadorDto> BanyoTrabajador;
    private BooleanProperty BanyoGenero;
    private BooleanProperty BanyoUso;
    
    public VistaBanyoCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
        super(new BorderPane(), anchoPanel, altoPanel);
        
        indiceActual = cargarIndiceGuardado();
        
        totalBanyos = BanyoControladorListar.cantidadBanyos();
        
        if (totalBanyos == 0) {
        // No hay objetos, mostrar mensaje y crear vista vacía
        miBorderPane = (BorderPane) this.getRoot();
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        miCajaVertical = new VBox();
        
        mostrarMensajeCarruselVacio();
        return; // Salir del constructor
    }
        if (indiceActual < 0 || indiceActual >= totalBanyos) {
            indiceActual = indice;
        }
        
        objCargado = BanyoControladorUna.obtenerBanyo(indiceActual);
        
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
    
            private void guardarIndiceActual() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            Files.writeString(rutaArchivo, String.valueOf(indiceActual));
        } catch (IOException ex) {
            Logger.getLogger(VistaBanyoCarrusel.class.getName())
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
            Logger.getLogger(VistaBanyoCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo cargar la posición del carrusel", ex);
        }
        return -1;
    }
    
     private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        miCajaVertical.getChildren().add(0, bloqueSeparador);

        totalBanyos = BanyoControladorListar.cantidadBanyos();
        BanyoTitulo = new SimpleStringProperty("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalBanyos + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(BanyoTitulo);
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
        btnAnterior.setOnAction(e -> 
        {
            indiceActual = obtenerIndice("Anterior", indiceActual, totalBanyos);
            objCargado = BanyoControladorUna.obtenerBanyo(indiceActual);
            
            guardarIndiceActual();
            
            BanyoTitulo.set("Detalle de Banyo (" + (indiceActual + 1) + "/" + totalBanyos + ")");
            BanyoUbicacion.set(objCargado.getUbicacionBanyo());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoBanyo();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                BanyoImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaBanyoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        BanyoGenero.set(objCargado.getGeneroBanyo());
        BanyoSede.set(objCargado.getSedeBanyo());
        BanyoTrabajador.set(objCargado.getEncargadoBanyo());
        BanyoUso.set(objCargado.getUsoBanyo());
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalBanyos);
            objCargado = BanyoControladorUna.obtenerBanyo(indiceActual);
            
            guardarIndiceActual();
            
            BanyoTitulo.set("Detalle de Banyo (" + (indiceActual + 1) + "/" + totalBanyos + ")");
            BanyoUbicacion.set(objCargado.getUbicacionBanyo());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoBanyo();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                BanyoImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaBanyoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        BanyoGenero.set(objCargado.getGeneroBanyo());
        BanyoSede.set(objCargado.getSedeBanyo());
        BanyoTrabajador.set(objCargado.getEncargadoBanyo());
        BanyoUso.set(objCargado.getUsoBanyo());
        });
        
        StackPane panelDerecho = new StackPane();
        // panelDerecho.setStyle(borderPanel);
        panelDerecho.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(porcentaje));
        panelDerecho.getChildren().add(btnSiguiente);
        miBorderPane.setRight(panelDerecho);
        
    }
     
    
      private void panelOpciones()
    {
        int anchoBoton = 40;
        int tamanioIcono = 18;

    // Botón para eliminar
    // ***************************************************
    

        Button btnEliminar = new Button();
        btnEliminar.setPrefWidth(anchoBoton);
        btnEliminar.setCursor(Cursor.HAND);
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));
        btnEliminar.setOnAction((e) -> {
        if(totalBanyos > 1)
        {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay película para eliminar");
        } else {
            String msg1, msg2, msg3, msg4, msg5;
            
            msg1 = "¿Estás seguro mi vale?";
            msg2 = "\nCódigo: " + objCargado.getIdBanyo();
            msg3 = "\nSede: " + objCargado.getSedeBanyo().getNombreSede();
            msg4 = "\n Ubicación: "+objCargado.getUbicacionBanyo();
            msg5 = "\nSi se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4 + msg5);
            mensajito.initOwner(laVentanaPrincipal);
            
            if (mensajito.showAndWait().get() == ButtonType.OK) {
                if (BanyoControladorEliminar.borrar(indiceActual)) {
                    totalBanyos = BanyoControladorListar.cantidadBanyos();
                    
                    // Ajustar el índice después de eliminar
                    if (indiceActual >= totalBanyos && totalBanyos > 0) {
                        indiceActual = totalBanyos - 1;
                    } else if (totalBanyos == 0) {
                        indiceActual = 0;
                    }
                    
                    guardarIndiceActual();
                    
                    // Actualizar el título
                    BanyoTitulo.set("Detalle de la película (" + 
                        (indiceActual + 1) + " / " + totalBanyos + ")");
                    
                    // Cargar la nueva película si hay disponibles
                    if (totalBanyos > 0) {
                        objCargado = BanyoControladorUna.obtenerBanyo(indiceActual);
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
        }
        }else{
        Mensaje.mostrar(Alert.AlertType.ERROR, 
                        laVentanaPrincipal, "Pailas", "No lo puedo borrar! despues se explota el carrusel");
    }    
    });        

        
    Button btnActualizar = new Button();
    btnActualizar.setPrefWidth(anchoBoton);
    btnActualizar.setCursor(Cursor.HAND);
    btnActualizar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_EDITAR, tamanioIcono));
    btnActualizar.setOnAction((ActionEvent e) -> {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay pelicula para editar");
        } else {
            panelCuerpo = BanyoControladorVentana.editar(
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
    
    // Inicializar propiedades
    BanyoUbicacion = new SimpleStringProperty(objCargado.getUbicacionBanyo());
    BanyoImagen = new SimpleObjectProperty<>();
    BanyoGenero = new SimpleBooleanProperty(objCargado.getGeneroBanyo());
    BanyoSede = new SimpleObjectProperty<>(objCargado.getSedeBanyo());
    BanyoTrabajador = new SimpleObjectProperty<>(objCargado.getEncargadoBanyo());
    BanyoUso = new SimpleBooleanProperty(objCargado.getUsoBanyo());
    
    int tamanioFuente = 18;
    
    // Ubicación del baño
    Label lblUbicacion = new Label();
    lblUbicacion.textProperty().bind(Bindings.concat("Ubicación: ", BanyoUbicacion));
    lblUbicacion.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
    lblUbicacion.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblUbicacion);
    
    // Imagen del baño
    FileInputStream imgArchivo;
    
    try {
        String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
            + Persistencia.SEPARADOR_CARPETAS 
            + objCargado.getNombreImagenPrivadoBanyo();
        imgArchivo = new FileInputStream(rutaNuevaImagen);
        Image imgNueva = new Image(imgArchivo);
        BanyoImagen.set(imgNueva);
        
        ImageView imgMostrar = new ImageView(imgNueva);
        imgMostrar.setFitHeight(250);
        imgMostrar.setSmooth(true);
        imgMostrar.setPreserveRatio(true);
        
        imgMostrar.imageProperty().bind(BanyoImagen);
        miCajaVertical.getChildren().add(imgMostrar);
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(VistaBanyoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Sede del baño
    Label lblSede = new Label();
    lblSede.textProperty().bind(Bindings.createStringBinding(
        () -> "Sede: " + (BanyoSede.get() != null ? BanyoSede.get().getNombreSede() : "N/A"),
        BanyoSede
    ));
    lblSede.setFont(Font.font("Verdana", tamanioFuente));
    lblSede.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblSede);
    
    // Género del baño
    Label lblGenero = new Label();
    lblGenero.textProperty().bind(
        Bindings.when(BanyoGenero)
            .then("Género: Masculino")
            .otherwise("Género: Femenino")
    );
    lblGenero.setFont(Font.font("Verdana", tamanioFuente));
    lblGenero.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblGenero);
    
    // Encargado del baño
    Label lblEncargado = new Label();
    lblEncargado.textProperty().bind(Bindings.createStringBinding(
        () -> "Encargado: " + (BanyoTrabajador.get() != null ? BanyoTrabajador.get().getNombreTrabajador() : "N/A"),
        BanyoTrabajador
    ));
    lblEncargado.setFont(Font.font("Verdana", tamanioFuente));
    lblEncargado.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblEncargado);
    
    // Estado de uso del baño
    Label lblUso = new Label();
    lblUso.textProperty().bind(
        Bindings.when(BanyoUso)
            .then("Estado: En uso")
            .otherwise("Estado: Fuera de servicio")
    );
    lblUso.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
    lblUso.styleProperty().bind(
        BanyoUso.map(dato -> dato.equals(true) 
            ? "-fx-text-fill: green;" 
            : "-fx-text-fill: red;")
    );
    miCajaVertical.getChildren().add(lblUso);
    
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
           BanyoUbicacion.set(objCargado.getUbicacionBanyo());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoBanyo();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                BanyoImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaBanyoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        BanyoGenero.set(objCargado.getGeneroBanyo());
        BanyoSede.set(objCargado.getSedeBanyo());
        BanyoTrabajador.set(objCargado.getEncargadoBanyo());
        BanyoUso.set(objCargado.getUsoBanyo()); 
        }
        
        
        
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
    Label lblTitulo = new Label("No hay baños registrados");
    lblTitulo.setTextFill(Color.web("#E82E68"));
    lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 30));
    
    // Mensaje descriptivo
    Label lblMensaje = new Label("Aún no se han creado baños en el sistema.\nPor favor, crea uno nuevo para comenzar.");
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
        "Carrusel vacío", "No hay baños registrados en el sistema.");
}
     
}
