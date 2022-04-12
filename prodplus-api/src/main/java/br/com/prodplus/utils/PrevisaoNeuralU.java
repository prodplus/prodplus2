package br.com.prodplus.utils;

import java.util.List;

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.DemandaPrev;
import br.com.prodplus.models.Produto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class PrevisaoNeuralU {

	/**
	 * Calcula a previsão da próxima demanda pela média das diferenças.
	 * 
	 * @param demandas
	 * @param produto
	 * @return
	 */
	public static DemandaPrev previsao(List<DemandaExec> demandas, Produto produto) {
		if (demandas.size() == 1)
			return new DemandaPrev(produto.getId(), demandas.get(0).getId().getAno(),
					demandas.get(0).getId().getSemana(), demandas.get(0).getQuantidade());
		if (demandas.size() == 2) {
			double soma = demandas.get(1).getQuantidade()
					+ (demandas.get(1).getQuantidade() - demandas.get(0).getQuantidade());
			return new DemandaPrev(produto.getId(), demandas.get(1).getId().getAno() + 1,
					demandas.get(0).getId().getSemana(), (double) Math.round(soma));
		}
		if (demandas.size() >= 3) {
			double soma = 0.0;
			soma += (demandas.get(demandas.size() - 1).getQuantidade()
					- demandas.get(demandas.size() - 2).getQuantidade())
					+ (demandas.get(demandas.size() - 2).getQuantidade()
							- demandas.get(demandas.size() - 3).getQuantidade());
			soma /= 2;
			soma += demandas.get(demandas.size() - 1).getQuantidade();
			return new DemandaPrev(produto.getId(),
					demandas.get(demandas.size() - 1).getId().getAno() + 1,
					demandas.get(demandas.size() - 1).getId().getSemana(),
					(double) Math.round(soma));
		}
		return null;
	}

}
