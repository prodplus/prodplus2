package br.com.prodplus.repositories;

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

}
