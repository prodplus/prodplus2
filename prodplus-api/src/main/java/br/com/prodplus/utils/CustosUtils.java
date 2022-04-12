package br.com.prodplus.utils;

import java.math.BigDecimal;
import java.util.List;

import br.com.prodplus.models.Configuracao;
import br.com.prodplus.models.Custo;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class CustosUtils {

	/**
	 * Calcula o total de custos fixos por mês na indústria.
	 * 
	 * @param custos
	 * @param config
	 * @return
	 */
	public static BigDecimal getTotalMes(List<Custo> custos, Configuracao config) {
		Long segundosMes = ConfigUtils.segundosSemana(config) * 4;
		Double total = 0.0;

		for (Custo c : custos) {
			switch (c.getPeriodo()) {
			case POR_SEGUNDO:
				total += c.getValor().doubleValue() * segundosMes;
				break;
			case POR_MINUTO:
				total += c.getValor().doubleValue() * (segundosMes / 60);
				break;
			case HORARIO:
				total += c.getValor().doubleValue() * (segundosMes / 3600);
				break;
			case DIARIO:
				total += c.getValor().doubleValue() * 22;
				break;
			case SEMANAL:
				total += c.getValor().doubleValue() * 4;
				break;
			case QUINZENAL:
				total += c.getValor().doubleValue() * 2;
				break;
			case MENSAL:
				total += c.getValor().doubleValue();
				break;
			case BIMESTRAL:
				total += c.getValor().doubleValue() / 2;
				break;
			case TRIMESTRAL:
				total += c.getValor().doubleValue() / 3;
				break;
			case SEMESTRAL:
				total += c.getValor().doubleValue() / 6;
				break;
			case ANUAL:
				total += c.getValor().doubleValue() / 12;
				break;
			default:
				break;
			}
		}

		return BigDecimal.valueOf(total);
	}

}
