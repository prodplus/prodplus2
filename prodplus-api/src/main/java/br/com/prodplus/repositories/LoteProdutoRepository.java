package br.com.prodplus.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.LoteProduto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface LoteProdutoRepository extends JpaRepository<LoteProduto, Integer> {

	Optional<LoteProduto> findByRastreio(String rastreio);

	List<LoteProduto> findAllByProdutoIdAndAtivo(Integer idProduto, boolean b, Sort by);

}
