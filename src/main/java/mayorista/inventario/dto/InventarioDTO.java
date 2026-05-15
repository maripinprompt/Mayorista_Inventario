package mayorista.inventario.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data // Genera getters y setters automáticamente
public class InventarioDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @NotNull(message = "La cantidad de stock es obligatoria")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer cantidadStock;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Long idProveedor;
}