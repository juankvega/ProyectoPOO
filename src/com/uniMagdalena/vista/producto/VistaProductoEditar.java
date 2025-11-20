package com.uniMagdalena.vista.producto;

import com.uniMagdalena.controlador.Producto.ProductoControladorVentana;
import com.uniMagdalena.controlador.producto.ProductoControladorEditar;
import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Contenedor;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VistaProductoEditar extends SubScene
{
    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    
    private static final int ALTO_FILA = 40;
    private static final int ALTO_CAJA = 35;
    
    private static final int TAMANYO_FUENTE = 18;
    private static final double AJUSTE_ARRIBA = 0.2;
    
    private final GridPane miGrilla;
    private final Stage laVentanaPrincipal;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    
    private TextField cajaNombre;
    private ComboBox<String> cbmTipoProducto;
    private ChoiceBox<String> choiceTamanioProducto;
    private PasswordField cajaPrecio;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    private final int posicion;
    private final ProductoDto objProducto;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    public VistaProductoEditar(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto, ProductoDto objProductoExterno, int posicionArchivo )
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal= ventanaPadre;
        
        posicion = posicionArchivo;
        objProducto = objProductoExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;
        
        miGrilla = new GridPane();
        
        miMarco = Marco.pintar(ventanaPadre, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
        miFormulario.getChildren().add(miMarco);
        
        rutaSeleccionada = "";
        
        configurarLaGrilla(ancho, alto);
        pintarTitulo();
        todoResponsive20Puntos();
        PintarFormulario();
        reUbicarFormulario();
    }
    
    public StackPane getMiFormulario()
    {
        return miFormulario;
    }
    
    private void configurarLaGrilla(double anchito, double altito)
    {
        double porcentaje_ancho = 0.7;
        double anchoMarco = anchito*porcentaje_ancho;
        
        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setPrefSize(anchoMarco, altito);
        miGrilla.setMinSize(anchoMarco, altito);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        
         miGrilla.prefWidthProperty().bind(laVentanaPrincipal.widthProperty().multiply(0.7));
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        col2.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        col3.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col1, col2);
        
        int j;
        for (j = 0; j < 5; j++) 
        {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setPrefHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }
    
    private void todoResponsive20Puntos() 
    {
        miGrilla.setAlignment(Pos.TOP_CENTER);
        miGrilla.prefWidthProperty().bind(miMarco.widthProperty());
        miGrilla.prefHeightProperty().bind(miMarco.heightProperty());
    }
    private void reUbicarFormulario() 
    {   
        Runnable organizar = ()->{double altoDelFormulario = miMarco.getHeight()*AJUSTE_ARRIBA;
        if (altoDelFormulario > 0)
        {
            miGrilla.setTranslateY(altoDelFormulario/2+altoDelFormulario);
        }};
        organizar.run();
        miMarco.heightProperty().addListener(((obs, antes, despues)->{organizar.run();}));
    }
    
    private void pintarTitulo()
    {
        Text titulo = new Text("Formulario actualización de producto");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void PintarFormulario()
    {
        Label lblNombre = new Label("Nombre del producto: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre,0,1);
        
        cajaNombre = new TextField();
        cajaNombre.setText(objProducto.getNombreProducto());
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaNombre, 1, 1);
        
        Label lblTipo = new Label("Tipo Producto: ");
        lblTipo.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblTipo,0,2);
        
        cbmTipoProducto =  new ComboBox<>();
        cbmTipoProducto.setMaxWidth(Double.MAX_VALUE);
        cbmTipoProducto.setPrefHeight(ALTO_CAJA);
        cbmTipoProducto.getItems().addAll("Seleccione el Tipo de Producto", "Comida", "Bebida");
        
        if(objProducto.getTipoProducto())
        {
            cbmTipoProducto.getSelectionModel().select(1); // Comida
        }
        else{
            cbmTipoProducto.getSelectionModel().select(2); // Bebida
        }
        miGrilla.add(cbmTipoProducto, 1, 2);
        
        Label lblImagen = new Label("Foto del producto: ");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,3);
        
        cajaImagen = new TextField();
        cajaImagen.setText(objProducto.getNombreImagenPublicoProducto()); // IGUAL QUE CLIENTE: setText directo
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Busca una imagen","La imagen", extensionesPermitidas);
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(ALTO_CAJA);
        
        btnEscogerImagen.setOnAction((e)-> 
{   
    rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);    
    
    if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
        // Usuario seleccionó una nueva imagen
        miGrilla.getChildren().remove(imgPorDefecto);
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 150);
        GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
        GridPane.setValignment(imgPrevisualizar, VPos.CENTER);
        
        miGrilla.add(imgPrevisualizar, 2, 1, 1, 3);
    } else {
        // Usuario canceló - mantener la imagen original
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        if (!miGrilla.getChildren().contains(imgPorDefecto)) {
            GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
            GridPane.setValignment(imgPorDefecto, VPos.CENTER);
            miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        }
        
    }
});
        
        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelCajaBoton = new HBox(2);
        panelCajaBoton.setAlignment(Pos.BOTTOM_RIGHT);
        panelCajaBoton.getChildren().addAll(cajaImagen, btnEscogerImagen);
        miGrilla.add(panelCajaBoton, 1, 3);
        
        imgPorDefecto = Icono.obtenerIconoExterno(objProducto.getNombreImagenPrivadoProducto(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        
        Label lblTamanioProducto = new Label("Tamaño Producto: ");
        lblTamanioProducto.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblTamanioProducto, 0, 4);
        
        choiceTamanioProducto = new ChoiceBox<>();
        choiceTamanioProducto.getItems().addAll("S(Small)", "M(Medium)", "L(Large)");
        choiceTamanioProducto.setValue(objProducto.getTamanioProducto()); // IGUAL QUE CLIENTE: setValue directo
        miGrilla.add(choiceTamanioProducto, 1, 4);
        
        Label lblPrecio = new Label("Ingrese el Precio: ");
        lblPrecio.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblPrecio, 0, 5);
        

        cajaPrecio = new PasswordField();
        cajaPrecio.setPromptText("Dale sin pena, clava a la gente con el precio: ");
        GridPane.setHgrow(cajaPrecio, Priority.ALWAYS);
        cajaPrecio.setPrefHeight(ALTO_CAJA);
        
        cajaPrecio.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,10}")) {
                return change;
            }
            return null;
        }));

        cajaPrecio.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                cajaPrecio.setStyle("");
            } else if (newVal.length() < 3) {
                cajaPrecio.setStyle("-fx-border-color: orange; -fx-border-width: 1px;");
            } else {
                cajaPrecio.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
            }
        });
        
        String precioProducto = String.valueOf(objProducto.getPrecioProducto().intValue());
        cajaPrecio.setText(precioProducto);
        
        miGrilla.add(cajaPrecio, 1, 5);
        
        Button btnGrabar = new Button("Actualizar el producto");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e)->{grabarProducto();});
        miGrilla.add(btnGrabar, 1, 6);
        
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = ProductoControladorVentana.administrar(
                    laVentanaPrincipal, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        
        miGrilla.add(btnRegresar, 0, 6);
        
       miFormulario.getChildren().add(miGrilla); 
    }
    
    private Boolean formularioValido()
    {
         if(cajaNombre.getText().isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Debes escribir algo en la caja.");
           cajaNombre.requestFocus();
           return false;
        }
         
        if(cbmTipoProducto.getSelectionModel().getSelectedIndex() == 0)
        {
             Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Debes escoger un tipo de producto.");
             cbmTipoProducto.requestFocus();
             return false;
        }
        
        if(choiceTamanioProducto.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Debes seleccionar un tamaño");
            choiceTamanioProducto.requestFocus();
            return false;
        }
        
        try
        {
           String numero = cajaPrecio.getText();
           double numero2 = Double.parseDouble(numero);
           if(numero2 <= 0)
           {
               Mensaje.mostrar(Alert.AlertType.WARNING, null, "ERROR", "El precio debe ser un número positivo");
                cajaPrecio.requestFocus();
                return false;
           }
        }catch(NumberFormatException e)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Error","El precio debe ser un número");
            cajaPrecio.requestFocus();
        return false;
        }
         
         return true;
    }
    
    private Boolean obtenerEstadoCombo()
    {
        String seleccion = cbmTipoProducto.getValue();
        if ("Comida".equals(seleccion))
        {
            return true;
        }
        if("Bebida".equals(seleccion))
        {
            return false;
        }
        
        return null;
    }
    
    private void grabarProducto()
    {
        if(formularioValido())
        {
            Double numeroCajaPrecio = Double.parseDouble(cajaPrecio.getText());
            int codProducto = objProducto.getIdProducto();
            String nomProducto = cajaNombre.getText();
            Boolean tipProducto = obtenerEstadoCombo();
            String tamProducto= choiceTamanioProducto.getValue();
            Double preProducto = numeroCajaPrecio;
            String imaProducto = cajaImagen.getText();
            String nocu = objProducto.getNombreImagenPrivadoProducto();
            
            ProductoDto nuevoProducto = new ProductoDto(codProducto, nomProducto, tipProducto, tamProducto, preProducto, Short.valueOf("0") ,imaProducto, nocu);
            
            if(ProductoControladorEditar.actualizar(posicion, nuevoProducto, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "EXITO", "Listo me fui !!!!!!!!!!!!!!");
            }
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, "ERROR", "No me fui!!!!");
            }
            
            // IGUAL QUE CLIENTE: NO llama limpiarFormulario() aquí
        }
    }
}
