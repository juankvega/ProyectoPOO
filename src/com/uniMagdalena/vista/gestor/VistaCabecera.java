
package com.uniMagdalena.vista.gestor;

import com.uniMagdalena.controlador.SalidaControlador;
import com.uniMagdalena.recurso.constante.Contenedor;
import com.unimagdalena.recurso.constante.Configuracion;
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
        
    }
    
    private void menuSede()
    {
        MenuItem opcion01 = new MenuItem("Crear sede");
        MenuItem opcion02 = new MenuItem("Listar sedes");
        MenuItem opcion03 = new MenuItem("Administrar sedes");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Sedes");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Salas");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Baños");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Trabajadores");
        menuBotones.getItems().addAll(opcion01, opcion02, opcion03, opcion04);
        agregaralmenu(menuBotones);
    }
    
    private void menuGenero()
    {
        MenuItem opcion01 = new MenuItem("Crear Género");
        MenuItem opcion02 = new MenuItem("Listar Géneros");
        MenuItem opcion03 = new MenuItem("Administrar Géneros");
        MenuItem opcion04 = new MenuItem("Carrusel");
        
        opcion01.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Géneros");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Peliculas");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Productos");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Clientes");
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
            System.out.println("El carrusel");
        });
        
        opcion02.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion03.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        opcion04.setOnAction((e) -> {
            System.out.println("El carrusel");
        });
        
        MenuButton menuBotones = new MenuButton("Ventas");
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
}
