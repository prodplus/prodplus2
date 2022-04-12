package br.com.prodplus.services;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.EstoqueProduto;
import br.com.prodplus.models.enums.TipoEntrada;
import br.com.prodplus.repositories.EstoqueProdutoRepository;
import br.com.prodplus.utils.EstoquesUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class EstoqueProdutoService {

	@Autowired
	private EstoqueProdutoRepository estoqueRepository;
	@Autowired
	private ProdutoService produtoService;

	public EstoqueProduto salvar(@Valid EstoqueProduto estoque) {
		try {
			return this.estoqueRepository.save(estoque);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void salvarTodos(List<EstoqueProduto> estoques) {
		for (EstoqueProduto est : estoques)
			this.salvar(est);
	}

	public EstoqueProduto buscar(Integer id) {
		try {
			return this.estoqueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "estoque não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<EstoqueProduto> listar(Integer idProduto) {
		try {
			return this.estoqueRepository.findAllByProdutoId(idProduto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<EstoqueProduto> listar(Integer idProduto, LocalDateTime de, LocalDateTime ate) {
		try {
			List<EstoqueProduto> estoques = this.estoqueRepository
					.findAllByProdutoIdAndDataBetween(idProduto, de, ate);
			Collections.sort(estoques);
			return estoques;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public EstoqueProduto inserir(EstoqueProduto estoque) {
		try {
			EstoqueProduto atual = this.atual(estoque.getProduto().getId());
			List<EstoqueProduto> estoques = this.listar(estoque.getProduto().getId());
			if (estoque.getData().isAfter(atual.getData()) || estoques == null
					|| estoques.isEmpty()) {
				estoque.setQuantidade(atual.getQuantidade() + estoque.getEntrada());
				return this.estoqueRepository.save(estoque);
			} else {
				estoques.add(this.estoqueRepository.save(estoque));
				List<EstoqueProduto> calculado = EstoquesUtils.calculaEstoquePeriodoP(estoques);
				this.salvarTodos(calculado);
				Collections.sort(calculado);
				Collections.reverse(calculado);
				return calculado.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public EstoqueProduto atual(Integer idProduto) {
		return this.estoqueRepository.findFirstByProdutoIdOrderByDataDesc(idProduto)
				.orElse(new EstoqueProduto(null, LocalDateTime.now(), 0.0, 0.0,
						TipoEntrada.AUTOMATICA, this.produtoService.buscar(idProduto)));
	}

	public void excluir(Integer id) {
		try {
			this.estoqueRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
