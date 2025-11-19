package com.uniMagdalena.servicio;
import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.model.Producto;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoServicio implements ApiOperacionBD<ProductoDto, Integer> {

    private NioFile miArchivo;
    private String nombrePersistencia;

    public ProductoServicio() {
        try {
            nombrePersistencia = Persistencia.NOMBRE_PRODUCTO;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getSerial() {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public ProductoDto insertInto(ProductoDto dto, String ruta) {
        Producto ObjProducto = new Producto();
        ObjProducto.setIdProducto(getSerial());
        ObjProducto.setNombreProducto(dto.getNombreProducto());
        ObjProducto.setTipoProducto(dto.getTipoProducto());
        ObjProducto.setTamanioProducto(dto.getTamanioProducto());
        ObjProducto.setPrecioProducto(dto.getPrecioProducto());
        ObjProducto.setNombreImagenPublicoProducto(dto.getNombreImagenPublicoProducto());
        ObjProducto.setNombreImagenPrivadoProducto(GestorImagen.grabarLaImagen(ruta));

        String cadena = ObjProducto.getIdProducto() + Persistencia.SEPARADOR_COLUMNAS
                + ObjProducto.getNombreProducto() + Persistencia.SEPARADOR_COLUMNAS
                + ObjProducto.getTipoProducto() + Persistencia.SEPARADOR_COLUMNAS
                + ObjProducto.getTamanioProducto() + Persistencia.SEPARADOR_COLUMNAS
                + ObjProducto.getPrecioProducto() + Persistencia.SEPARADOR_COLUMNAS
                + ObjProducto.getNombreImagenPublicoProducto() + Persistencia.SEPARADOR_COLUMNAS
                + ObjProducto.getNombreImagenPrivadoProducto();

        if (miArchivo.agregarRegistro(cadena)) {
            dto.setIdProducto(ObjProducto.getIdProducto());
            return dto;
        }

        return null;
    }

    @Override
    public List<ProductoDto> selectFrom() {
        List<ProductoDto> arregloProducto = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);

                int codProducto = Integer.parseInt(columnas[0].trim());
                String nomProducto = columnas[1].trim();
                Boolean tipoProducto = Boolean.valueOf(columnas[2].trim());
                String tamProducto = columnas[3].trim();
                Double preProducto = Double.parseDouble(columnas[4].trim());
                String nomImagenProductoP = columnas[5].trim();
                String nomImagenProductoPv = columnas[6].trim();

                arregloProducto.add(new ProductoDto(codProducto, nomProducto, tamProducto, tipoProducto, preProducto, nomImagenProductoP, nomImagenProductoPv));

            } catch (NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }
        return arregloProducto;
    }
    // *********************************************************************

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
            Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Boolean updateSet(Integer indice, ProductoDto obj, String ruta) 
    {
        boolean correcto = false;
        try{
            String cadena, nocu;
            List <String> arregloDeDatos;
            cadena = obj.getIdProducto() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getNombreProducto() + Persistencia.SEPARADOR_COLUMNAS
                    + obj.getTipoProducto()+ Persistencia.SEPARADOR_COLUMNAS
                    + obj.getTamanioProducto()+ Persistencia.SEPARADOR_COLUMNAS
                    + obj.getPrecioProducto()+ Persistencia.SEPARADOR_COLUMNAS
                    + obj.getNombreImagenPublicoProducto() + Persistencia.SEPARADOR_COLUMNAS;
        
            if(ruta.isBlank())
            {
                cadena = cadena + obj.getNombreImagenPrivadoProducto();
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
           Logger.getLogger(ProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }
    
    @Override
    public ProductoDto getOne(Integer codigo)
    {
        int contador;
        ProductoDto objListo;
        
        contador = 0;
        objListo = new ProductoDto();
        List<ProductoDto> arrProductos = selectFrom();
        
        for(ProductoDto objPro: arrProductos)
        {
            if(contador == codigo)
            {
                objListo = objPro;
            }
            contador++;
        }
        return objListo;
    }

    

}
