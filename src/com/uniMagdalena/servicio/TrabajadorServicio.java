
package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.model.Trabajador;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrabajadorServicio implements ApiOperacionBD<TrabajadorDto, Integer>
{
    private NioFile miArchivo;
    private String nombrePersistencia;
    
    public TrabajadorServicio()
    {
        
        try {
            nombrePersistencia = Persistencia.NOMBRE_TRABAJADOR;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(TrabajadorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(TrabajadorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public TrabajadorDto insertInto(TrabajadorDto dto, String ruta) {
        Trabajador objTrabajador = new Trabajador();
        objTrabajador.setIdTrabajador(getSerial());
        objTrabajador.setNombreTrabajador(dto.getNombreTrabajador());
        objTrabajador.setGeneroTrabajador(dto.getGeneroTrabajador());
        objTrabajador.setTipoDocumentoTrabajador(dto.getTipoDocumentoTrabajador());
        objTrabajador.setNumDocumentoTrabajador(dto.getNumDocumentoTrabajador());
        objTrabajador.setTipoTrabajador(dto.getTipoTrabajador());
        objTrabajador.setNombreImagenPublicoTrabajador(dto.getNombreImagenPublicoTrabajador());
        objTrabajador.setNombreImagenPrivadoTrabajador(GestorImagen.grabarLaImagen(ruta));
        
        BigInteger valorListo = new BigInteger(String.valueOf (objTrabajador.getNumDocumentoTrabajador()));
                
        
        String cadena = objTrabajador.getIdTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getNombreTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getGeneroTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getTipoDocumentoTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getTipoTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getNombreImagenPublicoTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getNombreImagenPrivadoTrabajador();
        
        if (miArchivo.agregarRegistro(cadena)) {
            dto.setIdTrabajador(objTrabajador.getIdTrabajador());
            return dto;
        }
        return null;
    }

    @Override
    public List<TrabajadorDto> selectFrom() 
    {
       List<TrabajadorDto> arregloTrabajadores = new ArrayList<>();
        List <String> arregloDatos = miArchivo.obtenerDatos();
        
        for (String cadena : arregloDatos) 
        {
            try{
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                
                int codTrabajador = Integer.parseInt(columnas[0].trim());
                String nomTrabajador = columnas[1].trim();
                Boolean genTrabajador = Boolean.valueOf(columnas[2].trim());
                String docTrabajador = columnas[3].trim();
                int numTrabajador = Integer.parseInt(columnas[4].trim());
                String tipoTrabajador = columnas[5].trim();
                String nomImagenTrabajadorP = columnas[6].trim();
                String nomImagenTrabajadorPv = columnas[7].trim();
                
                arregloTrabajadores.add(new TrabajadorDto(codTrabajador, nomTrabajador, genTrabajador, docTrabajador, numTrabajador, tipoTrabajador, nomImagenTrabajadorP, nomImagenTrabajadorPv));
                
            }
        catch(NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }
        return arregloTrabajadores; 
    }
    
    public List<TrabajadorDto> selectFromWhereTrabajadorAseo()
    {
       List<TrabajadorDto> arregloTrabajadores = new ArrayList<>();
        List <String> arregloDatos = miArchivo.obtenerDatos();
        
        for (String cadena : arregloDatos) 
        {
            try{
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                
                int codTrabajador = Integer.parseInt(columnas[0].trim());
                String nomTrabajador = columnas[1].trim();
                Boolean genTrabajador = Boolean.valueOf(columnas[2].trim());
                String docTrabajador = columnas[3].trim();
                int numTrabajador = Integer.parseInt(columnas[4].trim());
                String tipoTrabajador = columnas[5].trim();              
                
                if("Aseo".equals(tipoTrabajador))
                {
                arregloTrabajadores.add(new TrabajadorDto(codTrabajador, nomTrabajador, genTrabajador, docTrabajador, numTrabajador, tipoTrabajador, "", ""));
                }
            }
        catch(NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }
        return arregloTrabajadores; 
     
    }

    @Override
    public int numRows() 
    {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(TrabajadorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    @Override
    public Boolean deleteFrom(Integer codigo)
    {
        Boolean correcto = false; 
        try {
            List<String> arregloDeDatos;
            arregloDeDatos = miArchivo.borrarFilaPosicion(codigo);
            if(!arregloDeDatos.isEmpty())
            {
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(TrabajadorServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Boolean updateSet(Integer codigo, TrabajadorDto objTrabajador, String ruta)
    {
        boolean correcto = false;
        try
        {
            String cadena, nocu;
            List<String> arregloDeDatos;
            
            BigInteger valorListo = new BigInteger(String.valueOf (objTrabajador.getNumDocumentoTrabajador()));
                
        
        cadena = objTrabajador.getIdTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getNombreTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getGeneroTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getTipoDocumentoTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getTipoTrabajador() + Persistencia.SEPARADOR_COLUMNAS
                + objTrabajador.getNombreImagenPublicoTrabajador() + Persistencia.SEPARADOR_COLUMNAS;
        
        if(ruta.isBlank())
        {
            cadena = cadena + objTrabajador.getNombreImagenPrivadoTrabajador();
        }else{
             nocu = GestorImagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;
                arregloDeDatos = miArchivo.borrarFilaPosicion(codigo);
                if(!arregloDeDatos.isEmpty())
                {
                    String nomOculto = arregloDeDatos.get(arregloDeDatos.size()-1);
                    String nombreBorrar = Persistencia.RUTA_IMAGENES_EXTERNAS + "\\"+ nomOculto;
                    Path rutaBorrar = Paths.get(nombreBorrar);
                    Files.deleteIfExists(rutaBorrar);
                }
        }
        if(miArchivo.actualizaFilaPosicion(codigo, cadena))
        {
            correcto = true;
        }
        }catch(IOException ex){
            System.out.println("Error al analizar el archivo: " + ex.getMessage());
        }
        return correcto;
    }

    @Override
    public TrabajadorDto getOne(Integer codigo) 
    {
        int contador;
        TrabajadorDto objListo;
        contador = 0;
        objListo = new TrabajadorDto();
        List<TrabajadorDto> arrTrabajadores = selectFrom();
        for (TrabajadorDto objTrabajador : arrTrabajadores) 
        {
            if(contador == codigo)
            {
                objListo = objTrabajador;
            }
            contador++;
        }
        return objListo;
    }
}
