package com.uniMagdalena.recurso.constante;

import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class Configuracion {

    public static final int ALTO_APP = (int) (Screen.getPrimary().getBounds().getHeight() * 0.85);
    public static final int ANCHO_APP = (int) (Screen.getPrimary().getBounds().getWidth() * 0.7);

    public static final double ALTO_CABECERA = ALTO_APP * 0.1;

    // LOS DE LUCIO
    public static final String COLOR1 = "#dce629";
    public static final String COLOR2 = "#68e629";
    public static final String COLOR3 = "#29e691";
    public static final String COLOR4 = "#29e6c3";

    //ESTILO CSS
     public final static double CABECERA_ALTO_PORCENTAJE = 0.1;
    public static final String CABECERA_COLOR_FONDO
            = String.format("-fx-background-color: linear-gradient( to right, %s, %s, %s, %s);", COLOR1, COLOR2, COLOR3, COLOR4);

    public static final String COLOR_BORDE = "#29e6c3";
    public static final Stop[] DEGRADEE_ARREGLO = new Stop[]{
        new Stop(0.0, Color.web("#de7316", 0.8)),
        new Stop(0.25, Color.web("#edbd1f", 0.7)),
        new Stop(0.5, Color.web("#f2e141", 0.6)),
        new Stop(0.75, Color.web("#def739", 0.5))

    };
    //Porcentajes del marco
    public static final double MARCO_ALTO_PORCENTAJE = 0.75;
    public static final double MARCO_ANCHO_PORCENTAJE = 0.8;
    // imagen pantalla rota
    public final static String PANTALLA_ROTA = "pantallaRota.png";
    
    public final static String FONDOS[] =
    {
        "Fondo01.png",
        "Fondo02.png" 
    };

    // Iconos
    public static final String ICONO_BORRAR = "iconoBorrar.png";
    public static final String ICONO_CANCELAR = "iconoCancelar.png";
    public static final String ICONO_EDITAR = "iconoEditar.png";
    public static final String ICONO_NO_DISPONIBLE = "imgNoDisponible.png";

}
