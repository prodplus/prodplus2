package br.com.prodplus.utils;

import java.util.Arrays;
import java.util.List;

import br.com.prodplus.models.enums.TipoMetrico;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class TipoMetricoUtils {

	/**
	 * Retorna os tipos métricos correspondentes ao escolhido.
	 * 
	 * @param tipo
	 * @return
	 */
	public static List<TipoMetrico> correspondentes(TipoMetrico tipo) {
		switch (tipo) {
		case UN:
		case DE:
		case CE:
		case MI:
			return Arrays.asList(TipoMetrico.UN, TipoMetrico.DE, TipoMetrico.CE, TipoMetrico.MI);
		case CM:
		case M:
			return Arrays.asList(TipoMetrico.CM, TipoMetrico.M);
		case CM2:
		case M2:
			return Arrays.asList(TipoMetrico.CM2, TipoMetrico.M2);
		case ML:
		case L:
			return Arrays.asList(TipoMetrico.ML, TipoMetrico.L);
		case G:
		case KG:
			return Arrays.asList(TipoMetrico.G, TipoMetrico.KG);
		default:
			return Arrays.asList(TipoMetrico.values());
		}
	}

}
