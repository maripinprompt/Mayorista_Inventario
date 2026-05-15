package mayorista.inventario.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import mayorista.inventario.Service.InventarioService;
import mayorista.inventario.Model.InventarioModel;
// AQUÍ AGREGAMOS LA IMPORTACIÓN DEL DTO
import mayorista.inventario.dto.InventarioDTO;

import java.util.List;
import java.util.Map;

@RestController // indica que esta clase maneja peticiones HTTP y retorna JSON
@RequestMapping("/inventario") // todas las rutas de esta clase empiezan con /inventario
public class InventarioController {

    // inyectamos el service para usar la lógica de negocio
    private final InventarioService inventarioService;

    // constructor — Spring inyecta el service automáticamente
    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    //get/inventario — retorna todos los productos
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos en inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<InventarioModel>> getAllProductos() {
        return ResponseEntity.ok(inventarioService.getAllProductos()); // retorna 200 con la lista
    }

    //get/inventario/{id} — busca un producto por su id
    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<InventarioModel> obtenerPorId(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id)
                .map(ResponseEntity::ok) // si existe retorna 200 con el producto
                .orElseGet(() -> ResponseEntity.notFound().build()); // si no existe retorna 404
    }

    //get/inventario/categoria?categoria=xxx — busca productos por categoría
    @Operation(summary = "Buscar productos por categoría")
    @ApiResponse(responseCode = "200", description = "Productos encontrados")
    @GetMapping("/categoria")
    public ResponseEntity<List<InventarioModel>> buscarPorCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(inventarioService.buscarPorCategoria(categoria)); // retorna 200 con la lista
    }

    //get/inventario/proveedor/{idProveedor} — busca productos por proveedor
    @Operation(summary = "Buscar productos por proveedor")
    @ApiResponse(responseCode = "200", description = "Productos encontrados")
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<InventarioModel>> buscarPorProveedor(@PathVariable Long idProveedor) {
        return ResponseEntity.ok(inventarioService.buscarPorProveedor(idProveedor)); // retorna 200 con la lista
    }

    //post/inventario — crea un nuevo producto
    @Operation(summary = "Crear producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    // CAMBIO AQUÍ: Recibe InventarioDTO en lugar de InventarioModel
    public ResponseEntity<?> crearProducto(@Valid @RequestBody InventarioDTO producto) {
        // @Valid activa las validaciones del Model (@NotBlank, @NotNull, etc.)
        // @RequestBody convierte el JSON que llega en un objeto InventarioDTO
        InventarioModel creado = inventarioService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // retorna 201
    }

    //put/inventario/{id} — actualiza un producto existente
    @Operation(summary = "Actualizar producto")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    // CAMBIO AQUÍ: Recibe InventarioDTO en lugar de InventarioModel
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody InventarioDTO producto) {
        try {
            return ResponseEntity.ok(inventarioService.actualizarProducto(id, producto)); // retorna 200 con el producto actualizado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // si no existe retorna 404
        }
    }

    //delete/inventario/{id} — elimina un producto existente
    @Operation(summary = "Eliminar producto")
    @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            inventarioService.eliminarProducto(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado exitosamente")); // retorna 200
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // si no existe retorna 404
        }
    }
}