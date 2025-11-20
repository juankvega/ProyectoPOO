
package com.uniMagdalena.vista.pelicula;

import com.uniMagdalena.controlador.genero.GeneroControladorListar;
import com.uniMagdalena.controlador.pelicula.PeliculaControladorEditar;
import com.uniMagdalena.controlador.pelicula.PeliculaControladorVentana;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.dto.PeliculaDto;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
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

public class VistaPeliculaEditar extends SubScene
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
    
    private TextArea cajaNombre;
    private TextField cajaActor;
    private PasswordField cajaPresupuesto;
    private SlideSwitch switchAdultos;
    private ComboBox<GeneroDto> cbmGenero;
    
    private TextField cajaImagen;
    private ImageView imgPorDefecto;
    private ImageView imgPrevisualizar;
    private String rutaSeleccionada;
    
    private final int posicion;
    private final PeliculaDto objPelicula;
    
    private Pane panelCuerpo;
    private final BorderPane panelPrincipal;
    
    public VistaPeliculaEditar(Stage ventanaPadre, BorderPane princ, Pane pane, double ancho, double alto, PeliculaDto objPeliculaExterno, int posicionArchivo )
    {
        super(new StackPane(), ancho, alto);
        
        Background fondo = Fondo.asignarAleatorio(Configuracion.FONDOS);
        miFormulario = (StackPane) getRoot();
        miFormulario.setBackground(fondo);
        miFormulario.setAlignment(Pos.TOP_CENTER);
        
        laVentanaPrincipal = ventanaPadre;
        
        posicion = posicionArchivo;
        objPelicula = objPeliculaExterno;
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
        Text titulo = new Text("Formulario Actualización de película");
        titulo.setFill(Color.web(Configuracion.COLOR_BORDE));
        titulo.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        GridPane.setHalignment(titulo, HPos.CENTER);
        GridPane.setMargin(titulo, new Insets(10, 0, 0, 0));
        // columna, fila, colspan, rowspan
        miGrilla.add(titulo, 0, 0, 3, 1);
    }
    
    private void PintarFormulario()
    {
        Label lblNombre = new Label("Nombre de la película: ");
        lblNombre.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblNombre, 0, 2);
        
        
        cajaNombre = new TextArea();
        cajaNombre.setText(objPelicula.getNombrePelicula());
        cajaNombre.setPrefRowCount(3);
        cajaNombre.setWrapText(true);
        GridPane.setHgrow(cajaNombre, Priority.ALWAYS);
        cajaNombre.setPrefHeight(ALTO_CAJA * 2); // Duplica la altura para mejor visualización
        miGrilla.add(cajaNombre, 1, 2);
        
        Label lblImagen = new Label("Imagen de la pelicula");
        lblImagen.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblImagen,0,3);
        
        cajaImagen = new TextField();
        cajaImagen.setText(objPelicula.getNombreImagenPublicoPelicula());
        cajaImagen.setDisable(true);
        cajaImagen.setPrefHeight(ALTO_CAJA);
        
        String[] extensionesPermitidas = {"*.png", "*.jpg", "*.jpeg"};
        FileChooser objSeleccionar = Formulario.selectorImagen("Busca una imagen", "La imagen", extensionesPermitidas);
        Button btnEscogerImagen = new Button("+");
        btnEscogerImagen.setPrefHeight(ALTO_CAJA);
        btnEscogerImagen.setOnAction((e) -> 
        {
        rutaSeleccionada = GestorImagen.obtenerRutaImagen(cajaImagen, objSeleccionar);    
        if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
        miGrilla.getChildren().remove(imgPorDefecto);
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        imgPrevisualizar = Icono.previsualizar(rutaSeleccionada, 150);
        GridPane.setHalignment(imgPrevisualizar, HPos.CENTER);
        GridPane.setValignment(imgPrevisualizar, VPos.CENTER);
        
       
        miGrilla.add(imgPrevisualizar, 2, 1, 1, 6);
    } else {
        // Si el usuario canceló, mantener la imagen por defecto
        miGrilla.getChildren().remove(imgPrevisualizar);
        
        if (!miGrilla.getChildren().contains(imgPorDefecto)) {
            GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
            GridPane.setValignment(imgPorDefecto, VPos.CENTER);
            miGrilla.add(imgPorDefecto, 2, 1, 1, 6);
        }
    }
  
        
    });

        HBox.setHgrow(cajaImagen, Priority.ALWAYS);
        HBox panelCajaBoton = new HBox(2);
        panelCajaBoton.setAlignment(Pos.BOTTOM_RIGHT);
        panelCajaBoton.getChildren().addAll(cajaImagen, btnEscogerImagen);
        miGrilla.add(panelCajaBoton, 1, 3);

        imgPorDefecto = Icono.obtenerIconoExterno(objPelicula.getNombreImagenPrivadoPelicula(), 150);
        GridPane.setHalignment(imgPorDefecto, HPos.CENTER);
        GridPane.setValignment(imgPorDefecto, VPos.CENTER);
        miGrilla.add(imgPorDefecto, 2, 1, 1, 6);
        
        Label lblGenero = new Label("ID Género:  ");
        lblGenero.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblGenero, 0, 4);
        
        List<GeneroDto> arrGeneros = GeneroControladorListar.arregloGenerosActivos();
        GeneroDto opcionPorDefecto = new GeneroDto(0, "Seleccione un género", true, null,null , null,"", "");
        arrGeneros.add(0,opcionPorDefecto);
        cbmGenero = new ComboBox<>();
        cbmGenero.setMaxWidth(Double.MAX_VALUE);
        cbmGenero.setPrefHeight(ALTO_CAJA);
        ObservableList<GeneroDto> items = FXCollections.observableArrayList(arrGeneros);
        cbmGenero.setItems(items);
        //cbmGenero.getSelectionModel().select(0);
        GeneroDto generoPeliculaActual = objPelicula.getIdGeneroPelicula();

        if (generoPeliculaActual != null) {
    // Buscar el género en la lista por su ID
        for (GeneroDto genero : arrGeneros) {
            if (Objects.equals(genero.getIdGenero(), generoPeliculaActual.getIdGenero())) 
                {
                    cbmGenero.getSelectionModel().select(genero);
                    break;
                }
            }
        } else {
    // Si no hay género asignado, seleccionar la opción por defecto
    cbmGenero.getSelectionModel().selectFirst();
    }
// *************************************************************

        miGrilla.add(cbmGenero, 1, 4);
        
        Label lblActor = new Label ("Actor principal: ");
        lblActor.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblActor, 0, 5);
        
        cajaActor = new TextField();
        cajaActor.setText(objPelicula.getActorPPelicula());
        GridPane.setHgrow(cajaActor, Priority.ALWAYS);
        cajaActor.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaActor, 1, 5);
        
        Label lblPresupuesto = new Label("Ingrese el presupuesto: ");
        lblPresupuesto.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblPresupuesto, 0, 6);
        
        cajaPresupuesto = new PasswordField();
        String presupuestoPel = String.valueOf(objPelicula.getPresupuestoPelicula());
        cajaPresupuesto.setText(presupuestoPel);
        GridPane.setHgrow(cajaPresupuesto, Priority.ALWAYS);
        cajaPresupuesto.setPrefHeight(ALTO_CAJA);
        miGrilla.add(cajaPresupuesto, 1, 6);
        
        Label lblAdultos = new Label("¿Es para menor de 15 años? ");
        lblAdultos.setFont(Font.font("Verdana", TAMANYO_FUENTE));
        miGrilla.add(lblAdultos, 0, 7);
        
        switchAdultos = new SlideSwitch();
        if(objPelicula.getEsParaNinyosPelicula())
        {
            switchAdultos.setEstado(true);
        }
        else
        {
            switchAdultos.setEstado(false);
        }
        switchAdultos.setMaxWidth(Double.MAX_VALUE);
        switchAdultos.setPrefHeight(ALTO_CAJA);
        miGrilla.add(switchAdultos, 1, 7);
        
        Button btnGrabar = new Button("Actualizar la pelicula");
        btnGrabar.setMaxWidth(Double.MAX_VALUE);
        btnGrabar.setTextFill(Color.web(Configuracion.COLOR4));
        btnGrabar.setFont(Font.font("Verdana", FontWeight.BOLD, TAMANYO_FUENTE));
        btnGrabar.setOnAction((e)->{actualizarPelicula();});
        miGrilla.add(btnGrabar, 1, 8);
        
        Button btnRegresar = new Button("Regresar");
        btnRegresar.setPrefHeight(ALTO_CAJA);
        btnRegresar.setMaxWidth(Double.MAX_VALUE);
        btnRegresar.setTextFill(Color.web("#6C3483"));
        btnRegresar.setFont(Font.font("Verdana", FontWeight.NORMAL, 14));
        btnRegresar.setOnAction((ActionEvent e) -> {
            panelCuerpo = PeliculaControladorVentana.administrar(
                    laVentanaPrincipal, panelPrincipal, panelCuerpo,
                    Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
            panelPrincipal.setCenter(null);
            panelPrincipal.setCenter(panelCuerpo);
        });
        
        miGrilla.add(btnRegresar, 0, 8);
        
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
            
            
        if(cajaActor.getText().isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Necesitamos saber el actor principal para la publicidad \n"
                    + "Debes escribir algo en la caja.");
            cajaActor.requestFocus();
            return false;
        }
        
        if(cajaPresupuesto.getText().isBlank())
        {
           Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Queremos saber cuanta plata se malgastó.\n"
                    + "Debes escribir algo en la caja.");
           cajaPresupuesto.requestFocus();
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
            double numero2 = Double.parseDouble(cajaPresupuesto.getText());
            
            if(numero2 < 0)
            {
                Mensaje.mostrar(Alert.AlertType.WARNING, null, "ERROR", "¿Siquiera eso tiene sentido?, corriga el presupuesto de la pelicula." );
                cajaPresupuesto.requestFocus();
                return false;
            }
            
        }
        catch(NumberFormatException e)
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "Error", 
                "¡¡¡¡¡¡¡El presupuesto debe ser un número, un número!!!!!!!");
            cajaPresupuesto.requestFocus();
        return false;
        }
        if(cajaActor.getText().isBlank())
        {
            Mensaje.mostrar(Alert.AlertType.WARNING, null, "EY", "Necesitamos saber el actor principal para la publicidad \n"
                    + "Debes escribir algo en la caja.");
            cajaActor.requestFocus();
            return false;
        }
        
        
        return true;
    }
    
     private GeneroDto obtenerResultadoComboGenero()
    {
       GeneroDto seleccionado = cbmGenero.getSelectionModel().getSelectedItem();
       
        if (seleccionado != null && seleccionado.getIdGenero()!= 0) 
        {
            return seleccionado;
        }
        
        return null;
    }

    
    private void actualizarPelicula()
    {
       if(formularioValido()) 
       {
           
           Double PeliculaPrecio = Double.parseDouble(cajaPresupuesto.getText());
           int codPel = objPelicula.getIdPelicula();
           String nomPel = cajaNombre.getText();
           GeneroDto GenPel = obtenerResultadoComboGenero();
           String actPel = cajaActor.getText();
           Double prepPel = PeliculaPrecio;
           Boolean ninyosPel = switchAdultos.isOn();
           String imaPel = cajaImagen.getText();
           String nocu = objPelicula.getNombreImagenPrivadoPelicula();
           
           PeliculaDto nuevoPelicula = new PeliculaDto(codPel, nomPel, GenPel, actPel, prepPel, ninyosPel,Short.valueOf("0")  ,imaPel, nocu);
           
           if(PeliculaControladorEditar.actualizar(posicion, nuevoPelicula, rutaSeleccionada))
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
