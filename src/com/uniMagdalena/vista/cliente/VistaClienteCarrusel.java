package com.uniMagdalena.vista.cliente;

import com.uniMagdalena.controlador.cliente.ClienteControladorEliminar;
import com.uniMagdalena.controlador.cliente.ClienteControladorListar;
import com.uniMagdalena.controlador.cliente.ClienteControladorUna;
import com.uniMagdalena.controlador.cliente.ClienteControladorVentana;
import com.uniMagdalena.dto.ClienteDto;
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
import javafx.beans.property.LongProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
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

public class VistaClienteCarrusel extends SubScene
{
    private static final String ARCHIVO_MEMORIA = "carrusel_Cliente_posicion.txt";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    private int indiceActual;
    private int totalClientes;
    private ClienteDto objCargado;
    
    private StringProperty ClienteTitulo;
    private StringProperty ClienteNombre;
    private ObjectProperty<Image> ClienteImagen;
    private BooleanProperty ClienteGenero;
    private StringProperty ClienteTipoDocumento;
    private LongProperty ClienteNumeroDocumento;
    private StringProperty ClienteTipo;
    
    public VistaClienteCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
        super(new BorderPane(), anchoPanel, altoPanel);
        
        // Cargar el índice guardado en lugar del parámetro
        indiceActual = cargarIndiceGuardado();
        
        // Si el índice cargado es -1 (no existe) o está fuera de rango, usar el parámetro
        totalClientes = ClienteControladorListar.cantidadClientes();
         if (totalClientes == 0) {
        // No hay objetos, mostrar mensaje y crear vista vacía
        miBorderPane = (BorderPane) this.getRoot();
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        miCajaVertical = new VBox();
        
        mostrarMensajeCarruselVacio();
        return; // Salir del constructor
    }
        if (indiceActual < 0 || indiceActual >= totalClientes) {
            indiceActual = indice;
        }
        
        objCargado = ClienteControladorUna.obtenerCliente(indiceActual);
        
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
        
        // Guardar el índice cuando se cierre la ventana
        laVentanaPrincipal.setOnCloseRequest(event -> guardarIndiceActual());
    }
    
    public BorderPane getMiBorderPane()
    {
        return miBorderPane;
    }
    
    /**
     * Guarda el índice actual en un archivo para recordar la posición
     */
    private void guardarIndiceActual() {
        try {
            Path rutaArchivo = Paths.get(Persistencia.RUTA_IMAGENES_EXTERNAS, ARCHIVO_MEMORIA);
            Files.writeString(rutaArchivo, String.valueOf(indiceActual));
        } catch (IOException ex) {
            Logger.getLogger(VistaClienteCarrusel.class.getName())
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
            Logger.getLogger(VistaClienteCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo cargar la posición del carrusel", ex);
        }
        return -1;
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

        totalClientes = ClienteControladorListar.cantidadClientes();
        ClienteTitulo = new SimpleStringProperty("Detalle del Cliente (" + (indiceActual + 1) + " / " + totalClientes + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(ClienteTitulo);
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
            indiceActual = obtenerIndice("Anterior", indiceActual, totalClientes);
            objCargado = ClienteControladorUna.obtenerCliente(indiceActual);
            
            // Guardar el nuevo índice
            guardarIndiceActual();
            
            ClienteTitulo.set("Detalle del cliente (" + (indiceActual + 1) + "/" + totalClientes + ")");
            ClienteNombre.set(objCargado.getNombreCliente());
            
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoCliente();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                ClienteImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaClienteCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ClienteGenero.set(objCargado.getGeneroCliente());
            ClienteTipoDocumento.set(objCargado.getTipoDocumentoCliente());
            ClienteNumeroDocumento.set(objCargado.getNumeroDocumentoCliente());
            ClienteTipo.set(objCargado.getTipoCliente());         
            
        });
        
        StackPane panelIzquierdo = new StackPane();
       
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalClientes);
            objCargado = ClienteControladorUna.obtenerCliente(indiceActual);
            
            // Guardar el nuevo índice
            guardarIndiceActual();
            
            ClienteTitulo.set("Detalle del cliente (" + (indiceActual + 1) + "/" + totalClientes + ")");
            ClienteNombre.set(objCargado.getNombreCliente());
            
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoCliente();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                ClienteImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaClienteCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ClienteGenero.set(objCargado.getGeneroCliente());
            ClienteTipoDocumento.set(objCargado.getTipoDocumentoCliente());
            ClienteNumeroDocumento.set(objCargado.getNumeroDocumentoCliente());
            ClienteTipo.set(objCargado.getTipoCliente());         
            
        });
        
        StackPane panelDerecho = new StackPane();
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
        btnEliminar.setGraphic(Icono.obtenerIcono(Configuracion.ICONO_BORRAR, tamanioIcono));
        
        btnEliminar.setOnAction((e) -> {
            if(totalClientes > 1)
            {
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay película para eliminar");
        } else {
            if(objCargado.getClienteVentas() == 0)
            {
            String msg1, msg2, msg3, msg4;
            
            msg1 = "¿Estás seguro mi vale?";
            msg2 = "\nNum documento: " + objCargado.getNumeroDocumentoCliente();
            msg3 = "\nNombre: " + objCargado.getNombreCliente();
            msg4 = "\nSi se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(laVentanaPrincipal);
            
            if (mensajito.showAndWait().get() == ButtonType.OK) {
                if (ClienteControladorEliminar.borrar(indiceActual)) {
                    totalClientes = ClienteControladorListar.cantidadClientes();
                    
                    if (indiceActual >= totalClientes && totalClientes > 0) {
                        indiceActual = totalClientes - 1;
                    } else if (totalClientes == 0) {
                        indiceActual = 0;
                    }
                    
                    // Guardar el nuevo índice después de eliminar
                    guardarIndiceActual();
                    
                    ClienteTitulo.set("Detalle de la película (" + 
                        (indiceActual + 1) + " / " + totalClientes + ")");
                    
                    if (totalClientes > 0) {
                        objCargado = ClienteControladorUna.obtenerCliente(indiceActual);
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
                Mensaje.mostrar(Alert.AlertType.ERROR, laVentanaPrincipal, "Ey", "Ya tiene ventas");
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
                "Advertencia", "No hay género para editar");
        } else {
            panelCuerpo = ClienteControladorVentana.editar(
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

    Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
    centerPane.setBackground(fondo);

    Rectangle miMarco = Marco.pintar(laVentanaPrincipal, 0.55, 0.75,
            Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
    centerPane.getChildren().addAll(miMarco, miCajaVertical);
    
    panelOpciones();
    
    ClienteNombre = new SimpleStringProperty(objCargado.getNombreCliente());
    
    int tamanioFuente = 18;
    Label lblNombreCliente = new Label();
    lblNombreCliente.textProperty().bind(ClienteNombre);
    lblNombreCliente.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
    lblNombreCliente.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblNombreCliente);
    
    ClienteImagen = new SimpleObjectProperty<>();
    
    FileInputStream imgArchivo;
    
    try {
        String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
            + Persistencia.SEPARADOR_CARPETAS 
            + objCargado.getNombreImagenPrivadoCliente();
        imgArchivo = new FileInputStream(rutaNuevaImagen);
        Image imgNueva = new Image(imgArchivo);
        ClienteImagen.set(imgNueva);
        
        ImageView imgMostrar = new ImageView(imgNueva);
        imgMostrar.setFitHeight(250);
        imgMostrar.setSmooth(true);
        imgMostrar.setPreserveRatio(true);
        
        imgMostrar.imageProperty().bind(ClienteImagen);
        miCajaVertical.getChildren().add(imgMostrar);
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(VistaClienteCarrusel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    ClienteGenero = new SimpleBooleanProperty(objCargado.getGeneroCliente());
    
    Label lblGenero = new Label();
    lblGenero.textProperty().bind(Bindings.when(ClienteGenero)
        .then("Género: Masculino")
        .otherwise("Género: Femenino")
    );
    lblGenero.setFont(Font.font("Verdana", tamanioFuente));
    lblGenero.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblGenero);
    
    ClienteTipoDocumento = new SimpleStringProperty(objCargado.getTipoDocumentoCliente());
    
    Label lblTipoDoc = new Label();
    lblTipoDoc.textProperty().bind(Bindings.concat("Tipo de Documento: ", ClienteTipoDocumento));
    lblTipoDoc.setFont(Font.font("Verdana", tamanioFuente));
    lblTipoDoc.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblTipoDoc);
    
    ClienteNumeroDocumento = new SimpleLongProperty(objCargado.getNumeroDocumentoCliente());
    
    Label lblNumeroDoc = new Label();
    lblNumeroDoc.textProperty().bind(Bindings.concat("Número de Documento: ", ClienteNumeroDocumento.asString()));
    lblNumeroDoc.setFont(Font.font("Verdana", tamanioFuente));
    lblNumeroDoc.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblNumeroDoc);
    
    ClienteTipo = new SimpleStringProperty(objCargado.getTipoCliente());
    
    Label lblTipoCliente = new Label();
    lblTipoCliente.textProperty().bind(Bindings.concat("Tipo de Cliente: ", ClienteTipo));
    lblTipoCliente.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
    lblTipoCliente.setTextFill(Color.web("#E82E68"));
    miCajaVertical.getChildren().add(lblTipoCliente);
    
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
            ClienteNombre.set(objCargado.getNombreCliente());
            
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoCliente();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                ClienteImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaClienteCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ClienteGenero.set(objCargado.getGeneroCliente());
            ClienteTipoDocumento.set(objCargado.getTipoDocumentoCliente());
            ClienteNumeroDocumento.set(objCargado.getNumeroDocumentoCliente());
            ClienteTipo.set(objCargado.getTipoCliente());    
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
    Label lblTitulo = new Label("No hay clientes registrados");
    lblTitulo.setTextFill(Color.web("#E82E68"));
    lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 30));
    
    // Mensaje descriptivo
    Label lblMensaje = new Label("Aún no se han creado clientes en el sistema.\nPor favor, crea uno nuevo para comenzar.");
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
        "Carrusel vacío", "No hay clientes registrados en el sistema.");
}
}
