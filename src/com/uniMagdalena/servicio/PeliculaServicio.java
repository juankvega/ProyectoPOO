 package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.model.Genero;
import com.uniMagdalena.model.Pelicula;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PeliculaServicio implements ApiOperacionBD<PeliculaDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public PeliculaServicio() {
        try {
            nombrePersistencia = Persistencia.NOMBRE_PELICULA;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public PeliculaDto insertInto(PeliculaDto dto, String ruta) {
        Genero objGenero = new Genero(
                dto.getIdGeneroPelicula().getIdGenero(),
                dto.getIdGeneroPelicula().getNombreGenero(),
                dto.getIdGeneroPelicula().getEstadoGenero(),
                dto.getIdGeneroPelicula().getPopularidadGenero(),
                dto.getIdGeneroPelicula().getEsClasicoGenero(),
                null, "", "");

        Pelicula objPelicula = new Pelicula();
        objPelicula.setIdPelicula(getSerial());
        objPelicula.setNombrePelicula(dto.getNombrePelicula());
        objPelicula.setIdGeneroPelicula(objGenero);
        objPelicula.setActorPPelicula(dto.getActorPPelicula());
        objPelicula.setPresupuestoPelicula(dto.getPresupuestoPelicula());
        objPelicula.setEsParaNinyosPelicula(dto.getEsParaNinyosPelicula());
        objPelicula.setNombreImagenPublicoPelicula(dto.getNombreImagenPublicoPelicula());
        objPelicula.setNombreImagenPrivadoPelicula(GestorImagen.grabarLaImagen(ruta));

        BigDecimal valorListo = new BigDecimal(String.valueOf(objPelicula.getPresupuestoPelicula()));

        String cadena = objPelicula.getIdPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getNombrePelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getIdGeneroPelicula().getIdGenero() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getActorPPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getEsParaNinyosPelicula()+ Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getNombreImagenPublicoPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objPelicula.getNombreImagenPrivadoPelicula();

        if (miArchivo.agregarRegistro(cadena)) {
            dto.setIdPelicula(objPelicula.getIdPelicula());
            return dto;
        }
        return null;
    }

    @Override
    public List<PeliculaDto> selectFrom() {
        List<PeliculaDto> arregloPelicula = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        GeneroServicio generoServicio = new GeneroServicio();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codPelicula = Integer.parseInt(columnas[0].trim());
                String nomPelicula = columnas[1].trim();
                int idGenero = Integer.parseInt(columnas[2].trim());
                String actPelicula = columnas[3].trim();
                double presPelicula = Double.parseDouble(columnas[4].trim());
                Boolean adultosPelicula = Boolean.valueOf(columnas[5].trim());
                String nomImagenPeliculaP = columnas[6].trim();
                String nomImagenPeliculaPv = columnas[7].trim();

                // Alvaro eb bárbaro: Ciclo for dentro de for
                // Mala práctica
                GeneroDto generoDto = buscarGeneroPorId(generoServicio, idGenero);
                // *************************************************************

                if (generoDto != null) {
                    arregloPelicula.add(new PeliculaDto(codPelicula, nomPelicula, generoDto, actPelicula, presPelicula, adultosPelicula, nomImagenPeliculaP, nomImagenPeliculaPv));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }

        return arregloPelicula;
    }

    // Esta es la trampa más eficiente para sacar la cantidad de hijos de un papá
    // En otras palabras la cantidad de películas de un género
    // *************************************************************************
    public Map<Integer, Integer> selectFromCantidad() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idGenero)
                int idGenero = Integer.parseInt(columnas[2].trim());
                // Y el trucazo es que cuento los idGenero, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(idGenero, arrCantidades.getOrDefault(idGenero, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }
    // *************************************************************************

    // Esto es una MALA práctica, si, funciona pero no se debe hacer así.
    // Si lo tienes difeferente a esto mejor, me lloró el ojo al ver esto. 
    private GeneroDto buscarGeneroPorId(GeneroServicio generoServicio, int idGenero) {
        List<GeneroDto> todosLosGeneros = generoServicio.selectFrom();

        for (GeneroDto genero : todosLosGeneros) {
            if (genero.getIdGenero() == idGenero) {
                return genero;
            }
        }
        return null;
    }
    // *************************************************************************

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(PeliculaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
       
        Boolean correcto = false;
        try {
            List<String> arregloDeDatos;
            arregloDeDatos = miArchivo.borrarFilaPosicion(codigo);
            if (!arregloDeDatos.isEmpty()) {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }



    @Override
    public PeliculaDto getOne(Integer codigo) 
    {
      int contador;
      PeliculaDto objListo;
      
      contador = 0;
      objListo = new PeliculaDto();
      List<PeliculaDto> arrPeliculas = selectFrom();
        for (PeliculaDto objPel : arrPeliculas) 
        {
            if(contador == codigo)
            {
                objListo = objPel;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public Boolean updateSet(Integer indice, PeliculaDto obj, String ruta) {
        boolean correcto = false;
        try
        {
            String cadena, nocu;
            List<String> arregloDeDatos;
            
            BigDecimal valorListo = new BigDecimal(String.valueOf(obj.getPresupuestoPelicula()));
            
            cadena = obj.getIdPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getNombrePelicula() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getIdGeneroPelicula().getIdGenero() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getActorPPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + obj.getEsParaNinyosPelicula()+ Persistencia.SEPARADOR_COLUMNAS
                + obj.getNombreImagenPublicoPelicula() + Persistencia.SEPARADOR_COLUMNAS;
            if(ruta.isBlank())
            {
                cadena = cadena + obj.getNombreImagenPrivadoPelicula();
            }else
            {
                nocu = GestorImagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;
                arregloDeDatos = miArchivo.borrarFilaPosicion(indice);
                if(!arregloDeDatos.isEmpty())
                {
                    String nomOculto = arregloDeDatos.get(arregloDeDatos.size()-1);
                    String nombreBorrar = Persistencia.RUTA_IMAGENES_EXTERNAS + "\\"+ nomOculto;
                    Path rutaBorrar = Paths.get(nombreBorrar);
                    Files.deleteIfExists(rutaBorrar);
                }
            }
            if(miArchivo.actualizaFilaPosicion(indice, cadena))
            {
                correcto = true;
            }
            
        }
        catch(IOException ex)
        {
           Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

}
