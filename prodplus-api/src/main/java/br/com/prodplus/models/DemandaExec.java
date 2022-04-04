package br.com.prodplus.models;

import javax.persistence.Entity;

import br.com.prodplus.models.auxiliares.DemandaId;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@NoArgsConstructor
@Entity
public class DemandaExec extends Demanda {

	private static final long serialVersionUID = 2838841705063764651L;

	@Builder
	public DemandaExec(Integer produto, Integer ano, Integer semana, Double quantidade) {
		super(new DemandaId(produto, ano, semana), quantidade);
	}

}
