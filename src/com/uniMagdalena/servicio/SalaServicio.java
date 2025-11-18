
package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.model.Sala;
import com.uniMagdalena.model.Sede;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalaServicio implements ApiOperacionBD<SalaDto, Integer>
{
    
    private NioFile miArchivo;
    private String nombrePersistencia;

    public SalaServicio() 
    {
        try 
        {
            nombrePersistencia = Persistencia.NOMBRE_SALA;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex)
        {
            Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public int getSerial() 
    {
        int codigo = 0;
        try
        {
            codigo = miArchivo.ultimoCodigo() + 1;
        }
        catch(IOException ex)
        {
          Logger.getLogger(GeneroServicio.class.getName()).log(Level.SEVERE, null, ex);  
        }
        return codigo;
    }

    @Override
    public SalaDto insertInto(SalaDto dto, String ruta) 
    {
        
        Sede objSede = new Sede(dto.getSedeSala().getIdSede(), dto.getSedeSala().getNombreSede(), dto.getSedeSala().getCiudadSede(), dto.getSedeSala().getUbicacionSede(), dto.getSedeSala().getEs24horasSede(), null, "", "");
        
        
        Sala objSala = new Sala();
        objSala.setIdSala(getSerial());
        objSala.setNombreSala(dto.getNombreSala());
        objSala.setAsientosSala(dto.getAsientosSala());
        objSala.setSala4d(dto.getSala4d());
        objSala.setSedeSala(objSede);
        objSala.setNombreImagenPublicoSala(dto.getNombreImagenPublicoSala());
        objSala.setNombreImagenPrivadoSala(GestorImagen.grabarLaImagen(ruta));
        
        String cadena = objSala.getIdSala() + Persistencia.SEPARADOR_COLUMNAS
                + objSala.getNombreSala() + Persistencia.SEPARADOR_COLUMNAS
                + objSala.getAsientosSala() + Persistencia.SEPARADOR_COLUMNAS
                + objSala.getSala4d() + Persistencia.SEPARADOR_COLUMNAS
                + objSala.getSedeSala().getIdSede() + Persistencia.SEPARADOR_COLUMNAS
                + objSala.getNombreImagenPublicoSala() + Persistencia.SEPARADOR_COLUMNAS
                + objSala.getNombreImagenPrivadoSala();
        
        if(miArchivo.agregarRegistro(cadena))
        {
            dto.setIdSala(objSala.getIdSala());
            return dto;
        }
        
        
        
        return null;
    }

    @Override
    public List<SalaDto> selectFrom() 
    {
        List<SalaDto> arregloSala = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();
        
        SedeServicio sedeServicio = new SedeServicio();
        
        for(String cadena: arregloDatos)
        {
            try
            {    
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
            
                int codSala = Integer.parseInt(columnas[0].trim());
                String nomSala = columnas[1].trim();
                int assSala = Integer.parseInt(columnas[2].trim());
                Boolean es4dSala = Boolean.valueOf(columnas[3].trim());
                int idSede = Integer.parseInt(columnas[4].trim());
                String nomImagenSedeP = columnas[5].trim();
                String nomImagenSedePv = columnas[6].trim();
                
                
                
                SedeDto sedeDto = buscarSedePorID(sedeServicio, idSede);
                
                if(sedeDto != null){
                arregloSala.add(new SalaDto(codSala, nomSala, assSala, es4dSala, sedeDto, nomImagenSedeP, nomImagenSedePv ));
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println(e.getMessage());
            }
            
            
        }
        
        return arregloSala;
    }
    
    public Map<Integer, Integer> selectFromCantidad()
    {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();
        
        for (String cadena : arregloDatos) 
        {
            try{
                
            cadena = cadena.replace("@", "");
            String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
            int idSede = Integer.parseInt(columnas[4].trim());
            arrCantidades.put(idSede, arrCantidades.getOrDefault(idSede, 0) + 1);
            
            } catch(NumberFormatException error)
            {
               Logger.getLogger(SalaServicio.class.getName()).log(Level.SEVERE, null, error); 
            }
        }
        return arrCantidades;
    }
    
    
    private SedeDto buscarSedePorID(SedeServicio sedeServicio, int idSede)
    {
        List<SedeDto> todasLasSedes = sedeServicio.selectFrom();
        
        for (SedeDto sede : todasLasSedes) 
        {
            if(sede.getIdSede() == idSede)
            {
                return sede;
            }
        }
        return null;
    }
    


    @Override
    public int numRows() 
    {
        int cantidad = 0;
        
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) 
        {
            
            System.out.println(ex.getMessage());
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
            Logger.getLogger(SalaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }


    @Override
    public SalaDto getOne(Integer codigo)
    {
       int contador;
       SalaDto objListo;
       
       contador = 0;
       objListo = new SalaDto();
       List<SalaDto> arrSalas = selectFrom();
        for (SalaDto objSala : arrSalas) 
        {
            if(contador == codigo)
            {
                objListo = objSala;
            }
            contador++;
        }
        return objListo;
    }

    @Override
    public Boolean updateSet(Integer codigo, SalaDto obj, String ruta) 
    {
        boolean correcto = false;
    try{    
        String cadena, nocu;
        List<String> arregloDatos; 
        
        cadena = obj.getIdSala() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getNombreSala() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getAsientosSala() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getSala4d() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getSedeSala().getIdSede() + Persistencia.SEPARADOR_COLUMNAS
                + obj.getNombreImagenPublicoSala() + Persistencia.SEPARADOR_COLUMNAS;
        if(ruta.isBlank())
            {
                cadena = cadena + obj.getNombreImagenPrivadoSala();
            }else
            {
                nocu = GestorImagen.grabarLaImagen(ruta);
                cadena = cadena + nocu;
                arregloDatos = miArchivo.borrarFilaPosicion(codigo);
                if(!arregloDatos.isEmpty())
                {
                    String nomOculto = arregloDatos.get(arregloDatos.size()-1);
                    String nombreBorrar = Persistencia.RUTA_IMAGENES_EXTERNAS + "\\"+ nomOculto;
                    Path rutaBorrar = Paths.get(nombreBorrar);
                    Files.deleteIfExists(rutaBorrar);
                }
            }
        if(miArchivo.actualizaFilaPosicion(codigo, cadena))
        {
            correcto = true;
        } 
    
    }catch(IOException ex)
    {
        Logger.getLogger(SalaServicio.class.getName()).log(Level.SEVERE, null, ex);
    }
        return correcto;
    }
    
}
