package br.com.prodplus.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.Produto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	List<Produto> findAllByAtivo(boolean ativos, Sort by);

	Page<Produto> findAllByAtivo(boolean ativos, Pageable pageable);

	Page<Produto> findAllByAtivoAndDescricaoContainingIgnoreCase(boolean ativos, String descricao,
			Pageable pageable);

}
