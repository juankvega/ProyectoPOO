package com.uniMagdalena.vista.sede;

import com.uniMagdalena.controlador.sede.SedeControladorGrabar;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import com.uniMagdalena.recurso.utilidad.SlideSwitch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

public class VistaSedeCrear extends SubScene {
    
    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    
    private static final int ALTO_FILA = 40;
    private static final int AlTO_CAJA = 35;
    
    private static final int TAMANYO_FUENTE = 18;
    private static final double AJUSTE_ARRIBA = 0.2;
    
    private final GridPane miGrilla;
    private final Stage laVentanaPrincipal;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    
    private TextArea  cajaNombre;
    private ComboBox<String> cbmCiudad;
    private TextField cajaUbicacion;

    private SlideSwitch slideSwitch24horas;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;

    public VistaSedeCrear(Stage esce, double ancho, double alto) {
        
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal = esce;
        miGrilla = new GridPane();
        miMarco = Marco.pintar(esce, Configuracion.MARCO_ALTO_PORCENTAJE, Configuracion.MARCO_ANCHO_PORCENTAJE, Configuracion.DEGRADEE_ARREGLO, Configuracion.COLOR_BORDE);
        miFormulario.getChildren().add(miMarco);
        
        rutaSeleccionada = "";
        
        configurarLaGrilla(ancho, alto);
        pintarTitulo();
        todoResponsive20Puntos();
        pintarFormulario();
        reUbicarFormulario();
    }
    
    public StackPane getMiFormulario(){
        return miFormulario;
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
    
    private void todoResponsive20Puntos() 
    {
        miGrilla.setAlignment(Pos.TOP_CENTER);
        miGrilla.prefWidthProperty().bind(miMarco.widthProperty());
        miGrilla.prefHeightProperty().bind(miMarco.heightProperty());
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
    
    private void pintarTitulo()
    {
        Text titulo = new Text("Formulario creación de sede");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    
    private void pintarFormulario()
    {
        // Nombre de la sede
        Label lblNombre = new Label("Nombre de la sede: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre,0,1);
        
        cajaNombre = new TextArea();
        cajaNombre.setPromptText("Coloca el nombre mi vale: ");
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(AlTO_CAJA);
        miGrilla.add(cajaNombre, 1, 1);
        
        
        // El combo de la ciudad
        Label lblCiudad = new Label("Ciudad de la sede");
        lblCiudad.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblCiudad,0,2);
        
        cbmCiudad =  new ComboBox<>();
        cbmCiudad.setMaxWidth(Double.MAX_VALUE);
        cbmCiudad.setPrefHeight(AlTO_CAJA);
        cbmCiudad.getItems().addAll("Seleccione una ciudad",
                 "Barranquilla", "Santa Marta", "Valledupar");
        cbmCiudad.getSelectionModel().select(0);
        miGrilla.add(cbmCiudad, 1, 2);
        
        
        Label lblUbicacion = new Label("Ubicacion de la sede: ");
        lblUbicacion.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblUbicacion, 0, 3);
        
        cajaUbicacion = new TextField();
        cajaUbicacion.setPromptText("Coloca la ubicacion: ");
        GridPane.setHgrow(cajaUbicacion, Priority.ALWAYS);
        cajaUbicacion.setPrefHeight(AlTO_CAJA);
        miGrilla.add(cajaUbicacion, 1, 3);
        
        
        //**************************
        // Imagen de la sede
        Label lblImagen = new Label("Imagen de la sede");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,4);
        
        
        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(AlTO_CAJA);
        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Busca una imagen","La imagen", extensionesPermitidas);
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(AlTO_CAJA);
        btnEscogerImagen.setOnAction((e)-> 
        {rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            if (rutaSeleccionada.isEmpty()) 
            {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                
                miGrilla.add(imgPorDefecto, 2, 1, 1, 4);   //antes el ultimo era 3
            } 
            else 
            {
                miGrilla.getChildren().remove(imgPorDefecto);
                miGrilla.getChildren().remove(imgPrevisualizar);
                imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 150);
                GridPane.setHalignment(this, HPos.CENTER);
                miGrilla.add(imgPrevisualizar, 2, 1, 1, 4);
            }
        });
        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelCajaBoton = new HBox(2);
        panelCajaBoton.setAlignment(Pos.BOTTOM_RIGHT);
        panelCajaBoton.getChildren().addAll(cajaImagen, btnEscogerImagen);
        miGrilla.add(panelCajaBoton, 1, 4);
        
        imgPorDefecto = Icono.obtenerIcono("imgNoDisponible.png", 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 4);
        //*****************************************************************
        
        
        Label lbl24horas = new Label("Es 24 Horas?");
        lbl24horas.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lbl24horas, 0, 5);
        
        slideSwitch24horas = new SlideSwitch();
        slideSwitch24horas.setMaxWidth(Double.MAX_VALUE);
        slideSwitch24horas.setPrefHeight(AlTO_CAJA);
        miGrilla.add(slideSwitch24horas, 1, 5);
        
        
        //**************************
        // El botón masimo que grabe en alguna parte
        Button btnGrabar = new Button("Clic para grabar la sede!");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e)->{grabarSede();});
        miGrilla.add(btnGrabar, 1, 6);
        
        miFormulario.getChildren().add(miGrilla);   
    }

    // Aqui hay que definir como se van a obtener los estados
    //de las ciudades, ya que al ser varias
    //no pueden ser un Boolean

    private String obtenerCiudadCombo() {
        String seleccion = cbmCiudad.getValue();

            return switch (seleccion) {
                case "Barranquilla" -> "Barranquilla";
                case "Santa Marta" -> "Santa Marta";
                case "Valledupar" -> "Valledupar";
                default -> null;
            };
    }
    
    
    
        private Boolean obtenerResultadoSwitch()
    {
        return slideSwitch24horas.isOn();
    }
        
        
    private void limpiarFormulario()
    {
        cajaNombre.setText("");
        cajaUbicacion.setText("");
        cbmCiudad.getSelectionModel().select(0);
        slideSwitch24horas.reset();
        cajaNombre.requestFocus();
        cajaUbicacion.requestFocus();
        
        rutaSeleccionada = "";
        cajaImagen.setText("");
        miGrilla.getChildren().remove(imgPrevisualizar);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 4);
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
        if(cbmCiudad.getSelectionModel().getSelectedIndex() == 0)
        {
             Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Agarra una de las ciudades del combo");
             cbmCiudad.requestFocus();
             return false;
        }
        
        if(rutaSeleccionada.isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "PAILA", "Colocale una imagen");
            return false;
        }
        
        
        return true;
    }
        
        
    private void grabarSede()
    {
        if(formularioValido())
        {
            SedeDto dto = new SedeDto();
            dto.setNombreSede(cajaNombre.getText());
            dto.setCiudadSede(obtenerCiudadCombo());
            dto.setUbicacionSede(cajaUbicacion.getText());
            
            dto.setEs24horasSede(obtenerResultadoSwitch());
            dto.setNombreImagenPublicoSede(cajaImagen.getText());
            
            if(SedeControladorGrabar.crearSede(dto, rutaSeleccionada))
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
