package br.com.prodplus.utils;

import java.util.List;

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.Configuracao;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class TemposUtils {

	/**
	 * Retorna a média dos tempos.
	 * 
	 * @param tempos
	 * @return
	 */
	public static Integer getMedia(List<Integer> tempos) {
		int soma = 0;
		if (tempos != null && !tempos.isEmpty()) {
			for (Integer t : tempos)
				soma += t;
			return soma / tempos.size();
		}
		return null;
	}

	/**
	 * Retorna a média inteira dos tempos coletados na amostra do produto.
	 * 
	 * @param amostra
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Integer getTempoMedio(Amostra amostra) throws IllegalArgumentException {
		if (amostra.getTemposMedidos().isEmpty())
			throw new IllegalArgumentException();
		else
			return getMedia(amostra.getTemposMedidos());
	}

	/**
	 * Retorna o tempo normal de uma amostragem de um produto.
	 * 
	 * @param amostra
	 * @return
	 */
	public static Integer getTempoNormal(Amostra amostra) {
		Double tempoMedio = 0.0 + getTempoMedio(amostra);
		Double variacao = 0.0;
		variacao += amostra.getHabilidade().getValor() * tempoMedio;
		variacao += amostra.getEsforco().getValor() * tempoMedio;
		variacao += amostra.getCondicao().getValor() * tempoMedio;
		variacao += amostra.getConsistencia().getValor() * tempoMedio;
		tempoMedio += variacao;
		return (int) Math.round(tempoMedio);
	}

	/**
	 * Calcula o tempo padrão do produto baseado na amostragem.
	 * 
	 * @param amostra
	 * @param config
	 * @return
	 */
	public static Integer getTempoPadrao(Amostra amostra, Configuracao config) {
		Integer tempoTolerancia = amostra.getTempoTolerancia();
		int turnos = 0;
		turnos += config.getTurnosSemana().size() * 5;
		turnos += config.getTurnosSabado().size() + config.getTurnosDomingo().size();
		tempoTolerancia *= turnos;
		Long totalSemana = ConfigUtils.segundosSemana(config) - tempoTolerancia;
		Long totalProduzido = totalSemana / getTempoNormal(amostra);
		return (int) (ConfigUtils.segundosSemana(config) / totalProduzido);
	}

}
