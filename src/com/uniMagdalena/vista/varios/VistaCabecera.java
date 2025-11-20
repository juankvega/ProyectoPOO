
package com.uniMagdalena.vista.varios;

import com.uniMagdalena.controlador.Producto.ProductoControladorVentana;
import com.uniMagdalena.controlador.banyo.BanyoControladorVentana;
import com.uniMagdalena.controlador.cliente.ClienteControladorVentana;
import com.uniMagdalena.controlador.genero.GeneroControladorVentana;
import com.uniMagdalena.controlador.pelicula.PeliculaControladorVentana;
import com.uniMagdalena.controlador.sala.SalaControladorVentana;
import com.uniMagdalena.controlador.sede.SedeControladorVentana;
import com.uniMagdalena.controlador.trabajador.TrabajadorControladorVentana;
import com.uniMagdalena.controlador.varios.SalidaControlador;
import com.uniMagdalena.controlador.venta.VentaControladorVentana;
import com.uniMagdalena.recurso.constante.Contenedor;
import com.uniMagdalena.recurso.constante.Configuracion;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.Icono;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.stage.Stage;

public class VistaCabecera extends SubScene
{
    private final int MENU_ANCHO = 160;
    private final int MENU_ALTO = 35;
    private final int MENU_ESPACIO_X = 20;
    
    private Pane miPanelCuerpo;
    private final Stage miEscenario;
    private final HBox miPanelCabecera;
    private final BorderPane miPanelPrincipal;

    public VistaCabecera(Stage escenario, BorderPane panelPrin,Pane pane ,double altoCabecera, double anchoPanel) 
    {
        
        super(new HBox(), anchoPanel, altoCabecera);
        miPanelCabecera = (HBox) this.getRoot();
        miPanelCabecera.setAlignment(Pos.CENTER_LEFT);

        miPanelPrincipal = panelPrin;
        miPanelCuerpo = pane;
        miEscenario = escenario;

        miPanelCabecera.setSpacing(MENU_ESPACIO_X);
        miPanelCabecera.setPadding(new Insets(0, 30, 0, 30));
        miPanelCabecera.setPrefHeight(Contenedor.ALTO_CABECERA.getValor());
        miPanelCabecera.setStyle(Configuracion.CABECERA_COLOR_FONDO);

        crearBotones();
    }
    
      public HBox getMiPanelCabecera() 
      {
        return miPanelCabecera;
    }
    
    private void agregaralmenu(MenuButton menu) 
    {
        menu.setCursor(Cursor.HAND);
        menu.setPrefWidth(MENU_ANCHO);
        menu.setPrefHeight(MENU_ALTO);
        miPanelCabecera.getChildren().add(menu);
    }
    
    private void crearBotones()
    {
        menuSede();
        menuTrabajador();
        menuSala();
        menuBanyo();
        menuGenero();
        menuPelicula();
        menuProducto();
        menuCliente();
        menuVenta();
        btnSalir();
        
        btnAcerca(700, 600);
        
    }
    
    private void menuSede()
    {
        MenuItem opcion01 = new MenuItem("Crear sede");
        MenuItem opcion02 = new MenuItem("Listar sedes");
        MenuItem opcion03 = new MenuItem("Administrar sedes");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = SedeControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = SedeControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion03.setOnAction((e) -> {            
        miPanelCuerpo = SedeControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo, BASELINE_OFFSET_SAME_AS_HEIGHT, BASELINE_OFFSET_SAME_AS_HEIGHT);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = SedeControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);  
        });
        
        MenuButton menuBotones = new MenuButton("Sedes");
         menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoSedes.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuSala()
    {
        MenuItem opcion01 = new MenuItem("Crear sala");
        MenuItem opcion02 = new MenuItem("Listar salas");
        MenuItem opcion03 = new MenuItem("Administrar salas");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = SalaControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = SalaControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);    
        });
        
        opcion03.setOnAction((e) -> {
            
        miPanelCuerpo = SalaControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = SalaControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);  
        });
        
        MenuButton menuBotones = new MenuButton("Salas");
         menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoSalas.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuBanyo()
    {
        MenuItem opcion01 = new MenuItem("Crear baño");
        MenuItem opcion02 = new MenuItem("Listar baños");
        MenuItem opcion03 = new MenuItem("Administrar baños");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = BanyoControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo); 
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = BanyoControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = BanyoControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = BanyoControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);    
        });
        
        MenuButton menuBotones = new MenuButton("Baños");
         menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoBanyos.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuTrabajador()
    {
        MenuItem opcion01 = new MenuItem("Crear trabajador");
        MenuItem opcion02 = new MenuItem("Listar trabajadores");
        MenuItem opcion03 = new MenuItem("Administrar trabajadores");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = TrabajadorControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = TrabajadorControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = TrabajadorControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = TrabajadorControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);        
        
        });
        
        MenuButton menuBotones = new MenuButton("Trabajadores");
        menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoTrabajadores.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuGenero()
    {
        MenuItem opcion01 = new MenuItem("Crear Género");
        MenuItem opcion02 = new MenuItem("Listar Géneros");
        MenuItem opcion03 = new MenuItem("Administrar Géneros");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((ActionEvent e) -> {
        miPanelCuerpo = GeneroControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion02.setOnAction((ActionEvent e) -> {miPanelCuerpo = GeneroControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);});
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = GeneroControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);});
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = GeneroControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);        
        });
        
        MenuButton menuBotones = new MenuButton("Géneros");
        menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoGeneros.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuPelicula()
    {
        MenuItem opcion01 = new MenuItem("Crear pelicula");
        MenuItem opcion02 = new MenuItem("Listar peliculas");
        MenuItem opcion03 = new MenuItem("Administrar peliculas");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = PeliculaControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion02.setOnAction((e) -> {miPanelCuerpo = PeliculaControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);});
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = PeliculaControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);});
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = PeliculaControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);  });
        
        MenuButton menuBotones = new MenuButton("Peliculas");
        menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoPelicula.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuProducto()
    {
        MenuItem opcion01 = new MenuItem("Crear producto");
        MenuItem opcion02 = new MenuItem("Listar productos");
        MenuItem opcion03 = new MenuItem("Administrar productos");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = ProductoControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo); 
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = ProductoControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo); 
        });
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = ProductoControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = ProductoControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);  
        });
        
        MenuButton menuBotones = new MenuButton("Productos");
        menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoProducto.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuCliente()
    {
        MenuItem opcion01 = new MenuItem("Crear cliente");
        MenuItem opcion02 = new MenuItem("Listar clientes");
        MenuItem opcion03 = new MenuItem("Administrar clientes");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = ClienteControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);    
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = ClienteControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);    
        });
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = ClienteControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);  
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = ClienteControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo); 
        });
        
        MenuButton menuBotones = new MenuButton("Clientes");
         menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoClientes.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuVenta()
    {
        MenuItem opcion01 = new MenuItem("Crear venta");
        MenuItem opcion02 = new MenuItem("Listar ventas");
        MenuItem opcion03 = new MenuItem("Administrar ventas");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
        miPanelCuerpo = VentaControladorVentana.crear(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);    
        
        });
        
        opcion02.setOnAction((e) -> {
        miPanelCuerpo = VentaControladorVentana.listar(miEscenario, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        opcion03.setOnAction((e) -> {
        miPanelCuerpo = VentaControladorVentana.administrar(miEscenario, miPanelPrincipal, miPanelCuerpo ,Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor());
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        
        });
        
        opcion04.setOnAction((e) -> {
        int posicionInicial = 0;
        miPanelCuerpo = VentaControladorVentana.carrusel(miEscenario, miPanelPrincipal, miPanelCuerpo, Configuracion.ANCHO_APP, Contenedor.ALTO_CUERPO.getValor(), posicionInicial);
        miPanelPrincipal.setCenter(null);
        miPanelPrincipal.setCenter(miPanelCuerpo);
        });
        
        MenuButton menuBotones = new MenuButton("Ventas");
        menuBotones.setPrefWidth(MENU_ANCHO);
        menuBotones.setPrefWidth(MENU_ALTO);
        menuBotones.setGraphic(Icono.obtenerIcono("iconoVentas.png", 25));
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void btnSalir() {
        Button btnSalir = new Button("Salir");
        btnSalir.setCursor(Cursor.HAND);
        btnSalir.setPrefWidth(MENU_ANCHO);
        btnSalir.setPrefHeight(MENU_ALTO);

        btnSalir.setOnAction((ActionEvent event) -> {
            event.consume();
            SalidaControlador.verificar(miEscenario);
        });

        miPanelCabecera.getChildren().add(btnSalir);
    }
    
    private void btnAcerca(double anchoFlotante, double altoFlotante)
    {
        Button botonAyuda = new Button("?");
        botonAyuda.setOnAction((ActionEvent e) ->
        { VistaAcerca.mostrar(miEscenario, anchoFlotante, altoFlotante);
        });
        
        botonAyuda.setPrefWidth(30);
        botonAyuda.setId("btn-ayuda");
        botonAyuda.setCursor(Cursor.HAND);
        botonAyuda.getStylesheets().add(getClass().getResource(Persistencia.RUTA_ESTILO_BTN_ACERCA).toExternalForm());
        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        
        miPanelCabecera.getChildren().add(espacio);
        miPanelCabecera.getChildren().add(botonAyuda);
    }
    
}
