package com.uniMagdalena.vista.producto;

import com.uniMagdalena.controlador.Producto.ProductoControladorVentana;
import com.uniMagdalena.controlador.producto.ProductoControladorEliminar;
import com.uniMagdalena.controlador.producto.ProductoControladorListar;
import com.uniMagdalena.controlador.producto.ProductoControladorUna;
import com.uniMagdalena.dto.ProductoDto;
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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

public class VistaProductoCarrusel extends SubScene
{
    private static final String ARCHIVO_MEMORIA = "carrusel_Producto_posicion.txt";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;

    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    private int indiceActual;
    private int totalProductos;
    private ProductoDto objCargado;
    
    private StringProperty ProductoTitulo;
    private StringProperty ProductoNombre;
    private ObjectProperty<Image> ProductoImagen;
    private BooleanProperty ProductoTipo;
    private StringProperty ProductoTamanio;
    private DoubleProperty ProductoPrecio;
    
    public VistaProductoCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
        super(new BorderPane(), anchoPanel, altoPanel);
        
        // Cargar el índice guardado en lugar del parámetro
        indiceActual = cargarIndiceGuardado();
        
        
        
        // Si el índice cargado es -1 (no existe) o está fuera de rango, usar el parámetro
        totalProductos = ProductoControladorListar.cantidadProductos();
        
         if (totalProductos == 0) {
        // No hay objetos, mostrar mensaje y crear vista vacía
        miBorderPane = (BorderPane) this.getRoot();
        laVentanaPrincipal = ventanaPadre;
        panelPrincipal = princ;
        panelCuerpo = pane;
        miCajaVertical = new VBox();
        
        mostrarMensajeCarruselVacio();
        return; // Salir del constructor
    }
        
        if (indiceActual < 0 || indiceActual >= totalProductos) {
            indiceActual = indice;
        }
        
        objCargado = ProductoControladorUna.obtenerProducto(indiceActual);
        
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
            Logger.getLogger(VistaProductoCarrusel.class.getName())
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
            Logger.getLogger(VistaProductoCarrusel.class.getName())
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

        totalProductos = ProductoControladorListar.cantidadProductos();
        ProductoTitulo = new SimpleStringProperty("Detalle del Producto (" + (indiceActual + 1) + " / " + totalProductos + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(ProductoTitulo);
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
            indiceActual = obtenerIndice("Anterior", indiceActual, totalProductos);
            objCargado = ProductoControladorUna.obtenerProducto(indiceActual);
            
            // Guardar el nuevo índice
            guardarIndiceActual();
            
            ProductoTitulo.set("Detalle del producto (" + (indiceActual + 1) + "/" + totalProductos + ")");
            ProductoNombre.set(objCargado.getNombreProducto());
            
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoProducto();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                ProductoImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaProductoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ProductoTipo.set(objCargado.getTipoProducto());
            ProductoTamanio.set(objCargado.getTamanioProducto());
            ProductoPrecio.set(objCargado.getPrecioProducto());
            
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalProductos);
            objCargado = ProductoControladorUna.obtenerProducto(indiceActual);
            
            // Guardar el nuevo índice
            guardarIndiceActual();
            
            ProductoTitulo.set("Detalle del producto (" + (indiceActual + 1) + "/" + totalProductos + ")");
            ProductoNombre.set(objCargado.getNombreProducto());
            
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoProducto();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                ProductoImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaProductoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ProductoTipo.set(objCargado.getTipoProducto());
            ProductoTamanio.set(objCargado.getTamanioProducto());
            ProductoPrecio.set(objCargado.getPrecioProducto());
            
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
        if (objCargado == null) {
            Mensaje.mostrar(Alert.AlertType.WARNING, laVentanaPrincipal, 
                "Advertencia", "No hay producto para eliminar");
        } else {
            if(totalProductos > 1)
            {
            if(objCargado.getProductoVentas() == 0)
            {
            String msg1, msg2, msg3, msg4, msg5;
            
            msg1 = "¿Estás seguro de eliminar este producto?";
            msg2 = "\n\nNombre: " + objCargado.getNombreProducto();
            String tipoTexto = (objCargado.getTipoProducto() != null && objCargado.getTipoProducto())
                        ? "Comida"
                        : "Bebida";
            msg3 = "\nTipo: " + tipoTexto;
            msg4 = "\nTamaño: " + objCargado.getTamanioProducto();
            msg5 = "\n\nEsta acción no se puede deshacer.";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Confirmar eliminación");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4 + msg5);
            mensajito.initOwner(laVentanaPrincipal);
            
            if (mensajito.showAndWait().get() == ButtonType.OK) {
                if (ProductoControladorEliminar.borrar(indiceActual)) {
                    totalProductos = ProductoControladorListar.cantidadProductos();
                    
                    if (indiceActual >= totalProductos && totalProductos > 0) {
                        indiceActual = totalProductos - 1;
                    } else if (totalProductos == 0) {
                        indiceActual = 0;
                    }
                    
                    // Guardar el nuevo índice después de eliminar
                    guardarIndiceActual();
                    
                    ProductoTitulo.set("Detalle del producto (" + 
                        (indiceActual + 1) + " / " + totalProductos + ")");
                    
                    if (totalProductos > 0) {
                        objCargado = ProductoControladorUna.obtenerProducto(indiceActual);
                        actualizarDatosCarrusel();
                    } else {
                        objCargado = null;
                    }
                    
                    Mensaje.mostrar(Alert.AlertType.INFORMATION, 
                        laVentanaPrincipal, "Éxito", "Producto eliminado correctamente");
                } else {
                    Mensaje.mostrar(Alert.AlertType.ERROR, 
                        laVentanaPrincipal, "Error", "No se pudo eliminar el producto");
                    }
                }
            }else{
                Mensaje.mostrar(Alert.AlertType.ERROR, laVentanaPrincipal, "No se puede eliminar", "Este producto ya tiene ventas asociadas");
            }
        } else{
                Mensaje.mostrar(Alert.AlertType.ERROR, 
                        laVentanaPrincipal, "Pailas", "No lo puedo borrar! despues se explota el carrusel");
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
                "Advertencia", "No hay producto para editar");
        } else {
            panelCuerpo = ProductoControladorVentana.editar(
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
    
    ProductoNombre = new SimpleStringProperty(objCargado.getNombreProducto());
    
    int tamanioFuente = 18;
    Label lblNombreProducto = new Label();
    lblNombreProducto.textProperty().bind(ProductoNombre);
    lblNombreProducto.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
    lblNombreProducto.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblNombreProducto);
    
    ProductoImagen = new SimpleObjectProperty<>();
    
    FileInputStream imgArchivo;
    
    try {
        String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
            + Persistencia.SEPARADOR_CARPETAS 
            + objCargado.getNombreImagenPrivadoProducto();
        imgArchivo = new FileInputStream(rutaNuevaImagen);
        Image imgNueva = new Image(imgArchivo);
        ProductoImagen.set(imgNueva);
        
        ImageView imgMostrar = new ImageView(imgNueva);
        imgMostrar.setFitHeight(250);
        imgMostrar.setSmooth(true);
        imgMostrar.setPreserveRatio(true);
        
        imgMostrar.imageProperty().bind(ProductoImagen);
        miCajaVertical.getChildren().add(imgMostrar);
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(VistaProductoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    ProductoTipo = new SimpleBooleanProperty(objCargado.getTipoProducto());
    
    Label lblTipo = new Label();
    lblTipo.textProperty().bind(Bindings.when(ProductoTipo)
        .then("Tipo: Comida")
        .otherwise("Tipo: Bebida")
    );
    lblTipo.setFont(Font.font("Verdana", tamanioFuente));
    lblTipo.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblTipo);
    
    ProductoTamanio = new SimpleStringProperty(objCargado.getTamanioProducto());
    
    Label lblTamanio = new Label();
    lblTamanio.textProperty().bind(Bindings.concat("Tamaño: ", ProductoTamanio));
    lblTamanio.setFont(Font.font("Verdana", tamanioFuente));
    lblTamanio.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblTamanio);
    
    ProductoPrecio = new SimpleDoubleProperty(objCargado.getPrecioProducto());
    
    Label lblPrecio = new Label();
    lblPrecio.textProperty().bind(Bindings.concat("Precio: $", ProductoPrecio.asString("%.0f")));
    lblPrecio.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
    lblPrecio.setTextFill(Color.web("#E82E68"));
    miCajaVertical.getChildren().add(lblPrecio);
    
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
            ProductoNombre.set(objCargado.getNombreProducto());
            
            FileInputStream imgArchivo;
            try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoProducto();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                ProductoImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaProductoCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ProductoTipo.set(objCargado.getTipoProducto());
            ProductoTamanio.set(objCargado.getTamanioProducto());
            ProductoPrecio.set(objCargado.getPrecioProducto());
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
    Label lblTitulo = new Label("No hay Productos registrados");
    lblTitulo.setTextFill(Color.web("#E82E68"));
    lblTitulo.setFont(Font.font("verdana", FontWeight.BOLD, 30));
    
    // Mensaje descriptivo
    Label lblMensaje = new Label("Aún no se han creado Productos en el sistema.\nPor favor, crea uno nuevo para comenzar.");
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
        "Carrusel vacío", "No hay Productos registrados en el sistema.");
}
    
}