package br.com.prodplus.utils;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.StatUtils;

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.DemandaPrev;
import br.com.prodplus.models.Produto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class PrevisaoMediasMoveis {

	private static Produto PRODUTO;

	public static List<DemandaPrev> previsao(List<DemandaExec> demandasA, Produto produto) {
		PRODUTO = produto;
		List<DemandaExec> filtradas = demandasA.stream().filter(d -> d.getQuantidade() != null)
				.collect(Collectors.toList());
		Collections.sort(filtradas);
		List<DemandaPrev> futuras = retornaFuturas();
		for (DemandaPrev d : futuras) {
			List<DemandaExec> daSemana = filtradas.stream()
					.filter(s -> s.getId().getSemana() == d.getId().getSemana())
					.collect(Collectors.toList());
			double[] values = new double[daSemana.size()];
			for (int i = 0; i < daSemana.size(); i++)
				values[i] = daSemana.get(i).getQuantidade();
			d.setQuantidade((double) Math.round(StatUtils.mean(values)));
		}

		return futuras;
	}

	private static List<DemandaPrev> retornaFuturas() {
		LocalDate hoje = LocalDate.now();
		hoje = hoje.plusWeeks(1);
		List<DemandaPrev> retorno = new ArrayList<>();
		for (int i = 0; i < 52; i++) {
			DemandaPrev nova = new DemandaPrev(PRODUTO.getId(), hoje.getYear(),
					hoje.get(WeekFields.ISO.weekOfWeekBasedYear()), null);
			retorno.add(nova);
			hoje = hoje.plusWeeks(1);
		}

		return retorno;
	}

}
