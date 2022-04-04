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
public class DemandaPrev extends Demanda {

	private static final long serialVersionUID = 1509535854629076496L;

	@Builder
	public DemandaPrev(Integer produto, Integer ano, Integer semana, Double quantidade) {
		super(new DemandaId(produto, ano, semana), quantidade);
	}

}
