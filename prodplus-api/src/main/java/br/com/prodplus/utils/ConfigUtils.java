package br.com.prodplus.utils;

import java.time.Duration;

import br.com.prodplus.models.Configuracao;
import br.com.prodplus.models.auxiliares.Turno;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class ConfigUtils {

	/**
	 * Calcula a quantidade de segundos disponíveis na semana da indústria.
	 * 
	 * @param config
	 * @return
	 */
	public static Long segundosSemana(Configuracao config) {
		Long segundos = 0l;
		for (Turno t : config.getTurnosSemana())
			segundos += Duration.between(t.getInicio(), t.getFim()).getSeconds();
		segundos *= 5;
		for (Turno t : config.getTurnosSabado())
			segundos += Duration.between(t.getInicio(), t.getFim()).getSeconds();
		for (Turno t : config.getTurnosDomingo())
			segundos += Duration.between(t.getInicio(), t.getFim()).getSeconds();

		return segundos;
	}

}
