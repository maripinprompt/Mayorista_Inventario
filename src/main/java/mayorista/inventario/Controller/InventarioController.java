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
import mayorista.inventario.dto.InventarioDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos en inventario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<InventarioModel>> getAllProductos() {
        return ResponseEntity.ok(inventarioService.getAllProductos());
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<InventarioModel> obtenerPorId(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar productos por categoría")
    @ApiResponse(responseCode = "200", description = "Productos encontrados")
    @GetMapping("/categoria")
    public ResponseEntity<List<InventarioModel>> buscarPorCategoria(@RequestParam String categoria) {
        return ResponseEntity.ok(inventarioService.buscarPorCategoria(categoria));
    }

    @Operation(summary = "Buscar productos por proveedor")
    @ApiResponse(responseCode = "200", description = "Productos encontrados")
    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<InventarioModel>> buscarPorProveedor(@PathVariable Long idProveedor) {
        return ResponseEntity.ok(inventarioService.buscarPorProveedor(idProveedor));
    }

    @Operation(summary = "Crear producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<?> crearProducto(@Valid @RequestBody InventarioDTO producto) {
        InventarioModel creado = inventarioService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar producto")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody InventarioDTO producto) {
        try {
            return ResponseEntity.ok(inventarioService.actualizarProducto(id, producto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            inventarioService.eliminarProducto(id);
            return ResponseEntity.ok(Map.of("mensaje", "Producto eliminado exitosamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}