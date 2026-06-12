package mayorista.inventario.Service;

import mayorista.inventario.Model.InventarioModel;
import mayorista.inventario.Repository.InventarioRepository;
import mayorista.inventario.dto.InventarioDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    // cliente HTTP para comunicarse con el microservicio de proveedores
    private final WebClient webClient;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
        // apunta al microservicio de proveedores en el puerto 8080
        this.webClient = WebClient.create("http://host.docker.internal:8080");
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

    // verifica si el proveedor existe antes de guardar el producto
    private void verificarProveedorExiste(Long idProveedor) {
        try {
            // le pregunta al microservicio de proveedores si existe ese id
            webClient.get()
                    .uri("/proveedores/" + idProveedor)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            // si el proveedor no existe retorna 404 y lanzamos excepcion
            throw new IllegalArgumentException("El proveedor con id " + idProveedor + " no existe");
        } catch (Exception e) {
            // si el microservicio de proveedores no esta disponible lanzamos excepcion
            throw new IllegalArgumentException("No se pudo verificar el proveedor, el servicio no esta disponible");
        }
    }

    public InventarioModel crearProducto(InventarioDTO datosDTO) {
        // verifica que el proveedor exista antes de crear el producto
        verificarProveedorExiste(datosDTO.getIdProveedor());

        InventarioModel producto = new InventarioModel();
        producto.setNombreProducto(datosDTO.getNombreProducto());
        producto.setCantidadStock(datosDTO.getCantidadStock());
        producto.setPrecio(datosDTO.getPrecio());
        producto.setCategoria(datosDTO.getCategoria());
        producto.setIdProveedor(datosDTO.getIdProveedor());

        return inventarioRepository.save(producto);
    }

    public InventarioModel actualizarProducto(Long id, InventarioDTO datosDTO) {
        InventarioModel producto = inventarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // verifica que el proveedor exista antes de actualizar el producto
        verificarProveedorExiste(datosDTO.getIdProveedor());

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