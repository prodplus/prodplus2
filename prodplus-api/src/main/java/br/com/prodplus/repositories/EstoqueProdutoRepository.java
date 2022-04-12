package br.com.prodplus.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.EstoqueProduto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Integer> {

	List<EstoqueProduto> findAllByProdutoId(Integer idProduto);

	List<EstoqueProduto> findAllByProdutoIdAndDataBetween(Integer idProduto, LocalDateTime de,
			LocalDateTime ate);

	Optional<EstoqueProduto> findFirstByProdutoIdOrderByDataDesc(Integer idProduto);

}
