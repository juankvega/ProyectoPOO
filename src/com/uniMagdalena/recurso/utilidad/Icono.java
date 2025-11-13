package com.uniMagdalena.recurso.utilidad;

import com.uniMagdalena.recurso.constante.Persistencia;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Icono {
    
    public static ImageView obtenerIcono(String nombre, Integer tamanio) {
        String miRuta = Persistencia.RUTA_IMAGENES_INTERNAS + nombre;
        Image iconito = new Image(miRuta);
        ImageView vistaIconito = new ImageView(iconito);
        if (tamanio > 0) {
            vistaIconito.setFitHeight(tamanio);
        }
        vistaIconito.setPreserveRatio(true);
        vistaIconito.setSmooth(true);
        return vistaIconito;
    }
    public static ImageView previsualizar(String rutaImagen, int dimensionMaxima) {
        ImageView imgMostrar = null;

        try (FileInputStream archivo = new FileInputStream(rutaImagen)) {
            Image imgBasica = new Image(archivo);
            imgMostrar = new ImageView(imgBasica);

            double ancho = imgBasica.getWidth();
            double alto = imgBasica.getHeight();

            if (ancho > alto) {
                imgMostrar.setFitWidth(dimensionMaxima);
            } else {
                imgMostrar.setFitHeight(dimensionMaxima);
            }

            imgMostrar.setPreserveRatio(true);
            imgMostrar.setSmooth(true);

        } catch (IOException miError) {
            System.out.println("Error al cargar la foto externa: " + miError.getMessage());
        }

        return imgMostrar;
    }
    
}
