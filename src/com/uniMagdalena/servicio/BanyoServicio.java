package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.BanyoDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.dto.TrabajadorDto;
import com.uniMagdalena.model.Banyo;
import com.uniMagdalena.model.Sede;
import com.uniMagdalena.model.Trabajador;
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

public class BanyoServicio implements ApiOperacionBD<BanyoDto, Integer>{
    
    private NioFile miArchivo;
    private String nombrePersistencia;

    public BanyoServicio() {
        try {
            nombrePersistencia = Persistencia.NOMBRE_BANYO;
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
    public BanyoDto insertInto(BanyoDto dto, String ruta) {

        Sede objSede = new Sede(
        dto.getSedeBanyo().getIdSede(),
        dto.getSedeBanyo().getNombreSede(),
        dto.getSedeBanyo().getCiudadSede(),
        dto.getSedeBanyo().getUbicacionSede(),
        dto.getSedeBanyo().getEs24horasSede(),
        null, null,        // ojo aqui
        "","");
        
        Trabajador encargadoBanyo = new Trabajador(dto.getEncargadoBanyo().getIdTrabajador(), dto.getEncargadoBanyo().getNombreTrabajador()
                , dto.getEncargadoBanyo().getGeneroTrabajador(), dto.getEncargadoBanyo().getTipoDocumentoTrabajador(), dto.getEncargadoBanyo().getNumDocumentoTrabajador(), dto.getEncargadoBanyo().getTipoTrabajador(),null, "", "");

        
        
        Banyo objBanyo = new Banyo();
        objBanyo.setIdBanyo(getSerial());
        objBanyo.setSedeBanyo(objSede);
        objBanyo.setUbicacionBanyo(dto.getUbicacionBanyo());
        objBanyo.setEncargadoBanyo(encargadoBanyo);
        objBanyo.setUsoBanyo(dto.getUsoBanyo());
        objBanyo.setGeneroBanyo(dto.getGeneroBanyo());
        objBanyo.setNombreImagenPublicoBanyo(dto.getNombreImagenPublicoBanyo());
        objBanyo.setNombreImagenPrivadoBanyo(GestorImagen.grabarLaImagen(ruta));
        
        // Construir la cadena de datos para guardar
    String cadena = objBanyo.getIdBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getSedeBanyo().getIdSede() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getUbicacionBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getGeneroBanyo()+ Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getUsoBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getEncargadoBanyo().getIdTrabajador() +Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getNombreImagenPublicoBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getNombreImagenPrivadoBanyo();
    
    // Guardar el registro en el archivo
    if (miArchivo.agregarRegistro(cadena)) {
        dto.setIdBanyo(objBanyo.getIdBanyo());
        return dto;
    }
    
    return null;
    }

    @Override
    public List<BanyoDto> selectFrom() 
    {
        List<BanyoDto> arregloBanyo = new ArrayList<>();
        List <String> arregloDatos = miArchivo.obtenerDatos();
        
        TrabajadorServicio trabajadorServicio = new TrabajadorServicio();
        SedeServicio sedeServicio = new SedeServicio();
        
        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codBanyo = Integer.parseInt(columnas[0].trim());
                int idSede = Integer.parseInt(columnas[1].trim());
                String ubiBanyo = columnas[2].trim();
                Boolean generoBanyo = Boolean.valueOf(columnas[3].trim());
                Boolean usoBanyo = Boolean.valueOf(columnas[4].trim());
                int idTrabajador = Integer.parseInt(columnas[5].trim());
                String nomImagenBanyoP = columnas[6].trim();
                String nomImagenBanyoPv = columnas[7].trim();

                // Alvaro eb bárbaro: Ciclo for dentro de for
                // Mala práctica
                TrabajadorDto trabajadorDto = buscarTrabajadorAseoPorId(trabajadorServicio, idTrabajador);
                SedeDto sedeDto = buscarSedePorId(sedeServicio, idSede);
                                // *************************************************************

                if (trabajadorDto != null && sedeDto != null) {
                    arregloBanyo.add(new BanyoDto(codBanyo, sedeDto, ubiBanyo, generoBanyo, usoBanyo, trabajadorDto, nomImagenBanyoP, nomImagenBanyoPv));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }

        return arregloBanyo;
    }
    
    
     private TrabajadorDto buscarTrabajadorAseoPorId(TrabajadorServicio trabajadorServicio, int idTrabajador) {
        List<TrabajadorDto> todosLosTrabajadors = trabajadorServicio.selectFromWhereTrabajadorAseo();

        for (TrabajadorDto trabajador : todosLosTrabajadors) {
            if (trabajador.getIdTrabajador() == idTrabajador) {
                return trabajador;
            }
        }
        return null;
    }
     
        private SedeDto buscarSedePorId(SedeServicio sedeServicio, int idSede) {
        List<SedeDto> todosLosSedes = sedeServicio.selectFrom();

        for (SedeDto sede : todosLosSedes) {
            if (sede.getIdSede() == idSede) {
                return sede;
            }
        }
        return null;
    }
    
    
    
    
     public Map<Integer, Integer> selectFromCantidadSedes() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idSede)
                int idSede = Integer.parseInt(columnas[1].trim());
                // Y el trucazo es que cuento los idSede, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(idSede, arrCantidades.getOrDefault(idSede, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(BanyoServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }
     
     public Map<Integer, Integer> selectFromCantidadTrabajadorAseo() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idTrabajador)
                int idTrabajador= Integer.parseInt(columnas[5].trim());
                // Y el trucazo es que cuento los idTrabajador, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(idTrabajador, arrCantidades.getOrDefault(idTrabajador, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(BanyoServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }
    
    

    @Override
    public int numRows() {
        int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(BanyoServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BanyoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Boolean updateSet(Integer codigo, BanyoDto objBanyo, String ruta) 
    {
        boolean correcto = false;
    try
    {
        String cadena, nocu;
        List<String> arregloDeDatos;
        
        cadena = objBanyo.getIdBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getSedeBanyo().getIdSede() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getUbicacionBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getGeneroBanyo()+ Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getUsoBanyo() + Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getEncargadoBanyo().getIdTrabajador() +Persistencia.SEPARADOR_COLUMNAS
            + objBanyo.getNombreImagenPublicoBanyo() + Persistencia.SEPARADOR_COLUMNAS;
        if(ruta.isBlank())
            {
                cadena = cadena + objBanyo.getNombreImagenPrivadoBanyo();
            }else
            {
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
       Logger.getLogger(BanyoServicio.class.getName()).log(Level.SEVERE, null, ex); 
    }
    return correcto;
  }

    @Override
    public BanyoDto getOne(Integer codigo)
    {
        int contador;
      BanyoDto objListo;
      
      contador = 0;
      objListo = new BanyoDto();
      List<BanyoDto> arrBanyos = selectFrom();
        for (BanyoDto objPel : arrBanyos) 
        {
            if(contador == codigo)
            {
                objListo = objPel;
            }
            contador++;
        }
        return objListo;
    }
    
}


