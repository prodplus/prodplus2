package br.com.prodplus.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.models.auxiliares.MapaJson;
import br.com.prodplus.models.enums.PeriodoSetup;
import br.com.prodplus.models.enums.SituacaoProcesso;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class SimulacoesUtils {

	/**
	 * Trata um segundo de produção, simulando todos os processos.
	 * 
	 * @param estruturas
	 * @param faltantes
	 * @param primeiroTurno
	 * @param passados
	 * @return
	 */
	public static List<Estrutura> trataSegundo(List<Estrutura> estruturas, long faltantes,
			boolean primeiroTurno, long passados) {
		List<Estrutura> novo = new ArrayList<>();
		for (Estrutura est : estruturas) {
			est = preTratamento(est, passados, primeiroTurno);
			switch (est.getSituacao()) {
			case PARADO:
				est = preTratamento(est, passados, primeiroTurno);
				break;
			case SETUPN:
				est = trataSetup(est, faltantes, est.getTempoSetup());
				break;
			case SETUPC:
				est = trataSetup(est, faltantes, est.getTempoSetupC());
				break;
			case SETUPA:
				est = trataSetup(est, faltantes, est.getTempoSetupA());
				break;
			case SETUPF:
				est = trataSetup(est, faltantes, est.getTempoSetupF());
				break;
			case ESPERA:
				est = trataEspera(est, estruturas, faltantes);
				break;
			case PRODUCAO:
				est = trataProducao(est, estruturas, faltantes);
				break;
			default:
				est.setSituacao(SituacaoProcesso.PARADO);
				break;
			}
			novo.add(est);
		}
		return novo;
	}

	private static Estrutura trataProducao(Estrutura est, List<Estrutura> estruturas,
			long faltantes) {
		if ((est.getTempoNormal() - est.getContador()) < faltantes) {
			if (est.getContador() < est.getTempoNormal())
				est.setContador(est.getContador() + 1);
			else {
				for (MapaJson entry : est.getAbaixo()) {
					double abaixoCanban = estruturas
							.get(Estrutura.getIndexPorProduto(entry.getChave(), estruturas))
							.getCanban();
					estruturas.get(Estrutura.getIndexPorProduto(entry.getChave(), estruturas))
							.setCanban(abaixoCanban - entry.getValor());
				}
				est.setCanban(est.getCanban() + est.getBatelada());
				est.setProduzido(est.getProduzido() + est.getBatelada());
				est.setContador(0);
				est.setSituacao(SituacaoProcesso.ESPERA);
			}
		} else {
			est.setSituacao(SituacaoProcesso.PARADO);
		}

		return est;
	}

	private static Estrutura trataEspera(Estrutura est, List<Estrutura> estruturas,
			long faltantes) {
		if (est.getTempoNormal() > faltantes) {
			est.setSituacao(SituacaoProcesso.PARADO);
			return est;
		} else if (!est.getAbaixo().isEmpty()) {
			if (verificaCanban(est, estruturas))
				est.setSituacao(SituacaoProcesso.PRODUCAO);
		} else {
			est.setSituacao(SituacaoProcesso.PRODUCAO);
		}
		return est;
	}

	private static boolean verificaCanban(Estrutura est, List<Estrutura> estruturas) {
		boolean flag = true;
		for (MapaJson entry : est.getAbaixo()) {
			Estrutura abaixo = estruturas
					.get(Estrutura.getIndexPorProduto(entry.getChave(), estruturas));
			if (abaixo.getCanban() < entry.getValor())
				flag = false;
		}
		return flag;
	}

	private static Estrutura trataSetup(Estrutura est, long faltantes, Integer tempo) {
		if ((tempo - est.getContador()) < faltantes) {
			if (est.getContador() > tempo) {
				est.setContador(0);
				est.setSituacao(SituacaoProcesso.ESPERA);
			} else {
				est.setContador(est.getContador() + 1);
			}
		}
		return est;
	}

	private static Estrutura preTratamento(Estrutura est, long passados, boolean primeiroTurno) {
		if (!est.isContSetup()) {
			if (est.getSetup().equals(PeriodoSetup.DIA) && primeiroTurno) {
				est.setContador(0);
				est.setSituacao(SituacaoProcesso.SETUPN);
				return est;
			} else if (est.getSetup().equals(PeriodoSetup.TURNO) && passados == 0) {
				est.setContador(0);
				est.setSituacao(SituacaoProcesso.SETUPN);
				return est;
			} else if (est.getPeriodoSetup() > 0) {
				if (passados % est.getPeriodoSetup() == 0) {
					est.setContador(0);
					est.setSituacao(SituacaoProcesso.SETUPC);
					return est;
				}
			} else if (est.getQuantSetupA() > 0) {
				if (est.getProduzido() % est.getQuantSetupA() == 0) {
					est.setContador(0);
					est.setSituacao(SituacaoProcesso.SETUPA);
					return est;
				}
			} else if (est.getQuantSetupF() > 0) {
				if (est.getProduzido() % est.getQuantSetupF() == 0) {
					est.setContador(0);
					est.setSituacao(SituacaoProcesso.SETUPF);
					return est;
				}
			} else {
				est.setContador(0);
				est.setSituacao(SituacaoProcesso.ESPERA);
				return est;
			}
		}
		return est;
	}

	/**
	 * Verifica se todos os processos estão parados.
	 * 
	 * @param estruturas
	 * @param inicio
	 * @return
	 */
	public static boolean todosParados(List<Estrutura> estruturas, boolean inicio) {
		for (Estrutura est : estruturas)
			if (!est.getSituacao().equals(SituacaoProcesso.PARADO) && !inicio)
				return false;
		return true;
	}

}
