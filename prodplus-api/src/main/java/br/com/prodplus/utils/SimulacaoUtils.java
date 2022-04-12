package br.com.prodplus.utils;

import java.util.List;

import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.models.auxiliares.MapaJson;
import br.com.prodplus.models.enums.SituacaoProcesso;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class SimulacaoUtils {

	/**
	 * Verifica se todos os processos estão parados.
	 * 
	 * @param estruturas
	 * @return
	 */
	public static boolean todosTerminados(List<Estrutura> estruturas) {
		for (Estrutura e : estruturas)
			if (!e.getSituacao().equals(SituacaoProcesso.PARADO))
				return false;
		return true;
	}

	/**
	 * Trata cada segundo conforme posição.
	 * 
	 * @param estruturas
	 * @param tempo
	 * @param faltantes
	 * @return
	 */
	public static List<Estrutura> trataSegundo(List<Estrutura> estruturas, boolean tempo,
			long faltantes) {
		for (Estrutura est : estruturas) {
			switch (est.getSituacao()) {
			case PARADO:
				if (tempo) {
					est.setSituacao(SituacaoProcesso.SETUPC);
					est = trataSetup(est);
				}
				break;
			case SETUPC:
				est = trataSetup(est);
				break;
			case ESPERA:
				if (tempo)
					est = trataEspera(estruturas, est);
				break;
			case PRODUCAO:
				est = trataProducao(est);
				break;
			default:
				break;
			}
		}
		return estruturas;
	}

	private static Estrutura trataEspera(List<Estrutura> estruturas, Estrutura est) {
		if (!est.getAbaixo().isEmpty()) {
			boolean flag = true;
			for (MapaJson entry : est.getAbaixo()) {
				Estrutura abaixo = estruturas
						.get(Estrutura.getIndexPorProduto(entry.getChave(), estruturas));
				double quant = entry.getValor();
				if (abaixo.getCanban() < quant)
					flag = false;
			}

			if (flag) {
				est.setSituacao(SituacaoProcesso.PRODUCAO);
				for (MapaJson entry : est.getAbaixo()) {
					double abaixoCanban = estruturas
							.get(Estrutura.getIndexPorProduto(entry.getChave(), estruturas))
							.getCanban();
					double quant = entry.getValor();
					estruturas.get(Estrutura.getIndexPorProduto(entry.getChave(), estruturas))
							.setCanban(abaixoCanban - quant);
				}
			}
		} else {
			est.setSituacao(SituacaoProcesso.PRODUCAO);
			est = trataProducao(est);
		}
		return est;
	}

	private static Estrutura trataProducao(Estrutura est) {
		if (est.getContador() >= est.getTempoNormal()) {
			est.setCanban(est.getCanban() + est.getBatelada());
			est.setProduzido(est.getProduzido() + est.getBatelada());
			est.setContador(0);
			est.setSituacao(SituacaoProcesso.ESPERA);
		} else {
			est.setContador(est.getContador() + 1);
		}
		return est;
	}

	private static Estrutura trataSetup(Estrutura est) {
		if (est.getContador() >= est.getTempoSetupC()) {
			est.setSituacao(SituacaoProcesso.ESPERA);
			est.setContador(0);
		} else {
			est.setContador(est.getContador() + 1);
		}
		return est;
	}

}
