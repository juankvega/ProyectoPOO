package com.uniMagdalena.servicio;



import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.model.Sede;
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


public class SedeServicio implements ApiOperacionBD<SedeDto, Integer>{

    private NioFile miArchivo;
    private String nombrePersistencia;

    public SedeServicio() {
        try {
            nombrePersistencia = Persistencia.NOMBRE_SEDE;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(SedeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    

    @Override
    public int getSerial() {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(SedeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;

    }

    @Override
    public SedeDto insertInto(SedeDto dto, String ruta) {
        Sede ObjSede = new Sede();
        ObjSede.setIdSede(getSerial());
        ObjSede.setNombreSede(dto.getNombreSede());
        ObjSede.setCiudadSede(dto.getCiudadSede());
        ObjSede.setUbicacionSede(dto.getUbicacionSede());
        ObjSede.setEs24horasSede(dto.getEs24horasSede());
        ObjSede.setNombreImagenPublicoSede(dto.getNombreImagenPublicoSede());
        ObjSede.setNombreImagenPrivadoSede(GestorImagen.grabarLaImagen(ruta));
        
        String cadena = ObjSede.getIdSede() + Persistencia.SEPARADOR_COLUMNAS
        + ObjSede.getNombreSede() + Persistencia.SEPARADOR_COLUMNAS
        + ObjSede.getCiudadSede() + Persistencia.SEPARADOR_COLUMNAS
        + ObjSede.getUbicacionSede() + Persistencia.SEPARADOR_COLUMNAS
        + ObjSede.getEs24horasSede() + Persistencia.SEPARADOR_COLUMNAS
        + ObjSede.getNombreImagenPublicoSede() + Persistencia.SEPARADOR_COLUMNAS
        + ObjSede.getNombreImagenPrivadoSede() + Persistencia.SEPARADOR_COLUMNAS;
        
        if (miArchivo.agregarRegistro(cadena)) {
            dto.setIdSede(ObjSede.getIdSede());
            return dto;
        }

        return null;
    }

    @Override
    public List<SedeDto> selectFrom() {
        List<SedeDto> arregloSede = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();
        
        // Un servicio puede llamar a otro servicio
        // *********************************************************************
        // Aqui llama a SalaServicio (en proceso) para asignar la cantidad de
        // salas que tendria cada sede
        SalaServicio salaServicio = new SalaServicio();
        Map<Integer, Integer> arrCantSalas = salaServicio.selectFromCantidad();
        //*********************************************************************
        
        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                
                Integer codSede = Integer.parseInt(columnas[0].trim());
                String nomSede = columnas[1].trim();
                String ciudadSede  = columnas[2].trim();
                String ubiSede = columnas[3].trim();
                Boolean veinticuatroHorasSede = Boolean.valueOf(columnas[4].trim());
                String nomImagenSedePub = columnas[5].trim();
                String nomImagenSedePriv = columnas[6].trim();
                
                // Y en la siguiente linea resolvemos lo de la cantidad de salas de una sede
                // Breve y pulido, recuerda: eres uniMagdalena
                Short cantSalas = arrCantSalas.getOrDefault(codSede, 0).shortValue();
                
                arregloSede.add(new SedeDto(codSede, nomSede, ciudadSede, ubiSede
                        , veinticuatroHorasSede, cantSalas, nomImagenSedePub, nomImagenSedePriv));
                
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
            
        }
        return arregloSede;
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
        List<String> arregloDeDatos;
        try {          
            arregloDeDatos = miArchivo.borrarFilaPosicion(codigo);
            if (!arregloDeDatos.isEmpty()) 
            {
                String nocu = arregloDeDatos.get(arregloDeDatos.size() - 1);
                String nombreBorrar = Persistencia.RUTA_IMAGENES_EXTERNAS + Persistencia.SEPARADOR_CARPETAS + nocu;
                Path rutaBorrar = Paths.get(nombreBorrar);
                Files.deleteIfExists(rutaBorrar);
                
                correcto = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(SedeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;

    }

    @Override
    public Boolean updateSet(Integer codigo, SedeDto obj, String ruta) {
        boolean correcto = false;
        try {
            String cadena, nocu;
            List<String> arregloDeDatos;
            
            cadena = obj.getIdSede() + Persistencia.SEPARADOR_COLUMNAS
            + obj.getNombreSede() + Persistencia.SEPARADOR_COLUMNAS
            + obj.getCiudadSede() + Persistencia.SEPARADOR_COLUMNAS
            + obj.getUbicacionSede() + Persistencia.SEPARADOR_COLUMNAS
            + obj.getEs24horasSede() + Persistencia.SEPARADOR_COLUMNAS
            + obj.getNombreImagenPublicoSede() + Persistencia.SEPARADOR_COLUMNAS
            + obj.getNombreImagenPrivadoSede() + Persistencia.SEPARADOR_COLUMNAS;
            
            if (ruta.isBlank()) {
                
                cadena = cadena + obj.getNombreImagenPrivadoSede();
            } else {
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
                if (miArchivo.actualizaFilaPosicion(codigo, cadena)) {
                    correcto = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SedeServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public SedeDto getOne(Integer codigo) {
        int contador;
        SedeDto objListo;
        
        contador = 0;
        objListo = new SedeDto();
        List<SedeDto> arrSedes = selectFrom();
        
        for(SedeDto objSede: arrSedes)
        {
            if(contador == codigo)
            {
                objListo = objSede;
            }
            contador++;
        }
        return objListo;
    }
    
}
