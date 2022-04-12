package br.com.prodplus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.auxiliares.AmostraId;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface AmostraRepository extends JpaRepository<Amostra, AmostraId> {

	List<Amostra> findAllByIdProcesso(Integer idProcesso);

	List<Amostra> findAllByIdProduto(Integer idProduto);

}
