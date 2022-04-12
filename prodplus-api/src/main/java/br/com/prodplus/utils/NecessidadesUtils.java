package br.com.prodplus.utils;

import java.util.HashSet;
import java.util.Set;

import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.ProdutoNecessidade;
import br.com.prodplus.models.auxiliares.Quantidade;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class NecessidadesUtils {

	/**
	 * Retorna a quantidade de matéria prima e produtos necessários.
	 * 
	 * @param produto
	 * @return
	 */
	public static ProdutoNecessidade getNecessidade(Produto produto) {
		ProdutoNecessidade retorno = new ProdutoNecessidade();
		retorno.setProduto(produto);
		retorno.setProdutos(getProdutos(produto, new HashSet<>(), 1));
		if (!produto.getMateriais().isEmpty())
			retorno.setMateriais(new HashSet<>(produto.getMateriais()));
		retorno.getMateriais()
				.addAll(EstruturasUtils.quantidadeNecessaria(produto, new HashSet<>(), 1));
		return retorno;
	}

	private static Set<Quantidade<Produto>> getProdutos(Produto produto,
			Set<Quantidade<Produto>> produtos, int quant) {
		if (!produto.getProdutos().isEmpty()) {
			for (Quantidade<Produto> qp : produto.getProdutos()) {
				double temp = 0;
				if (produtos.contains(qp))
					temp = produtos.stream().filter(q -> q.equals(qp)).map(q -> q.getQuantidade())
							.findFirst().orElse(null);
				produtos.add(
						new Quantidade<Produto>(qp.getTipo(), temp + (qp.getQuantidade() * quant)));
				getProdutos(qp.getTipo(), produtos, (int) (qp.getQuantidade() * quant));
			}
		}
		return produtos;
	}

}
