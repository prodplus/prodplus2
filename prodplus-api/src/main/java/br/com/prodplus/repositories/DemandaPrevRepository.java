package br.com.prodplus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.DemandaPrev;
import br.com.prodplus.models.auxiliares.DemandaId;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface DemandaPrevRepository extends JpaRepository<DemandaPrev, DemandaId> {

	List<DemandaPrev> findAllByIdProduto(Integer idProduto);

	List<DemandaPrev> findAllByIdProdutoAndIdAno(Integer idProduto, Integer ano);

}
