package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.GeneroDto;
import com.uniMagdalena.model.Genero;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneroServicio implements ApiOperacionBD<GeneroDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public GeneroServicio() {
        try {
            nombrePersistencia = Persistencia.NOMBRE_GENERO;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public GeneroDto insertInto(GeneroDto dto, String ruta) {
        Genero ObjGenero = new Genero();
        ObjGenero.setIdGenero(getSerial());
        ObjGenero.setNombreGenero(dto.getNombreGenero());
        ObjGenero.setEstadoGenero(dto.getEstadoGenero());
        ObjGenero.setPopularidadGenero(dto.getPopularidadGenero());
        ObjGenero.setEsClasicoGenero(dto.getEsClasicoGenero());
        ObjGenero.setNombreImagenPublicoGenero(dto.getNombreImagenPublicoGenero());
        ObjGenero.setNombreImagenPrivadoGenero(GestorImagen.grabarLaImagen(ruta));

        String cadena = ObjGenero.getIdGenero() + Persistencia.SEPARADOR_COLUMNAS
                + ObjGenero.getNombreGenero() + Persistencia.SEPARADOR_COLUMNAS
                + ObjGenero.getEstadoGenero() + Persistencia.SEPARADOR_COLUMNAS
                + ObjGenero.getPopularidadGenero() + Persistencia.SEPARADOR_COLUMNAS
                + ObjGenero.getEsClasicoGenero() + Persistencia.SEPARADOR_COLUMNAS
                + ObjGenero.getNombreImagenPublicoGenero() + Persistencia.SEPARADOR_COLUMNAS
                + ObjGenero.getNombreImagenPrivadoGenero();

        if (miArchivo.agregarRegistro(cadena)) {
            dto.setIdGenero(ObjGenero.getIdGenero());
            return dto;
        }

        return null;
    }

    @Override
    public List<GeneroDto> selectFrom() {
        List<GeneroDto> arregloGenero = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        // Un servicio puede llamar a otro servicio
        // *********************************************************************
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        Map<Integer, Integer> arrCantPelis = peliculaServicio.selectFromCantidad();
        // *********************************************************************

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codGenero = Integer.parseInt(columnas[0].trim());
                String nomGenero = columnas[1].trim();
                Boolean estGenero = Boolean.valueOf(columnas[2].trim());
                Short popGenero = Short.valueOf(columnas[3].trim());
                Boolean clasGenero = Boolean.valueOf(columnas[4].trim());
                String nomImagenGeneroP = columnas[5].trim();
                String nomImagenGeneroPv = columnas[6].trim();

                // Y en la siguiente linea resolvemos lo de la cantidad de peliculas de un género
                // Breve y pulido, recuerda: eres uniMagdalena
                Short cantPelis = arrCantPelis.getOrDefault(codGenero, 0).shortValue();

                arregloGenero.add(new GeneroDto(codGenero, nomGenero, estGenero,popGenero,clasGenero ,cantPelis, nomImagenGeneroP, nomImagenGeneroPv));

            } catch (NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }
        return arregloGenero;
    }
    // *********************************************************************

    public List<GeneroDto> selectFromWhereEstadoTrue() {
        List<GeneroDto> arregloGenero = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codGenero = Integer.parseInt(columnas[0].trim());
                String nomGenero = columnas[1].trim();
                Boolean estGenero = Boolean.valueOf(columnas[2].trim());
                Short popGenero = Short.valueOf(columnas[3].trim());
                Boolean clasGenero = Boolean.valueOf(columnas[4].trim());
                if (Boolean.TRUE.equals(estGenero)) {
                    arregloGenero.add(new GeneroDto(codGenero, nomGenero, estGenero,popGenero, clasGenero,null, "", ""));
                }

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
        return arregloGenero;
    }

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo) {
        Boolean correcto = false;
        try {
        List<String> arregloDeDatos;
            arregloDeDatos = miArchivo.borrarFilaPosicion(codigo);
            if(!arregloDeDatos.isEmpty())
            {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Boolean updateSet(Integer indice, GeneroDto obj, String ruta) 
    {
        boolean correcto = false;
        try{
            String cadena, nocu;
            List <String> arregloDeDatos;
            cadena = obj.getIdGenero() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getNombreGenero() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getEstadoGenero() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getPopularidadGenero() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getEsClasicoGenero() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getNombreImagenPublicoGenero() + Persistencia.SEPARADOR_COLUMNAS;
        
            if(ruta.isBlank())
            {
                cadena = cadena + obj.getNombreImagenPrivadoGenero();
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
        }catch(IOException ex)
        {
           Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }
    
    @Override
    public GeneroDto getOne(Integer codigo)
    {
        int contador;
        GeneroDto objListo;
        
        contador = 0;
        objListo = new GeneroDto();
        List<GeneroDto> arrGeneros = selectFrom();
        
        for(GeneroDto objGen: arrGeneros)
        {
            if(contador == codigo)
            {
                objListo = objGen;
            }
            contador++;
        }
        return objListo;
    }

    

}
