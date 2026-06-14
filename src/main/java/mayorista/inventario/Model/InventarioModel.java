package mayorista.inventario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore // el id lo genera MySQL automaticamente, no se acepta ni se muestra en el JSON
    private Long id;

    @NotBlank // valida que el nombre del producto no venga vacio
    private String nombreProducto;

    @NotNull // valida que la cantidad no venga nula
    private Integer cantidadStock;

    @NotNull // valida que el precio no venga nulo
    private Double precio;

    private String categoria;

    private Long idProveedor; // referencia al id del proveedor
}