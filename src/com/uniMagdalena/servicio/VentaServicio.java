
package com.uniMagdalena.servicio;

import com.poo.persistence.NioFile;
import com.uniMagdalena.api.ApiOperacionBD;
import com.uniMagdalena.dto.ClienteDto;
import com.uniMagdalena.dto.PeliculaDto;
import com.uniMagdalena.dto.ProductoDto;
import com.uniMagdalena.dto.SalaDto;
import com.uniMagdalena.dto.SedeDto;
import com.uniMagdalena.dto.VentaDto;
import com.uniMagdalena.model.Cliente;
import com.uniMagdalena.model.Genero;
import com.uniMagdalena.model.Pelicula;
import com.uniMagdalena.model.Producto;
import com.uniMagdalena.model.Sala;
import com.uniMagdalena.model.Sede;
import com.uniMagdalena.model.Venta;
import com.uniMagdalena.recurso.constante.Persistencia;
import com.uniMagdalena.recurso.utilidad.Aleatorio;
import com.uniMagdalena.recurso.utilidad.GestorImagen;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VentaServicio implements ApiOperacionBD<VentaDto, Integer>
{
    
    private NioFile miArchivo;
    private String nombrePersistencia;
    
    public VentaServicio()
    {
        try {
            nombrePersistencia = Persistencia.NOMBRE_VENTA;
            miArchivo = new NioFile(nombrePersistencia);
        } catch (IOException ex) {
            Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public int getSerial() 
    {
        int codigo = 0;
        try {
            codigo = miArchivo.ultimoCodigo() + 1;
        } catch (IOException ex) {
            Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return codigo;
    }

    @Override
    public VentaDto insertInto(VentaDto dto, String ruta)
    {
        Cliente objCliente = new Cliente
        (dto.getClienteVenta().getIdCliente(), dto.getClienteVenta().getNombreCliente(), 
                dto.getClienteVenta().getGeneroCliente(), dto.getClienteVenta().getTipoDocumentoCliente(),dto.getClienteVenta().getNumeroDocumentoCliente() , 
                dto.getClienteVenta().getTipoCliente(),null ,"", "");
        
        Genero objGenero = new Genero(dto.getPeliculaVenta().getIdGeneroPelicula().getIdGenero(),
                dto.getPeliculaVenta().getIdGeneroPelicula().getNombreGenero(), dto.getPeliculaVenta().getIdGeneroPelicula().getEstadoGenero(), 
                dto.getPeliculaVenta().getIdGeneroPelicula().getPopularidadGenero(), dto.getPeliculaVenta().getIdGeneroPelicula().getEsClasicoGenero(), null, "", "");
        
        Pelicula objPelicula = new Pelicula(dto.getPeliculaVenta().getIdPelicula(),
                dto.getPeliculaVenta().getNombrePelicula(), objGenero,
                 dto.getPeliculaVenta().getActorPPelicula(), dto.getPeliculaVenta().getPresupuestoPelicula()
                , dto.getPeliculaVenta().getEsParaNinyosPelicula(),null ,"", "");
        
        Sede objSede = new Sede(dto.getSedeVenta().getIdSede(), dto.getSedeVenta().getNombreSede(), dto.getSedeVenta().getCiudadSede(), 
                dto.getSedeVenta().getUbicacionSede(), dto.getSedeVenta().getEs24horasSede(), null, null,
                "", "");
        
        Sala objSala = new Sala(dto.getSalaVenta().getIdSala(),dto.getSalaVenta().getNombreSala() , dto.getSalaVenta().getAsientosSala(), 
                dto.getSalaVenta().getSala4d(), objSede,null ,"", "");
        
        Producto objProducto = new Producto(dto.getProductoVenta().getIdProducto(),dto.getProductoVenta().getNombreProducto(), dto.getProductoVenta().getTipoProducto(), dto.getProductoVenta().getTamanioProducto(), dto.getProductoVenta().getPrecioProducto(), null, "", "" );
        
        Venta objVenta = new Venta();
        objVenta.setIdVenta(getSerial());
        objVenta.setClienteVenta(objCliente);
        objVenta.setPeliculaVenta(objPelicula);
        objVenta.setSedeVenta(objSede);
        objVenta.setSalaVenta(objSala);
        objVenta.setProductoVenta(objProducto);
        objVenta.setTipoAsientoVenta(dto.getTipoAsientoVenta());
        objVenta.setFechaVenta(dto.getFechaVenta());
        objVenta.setValorVenta(dto.getValorVenta());
        objVenta.setNombreImagenPublicoVenta(dto.getNombreImagenPublicoVenta());
        objVenta.setNombreImagenPrivadoVenta(GestorImagen.grabarLaImagen(ruta));
        
        BigDecimal valorListo = new BigDecimal(String.valueOf(objVenta.getValorVenta()));
        
        String cadena = objVenta.getIdVenta() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getClienteVenta().getIdCliente()+ Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getPeliculaVenta().getIdPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getSedeVenta().getIdSede() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getSalaVenta().getIdSala()+ Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getProductoVenta().getIdProducto()+ Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getTipoAsientoVenta() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getFechaVenta() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getNombreImagenPublicoVenta() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getNombreImagenPrivadoVenta();
        
        if(miArchivo.agregarRegistro(cadena))
        {
            dto.setIdVenta(objVenta.getIdVenta());
            return dto;
        }
        return null;
    }

    @Override
    public List<VentaDto> selectFrom() {
        List<VentaDto> arregloVenta = new ArrayList<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();
        
        ClienteServicio clienteServicio = new ClienteServicio();
        PeliculaServicio peliculaServicio = new PeliculaServicio();
        SedeServicio sedeServicio = new SedeServicio();
        SalaServicio salaServicio = new SalaServicio();
        ProductoServicio productoServicio = new ProductoServicio();
        
        for (String cadena : arregloDatos) 
        {
            try
            {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                
                int codVenta = Integer.parseInt(columnas[0].trim());
                int idCliente = Integer.parseInt(columnas[1].trim());
                int idPelicula = Integer.parseInt(columnas[2].trim());
                int idSede = Integer.parseInt(columnas[3].trim());
                int idSala = Integer.parseInt(columnas[4].trim());
                int idProducto = Integer.parseInt(columnas[5].trim());
                String tipoASiento = columnas[6].trim();
                LocalDate fechaVenta = LocalDate.parse(columnas[7].trim());
                double valorVenta = Double.parseDouble(columnas[8].trim());
                String nomImagenVentaP = columnas[9].trim();
                String nomImagenVentaPv = columnas[10].trim();
                
                ClienteDto clienteDto = buscarClientePorDocumento(clienteServicio, idCliente);
                PeliculaDto peliculaDto = buscarPeliculaPorId(peliculaServicio, idPelicula);
                SedeDto sedeDto = buscarSedePorID(sedeServicio, idSede);
                SalaDto salaDto = buscarSalaPorID(salaServicio, idSala);
                ProductoDto productoDto = buscarProductoPorID(productoServicio, idProducto);
                
                if(clienteDto != null && peliculaDto != null && sedeDto != null && salaDto != null && productoDto != null)
                {
                   arregloVenta.add(new VentaDto(codVenta, clienteDto, peliculaDto, sedeDto, salaDto, productoDto, tipoASiento, fechaVenta, valorVenta, nomImagenVentaP, nomImagenVentaPv));
                }
                              
                
            }catch(NumberFormatException e)
            {
                System.out.println(e.getMessage());
            }
        }
        
        return arregloVenta;
    }
    
    
    public Map<Integer, Integer> selectFromCantidadCliente() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idCliente)
                int numCliente = Integer.parseInt(columnas[1].trim());
                // Y el trucazo es que cuento los idCliente, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(numCliente, arrCantidades.getOrDefault(numCliente, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }
    
    public Map<Integer, Integer> selectFromCantidadPelicula() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idCliente)
                int idPelicula = Integer.parseInt(columnas[2].trim());
                // Y el trucazo es que cuento los idCliente, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(idPelicula, arrCantidades.getOrDefault(idPelicula, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }
    
     public Map<Integer, Integer> selectFromCantidadSala() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idCliente)
                int idSala = Integer.parseInt(columnas[4].trim());
                // Y el trucazo es que cuento los idCliente, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(idSala, arrCantidades.getOrDefault(idSala, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, error);
            }
        }
        return arrCantidades;
    }
     
      public Map<Integer, Integer> selectFromCantidadProducto() {
        Map<Integer, Integer> arrCantidades = new HashMap<>();
        List<String> arregloDatos = miArchivo.obtenerDatos();

        for (String cadena : arregloDatos) {
            try {
                cadena = cadena.replace("@", "");
                String[] columnas = cadena.split(Persistencia.SEPARADOR_COLUMNAS);
                // Analiza que la única columna que uso es la del papá (idCliente)
                int idProducto = Integer.parseInt(columnas[5].trim());
                // Y el trucazo es que cuento los idCliente, si no existe mi vale le pongo cero y si existe le sumo 1, breve
                arrCantidades.put(idProducto, arrCantidades.getOrDefault(idProducto, 0) + 1);

            } catch (NumberFormatException error) {
                Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, error);
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
    
    private ProductoDto buscarProductoPorID(ProductoServicio productoServicio, int idProducto)
    {
        List<ProductoDto> todosLosProductos = productoServicio.selectFrom();
        
        for (ProductoDto producto : todosLosProductos) {
            if(producto.getIdProducto() == idProducto)
            {
                return producto;
            }
        }
        return null;
    }
    
    private SalaDto buscarSalaPorID(SalaServicio salaServicio, int idSala)
    {
        List<SalaDto> todasLasSalas = salaServicio.selectFrom();
        
        for (SalaDto sala : todasLasSalas) 
        {
            if(sala.getIdSala()== idSala)
            {
                return sala;
            }
        }
        return null;
    }
    
    private PeliculaDto buscarPeliculaPorId(PeliculaServicio peliculaServicio, int idPelicula)
    {
        List<PeliculaDto> todasLasPeliculas = peliculaServicio.selectFrom();
        for(PeliculaDto pelicula: todasLasPeliculas)
        {
            if(pelicula.getIdPelicula() == idPelicula)
            {
                return pelicula;
            }
        }
        return null;
    }
    
    private ClienteDto buscarClientePorDocumento(ClienteServicio clienteServicio, int idCliente)
    {
        List<ClienteDto> todosLosClientes = clienteServicio.selectFrom();
        for(ClienteDto cliente: todosLosClientes)
        {
            if(cliente.getIdCliente() == idCliente)
            {
                return cliente;
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
            Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return correcto;
    }

    @Override
    public Boolean updateSet(Integer indice, VentaDto objVenta, String ruta) {
        
        boolean correcto = false;
        
        try{
            String cadena, nocu;
            List<String> arregloDeDatos;
            
            BigDecimal valorListo = new BigDecimal(String.valueOf(objVenta.getValorVenta()));
        
         cadena = objVenta.getIdVenta() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getClienteVenta().getNumeroDocumentoCliente() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getPeliculaVenta().getIdPelicula() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getSedeVenta().getIdSede() + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getSalaVenta().getIdSala()+ Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getProductoVenta().getIdProducto()+ Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getTipoAsientoVenta() + Persistencia.SEPARADOR_COLUMNAS 
                + objVenta.getFechaVenta() + Persistencia.SEPARADOR_COLUMNAS
                + valorListo + Persistencia.SEPARADOR_COLUMNAS
                + objVenta.getNombreImagenPublicoVenta() + Persistencia.SEPARADOR_COLUMNAS;
         
         if(ruta.isBlank())
         {
             cadena = cadena + objVenta.getNombreImagenPrivadoVenta();
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
        Logger.getLogger(VentaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return correcto;
        
    }

    @Override
    public VentaDto getOne(Integer codigo)
    {
        int contador;
        VentaDto objListo;
        
        contador = 0;
        objListo = new VentaDto();
        List<VentaDto> arrVentas = selectFrom();
        for (VentaDto objVenta : arrVentas) 
        {
            if(contador == codigo)
            {
                objListo = objVenta;
            }
            contador++;
        }
        return objListo;
    }
    
}
