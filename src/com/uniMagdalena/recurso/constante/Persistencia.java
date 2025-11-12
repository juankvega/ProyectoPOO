package com.unimagdalena.recurso.constante;

import java.io.File;

public class Persistencia {

    public static final String RUTA_PROYECTO = System.getProperty("user.dir");
    public static final String NOMBRE_BASE_DATOS_GENEROS = "generos.txt";
    public static final String NOMBRE_BASE_DATOS_PELICULAS = "peliculas.txt";
    public static final String NOMBRE_BASE_DATOS_SALAS = "salas.txt";
    public static final String NOMBRE_BASE_DATOS_VENTA = "ventas.txt";
    public static final String NOMBRE_BASE_DATOS_SEDE = "sedes.txt";
    public static final String NOMBRE_BASE_DATOS_PRODUCTO = "productos.txt";
    public static final String NOMBRE_BASE_DATOS_CLIENTE = "clientes.txt";
    public static final String NOMBRE_BASE_DATOS_TRABAJADOR = "trabajadores.txt";
    public static final String NOMBRE_BASE_DATOS_BANYO = "banyos.txt";
    
    public static final String NOMBRE_CARPETA = "LaBaseDeDatos"; 
    public static final String NOMBRE_CARPETA_IMAGENES_EXTERNAS 
            = "ImagenesAlmacenadas";

    public static final String SEPARADOR_COLUMNAS = ";";
    public static final String SEPARADOR_CARPETAS = File.separator;

    public static final String NOMBRE_GENERO = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_GENEROS;

    public static final String NOMBRE_PELICULA = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_PELICULAS;

    public static final String NOMBRE_SALA = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_SALAS;

    public static final String NOMBRE_VENTA = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_VENTA;
    
    public static final String NOMBRE_SEDE = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_SEDE;
    
    public static final String NOMBRE_PRODUCTO = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_PRODUCTO;
    
    public static final String NOMBRE_CLIENTE = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_CLIENTE;
    
    public static final String NOMBRE_TRABAJADOR = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_TRABAJADOR;
    
    public static final String NOMBRE_BANYO = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA
            + SEPARADOR_CARPETAS + NOMBRE_BASE_DATOS_BANYO;

    public static final String RUTA_IMAGENES_INTERNAS 
            = "/com/unimagdalena/recurso/imagenes/";
    public static final String RUTA_IMAGENES_EXTERNAS = RUTA_PROYECTO
            + SEPARADOR_CARPETAS + NOMBRE_CARPETA_IMAGENES_EXTERNAS;

}
