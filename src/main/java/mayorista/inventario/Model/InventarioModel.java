package mayorista.inventario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // permite que el id se muestre en las respuestas GET, pero bloquea que se envie manualmente en POST/PUT
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String nombreProducto;

    @NotNull
    private Integer cantidadStock;

    @NotNull
    private Double precio;

    private String categoria;

    private Long idProveedor;
}