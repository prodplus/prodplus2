package br.com.prodplus.utils;

import java.util.Collections;
import java.util.List;

import br.com.prodplus.models.EstoqueMaterial;
import br.com.prodplus.models.EstoqueProduto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class EstoquesUtils {

	/**
	 * Recalcula todos os estoques do período.
	 * 
	 * @param estoques
	 * @return
	 */
	public static List<EstoqueMaterial> calculaEstoquePeriodoM(List<EstoqueMaterial> estoques) {
		Collections.sort(estoques);
		for (int i = 1; i < estoques.size(); i++)
			estoques.get(i).setQuantidade(
					estoques.get(i - 1).getQuantidade() + estoques.get(i).getEntrada());
		return estoques;
	}

	/**
	 * Recalcula todos os estoques do período.
	 * 
	 * @param estoques
	 * @return
	 */
	public static List<EstoqueProduto> calculaEstoquePeriodoP(List<EstoqueProduto> estoques) {
		Collections.sort(estoques);
		for (int i = 1; i < estoques.size(); i++)
			estoques.get(i).setQuantidade(
					estoques.get(i - 1).getQuantidade() + estoques.get(i).getEntrada());
		return estoques;
	}

}
