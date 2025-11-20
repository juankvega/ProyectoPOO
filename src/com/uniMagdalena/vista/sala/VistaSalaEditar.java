
package com.uniMagdalena.vista.sala;

import com.uniMagdalena.controlador.sala.SalaControladorEditar;
import com.uniMagdalena.controlador.sala.SalaControladorGrabar;
import com.uniMagdalena.controlador.sala.SalaControladorVentana;
import com.uniMagdalena.controlador.sede.SedeControladorListar;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Contenedor;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import com.uniMagdalena.recurso.utilidad.SlideSwitch;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextField;
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

public class VistaSalaEditar extends SubScene
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
   private ChoiceBox<String> chbAsientos;
   private SlideSwitch sala4dSwitch;
   private ComboBox<SedeDto> cbmSedes;
   
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    private final int posicion;
    private final SalaDto objSala;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    

    public VistaSalaEditar(Stage ventanaPadre,BorderPane princ, Pane pane ,double ancho, double alto, SalaDto objSalaExterno, int posicionArchivo) 
    {
        super(new StackPane(), ancho, alto);
        
        
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal= ventanaPadre;
        
        posicion = posicionArchivo;
        objSala = objSalaExterno;
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
        Text titulo = new Text("Formulario creación de la sala");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(25, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 2, 1);
    }
    
    private void todoResponsive20Puntos() 
    {
        miGrilla.setAlignment(Pos.TOP_CENTER);
        miGrilla.prefWidthProperty().bind(miMarco.widthProperty());
        miGrilla.prefHeightProperty().bind(miMarco.heightProperty());
    }
    
    private void PintarFormulario()
    {
        Label lblNombre = new Label("Nombre de la sala: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre, 0, 1);
        
        cajaNombre = new TextField();
        cajaNombre.setText(objSala.getNombreSala());
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaNombre, 1, 1);
        
        
        
        Label lblAsientos = new Label("Cantidad de asientos: ");
        lblAsientos.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblAsientos, 0, 2);
        
        chbAsientos = new ChoiceBox<>();
        chbAsientos.getItems().setAll("25", "36", "49", "64", "81", "100");
        chbAsientos.setValue(String.valueOf(objSala.getAsientosSala()));
        miGrilla.add(chbAsientos, 1, 2);
        
        Label lblImagen = new Label("Imagen de la sala: ");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,3);
        
        cajaImagen = new TextField();
        cajaImagen.setText(objSala.getNombreImagenPublicoSala());
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
        
        
        
        imgPorDefecto = Icono.obtenerIconoExterno(objSala.getNombreImagenPrivadoSala(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        
        Label lbl4d = new Label("¿La sala es 4d? ");
        lbl4d.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lbl4d, 0, 4);
        
        sala4dSwitch = new SlideSwitch();
        if(objSala.getSala4d())
        {
            sala4dSwitch.setEstado(true);
        }else{
            sala4dSwitch.setEstado(false);
        }     
        sala4dSwitch.setMaxWidth(Double.MAX_VALUE);
        sala4dSwitch.setPrefHeight(ALTO_CAJA);
        miGrilla.add(sala4dSwitch, 1, 4);
        
        Label lblSede = new Label("Sede: ");
        lblSede.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblSede, 0, 5);
        
        List<SedeDto> arrSedes = SedeControladorListar.arregloSedes();
        SedeDto opcionPorDefecto = new SedeDto(0, "Seleccione una sede", "", "", null, null, null ,"", "");
        arrSedes.add(0, opcionPorDefecto);
        
        cbmSedes = new ComboBox<>();
        cbmSedes.setMaxWidth(Double.MAX_VALUE);
        cbmSedes.setPrefHeight(ALTO_CAJA);
        ObservableList<SedeDto> items = FXCollections.observableArrayList(arrSedes);
        cbmSedes.setItems(items);
        
        SedeDto sedeSalaActual = objSala.getSedeSala();
        
        if(sedeSalaActual != null)
        {
            for (SedeDto sede : arrSedes) 
            {
                if(Objects.equals(sede.getIdSede(), sedeSalaActual.getIdSede()))
                {
                    cbmSedes.getSelectionModel().select(sede);
                    break;
                }
            }
        }else{
            cbmSedes.getSelectionModel().selectFirst();
        }
        
        
        miGrilla.add(cbmSedes, 1, 5);
        
        
        Button btnGrabar = new Button("Actualizar la sala");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e)-> {actualizarSala();});
        miGrilla.add(btnGrabar, 1, 6);
        
         Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = SalaControladorVentana.administrar(
                    laVentanaPrincipal, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        
        miGrilla.add(btnRegresar, 0, 6);
        
        miFormulario.getChildren().add(miGrilla);
    }
    
    private void configurarLaGrilla(double anchito, double altito)
    {
        double porcentaje_ancho = 0.3;
        double anchoMarco = anchito*porcentaje_ancho;
        
        miGrilla.setHgap(H_GAP);
        miGrilla.setVgap(V_GAP);
        miGrilla.setPrefSize(anchoMarco, altito);
        miGrilla.setMinSize(anchoMarco, altito);
        miGrilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        col1.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        col2.setHgrow(Priority.ALWAYS);
        
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(30);
        col3.setHgrow(Priority.ALWAYS);
        
        miGrilla.getColumnConstraints().addAll(col1, col2, col3);
        
        int i;
        for (int j = 0; j < 7; j++) 
        {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setPrefHeight(ALTO_FILA);
            miGrilla.getRowConstraints().add(fila);
        }
    }
    
    private Boolean obtenerResultadoSwitch()
    {
        return sala4dSwitch.isOn();
    }
//    private void limpiarFormulario()
//    {
//        cajaNombre.setText("");
//        chbAsientos.getSelectionModel().clearSelection();
//        cbmSedes.getSelectionModel().selectFirst();
//        sala4dSwitch.reset();
//        cajaNombre.requestFocus();
//        
//        
//        rutaSeleccionada = "";
//        cajaImagen.setText("");
//        miGrilla.getChildren().remove(imgPrevisualizar);
//        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
//        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
//    }
    private Boolean formularioValido()
    {
        if(cajaNombre.getText().isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Lease 100 años de seriedad.\n"
                    + "Debes escribir algo en la caja.");
            cajaNombre.requestFocus();
            return false; 
        }
        
        if(chbAsientos.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Agarra algo del ChoiceBox");
             chbAsientos.requestFocus();
             return false; 
        }
        
        if(cbmSedes.getSelectionModel().getSelectedIndex() == 0)
        {
             Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Agarra una de las Ver*** del combo");
             cbmSedes.requestFocus();
             return false;
        }
        
        
        return true;
    }
    
    private void actualizarSala()
    {
        if(formularioValido())
        {
            int cantidadAsientos = Integer.parseInt(chbAsientos.getValue());
            int codSala = objSala.getIdSala();
            String nomSala = cajaNombre.getText();
            int asientosSala = cantidadAsientos;
            Boolean sala4d =obtenerResultadoSwitch();
            SedeDto sedeSala = obtenerResultadoComboSede();
            String imaSala = cajaImagen.getText();
            String nocu = objSala.getNombreImagenPrivadoSala();
            
            SalaDto nuevaSala = new SalaDto(codSala, nomSala, asientosSala, sala4d, sedeSala, Short.valueOf("0") ,imaSala, nocu);
            
            if(SalaControladorEditar.actualizar(posicion, nuevaSala, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "EXITO", "Listo me fui !!!!!!!!!!!!!!");
            }
            
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, "ERROR", "No me fui!!!!");
            }
            
//            limpiarFormulario();
        }
    }
    
    private SedeDto obtenerResultadoComboSede()
    {
        SedeDto seleccionado = cbmSedes.getSelectionModel().getSelectedItem();
        
        if(seleccionado != null && seleccionado.getIdSede() != 0)
        {
            return seleccionado;
        }
        return null;
    }
   
   
}
