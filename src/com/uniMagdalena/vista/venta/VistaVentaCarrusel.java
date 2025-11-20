
package com.uniMagdalena.vista.venta;

import com.uniMagdalena.controlador.venta.VentaControladorEliminar;
import com.uniMagdalena.controlador.venta.VentaControladorListar;
import com.uniMagdalena.controlador.venta.VentaControladorUna;
import com.uniMagdalena.controlador.venta.VentaControladorVentana;
import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
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

public class VistaVentaCarrusel extends SubScene
{
    private static final String ARCHIVO_MEMORIA = "carrusel_Venta_posicion.txt";
    private final BorderPane miBorderPane;
    private final Stage laVentanaPrincipal;
    private final VBox miCajaVertical;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    private int indiceActual;
    private int totalVentas;
    private VentaDto objCargado;
    
    private StringProperty VentaTitulo;
    private ObjectProperty<ClienteDto> VentaCliente;
    private ObjectProperty<Image> VentaImagen;
    private ObjectProperty<PeliculaDto> VentaPelicula;
    private ObjectProperty<SedeDto> VentaSede;
    private ObjectProperty<SalaDto> VentaSala;
    private StringProperty VentaTipoAsiento;
    private ObjectProperty<LocalDate> VentaFecha;
    private DoubleProperty VentaValor;
    
    public VistaVentaCarrusel(Stage ventanaPadre, BorderPane princ, Pane pane, double anchoPanel, double altoPanel, int indice)
    {
        super(new BorderPane(), anchoPanel, altoPanel);
        
        indiceActual = cargarIndiceGuardado();
        
        totalVentas = VentaControladorListar.cantidadVentas();
        if (indiceActual < 0 || indiceActual >= totalVentas) {
            indiceActual = indice;
        }
        objCargado = VentaControladorUna.obtenerVenta(indiceActual);
        
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
            Logger.getLogger(VistaVentaCarrusel.class.getName())
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
            Logger.getLogger(VistaVentaCarrusel.class.getName())
                .log(Level.WARNING, "No se pudo cargar la posición del carrusel", ex);
        }
        return -1;
    }
    
    private void crearTitulo() {
        Region bloqueSeparador = new Region();
        bloqueSeparador.prefHeightProperty().bind(laVentanaPrincipal.heightProperty().multiply(0.10));
        miCajaVertical.getChildren().add(0, bloqueSeparador);

        totalVentas = VentaControladorListar.cantidadVentas();
        VentaTitulo = new SimpleStringProperty("Detalle de la categoría (" + (indiceActual + 1) + " / " + totalVentas + ")");

        Label lblTitulo = new Label();
        lblTitulo.textProperty().bind(VentaTitulo);
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
            indiceActual = obtenerIndice("Anterior", indiceActual, totalVentas);
            objCargado = VentaControladorUna.obtenerVenta(indiceActual);
            
            guardarIndiceActual();
            
            VentaTitulo.set("Detalle de Venta (" + (indiceActual + 1) + "/" + totalVentas + ")");
            VentaCliente.set(objCargado.getClienteVenta());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoVenta();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                VentaImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaVentaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        VentaPelicula.set(objCargado.getPeliculaVenta());
        VentaSede.set(objCargado.getSedeVenta());
        VentaSala.set(objCargado.getSalaVenta());
        VentaTipoAsiento.set(objCargado.getTipoAsientoVenta()); 
        VentaFecha.set(objCargado.getFechaVenta());
        VentaValor.set(objCargado.getValorVenta());
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
            indiceActual = obtenerIndice("Siguiente", indiceActual, totalVentas);
            objCargado = VentaControladorUna.obtenerVenta(indiceActual);
            
            guardarIndiceActual();
            
            VentaTitulo.set("Detalle de Venta (" + (indiceActual + 1) + "/" + totalVentas + ")");
            VentaCliente.set(objCargado.getClienteVenta());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoVenta();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                VentaImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaVentaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        VentaPelicula.set(objCargado.getPeliculaVenta());
        VentaSede.set(objCargado.getSedeVenta());
        VentaSala.set(objCargado.getSalaVenta());
        VentaTipoAsiento.set(objCargado.getTipoAsientoVenta()); 
        VentaFecha.set(objCargado.getFechaVenta());
        VentaValor.set(objCargado.getValorVenta());
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
            String msg1, msg2, msg3, msg4;
            
            msg1 = "¿Estás seguro mi vale?";
            msg2 = "\nCódigo: " + objCargado.getIdVenta();
            msg3 = "\nNombre de cliente: " + objCargado.getClienteVenta().getNombreCliente();
            msg4 = "\nSi se fue, se fue!";
            
            Alert mensajito = new Alert(Alert.AlertType.CONFIRMATION);
            mensajito.setTitle("Te lo advierto");
            mensajito.setHeaderText(null);
            mensajito.setContentText(msg1 + msg2 + msg3 + msg4);
            mensajito.initOwner(laVentanaPrincipal);
            
            if (mensajito.showAndWait().get() == ButtonType.OK) {
                if (VentaControladorEliminar.borrar(indiceActual)) {
                    totalVentas = VentaControladorListar.cantidadVentas();
                    
                    // Ajustar el índice después de eliminar
                    if (indiceActual >= totalVentas && totalVentas > 0) {
                        indiceActual = totalVentas - 1;
                    } else if (totalVentas == 0) {
                        indiceActual = 0;
                    }
                    
                    guardarIndiceActual();
                    
                    // Actualizar el título
                    VentaTitulo.set("Detalle de la película (" + 
                        (indiceActual + 1) + " / " + totalVentas + ")");
                    
                    // Cargar la nueva película si hay disponibles
                    if (totalVentas > 0) {
                        objCargado = VentaControladorUna.obtenerVenta(indiceActual);
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
            panelCuerpo = VentaControladorVentana.editar(
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
    
    // Cliente de la venta
    VentaCliente = new SimpleObjectProperty<>(objCargado.getClienteVenta());
    
    int tamanioFuente = 18;
    Label lblCliente = new Label();
    lblCliente.textProperty().bind(Bindings.createStringBinding(
        () -> "Cliente: " + (VentaCliente.get() != null ? VentaCliente.get().getNombreCliente() : "N/A"),
        VentaCliente
    ));
    lblCliente.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
    lblCliente.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblCliente);
    
    // Imagen de la venta
    VentaImagen = new SimpleObjectProperty<>();
    
    FileInputStream imgArchivo;
    
    try {
        String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS 
            + Persistencia.SEPARADOR_CARPETAS 
            + objCargado.getNombreImagenPrivadoVenta();
        imgArchivo = new FileInputStream(rutaNuevaImagen);
        Image imgNueva = new Image(imgArchivo);
        VentaImagen.set(imgNueva);
        
        ImageView imgMostrar = new ImageView(imgNueva);
        imgMostrar.setFitHeight(250);
        imgMostrar.setSmooth(true);
        imgMostrar.setPreserveRatio(true);
        
        imgMostrar.imageProperty().bind(VentaImagen);
        miCajaVertical.getChildren().add(imgMostrar);
        
    } catch (FileNotFoundException ex) {
        Logger.getLogger(VistaVentaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Película de la venta
    VentaPelicula = new SimpleObjectProperty<>(objCargado.getPeliculaVenta());
    
    Label lblPelicula = new Label();
    lblPelicula.textProperty().bind(Bindings.createStringBinding(
        () -> "Película: " + (VentaPelicula.get() != null ? VentaPelicula.get().getNombrePelicula() : "N/A"),
        VentaPelicula
    ));
    lblPelicula.setFont(Font.font("Verdana", tamanioFuente));
    lblPelicula.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblPelicula);
    
    // Sede de la venta
    VentaSede = new SimpleObjectProperty<>(objCargado.getSedeVenta());
    
    Label lblSede = new Label();
    lblSede.textProperty().bind(Bindings.createStringBinding(
        () -> "Sede: " + (VentaSede.get() != null ? VentaSede.get().getNombreSede() : "N/A"),
        VentaSede
    ));
    lblSede.setFont(Font.font("Verdana", tamanioFuente));
    lblSede.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblSede);
    
    // Sala de la venta
    VentaSala = new SimpleObjectProperty<>(objCargado.getSalaVenta());
    
    Label lblSala = new Label();
    lblSala.textProperty().bind(Bindings.createStringBinding(
        () -> "Sala: " + (VentaSala.get() != null ? VentaSala.get().getNombreSala() : "N/A"),
        VentaSala
    ));
    lblSala.setFont(Font.font("Verdana", tamanioFuente));
    lblSala.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblSala);
    
    // Tipo de asiento
    VentaTipoAsiento = new SimpleStringProperty(objCargado.getTipoAsientoVenta());
    
    Label lblTipoAsiento = new Label();
    lblTipoAsiento.textProperty().bind(Bindings.concat("Tipo de Asiento: ", VentaTipoAsiento));
    lblTipoAsiento.setFont(Font.font("Verdana", tamanioFuente));
    lblTipoAsiento.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblTipoAsiento);
    
    // Fecha de la venta
    VentaFecha = new SimpleObjectProperty<>(objCargado.getFechaVenta());
    
    Label lblFecha = new Label();
    lblFecha.textProperty().bind(Bindings.createStringBinding(
        () -> "Fecha: " + (VentaFecha.get() != null ? VentaFecha.get().toString() : "N/A"),
        VentaFecha
    ));
    lblFecha.setFont(Font.font("Verdana", tamanioFuente));
    lblFecha.setTextFill(Color.web("#6C3483"));
    miCajaVertical.getChildren().add(lblFecha);
    
    // Valor de la venta
    VentaValor = new SimpleDoubleProperty(objCargado.getValorVenta());
    
    Label lblValor = new Label();
    lblValor.textProperty().bind(Bindings.createStringBinding(
        () -> String.format("Valor: $%.2f", VentaValor.get()),
        VentaValor
    ));
    lblValor.setFont(Font.font("Verdana", FontWeight.BOLD, tamanioFuente));
    lblValor.setTextFill(Color.web("#E82E68"));
    miCajaVertical.getChildren().add(lblValor);
    
    miBorderPane.setCenter(centerPane);
}
    
    
    
    private void actualizarDatosCarrusel()
    {
        if(objCargado != null)
        {
             VentaCliente.set(objCargado.getClienteVenta());
            
            FileInputStream imgArchivo;
        try {
                String rutaNuevaImagen = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + objCargado.getNombreImagenPrivadoVenta();
                imgArchivo = new FileInputStream(rutaNuevaImagen);
                Image imgNueva = new Image(imgArchivo);
                VentaImagen.set(imgNueva);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VistaVentaCarrusel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        VentaPelicula.set(objCargado.getPeliculaVenta());
        VentaSede.set(objCargado.getSedeVenta());
        VentaSala.set(objCargado.getSalaVenta());
        VentaTipoAsiento.set(objCargado.getTipoAsientoVenta()); 
        VentaFecha.set(objCargado.getFechaVenta());
        VentaValor.set(objCargado.getValorVenta());
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
