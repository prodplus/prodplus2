package br.com.prodplus.repositories;

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

}
