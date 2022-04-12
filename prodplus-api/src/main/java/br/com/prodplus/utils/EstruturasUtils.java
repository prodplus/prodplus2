package br.com.prodplus.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.Material;
import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.models.auxiliares.MapaJson;
import br.com.prodplus.models.auxiliares.ProdutoNecessidade;
import br.com.prodplus.models.auxiliares.Quantidade;
import br.com.prodplus.models.enums.SituacaoProcesso;
import br.com.prodplus.services.AmostraService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class EstruturasUtils {

	/**
	 * Verifica a quantidade de materiais necessária para produzir um determinada
	 * quantidade de produtos.
	 * 
	 * @param produto
	 * @param materiais
	 * @param quantidade
	 * @return
	 */
	public static Set<Quantidade<Material>> quantidadeNecessaria(Produto produto,
			Set<Quantidade<Material>> materiais, int quantidade) {
		if (!produto.getProdutos().isEmpty())
			materiais = new HashSet<>(
					incluiMateriais(produto.getMateriais(), materiais, quantidade));
		if (!produto.getProdutos().isEmpty())
			for (Quantidade<Produto> qp : produto.getProdutos()) {
				int quant = qp.getQuantidade().intValue() * quantidade;
				materiais = quantidadeNecessaria(qp.getTipo(), materiais, quant);
			}

		return materiais;
	}

	private static Set<Quantidade<Material>> incluiMateriais(Set<Quantidade<Material>> aIncluir,
			Set<Quantidade<Material>> base, int quantidade) {
		for (Quantidade<Material> ai : aIncluir) {
			Quantidade<Material> qm = new Quantidade<Material>(ai.getTipo(),
					ai.getQuantidade() * quantidade);
			if (base.contains(qm)) {
				for (Quantidade<Material> b : base)
					if (b.equals(qm))
						b.setQuantidade(b.getQuantidade() + qm.getQuantidade());
			} else {
				base.add(qm);
			}
		}
		return base;
	}

	/**
	 * 
	 * @param produtoFinal
	 * @param amostraService
	 * @return
	 */
	public static List<Estrutura> getEstrutura(Produto produtoFinal,
			AmostraService amostraService) {
		ProdutoNecessidade necessidade = NecessidadesUtils.getNecessidade(produtoFinal);
		List<Estrutura> estruturas = new ArrayList<>();
		List<Amostra> amostras = amostraService.listarPorProduto(produtoFinal.getId());
		for (Amostra a : amostras)
			estruturas.add(new Estrutura(a.getId().getProduto(), a.getId().getProcesso(),
					new HashSet<>(), new HashSet<>(), a.getBatelada().intValue(),
					a.getPeriodoSetup(), TemposUtils.getMedia(a.getSetups()),
					TemposUtils.getMedia(a.getSetupsCiclicos()), a.getPeriodo(),
					TemposUtils.getMedia(a.getSetupsAciclicos()), a.getQuantidadeAciclica(),
					TemposUtils.getMedia(a.getFinalizacoesMedidas()), a.getQuantidadeFinalizacao(),
					TemposUtils.getTempoNormal(a), 0, 0, produtoFinal.getId(),
					SituacaoProcesso.PARADO, 0.0, 0, false, 0.0));
		for (Quantidade<Produto> prod : necessidade.getProdutos()) {
			List<Amostra> amostrasP = amostraService.listarPorProduto(prod.getTipo().getId());
			for (Amostra a : amostrasP)
				estruturas.add(new Estrutura(a.getId().getProduto(), a.getId().getProcesso(),
						new HashSet<>(), new HashSet<>(), a.getBatelada().intValue(),
						a.getPeriodoSetup(), TemposUtils.getMedia(a.getSetups()),
						TemposUtils.getMedia(a.getSetupsCiclicos()), a.getPeriodo(),
						TemposUtils.getMedia(a.getSetupsAciclicos()), a.getQuantidadeAciclica(),
						TemposUtils.getMedia(a.getFinalizacoesMedidas()),
						a.getQuantidadeFinalizacao(), TemposUtils.getTempoNormal(a), 0, 0,
						produtoFinal.getId(), SituacaoProcesso.PARADO, 0.0, 0, false, 0.0));
		}
		List<Estrutura> estList = trataOrdem(produtoFinal, estruturas);
		estList = getNivelZero(estList);
		estList = getProximosNiveis(estList, 0);
		Collections.sort(estList);
		return estList;
	}

	private static List<Estrutura> getProximosNiveis(List<Estrutura> estruturas, int nivel) {
		List<Integer> proximos = new ArrayList<>();
		boolean flag = false;
		for (Estrutura est : estruturas) {
			if (est.getNivel() == nivel) {
				if (!est.getAcima().isEmpty())
					flag = true;
				for (MapaJson entry : est.getAcima())
					proximos.add(entry.getChave());
			}
		}
		for (Estrutura est : estruturas)
			if (proximos.contains(est.getProduto()) && est.getNivel() <= (nivel + 1))
				est.setNivel(nivel + 1);
		if (flag)
			getProximosNiveis(estruturas, nivel + 1);
		return estruturas;
	}

	private static List<Estrutura> getNivelZero(List<Estrutura> estruturas) {
		for (Estrutura est : estruturas)
			if (est.getAbaixo().isEmpty())
				est.setNivel(0);
			else
				est.setNivel(1);
		return estruturas;
	}

	private static List<Estrutura> trataOrdem(Produto produto, List<Estrutura> estruturas) {
		List<Estrutura> raizes = getPorProduto(produto.getId(), estruturas);
		if (produto.getProdutos() != null && !produto.getProdutos().isEmpty()) {
			for (Quantidade<Produto> qp : produto.getProdutos()) {
				List<Estrutura> abaixoEst = getPorProduto(qp.getTipo().getId(), estruturas);
				for (Estrutura abaixo : abaixoEst) {
					for (Estrutura raiz : raizes) {
						for (Estrutura est : estruturas) {
							if (est.getProduto() == raiz.getProduto()
									&& est.getProcesso() == raiz.getProcesso())
								est.getAbaixo()
										.add(new MapaJson(abaixo.getProduto(), qp.getQuantidade()));
							if (est.getProduto() == abaixo.getProduto()
									&& est.getProcesso() == abaixo.getProcesso())
								est.getAcima()
										.add(new MapaJson(raiz.getProduto(), qp.getQuantidade()));
						}
						trataOrdem(qp.getTipo(), estruturas);
					}
				}
			}
		}
		return new ArrayList<>(estruturas);
	}

	private static List<Estrutura> getPorProduto(Integer idProduto, List<Estrutura> estruturas) {
		return estruturas.stream().filter(e -> e.getProduto() == idProduto)
				.collect(Collectors.toList());
	}

}
