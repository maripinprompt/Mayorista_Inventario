package mayorista.inventario.Service;

import mayorista.inventario.Model.InventarioModel;
import mayorista.inventario.Repository.InventarioRepository;
//IMPORTACIÓN DEL DTO AQUÍ
import mayorista.inventario.dto.InventarioDTO;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<InventarioModel> getAllProductos() {
        return inventarioRepository.findAll();
    }

    public Optional<InventarioModel> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public List<InventarioModel> buscarPorCategoria(String categoria) {
        return inventarioRepository.findByCategoria(categoria);
    }

    public List<InventarioModel> buscarPorProveedor(Long idProveedor) {
        return inventarioRepository.findByIdProveedor(idProveedor);
    }

    //Ahora recibe InventarioDTO
    public InventarioModel crearProducto(InventarioDTO datosDTO) {
        // Creamos la entidad vacía y le pasamos los datos del DTO
        InventarioModel producto = new InventarioModel();
        producto.setNombreProducto(datosDTO.getNombreProducto());
        producto.setCantidadStock(datosDTO.getCantidadStock());
        producto.setPrecio(datosDTO.getPrecio());
        producto.setCategoria(datosDTO.getCategoria());
        producto.setIdProveedor(datosDTO.getIdProveedor());
        
        return inventarioRepository.save(producto);
    }

    //Ahora recibe InventarioDTO
    public InventarioModel actualizarProducto(Long id, InventarioDTO datosDTO) {
        InventarioModel producto = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
                
        //Actualizamos la entidad con los datos nuevos del DTO
        producto.setNombreProducto(datosDTO.getNombreProducto());
        producto.setCantidadStock(datosDTO.getCantidadStock());
        producto.setPrecio(datosDTO.getPrecio());
        producto.setCategoria(datosDTO.getCategoria());
        producto.setIdProveedor(datosDTO.getIdProveedor());
        
        return inventarioRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        inventarioRepository.deleteById(id);
    }
}