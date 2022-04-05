package br.com.prodplus.repositories;

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

}
