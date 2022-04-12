package br.com.prodplus.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.LoteMaterial;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface LoteMaterialRepository extends JpaRepository<LoteMaterial, Integer> {

	Optional<LoteMaterial> findByRastreio(String rastreio);

	List<LoteMaterial> findAllByMaterialIdAndAtivo(Integer idMaterial, boolean b, Sort by);

}
