package br.com.prodplus.utils;

import java.util.HashSet;

import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.OrganoNode;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
public class OrganoUtils {

	/**
	 * Gera a estrutura organizacional da explosão do produto informado.
	 * 
	 * @param produto
	 * @param quantidade
	 * @return
	 */
	public static OrganoNode getOrganograma(Produto produto, int quantidade) {
		OrganoNode node = new OrganoNode(produto.getDescricao(), String.format("%d %s", quantidade,
				produto.getTipoMetrico().getDescricao().toUpperCase()), new HashSet<>());
		produto.getMateriais()
				.forEach(mq -> node.getChildren()
						.add(new OrganoNode(
								mq.getTipo().getDescricao(), String.format("%d %s",
										mq.getQuantidade().intValue() * quantidade, mq.getTipo()
												.getTipoProducao().getDescricao().toUpperCase()),
								new HashSet<>())));
		produto.getProdutos().forEach(pq -> {
			OrganoNode nodeI = getOrganograma(pq.getTipo(),
					pq.getQuantidade().intValue() * quantidade);
			node.getChildren().add(nodeI);
		});

		return node;
	}

}
