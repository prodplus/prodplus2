package br.com.prodplus.utils;

import java.util.List;
import java.util.stream.Collectors;

import br.com.prodplus.models.DemandaExec;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class DemandasUtils {

	/**
	 * Calcula a média móvel das demandas para cálculo de estoques mínimos.
	 * 
	 * @param demandas
	 * @param semanasAno
	 * @return
	 */
	public static Integer mediaMovel(List<DemandaExec> demandas, Integer semanasAno) {
		final int value = semanasAno;
		demandas = demandas.stream().filter(d -> d.getId().getSemana().equals(value))
				.filter(d -> d.getQuantidade() != null).collect(Collectors.toList());
		if (demandas.isEmpty())
			return 0;
		return (int) PrevisaoFuncional.prever(demandas);
	}

}
