package br.com.prodplus.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.Processo;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Integer> {

	List<Processo> findAllByAtivo(boolean ativos, Sort by);

	Page<Processo> findAllByAtivo(boolean ativos, Pageable pageable);

	Page<Processo> findAllByAtivoAndDescricaoContainingIgnoreCase(boolean ativos, String descricao,
			Pageable pageable);

}
