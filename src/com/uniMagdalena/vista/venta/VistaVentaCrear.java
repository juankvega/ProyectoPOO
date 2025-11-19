package com.uniMagdalena.vista.venta;

import com.uniMagdalena.controlador.cliente.ClienteControladorListar;
import com.uniMagdalena.controlador.pelicula.PeliculaControladorListar;
import com.uniMagdalena.controlador.producto.ProductoControladorListar;
import com.uniMagdalena.controlador.sala.SalaControladorListar;
import com.uniMagdalena.controlador.sede.SedeControladorListar;
import com.uniMagdalena.controlador.venta.VentaControladorGrabar;
import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VistaVentaCrear extends SubScene
{
    private static final int H_GAP = 10;
    private static final int V_GAP = 10; // Reducido para mejor ajuste
    
    private static final int ALTO_FILA = 35;
    private static final int ALTO_CONTROL = 30;
    private static final int ALTO_LISTA = 80; // Altura fija para ListViews
    
    private static final int TAMANYO_FUENTE = 14; // Reducido para mejor ajuste
    
    private final GridPane miGrilla;
    private final Stage laVentanaPrincipal;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    private final ScrollPane scrollPane;
    
    // Controles
    private ListView<ClienteDto> listCliente;
    private ChoiceBox<PeliculaDto> choicePelicula;
    private ListView<SedeDto> listSede;
    private ComboBox<SalaDto> cbmSala;
    private ListView<ProductoDto> listProducto;
    
    private ToggleGroup grupoTipoAsiento;
    private RadioButton rbVIP;
    private RadioButton rbEstandar;
    private RadioButton rbPremium;
    
    private DatePicker dpFechaVenta;
    private TextField cajaValor;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    public VistaVentaCrear(Stage esce, double ancho, double alto)
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.CENTER);
        
        laVentanaPrincipal = esce;
        miGrilla = new GridPane();
        miMarco = Marco.pintar(esce, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
        
        // ScrollPane para contenido que no cabe
        scrollPane = new ScrollPane();
        scrollPane.setContent(miGrilla);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        miFormulario.getChildren().addAll(miMarco, scrollPane);
        
        rutaSeleccionada = "";
        configurarLaGrilla(ancho, alto);
        pintarTitulo();
        configurarResponsive();
        PintarFormulario();
        configurarScrollPane();
    }
    
    public StackPane getMiFormulario()
    {
        return miFormulario;
    }
    
    private void configurarLaGrilla(double anchito, double altito)
    {
        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setPadding(new Insets(10));
        
        // Configurar columnas con porcentajes más equilibrados
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        col1.setHgrow(Priority.ALWAYS);// Etiquetas
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(55); // Controles
        col2.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25); // Imagen
        col3.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col1, col2, col3);
        
        // Configurar filas con alturas más compactas
        for (int j = 0; j < 12; j++) 
        {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setPrefHeight(ALTO_FILA);
            fila.setMaxHeight(ALTO_FILA * 1.5);
            fila.setVgrow(Priority.ALWAYS);
            miGrilla.getRowConstraints().add(fila);
        }
    }
    
    private void configurarResponsive() 
    {
        // Vincular tamaño del ScrollPane al marco
        scrollPane.prefWidthProperty().bind(miMarco.widthProperty().multiply(0.85));
        scrollPane.prefHeightProperty().bind(miMarco.heightProperty().multiply(0.85));
        
        // Centrar el ScrollPane dentro del marco
        StackPane.setAlignment(scrollPane, Pos.CENTER);
        
        // Hacer que la grilla ocupe el ancho disponible del ScrollPane
        miGrilla.prefWidthProperty().bind(scrollPane.widthProperty().multiply(0.95));
    }
    
    private void configurarScrollPane()
    {
        // Asegurar que el ScrollPane esté bien posicionado
        scrollPane.setTranslateY(0);
    }
    
    private void pintarTitulo()
    {
        Text titulo = new Text("Formulario creación de venta");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        titulo.setWrappingWidth(miMarco.getWidth()*0.8);
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(5, 10, 10, 10));
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void PintarFormulario()
    {
        int filaActual = 1;
        
        // Cliente - ListView
        Label lblCliente = new Label("Cliente:");
        lblCliente.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblCliente, 0, filaActual);
        
        List<ClienteDto> arrClientes = ClienteControladorListar.arregloClientes();
        listCliente = new ListView<>();
        listCliente.getItems().addAll(arrClientes);
        listCliente.setPrefHeight(ALTO_LISTA);
        listCliente.setMaxHeight(ALTO_LISTA);
        GridPane.setHgrow(listCliente, Priority.ALWAYS);
        miGrilla.add(listCliente, 1, filaActual);
        filaActual++;
        
        // Película - ChoiceBox
        Label lblPelicula = new Label("Película:");
        lblPelicula.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblPelicula, 0, filaActual);
        
        List<PeliculaDto> arrPeliculas = PeliculaControladorListar.arregloPeliculas();
        choicePelicula = new ChoiceBox<>();
        choicePelicula.getItems().addAll(arrPeliculas);
        choicePelicula.setMaxWidth(Double.MAX_VALUE);
        choicePelicula.setPrefHeight(ALTO_CONTROL);
        GridPane.setHgrow(choicePelicula, Priority.ALWAYS);
        miGrilla.add(choicePelicula, 1, filaActual);
        filaActual++;
        
        // Sede - ListView
        Label lblSede = new Label("Sede:");
        lblSede.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblSede, 0, filaActual);
        
        List<SedeDto> arrSedes = SedeControladorListar.arregloSedes();
        listSede = new ListView<>();
        listSede.getItems().addAll(arrSedes);
        listSede.setPrefHeight(ALTO_LISTA);
        listSede.setMaxHeight(ALTO_LISTA);
        GridPane.setHgrow(listSede, Priority.ALWAYS);
        miGrilla.add(listSede, 1, filaActual);
        filaActual++;
        
        // Sala - ChoiceBox
        Label lblSala = new Label("Sala:");
        lblSala.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblSala, 0, filaActual);
        
        cbmSala = new ComboBox<>();
        cbmSala.setMaxWidth(Double.MAX_VALUE);
        cbmSala.setPrefHeight(ALTO_CONTROL);
        GridPane.setHgrow(cbmSala, Priority.ALWAYS);
        miGrilla.add(cbmSala, 1, filaActual);
        filaActual++;
        
        
        
        // Producto - ListView
        Label lblProducto = new Label("Producto:");
        lblProducto.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblProducto, 0, filaActual);
        
        List<ProductoDto> arrProductos = ProductoControladorListar.arregloProductos();
        listProducto = new ListView<>();
        listProducto.getItems().addAll(arrProductos);
        listProducto.setPrefHeight(ALTO_LISTA);
        listProducto.setMaxHeight(ALTO_LISTA);
        GridPane.setHgrow(listProducto, Priority.ALWAYS);
        miGrilla.add(listProducto, 1, filaActual);
        filaActual++;
        
        // Tipo de asiento - RadioButtons
        Label lblTipoAsiento = new Label("Tipo de Asiento:");
        lblTipoAsiento.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblTipoAsiento, 0, filaActual);
        
        grupoTipoAsiento = new ToggleGroup();
        
        rbVIP = new RadioButton("VIP");
        rbVIP.setToggleGroup(grupoTipoAsiento);
        rbVIP.setFont(Font.font("Verdana", TAMANYO_FUENTE - 2));
        
        rbEstandar = new RadioButton("Estándar");
        rbEstandar.setToggleGroup(grupoTipoAsiento);
        rbEstandar.setFont(Font.font("Verdana", TAMANYO_FUENTE - 2));
        
        rbPremium = new RadioButton("Premium");
        rbPremium.setToggleGroup(grupoTipoAsiento);
        rbPremium.setFont(Font.font("Verdana", TAMANYO_FUENTE - 2));
        
        VBox contenedorRadios = new VBox(5);
        contenedorRadios.getChildren().addAll(rbVIP, rbEstandar, rbPremium);
        miGrilla.add(contenedorRadios, 1, filaActual);
        filaActual++;
        
        // Fecha de venta
        Label lblFecha = new Label("Fecha de Venta:");
        lblFecha.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblFecha, 0, filaActual);
        
        dpFechaVenta = new DatePicker();
        dpFechaVenta.setValue(LocalDate.now());
        dpFechaVenta.setMaxWidth(Double.MAX_VALUE);
        dpFechaVenta.setPrefHeight(ALTO_CONTROL);
        GridPane.setHgrow(dpFechaVenta, Priority.ALWAYS);
        miGrilla.add(dpFechaVenta, 1, filaActual);
        filaActual++;
        
        // Valor de la venta
        Label lblValor = new Label("Valor Total:");
        lblValor.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblValor, 0, filaActual);
        
        cajaValor = new TextField();
        cajaValor.setPromptText("El valor se calculará automáticamente");
        cajaValor.setDisable(true);
        cajaValor.setStyle("-fx-opacity: 1.0; -fx-text-fill: black;");
        cajaValor.setPrefHeight(ALTO_CONTROL);
        GridPane.setHgrow(cajaValor, Priority.ALWAYS);
        miGrilla.add(cajaValor, 1, filaActual);
        filaActual++;
        
        // Imagen
        Label lblImagen = new Label("Imagen:");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen, 0, filaActual);
        
        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CONTROL);
        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Busca una imagen", "La imagen", extensionesPermitidas);
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(ALTO_CONTROL);
        btnEscogerImagen.setPrefWidth(40);
        btnEscogerImagen.setOnAction((e) -> {
            rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            
            if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 200); // Tamaño reducido
                GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
                GridPane.setValignment(imgPrevisualizar, VPos.CENTER);
                GridPane.setRowSpan(imgPrevisualizar, 8);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 8);
            } else {
                miGrilla.getChildren().remove(imgPrevisualizar);
                if (!miGrilla.getChildren().contains(imgPorDefecto)) {
                    GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
                    GridPane.setValignment(imgPorDefecto, VPos.CENTER);
                    GridPane.setRowSpan(imgPorDefecto, 8);
                    miGrilla.add(imgPorDefecto, 2, 1, 1, 8);
                }
            }
        });
        
        HBox panelCajaBoton = new HBox(2);
        panelCajaBoton.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        panelCajaBoton.getChildren().addAll(cajaImagen, btnEscogerImagen);
        miGrilla.add(panelCajaBoton, 1, filaActual);
        filaActual++;
        
        // Imagen por defecto (más pequeña)
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 200);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        GridPane.setRowSpan(imgPorDefecto, 8);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 8);
        
        // Botón grabar
        Button btnGrabar = new Button("¡Registrar Venta!");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setPrefHeight(ALTO_CONTROL + 5);
        btnGrabar.setOnAction((e) -> {
            grabarVenta();
        });
        GridPane.setMargin(btnGrabar, new Insets(10, 0, 0, 0));
        miGrilla.add(btnGrabar, 1, filaActual, 2, 1);
        
        agregarListenerParaFiltrarSalas();
        agregarListenersParaCalculo();
        
        miFormulario.getChildren().add(miGrilla);
    }
    
    
    private void limpiarFormulario()
    {
        listCliente.getSelectionModel().clearSelection();
        choicePelicula.getSelectionModel().clearSelection();
        listSede.getSelectionModel().clearSelection();
        cbmSala.getSelectionModel().clearSelection();
        cbmSala.getItems().clear();
        listProducto.getSelectionModel().clearSelection();
        
        if (grupoTipoAsiento.getSelectedToggle() != null) {
            grupoTipoAsiento.getSelectedToggle().setSelected(false);
        }
        
        dpFechaVenta.setValue(LocalDate.now());
        cajaValor.setText("");
        
        rutaSeleccionada = "";
        cajaImagen.setText("");
        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 8);
        
        listCliente.requestFocus();
    }
    
    private Boolean formularioValido()
    {
        if (listCliente.getSelectionModel().getSelectedItem() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar un cliente");
            listCliente.requestFocus();
            return false;
        }
        
        if (choicePelicula.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar una película");
            choicePelicula.requestFocus();
            return false;
        }
        
        if (listSede.getSelectionModel().getSelectedItem() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar una sede");
            listSede.requestFocus();
            return false;
        }
        
        if (cbmSala.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar una sala");
            cbmSala.requestFocus();
            return false;
        }
        
        if (listProducto.getSelectionModel().getSelectedItem() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar un producto");
            listProducto.requestFocus();
            return false;
        }
        
        if (grupoTipoAsiento.getSelectedToggle() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar el tipo de asiento");
            return false;
        }
        
        if (dpFechaVenta.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Advertencia", "Debe seleccionar la fecha de venta");
            dpFechaVenta.requestFocus();
            return false;
        }
        
        double valorCalculado = 0.0;
    try {
        valorCalculado = Double.parseDouble(cajaValor.getText().replace(",", "."));
    } catch (NumberFormatException e) {
        Mensaje.mostrar(Alert.AlertType.WARNING, null, "Error", "Error en el cálculo del valor");
        return false;
    }
    
    if (valorCalculado <= 0) {
        Mensaje.mostrar(Alert.AlertType.WARNING, null, "ERROR", "Debe seleccionar al menos un producto y tipo de asiento");
        return false;
    }
        
        if (rutaSeleccionada.isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "PAILA", "Debe agregar una imagen");
            return false;
        }
        
        return true;
    }
    
    private String obtenerTipoAsiento() {
    if (grupoTipoAsiento.getSelectedToggle() == null) {
        return null;
    }
    
    RadioButton seleccionado = (RadioButton) grupoTipoAsiento.getSelectedToggle();
    return seleccionado.getText();
}
    
    private void agregarListenerParaFiltrarSalas() {
    // Listener para cuando se selecciona una sede
    listSede.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldVal, nuevaSede) -> {
            if (nuevaSede != null) {
                filtrarSalasPorSede(nuevaSede.getIdSede());
            } else {
                // Si no hay sede seleccionada, limpiar las salas
                cbmSala.getItems().clear();
            }
        }
    );
}
    
    private void filtrarSalasPorSede(int idSede) {
    // Obtener salas filtradas por sede
    List<SalaDto> salasFiltradas = SalaControladorListar.arregloSalasPorSede(idSede);
    
    // Actualizar el ChoiceBox
    cbmSala.getItems().clear();
    cbmSala.getItems().addAll(salasFiltradas);
    
    // Mensaje informativo si no hay salas
    if (salasFiltradas.isEmpty()) {
        Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "Información", 
            "La sede seleccionada no tiene salas disponibles");
    }
}
    
    private void agregarListenersParaCalculo() {
    // Listener para producto
    listProducto.getSelectionModel().selectedItemProperty().addListener(
        (obs, oldVal, newVal) -> calcularValorTotal()
    );
    
    // Listener para tipo de asiento
    grupoTipoAsiento.selectedToggleProperty().addListener(
        (obs, oldVal, newVal) -> calcularValorTotal()
    );
}

private void calcularValorTotal() {
    double total = 0.0;
    
    // Calcular precio del producto
    ProductoDto productoSeleccionado = listProducto.getSelectionModel().getSelectedItem();
    if (productoSeleccionado != null) {
        // Asumiendo que ProductoDto tiene getPrecioProducto()
        total += productoSeleccionado.getPrecioProducto();
    }
    
    // Calcular precio del asiento
    String tipoAsiento = obtenerTipoAsiento();
    if (tipoAsiento != null) {
        switch (tipoAsiento) {
            case "Estándar":
                total += 10000; // Precio asiento estándar
                break;
            case "Premium":
                total += 15000; // Precio asiento premium
                break;
            case "VIP":
                total += 20000; // Precio asiento VIP
                break;
        }
    }
    
    // Actualizar el campo de valor
    cajaValor.setText(String.format("%.2f", total));
}
    
    
    
    private void grabarVenta()
    {
        if (formularioValido())
        {
            Double valorVenta = Double.parseDouble(cajaValor.getText().replace(",", "."));
            
            VentaDto dto = new VentaDto();
            dto.setClienteVenta(listCliente.getSelectionModel().getSelectedItem());
            dto.setPeliculaVenta(choicePelicula.getValue());
            dto.setSedeVenta(listSede.getSelectionModel().getSelectedItem());
            dto.setSalaVenta(cbmSala.getValue());
            dto.setProductoVenta(listProducto.getSelectionModel().getSelectedItem());
            dto.setTipoAsientoVenta(obtenerTipoAsiento());
            dto.setFechaVenta(dpFechaVenta.getValue());
            dto.setValorVenta(valorVenta);
            dto.setNombreImagenPublicoVenta(cajaImagen.getText());
            
            if (VentaControladorGrabar.crearVenta(dto, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "ÉXITO", "¡Venta registrada exitosamente!");
            }
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, "ERROR", "No se pudo registrar la venta");
            }
            
            limpiarFormulario();
        }
    }
}


