package mayorista.inventario.Service;

import mayorista.inventario.Model.InventarioModel;
import mayorista.inventario.Repository.InventarioRepository;
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

    public InventarioModel crearProducto(InventarioModel producto) {
        return inventarioRepository.save(producto);
    }

    public InventarioModel actualizarProducto(Long id, InventarioModel datos) {
        InventarioModel producto = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        producto.setNombreProducto(datos.getNombreProducto());
        producto.setCantidadStock(datos.getCantidadStock());
        producto.setPrecio(datos.getPrecio());
        producto.setCategoria(datos.getCategoria());
        producto.setIdProveedor(datos.getIdProveedor());
        return inventarioRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        inventarioRepository.deleteById(id);
    }
}