
package com.uniMagdalena.vista.genero;

import com.uniMagdalena.controlador.genero.GeneroControladorEditar;
import com.uniMagdalena.controlador.genero.GeneroControladorGrabar;
import com.uniMagdalena.controlador.genero.GeneroControladorVentana;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Contenedor;
import com.uniMagdalena.recurso.utilidad.Fondo;
import com.uniMagdalena.recurso.utilidad.Formulario;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import com.uniMagdalena.recurso.utilidad.Icono;
import com.uniMagdalena.recurso.utilidad.Marco;
import com.uniMagdalena.recurso.utilidad.Mensaje;
import com.uniMagdalena.recurso.utilidad.SlideSwitch;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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

public class VistaGeneroEditar extends SubScene
{
    private static final int H_GAP = 10;
    private static final int V_GAP = 20;
    
    private static final int ALTO_FILA = 40;
    private static final int AlTO_CAJA = 35;
    private static final Integer VALOR_INICIAL_SPINER = 1;
    
    private static final int TAMANYO_FUENTE = 18;
    private static final double AJUSTE_ARRIBA = 0.2;
    
    private final GridPane miGrilla;
    private final Stage laVentanaPrincipal;
    private final StackPane miFormulario;
    private final Rectangle miMarco;
    
    private TextField cajaNombre;
    private ComboBox<String> cbmEstado;
    private Spinner<Integer> spPopularidad;
    
    private SlideSwitch slideSwitchClasico;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    private final int posicion;
    private final GeneroDto objGenero;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    public VistaGeneroEditar(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto, GeneroDto objGeneroExterno, int posicionArchivo)
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal = ventanaPadre;
        
        posicion = posicionArchivo;
        objGenero = objGeneroExterno;
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
        Text titulo = new Text("Formulario actualización de género");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void PintarFormulario()
    {
        // Nombre del genero
        Label lblNombre = new Label("Nombre del género: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre,0,1);
        
        cajaNombre = new TextField();
        cajaNombre.setText(objGenero.getNombreGenero());
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(AlTO_CAJA);
        Formulario.cantidadCaracteres(cajaNombre, 25);
        miGrilla.add(cajaNombre, 1, 1);
        
        //*************
        // El combo del estado
        Label lblEstado = new Label("Estado del género");
        lblEstado.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblEstado,0,2);
        
        cbmEstado =  new ComboBox<>();
        cbmEstado.setMaxWidth(Double.MAX_VALUE);
        cbmEstado.setPrefHeight(AlTO_CAJA);
        cbmEstado.getItems().addAll("Seleccione el estado", "Activo", "Inactivo");
        if(objGenero.getEstadoGenero())
        {
            cbmEstado.getSelectionModel().select(1);
        }
        else
        {
            cbmEstado.getSelectionModel().select(2);
        }
        miGrilla.add(cbmEstado, 1, 2);
        //**************************
        Label lblImagen = new Label("Imagen del género");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,3);
        
        cajaImagen = new TextField();
        cajaImagen.setText(objGenero.getNombreImagenPublicoGenero());
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(AlTO_CAJA);

        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Busca una imagen", "La imagen", extensionesPermitidas);
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(AlTO_CAJA);
        btnEscogerImagen.setOnAction((e) -> {
        rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);
        if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
        miGrilla.getChildren().remove(imgPorDefecto);
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 150);
        GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
        GridPane.setValignment(imgPrevisualizar, VPos.CENTER);
        
       
        miGrilla.add(imgPrevisualizar, 2, 1, 1, 3);
    } else {
        // Si el usuario canceló, mantener la imagen por defecto
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

        imgPorDefecto = Icono.obtenerIconoExterno(objGenero.getNombreImagenPrivadoGenero(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 3);
        
        Label lblPopularidad = new Label("Popularidad: ");
        lblPopularidad.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblPopularidad,0,4);
        
        spPopularidad = new Spinner<>(1, 5, objGenero.getPopularidadGenero());
        spPopularidad.setMaxWidth(Double.MAX_VALUE);
        spPopularidad.setPrefHeight(AlTO_CAJA);
        miGrilla.add(spPopularidad, 1, 4);
        
        Label lblClasico = new Label("Es clásico?");
        lblClasico.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblClasico, 0, 5);
        
        slideSwitchClasico = new SlideSwitch();
        if(objGenero.getEsClasicoGenero())
        {
            slideSwitchClasico.setEstado(true);
        }
        else
        {
            slideSwitchClasico.setEstado(false);
        }
        slideSwitchClasico.setMaxWidth(Double.MAX_VALUE);
        slideSwitchClasico.setPrefHeight(AlTO_CAJA);
        miGrilla.add(slideSwitchClasico, 1, 5);
        
        
        
        //**************************
        // El botón masimo que grabe en alguna parte
        Button btnEditar = new Button("Actualizar categoria");
        btnEditar.setMaxWidth(Double.MAX_VALUE);
        btnEditar.setTextFill(Color.web(Configuracion.COLOR4));
        btnEditar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnEditar.setOnAction((e)->{actualizarGenero();});
        miGrilla.add(btnEditar, 1, 6);
        
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(AlTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = GeneroControladorVentana.administrar(
                    laVentanaPrincipal, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        
        miGrilla.add(btnRegresar, 1, 7);
        
        miFormulario.getChildren().add(miGrilla);
    }
    
        private Boolean obtenerEstadoCombo()
    {
        String seleccion = cbmEstado.getValue();
        if ("Activo".equals(seleccion))
        {
            return true;
        }
        if("Inactivo".equals(seleccion))
        {
            return false;
        }
        
        return null;
    }
    
    private void actualizarGenero()
    {
        if(formularioValido())
        {
            int codGen = objGenero.getIdGenero();
            String nomGen = cajaNombre.getText();
            boolean estGen = obtenerEstadoCombo();
            Short popGen = spPopularidad.getValue().shortValue();
            boolean clasGen = slideSwitchClasico.isOn();
            String imaGen = cajaImagen.getText();
            String nocu = objGenero.getNombreImagenPrivadoGenero();
            
            GeneroDto nuevoGenero = new GeneroDto(codGen, nomGen, estGen, popGen, clasGen, Short.valueOf("0"), imaGen, nocu);
            
            if(GeneroControladorEditar.actualizar(posicion, nuevoGenero, rutaSeleccionada))
            {
                Mensaje.mostrar(Alert.AlertType.INFORMATION, null, "EXITO", "Listo me fui !!!!!!!!!!!!!!");
            }
            else
            {
                Mensaje.mostrar(Alert.AlertType.ERROR, null, "ERROR", "No me fui!!!!");
            }
            
            
       
                       
        }
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
        if(cbmEstado.getSelectionModel().getSelectedIndex() == 0)
        {
             Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Agarra una de las Ver*** del combo");
             cbmEstado.requestFocus();
             return false;
        }
        

        
        if(spPopularidad.getValue() == null)
    {
        Mensaje.mostrar(Alert.AlertType.WARNING, null, "Error", 
            "El valor de popularidad no es válido.");
        spPopularidad.getValueFactory().setValue(VALOR_INICIAL_SPINER);
        return false;
    }
        
        
        
        return true;
    }
    
    
}
