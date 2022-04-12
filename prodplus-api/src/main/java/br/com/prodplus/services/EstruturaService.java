package br.com.prodplus.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.Material;
import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.models.auxiliares.Quantidade;
import br.com.prodplus.utils.EstruturasUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class EstruturaService {

	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private AmostraService amostraService;

	public Set<Quantidade<Material>> getQuantidadeMaterial(Integer idProduto, int quant) {
		try {
			return EstruturasUtils.quantidadeNecessaria(this.produtoService.buscar(idProduto),
					new HashSet<>(), quant);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Estrutura> getEstrutura(Integer idProduto) {
		try {
			return EstruturasUtils.getEstrutura(this.produtoService.buscar(idProduto),
					this.amostraService);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
