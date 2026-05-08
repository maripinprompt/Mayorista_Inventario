package mayorista.inventario.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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