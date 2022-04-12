package br.com.prodplus.utils;

import java.util.Collections;
import java.util.List;

import br.com.prodplus.models.DemandaExec;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class PrevisaoFuncional {

	private static double[] observados; // demandas observadas
	private static double[] previsoes; // previsões
	private static double[] tendencias; // tendências
	private static double alfa = 0.9; // coeficiente de suavização
	private static double beta = 0.9; // coeficiente de suavização
	private static double[] niveis; // níveis de observação
	private static int nivel;
	private static double[] erros; // níveis de erro
	private static double erroMedio;

	public static double prever(List<DemandaExec> demandas) {
		if (!demandas.isEmpty()) {
			nivel = demandas.size();
			observados = new double[nivel];
			previsoes = new double[nivel];
			tendencias = new double[nivel];
			niveis = new double[nivel];
			erros = new double[nivel];
			Collections.sort(demandas);
			for (int i = 0; i < demandas.size(); i++)
				observados[i] = demandas.get(i).getQuantidade();
			iteraCoeficientes();
			return Math.round(previsoes[nivel - 1]);
		} else {
			throw new RuntimeException("sem demandas!");
		}
	}

	private static void iteraCoeficientes() {
		double alfaC = 0.0;
		double betaC = 0.0;
		double erroTemp = 0.0;

		for (int i = 9; i >= 1; i--) {
			for (int j = 9; j >= 1; j--) {
				alfaC = i / 10;
				betaC = j / 10;
				calculaPrimeiroNivel();
				for (int t = 1; t < nivel; t++) {
					niveis[t] = calculaNivel(alfaC, t);
					tendencias[t] = calculaTendencia(betaC, t);
					previsoes[t] = calculaPrevisao(t);
					erros[t] = Math.abs(previsoes[t - 1] - observados[t]);
				}
				calculaErroMedio();
				if (erroTemp == 0.0 || erroMedio < erroTemp) {
					erroTemp = erroMedio;
					alfa = alfaC;
					beta = betaC;
				}
			}
		}

		calculaPrimeiroNivel();
		for (int t = 1; t < nivel; t++) {
			niveis[t] = calculaNivel(alfa, t);
			tendencias[t] = calculaTendencia(beta, t);
			previsoes[t] = calculaPrevisao(t);
			erros[t] = Math.abs(previsoes[t - 1] - observados[t]);
		}

		calculaErroMedio();
	}

	private static void calculaErroMedio() {
		erroMedio = 0;
		for (int i = 0; i < erros.length; i++)
			erroMedio += Math.abs(erros[i]);
		erroMedio /= nivel;
	}

	private static double calculaPrevisao(int n) {
		return Math.round(niveis[n] + tendencias[n]);
	}

	private static double calculaTendencia(double betaI, int n) {
		return (betaI * (niveis[n] - niveis[n - 1] + (1 + betaI) * (tendencias[n - 1])));
	}

	private static double calculaNivel(double alfaI, int n) {
		return (alfaI * observados[n]) + (1 - alfaI) * (niveis[n - 1] + tendencias[n - 1]);
	}

	private static void calculaPrimeiroNivel() {
		niveis[0] = observados[0];
		if (nivel >= 4)
			tendencias[0] = ((observados[1] - observados[0]) + (observados[3] - observados[2])) / 2;
		else if (nivel >= 2)
			tendencias[0] = (observados[1] - observados[0]);
		else
			tendencias[0] = 0;
		previsoes[0] = Math.round(niveis[0] + tendencias[0]);
	}

}
