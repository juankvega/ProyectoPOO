
package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.model.Cliente;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteServicio implements ApiOperacionBD<ClienteDto, Integer>
{
    
    private NioFile miArchivo;
    private String nombrePersistencia;

    public ClienteServicio() 
    {
        try {
            nombrePersistencia = Persistencia.NOMBRE_CLIENTE;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public int getSerial() 
    {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public ClienteDto insertInto(ClienteDto dto, String ruta) 
    {
        Cliente objCliente = new Cliente();
        objCliente.setIdCliente(getSerial());
        objCliente.setNombreCliente(dto.getNombreCliente());
        objCliente.setGeneroCliente(dto.getGeneroCliente());
        objCliente.setTipoDocumentoCliente(dto.getTipoDocumentoCliente());
        objCliente.setNumeroDocumentoCliente(dto.getNumeroDocumentoCliente());
        objCliente.setTipoCliente(dto.getTipoCliente());
        objCliente.setNombreImagenPublicoCliente(dto.getNombreImagenPublicoCliente());
        objCliente.setNombreImagenPrivadoCliente(GestorImagen.grabarLaImagen(ruta));
        
        BigInteger valorListo = new BigInteger(String.valueOf(objCliente.getNumeroDocumentoCliente()));
                
        
        String cadena = objCliente.getIdCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getNombreCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getGeneroCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getTipoDocumentoCliente() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getTipoCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getNombreImagenPublicoCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getNombreImagenPrivadoCliente();
        
        if (miArchivo.agregarRegistro(cadena)) {
            dto.setIdCliente(objCliente.getIdCliente());
            return dto;
        }
        return null;
      
    }

    @Override
    public List<ClienteDto> selectFrom() {
        List<ClienteDto> arregloClientes = new ArrayList<>();
        List <String> arregloDatos = miArchivo.obtenerDatos();
        
        VentaServicio ventaServicio = new VentaServicio();
        Map<Integer, Integer> arrCantVentas = ventaServicio.selectFromCantidadCliente();
        
        for (String cadena : arregloDatos) 
        {
            try{
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                
                int codCliente = Integer.parseInt(columnas[0].trim());
                String nomCliente = columnas[1].trim();
                Boolean genCliente = Boolean.valueOf(columnas[2].trim());
                String docCliente = columnas[3].trim();
                int numCliente = Integer.parseInt(columnas[4].trim());
                String tipoCliente = columnas[5].trim();
                String nomImagenClienteP = columnas[6].trim();
                String nomImagenClientePv = columnas[7].trim();
                
                short cantVentas = arrCantVentas.getOrDefault(numCliente, 0).shortValue();
                
                arregloClientes.add(new ClienteDto(codCliente, nomCliente, genCliente, docCliente, numCliente, tipoCliente, cantVentas ,nomImagenClienteP, nomImagenClientePv));
                
            }
        catch(NumberFormatException e) {
                System.out.println("Error al parsear datos: " + e.getMessage());
            }
        }
        return arregloClientes;
    }

    @Override
    public int numRows() 
    {
       int cantidad = 0;
        try {
            cantidad = miArchivo.cantidadFilas();
        } catch (IOException ex) {
            Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ClienteServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Boolean updateSet(Integer indice, ClienteDto objCliente, String ruta) {
        boolean correcto = false;
        try{
            String cadena, nocu;
            List<String> arregloDeDatos;
            
            BigInteger valorListo = new BigInteger(String.valueOf(objCliente.getNumeroDocumentoCliente()));
                
        
        cadena = objCliente.getIdCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getNombreCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getGeneroCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getTipoDocumentoCliente() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getTipoCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objCliente.getNombreImagenPublicoCliente() + Persistencia.SEPARADOR_COLUMNAS;
        
        if(ruta.isBlank())
        {
            cadena = cadena + objCliente.getNombreImagenPrivadoCliente();
        }else{
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
            
        }catch(IOException ex){
            System.out.println("Error al analizar el archivo: " + ex.getMessage());
        }
        return correcto;
    }

    @Override
    public ClienteDto getOne(Integer codigo) {
        int contador;
        ClienteDto objListo;
        
        contador = 0;
        objListo = new ClienteDto();
        List<ClienteDto> arrClientes = selectFrom();
        for (ClienteDto objCliente : arrClientes) 
        {
            if(contador == codigo)
            {
                objListo = objCliente;
            }
            contador++;
        }
        return objListo;
    }
    
}
