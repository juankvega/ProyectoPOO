
package com.uniMagdalena.vista.trabajador;


import com.uniMagdalena.controlador.trabajador.TrabajadorControladorGrabar;
import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
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

public class VistaTrabajadorCrear extends SubScene
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
    
    private ToggleGroup grupoTipoTrabajador;
    
    private RadioButton rbAseo;
    private RadioButton rbCajero;
    private RadioButton rbConfitero;
    private RadioButton rbVigilante;
    
    private TextField cajaNombre;
    private ComboBox<String> cbmGenero;
    private PasswordField cajaNumDoc;
    private ChoiceBox<String> choiceTipoDocumento;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    public VistaTrabajadorCrear(Stage esce, double ancho, double alto)
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal= esce;
        miGrilla = new GridPane();
        miMarco = Marco.pintar(esce, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
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
        Text titulo = new Text("Formulario creación de trabajador");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void PintarFormulario()
    {
        Label lblNombre = new Label("Nombre del trabajador: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre,0,1);
        
        cajaNombre = new TextField();
        cajaNombre.setPromptText("Coloca el nombre mi vale: ");
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaNombre, 1, 1);
        
        Label lblGenero = new Label("Género: ");
        lblGenero.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblGenero,0,2);
        
        cbmGenero =  new ComboBox<>();
        cbmGenero.setMaxWidth(Long.MAX_VALUE);
        cbmGenero.setPrefHeight(ALTO_CAJA);
        cbmGenero.getItems().addAll("Seleccione el Género", "Masculino", "Femenino");
        cbmGenero.getSelectionModel().select(0);
        miGrilla.add(cbmGenero, 1, 2);
        
        Label lblImagen = new Label("Foto del trabajador: ");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,3);
        
        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Busca una imagen","La imagen", extensionesPermitidas);
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(ALTO_CAJA);
        btnEscogerImagen.setOnAction((e)-> 
        {rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaSeleccionada.isEmpty()) 
            {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
            } 
            else 
            {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 150);
                GridPane.setHalignment(this, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 3);
            }
        });
        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelCajaBoton = new HBox(2);
        panelCajaBoton.setAlignment(Pos.BOTTOM_RIGHT);
        panelCajaBoton.getChildren().addAll(cajaImagen, btnEscogerImagen);
        miGrilla.add(panelCajaBoton, 1, 3);
        
        
        
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        
        Label lblDocumento = new Label("Tipo Documento: ");
        lblDocumento.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblDocumento,0,4);
        
        choiceTipoDocumento = new ChoiceBox<>();
        choiceTipoDocumento.getItems().addAll("CC", "PEP", "CE", "PA");
        miGrilla.add(choiceTipoDocumento, 1, 4);
        
        Label lblnum = new Label("Ingrese el número de documento: ");
        lblnum.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblnum, 0, 5);
        
        cajaNumDoc = new PasswordField();
        cajaNumDoc.setPromptText("Coloca el número de documento:  ");
        GridPane.setHgrow(cajaNumDoc, Priority.ALWAYS);
        cajaNumDoc.setPrefHeight(ALTO_CAJA);      
        miGrilla.add(cajaNumDoc, 1, 5);
        
         Label lblTipoTrabajador = new Label("Tipo de Trabajador:");
        lblTipoTrabajador.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblTipoTrabajador, 0, 6);
        
        grupoTipoTrabajador = new ToggleGroup();
        
        rbAseo = new RadioButton("Aseo");
        rbAseo.setToggleGroup(grupoTipoTrabajador);
        rbCajero = new RadioButton("Cajero");
        rbCajero.setToggleGroup(grupoTipoTrabajador);
        rbConfitero = new RadioButton("Confitero");
        rbConfitero.setToggleGroup(grupoTipoTrabajador);
        rbVigilante = new RadioButton("Vigilante");
        rbVigilante.setToggleGroup(grupoTipoTrabajador);
        
        HBox contenedorRadios = new HBox(16);
        contenedorRadios.getChildren().addAll(rbAseo, rbCajero, rbConfitero, rbVigilante);
        miGrilla.add(contenedorRadios, 1, 6);
        
        Button btnGrabar = new Button("Clic para GRABAR YA!!!!");
        btnGrabar.setMaxWidth(Long.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e)->{grabarTrabajador();});
        miGrilla.add(btnGrabar, 1, 7);
        
       miFormulario.getChildren().add(miGrilla); 
    }
    
    private void limpiarFormulario()
    {
        cajaNombre.setText("");
        cajaNumDoc.setText("");
        cbmGenero.getSelectionModel().selectFirst();
        choiceTipoDocumento.getSelectionModel().clearSelection();
        grupoTipoTrabajador.getSelectedToggle().setSelected(false);
        cajaNombre.requestFocus();
        cajaNumDoc.requestFocus();
        
        rutaSeleccionada = "";
        cajaImagen.setText("");
        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        
        
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
            double numero2 = Long.parseLong(cajaNumDoc.getText());
            
            if(numero2 < 0)
            {
                Mensaje.mostrar(Alert.AlertType.WARNING, null, "ERROR", "¿Siquiera eso tiene sentido?, corriga el num documento." );
                cajaNumDoc.requestFocus();
                return false;
            }
            
        }catch(NumberFormatException e)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Error", 
                "¡¡¡¡¡¡¡El documento debe ser un número, un número!!!!!!!");
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
        
        if (grupoTipoTrabajador.getSelectedToggle() == null) 
        {
        Mensaje.mostrar(Alert.AlertType.WARNING, null, 
            "Advertencia", "Debe seleccionar un tipo de documento");
        return false;
        }         
         
         
        if(rutaSeleccionada.isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "PAILA", "Colocale una imagen");
            return false;
        }
                 
         
         return true;
    }
    
    
    private String obtenerTipoTrabajador()
    {
        RadioButton seleccionado = (RadioButton) grupoTipoTrabajador.getSelectedToggle();
        
        if(seleccionado == null)
        {
            return null;
        }
        if(seleccionado == rbAseo)
        {
            return "Aseo";
        }else if(seleccionado == rbConfitero){
            return "Confitero";
        }else if(seleccionado == rbCajero){
            return "Cajero";
        }else if (seleccionado == rbVigilante){
            return "Vigilante";
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
    
    private void grabarTrabajador()
    {
        if(formularioValido())
        {
           Long numeroDocumento = Long.parseLong(cajaNumDoc.getText());
            TrabajadorDto dto = new TrabajadorDto();
            dto.setNombreTrabajador(cajaNombre.getText());
            dto.setGeneroTrabajador(obtenerEstadoCombo());
            dto.setTipoDocumentoTrabajador(choiceTipoDocumento.getValue());
            dto.setNumDocumentoTrabajador(numeroDocumento);
            dto.setTipoTrabajador(obtenerTipoTrabajador());
            dto.setNombreImagenPublicoTrabajador(cajaImagen.getText());
            
            if(TrabajadorControladorGrabar.crearTrabajador(dto, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "EXITO", "Listo me fui !!!!!!!!!!!!!!");
            }
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, "ERROR", "No me fui!!!!");
            }
            
            
            limpiarFormulario();
        }
    }
    
}
