package com.uniMagdalena.vista.banyo;

import com.uniMagdalena.controlador.banyo.BanyoControladorEditar;
import com.uniMagdalena.controlador.banyo.BanyoControladorGrabar;
import com.uniMagdalena.controlador.banyo.BanyoControladorVentana;
import com.uniMagdalena.controlador.sede.SedeControladorListar;
import com.uniMagdalena.controlador.trabajador.TrabajadorControladorListar;
import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Contenedor;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;

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
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class VistaBanyoEditar extends SubScene
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
    
    // Controles del formulario
    private ToggleGroup grupoUsoBanyo;
    
    private RadioButton rbEnUso;            //En uso / Fuera de uso
    private RadioButton rbFueraDeUso;
            
    private TextArea cajaUbicacion;
    private ChoiceBox<String> choiceGeneroBanyo;     // Masculino / Femenino      
    private ComboBox<SedeDto> cbmSede;
    private ListView<TrabajadorDto> listTrabajador;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    private final int posicion;
    private final BanyoDto objBanyo;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;

    public VistaBanyoEditar(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto, BanyoDto objBanyoExterno, int posicionArchivo )
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal = ventanaPadre;
        
        posicion = posicionArchivo;
        objBanyo = objBanyoExterno;
        panelPrincipal = princ;
        panelCuerpo = pane;       
        
        
        
        
        miGrilla = new GridPane();
        miMarco = Marco.pintar(ventanaPadre, 
                Configuracion.MARCO_ALTO_PORCENTAJE, 
                Configuracion.MARCO_ANCHO_PORCENTAJE, 
                Configuracion.DEGRADEE_ARREGLO, 
                Configuracion.COLOR_BORDE);
        miFormulario.getChildren().add(miMarco);
        
        rutaSeleccionada = "";
        
        configurarLaGrilla(ancho, alto);
        pintarTitulo();
        todoResponsive20Puntos();
        pintarFormulario();
        reUbicarFormulario();
    }
    
    public StackPane getMiFormulario()
    {
        return miFormulario;
    }
    
    private void reUbicarFormulario() 
    {   
        Runnable organizar = () -> {
            double altoDelFormulario = miMarco.getHeight() * AJUSTE_ARRIBA;
            if (altoDelFormulario > 0)
            {
                miGrilla.setTranslateY(altoDelFormulario / 2 + altoDelFormulario);
            }
        };
        organizar.run();
        miMarco.heightProperty().addListener((obs, antes, despues) -> organizar.run());
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
        double anchoMarco = anchito * porcentaje_ancho;
        
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
        
        for (int j = 0; j < 8; j++) 
        {
            RowConstraints fila = new RowConstraints();
            fila.setMinHeight(ALTO_FILA);
            fila.setPrefHeight(ALTO_FILA);
            fila.setValignment(VPos.CENTER);
            miGrilla.getRowConstraints().add(fila);
        }
    }
    
    private void pintarTitulo()
    {
        Text titulo = new Text("Formulario actualización de baño");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void pintarFormulario()
    {
        // Ubicación del baño
        Label lblUbicacion = new Label("Ubicación del baño: ");
        lblUbicacion.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblUbicacion, 0, 1);
        
        cajaUbicacion = new TextArea();
        cajaUbicacion.setText(objBanyo.getUbicacionBanyo());
        GridPane.setHgrow(cajaUbicacion, Priority.ALWAYS);
        cajaUbicacion.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaUbicacion, 1, 1);
        
        // Combo género del baño (Masculino / Femenino)
        Label lblGenero = new Label("Género del baño:");
        lblGenero.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblGenero, 0, 2);
        
        choiceGeneroBanyo = new ChoiceBox<>();
        choiceGeneroBanyo.getItems().addAll("Masculino", "Femenino");
        
        if(objBanyo.getGeneroBanyo())
        {
            choiceGeneroBanyo.getSelectionModel().select(0);
        }
        else{
            choiceGeneroBanyo.getSelectionModel().select(1);
        }
        
        
        miGrilla.add(choiceGeneroBanyo, 1, 2);
        
        // Combo uso del baño (Público / Privado, o En uso / Fuera de uso)
        Label lblUso = new Label("Uso del baño:");
        lblUso.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblUso, 0, 3);
        
        grupoUsoBanyo = new ToggleGroup();
        
        rbEnUso = new RadioButton("En uso");
        rbEnUso.setToggleGroup(grupoUsoBanyo);
        rbFueraDeUso = new RadioButton("Fuera de uso");
        rbFueraDeUso.setToggleGroup(grupoUsoBanyo);
        
        seleccionUso();
        
        HBox contenedorRadios = new HBox(16);
        contenedorRadios.getChildren().addAll(rbEnUso, rbFueraDeUso);
        miGrilla.add(contenedorRadios, 1, 3);
        
        // Combo trabajador encargado
        Label lblTrabajador = new Label("Trabajador encargado:");
        lblTrabajador.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblTrabajador, 0, 4);
        
        List<TrabajadorDto> arrTrabajadores = TrabajadorControladorListar.arregloTrabajadoresdeAseo();
        TrabajadorDto opcionPorDefecto = new TrabajadorDto(0, "Seleccione un trabajador", true, null,null , null, null,"", "");
        arrTrabajadores.add(0,opcionPorDefecto);
        listTrabajador = new ListView<>();
        listTrabajador.setMaxWidth(Double.MAX_VALUE);
        listTrabajador.setPrefHeight(ALTO_CAJA);
        ObservableList<TrabajadorDto> items = FXCollections.observableArrayList(arrTrabajadores);
        listTrabajador.setItems(items);
        
        TrabajadorDto TrabajadorVentaActual = objBanyo.getEncargadoBanyo();
        if(TrabajadorVentaActual != null)
        {
            for (TrabajadorDto Trabajador : arrTrabajadores) {
                if(Objects.equals(Trabajador.getIdTrabajador(), TrabajadorVentaActual.getIdTrabajador()))
                {
                    listTrabajador.getSelectionModel().select(Trabajador);
                    break;
                }
            }
            
        }else{
        //listTrabajador.getSelectionModel().select(0);
        listTrabajador.getSelectionModel().selectFirst();
        }
        miGrilla.add(listTrabajador, 1, 4);
        

//        cargarTrabajadores();
        
        // Selección de imagen 
        Label lblImagen = new Label("Imagen del baño:");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen, 0, 5);
        
        cajaImagen = new TextField();
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen(
                "Busca una imagen","La imagen", extensionesPermitidas);
        
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(ALTO_CAJA);
        btnEscogerImagen.setOnAction((e)-> 
        {
            rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
            
            if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
        // Usuario seleccionó una nueva imagen
        miGrilla.getChildren().remove(imgPorDefecto);
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 200);
        GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
        GridPane.setValignment(imgPrevisualizar, VPos.CENTER);
        
        miGrilla.add(imgPrevisualizar, 2, 1, 1, 5);
    } else {
        // Usuario canceló - mantener la imagen original
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        // Solo agregar imgPorDefecto si no está ya en la grilla
        if (!miGrilla.getChildren().contains(imgPorDefecto)) {
            GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
            GridPane.setValignment(imgPorDefecto, VPos.CENTER);
            miGrilla.add(imgPorDefecto, 2, 1, 1, 5);
        }
        
    }
        });
        
        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelCajaBoton = new HBox(2);
        panelCajaBoton.setAlignment(Pos.BOTTOM_RIGHT);
        panelCajaBoton.getChildren().addAll(cajaImagen, btnEscogerImagen);
        miGrilla.add(panelCajaBoton, 1, 5);
        
        // Imagen por defecto
        imgPorDefecto = Icono.obtenerIconoExterno(objBanyo.getNombreImagenPrivadoBanyo(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 5);
        
        
        Label lblSede = new Label("Sede del baño:");
        lblSede.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblSede, 0, 6);
        
        List<SedeDto> arrSedes = SedeControladorListar.arregloSedes();
        SedeDto opcionPorDefecto2 = new SedeDto(0, "Seleccione una sede", "", null,null,null , null,"", "");
        arrSedes.add(0,opcionPorDefecto2);
        cbmSede = new ComboBox<>();
        cbmSede.setMaxWidth(Double.MAX_VALUE);
        cbmSede.setPrefHeight(ALTO_CAJA);
        ObservableList<SedeDto> items2 = FXCollections.observableArrayList(arrSedes);
        cbmSede.setItems(items2);
        
        SedeDto SedeVentaActual = objBanyo.getSedeBanyo();
        if(SedeVentaActual != null)
        {
            for (SedeDto Sede : arrSedes) {
                if(Objects.equals(Sede.getIdSede(), SedeVentaActual.getIdSede()))
                {
                    cbmSede.getSelectionModel().select(Sede);
                    break;
                }
            }
            
        }else{

        cbmSede.getSelectionModel().selectFirst();
        }
        miGrilla.add(cbmSede, 1, 6);
                
        
        // Botón Grabar
        Button btnGrabar = new Button("Clic para actualizar el baño");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e) -> {actualizarBanyo();});
        miGrilla.add(btnGrabar, 1, 7);
        
        
         Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = BanyoControladorVentana.administrar(
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
        if (cajaUbicacion.getText().isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, 
                    "EY", "Debes escribir la ubicación del baño.");
            cajaUbicacion.requestFocus();
            return false;
        }
        
        if (grupoUsoBanyo.getSelectedToggle() == null) 
        {
        Mensaje.mostrar(Alert.AlertType.WARNING, null, 
            "Advertencia", "Debe seleccionar un estado para el baño");
        return false;
        }         
        
        if (choiceGeneroBanyo.getValue() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, 
             "Advertencia", "Debe seleccionar un genero para el baño");
            choiceGeneroBanyo.requestFocus();
            return false;
        }
        
        if (listTrabajador.getSelectionModel().getSelectedItem() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, 
                    "EY", "Selecciona el trabajador encargado del baño.");
            listTrabajador.requestFocus();
            return false;
        }
        
        if (cbmSede.getSelectionModel().getSelectedItem() == null)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, 
                    "EY", "Selecciona la sede del baño.");
            cbmSede.requestFocus();
            return false;
        }
       
        
        return true;
    }
    
    
    private Boolean obtenerUsoBanyo()
    {
        RadioButton seleccionado = (RadioButton) grupoUsoBanyo.getSelectedToggle();

        if(seleccionado == null)
        {
            return null;
        }
        if(seleccionado == rbEnUso)  
        {
            return true;
        }else if(seleccionado == rbFueraDeUso){  
            return false;
        }

        return null;
    }
    
    private void seleccionUso()
    {
        if(objBanyo.getUsoBanyo())
        {
            rbEnUso.setSelected(true);
        }
        else
        {
            rbFueraDeUso.setSelected(true);
        }
    }
    
    
    
    private Boolean obtenerGeneroBanyo()
    {
        String seleccion = choiceGeneroBanyo.getValue();
        // true = Público, false = Privado (puedes cambiarlo si lo manejas distinto)
        if ("Masculino".equals(seleccion))
        {
            return true;
        }
        if ("Femenino".equals(seleccion))
        {
            return false;
        }
        return null;
    }
    
    
    private SedeDto obtenerResultadoComboSede()
    {
       SedeDto seleccionado = cbmSede.getSelectionModel().getSelectedItem();
       
        if (seleccionado != null && seleccionado.getIdSede()!= 0) 
        {
            return seleccionado;
        }
        
        return null;
    }


    private TrabajadorDto obtenerResultadoComboTrabajador()
    {
       TrabajadorDto seleccionado = listTrabajador.getSelectionModel().getSelectedItem();
       
        if (seleccionado != null && seleccionado.getIdTrabajador()!= 0) 
        {
            return seleccionado;
        }
        
        return null;
    }
  
   

    
    private void actualizarBanyo()
    {
        if (formularioValido())
        {
            int codBanyo = objBanyo.getIdBanyo();
            String UbicacionBanyo = cajaUbicacion.getText();
            Boolean GeneroBanyo = obtenerGeneroBanyo();
            Boolean UsoBanyo = obtenerUsoBanyo();
             TrabajadorDto EncargadoBanyo =obtenerResultadoComboTrabajador();
            SedeDto SedeBanyo = obtenerResultadoComboSede();
            String imaBanyo = cajaImagen.getText();
            String nocu = objBanyo.getNombreImagenPrivadoBanyo();
            
            BanyoDto nuevoBanyo = new BanyoDto(codBanyo, SedeBanyo, UbicacionBanyo, GeneroBanyo, UsoBanyo, EncargadoBanyo, imaBanyo, nocu);
            
            if (BanyoControladorEditar.actualizar(posicion, nuevoBanyo, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, 
                        "EXITO", "Baño actualizado correctamente.");
            }
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, 
                        "ERROR", "No se pudo actualizar el baño.");
            }
            
        }
    }
}