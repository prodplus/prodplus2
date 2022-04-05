package br.com.prodplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.auxiliares.DemandaId;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Repository
public interface DemandaExecRepository extends JpaRepository<DemandaExec, DemandaId> {

}
