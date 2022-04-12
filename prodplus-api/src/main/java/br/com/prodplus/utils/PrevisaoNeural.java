package br.com.prodplus.utils;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.temporal.TemporalDataDescription;
import org.encog.ml.data.temporal.TemporalMLDataSet;
import org.encog.ml.data.temporal.TemporalPoint;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.arrayutil.NormalizeArray;
import org.encog.util.arrayutil.NormalizedField;

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.DemandaPrev;
import br.com.prodplus.models.Produto;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class PrevisaoNeural {

	private static final double MAX_ERROR = 0.0001;
	private static double[] semanasCadastradas;
	private static double[] anosCadastrados;
	private static double[] quantidadesCadastradas;
	private static Produto PRODUTO;
	private static NormalizedField normQuant = new NormalizedField(NormalizationAction.Normalize,
			"quant");

	/**
	 * Calcula a previsao de demanda baseada nos padrões encontrados por uma rede
	 * neural.
	 * 
	 * @param demandas
	 * @param produto
	 * @return
	 */
	public static List<DemandaPrev> previsao(List<DemandaExec> demandas, Produto produto) {
		PRODUTO = produto;
		List<DemandaPrev> previstas = new ArrayList<>();
		List<DemandaPrev> futuras = retornaFuturas();
		String file_name = String.format("%s%04d%04d%02d%s", PRODUTO.getId(),
				futuras.get(0).getId().getAno(), futuras.get(0).getId().getSemana(), ".eg");
		File arquivo = new File(file_name);
		List<DemandaExec> filtradas = demandas.stream().filter(d -> d.getQuantidade() != null)
				.collect(Collectors.toList());
		anosCadastrados = new double[filtradas.size()];
		semanasCadastradas = new double[filtradas.size()];
		quantidadesCadastradas = new double[filtradas.size()];

		Collections.sort(filtradas);

		for (int i = 0; i < filtradas.size(); i++) {
			anosCadastrados[i] = filtradas.get(i).getId().getAno();
			semanasCadastradas[i] = filtradas.get(i).getId().getSemana();
			quantidadesCadastradas[i] = filtradas.get(i).getQuantidade();
		}

		BasicNetwork network = createNetwork(new int[] { 2, 4, 1 });
		MLDataSet treino = trainGenerator(filtradas);
		train(network, treino, arquivo);
		previstas.addAll(prediction(network, treino, futuras, filtradas));
		treino.close();
		Encog.getInstance().shutdown();
		return previstas;
	}

	private static List<DemandaPrev> prediction(BasicNetwork network, MLDataSet treino,
			List<DemandaPrev> futuras, List<DemandaExec> demandas) {
		double[] anosFuturos = new double[futuras.size()];
		double[] semanasFuturas = new double[futuras.size()];

		for (int i = 0; i < futuras.size(); i++) {
			anosFuturos[i] = futuras.get(i).getId().getAno();
			semanasFuturas[i] = futuras.get(i).getId().getSemana();
		}

		double[] anosFuturosNorm = normalizeArray(anosFuturos);
		double[] semanasFuturasNorm = normalizeArray(semanasFuturas);

		for (int i = 0; i < futuras.size(); i++) {
			MLData input = new BasicMLData(2);
			input.setData(0, anosFuturosNorm[i]);
			input.setData(1, semanasFuturasNorm[i]);
			MLData output = network.compute(input);
			double predicao = normQuant.deNormalize(output.getData(0));
			futuras.get(i).setQuantidade((double) Math.round(predicao));
		}

		return futuras;
	}

	private static void train(BasicNetwork network, MLDataSet treino, File arquivo) {
		final Train treinamento = new ResilientPropagation(network, treino);
		int epoch = 1;
		do {
			treinamento.iteration();
			epoch++;
		} while (treinamento.getError() > MAX_ERROR && epoch <= 2000);
		treinamento.finishTraining();
		EncogDirectoryPersistence.saveObject(arquivo, network);
	}

	private static MLDataSet trainGenerator(List<DemandaExec> demandas) {
		TemporalMLDataSet result = new TemporalMLDataSet(2, 1);
		List<TemporalDataDescription> descriptions = new ArrayList<>();

		for (int i = 0; i < 3; i++)
			descriptions.add(
					new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, false));
		descriptions.add(new TemporalDataDescription(TemporalDataDescription.Type.RAW, true, true));
		descriptions.forEach(d -> result.addDescription(d));

		double[] anosNorm = normalizeArray(anosCadastrados);
		double[] semanasNorm = normalizeArray(semanasCadastradas);
		normQuant.setActualHigh(getDemandaMaxima(demandas));
		normQuant.setActualLow(0.0);
		normQuant.setNormalizedHigh(1.0);
		normQuant.setNormalizedLow(0.0);
		double[] quantidadesNorm = new double[quantidadesCadastradas.length];
		for (int i = 0; i < quantidadesNorm.length; i++)
			quantidadesNorm[i] = normQuant.normalize(quantidadesCadastradas[i]);

		int sequence = 0;
		for (int i = 0; i < demandas.size(); i++) {
			TemporalPoint point = new TemporalPoint(4);
			sequence++;
			point.setSequence(sequence);
			point.setData(0, anosNorm[i]);
			point.setData(1, semanasNorm[i]);
			point.setData(2, quantidadesNorm[i]);
			result.getPoints().add(point);
		}
		result.generate();
		return result;
	}

	private static double getDemandaMaxima(List<DemandaExec> demandas) {
		return demandas.stream().max(Comparator.comparing(DemandaExec::getQuantidade))
				.orElseThrow(NoSuchElementException::new).getQuantidade() * 2;
	}

	private static double[] normalizeArray(double[] array) {
		final double hi = 1.0;
		final double lo = 0.0;
		NormalizeArray norm = new NormalizeArray();
		norm.setNormalizedHigh(hi);
		norm.setNormalizedLow(lo);
		return norm.process(array);
	}

	private static BasicNetwork createNetwork(int[] camadas) {
		BasicNetwork network = new BasicNetwork();
		for (int i = 0; i < camadas.length; i++)
			network.addLayer(new BasicLayer(camadas[i]));
		network.getStructure().finalizeStructure();
		network.reset();
		return network;
	}

//	private static double getDesvioPadrao(List<DemandaExec> demandas, Integer semana) {
//		List<Double> valoresC = new ArrayList<>();
//		valoresC = demandas.stream().filter(d -> d.getId().getSemana() == semana)
//				.map(d -> d.getQuantidade()).collect(Collectors.toList());
//		double valores[] = new double[valoresC.size()];
//		for (int i = 0; i < valoresC.size(); i++)
//			valores[i] = valoresC.get(i);
//		StandardDeviation desvio = new StandardDeviation();
//		return desvio.evaluate(valores);
//	}

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
