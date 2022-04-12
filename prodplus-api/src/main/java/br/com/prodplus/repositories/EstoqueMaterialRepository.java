package br.com.prodplus.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.EstoqueMaterial;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface EstoqueMaterialRepository extends JpaRepository<EstoqueMaterial, Integer> {

	Optional<EstoqueMaterial> findFirstByMaterialIdOrderByDataDesc(Integer idMaterial);

	List<EstoqueMaterial> findAllByMaterialIdOrderByDataDesc(Integer idMaterial);

	List<EstoqueMaterial> findAllByMaterialIdAndDataBetween(Integer idMaterial, LocalDateTime de,
			LocalDateTime ate);

}
