package br.com.prodplus.repositories;

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

}
