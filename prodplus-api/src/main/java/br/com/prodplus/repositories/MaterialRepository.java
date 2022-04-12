package br.com.prodplus.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.Material;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

	Page<Material> findAllByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);

}
