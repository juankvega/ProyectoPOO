
package com.uniMagdalena.vista.cliente;

import com.uniMagdalena.controlador.cliente.ClienteControladorEditar;
import com.uniMagdalena.controlador.cliente.ClienteControladorGrabar;
import com.uniMagdalena.controlador.cliente.ClienteControladorVentana;
import com.uniMagdalena.dto.ClienteDto;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VistaClienteEditar extends SubScene
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
    
    private ToggleGroup grupoTipoCliente;
    
    private RadioButton rbEstandar;
    private RadioButton rbSilver;
    private RadioButton rbGold;
    private RadioButton rbPlatinum;
    
    private TextField cajaNombre;
    private ComboBox<String> cbmGenero;
    private PasswordField cajaNumDoc;
    private ChoiceBox<String> choiceTipoDocumento;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    private final int posicion;
    private final ClienteDto objCliente;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    public VistaClienteEditar(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto, ClienteDto objClienteExterno, int posicionArchivo )
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal= ventanaPadre;
        
        posicion = posicionArchivo;
        objCliente = objClienteExterno;
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
        Text titulo = new Text("Formulario creación de cliente");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void PintarFormulario()
    {
        Label lblNombre = new Label("Nombre del cliente: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre,0,1);
        
        cajaNombre = new TextField();
        cajaNombre.setText(objCliente.getNombreCliente());
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaNombre, 1, 1);
        
        Label lblGenero = new Label("Género: ");
        lblGenero.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblGenero,0,2);
        
        cbmGenero =  new ComboBox<>();
        cbmGenero.setMaxWidth(Double.MAX_VALUE);
        cbmGenero.setPrefHeight(ALTO_CAJA);
        cbmGenero.getItems().addAll("Seleccione el Género", "Masculino", "Femenino");
        if(objCliente.getGeneroCliente())
        {
            cbmGenero.getSelectionModel().select(1);
        }
        else{
            cbmGenero.getSelectionModel().select(2);
        }
        miGrilla.add(cbmGenero, 1, 2);
        
        Label lblImagen = new Label("Foto del cliente: ");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,3);
        
        cajaImagen = new TextField();
        cajaImagen.setText(objCliente.getNombreImagenPublicoCliente());
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
        
        // Solo agregar imgPorDefecto si no está ya en la grilla
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
        
        
        
        imgPorDefecto = Icono.obtenerIconoExterno(objCliente.getNombreImagenPrivadoCliente(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        
        Label lblDocumento = new Label("Tipo Documento: ");
        lblDocumento.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblDocumento,0,4);
        
        choiceTipoDocumento = new ChoiceBox<>();
        choiceTipoDocumento.getItems().addAll("CC", "PEP", "CE", "PA");
        choiceTipoDocumento.setValue(objCliente.getTipoDocumentoCliente());
        miGrilla.add(choiceTipoDocumento, 1, 4);
        
        Label lblnum = new Label("Ingrese el número de documento: ");
        lblnum.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblnum, 0, 5);
        
        cajaNumDoc = new PasswordField();
        String numeroDocumento = String.valueOf(objCliente.getNumeroDocumentoCliente());
        cajaNumDoc.setText(numeroDocumento);
        GridPane.setHgrow(cajaNumDoc, Priority.ALWAYS);
        cajaNumDoc.setPrefHeight(ALTO_CAJA);
        
                // 1. PREVENIR: Solo permitir números
cajaNumDoc.setTextFormatter(new TextFormatter<>(change -> {
    String newText = change.getControlNewText();
    if (newText.matches("\\d{0,10}")) {
        return change;
    }
    return null;
}));

// 2. FEEDBACK VISUAL: Cambiar borde según la longitud
cajaNumDoc.textProperty().addListener((obs, oldVal, newVal) -> {
    if (newVal.isEmpty()) {
        cajaNumDoc.setStyle("");
    } else if (newVal.length() < 9) {
        cajaNumDoc.setStyle("-fx-border-color: orange; -fx-border-width: 1px;");
    } else {
        cajaNumDoc.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
    }
});
        miGrilla.add(cajaNumDoc, 1, 5);
        
         Label lblTipoCliente = new Label("Tipo de Cliente:");
        lblTipoCliente.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblTipoCliente, 0, 6);
        
        grupoTipoCliente = new ToggleGroup();
        
        rbEstandar = new RadioButton("Estándar");
        rbEstandar.setToggleGroup(grupoTipoCliente);
        rbSilver = new RadioButton("Silver");
        rbSilver.setToggleGroup(grupoTipoCliente);
        rbGold = new RadioButton("Gold");
        rbGold.setToggleGroup(grupoTipoCliente);
        rbPlatinum = new RadioButton("Platinum");
        rbPlatinum.setToggleGroup(grupoTipoCliente);
        
        seleccionTipoCliente();
        
        HBox contenedorRadios = new HBox(16);
        contenedorRadios.getChildren().addAll(rbEstandar, rbSilver, rbGold, rbPlatinum);
        miGrilla.add(contenedorRadios, 1, 6);
        
        Button btnGrabar = new Button("Actualizar el cliente");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e)->{grabarCliente();});
        miGrilla.add(btnGrabar, 1, 7);
        
         Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = ClienteControladorVentana.administrar(
                    laVentanaPrincipal, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        
        miGrilla.add(btnRegresar, 0, 7);
        
       miFormulario.getChildren().add(miGrilla); 
    }
    
    
    
    private Boolean formularioValido()
    {
         if(cajaNombre.getText().isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Lease 100 años de seriedad.\n"
                    + "Debes escribir algo en la caja.");
           cajaNombre.requestFocus();
           return false;
        }
         
        if(cbmGenero.getSelectionModel().getSelectedIndex() == 0)
        {
             Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Agarra una de las Ver*** del combo");
             cbmGenero.requestFocus();
             return false;
        }
        
        try
        {
           String numero = cajaNumDoc.getText();
           int numero2 = Integer.parseInt(numero);
           if(numero2 <= 0 || numero.length() < 9)
           {
               Mensaje.mostrar(Alert.AlertType.WARNING, null, "ERROR", "Un número de identificación debe ser un número positivo y además debe tener al menos 9 digitos" );
                cajaNumDoc.requestFocus();
                return false;
           }
        }catch(NumberFormatException e)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Error","La identificación debe y solamente es un número");
            cajaNumDoc.requestFocus();
        return false;
        }
        
        if (choiceTipoDocumento.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, 
             "Advertencia", "Debe seleccionar un tipo de documento");
            choiceTipoDocumento.requestFocus();
            return false;
        }
        
        if (grupoTipoCliente.getSelectedToggle() == null) 
        {
        Mensaje.mostrar(Alert.AlertType.WARNING, null, 
            "Advertencia", "Debe seleccionar un tipo de documento");
        return false;
        }         
         
         
                 
         
         return true;
    }
    
    
    private String obtenerTipoCliente()
    {
        RadioButton seleccionado = (RadioButton) grupoTipoCliente.getSelectedToggle();
        
        if(seleccionado == null)
        {
            return null;
        }
        if(seleccionado == rbEstandar)
        {
            return "Estándar";
        }else if(seleccionado == rbSilver){
            return "Silver";
        }else if(seleccionado == rbGold){
            return "Gold";
        }else if (seleccionado == rbPlatinum){
            return "Platinum";
        }
        
        return null;
        
    }
    
    private Boolean obtenerEstadoCombo()
    {
        String seleccion = cbmGenero.getValue();
        if ("Masculino".equals(seleccion))
        {
            return true;
        }
        if("Femenino".equals(seleccion))
        {
            return false;
        }
        
        return null;
    }
    
    private void seleccionTipoCliente()
    {
        String tipoCliente = objCliente.getTipoCliente();
        
        switch(tipoCliente)
        {
            case "Estándar":
                rbEstandar.setSelected(true);
                break;
            case "Silver":
                rbSilver.setSelected(true);
                break;
            case "Gold":
                rbGold.setSelected(true);
                break;
            case "Platinum":
                rbPlatinum.setSelected(true);
                break;
        }
    }
    
    private void grabarCliente()
    {
        if(formularioValido())
        {
            Integer numeroDocumento = Integer.parseInt(cajaNumDoc.getText());
            int codCliente = objCliente.getIdCliente();
            String nomCliente = cajaNombre.getText();
            Boolean genCliente = obtenerEstadoCombo();
            String tipoDocCliente = choiceTipoDocumento.getValue();
            int numDocCliente = numeroDocumento;
            String tipoCliente = obtenerTipoCliente();
            String imaCliente = cajaImagen.getText();
            String nocu = objCliente.getNombreImagenPrivadoCliente();
            
            ClienteDto nuevoCliente = new ClienteDto(codCliente, nomCliente, genCliente, tipoDocCliente, numDocCliente, tipoCliente, imaCliente, nocu);
            
            if(ClienteControladorEditar.actualizar(posicion, nuevoCliente, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "EXITO", "Listo me fui !!!!!!!!!!!!!!");
            }
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, "ERROR", "No me fui!!!!");
            }
            
            
            
        }
    }
    
}
