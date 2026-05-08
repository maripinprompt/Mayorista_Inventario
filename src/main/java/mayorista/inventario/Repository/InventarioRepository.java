package mayorista.inventario.Repository;

import mayorista.inventario.Model.InventarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioModel, Long> {
    List<InventarioModel> findByCategoria(String categoria);
    List<InventarioModel> findByIdProveedor(Long idProveedor);
}