package br.com.prodplus.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.ProdutoSaida;
import br.com.prodplus.models.forms.ProdutoSaidaForm;
import br.com.prodplus.repositories.ProdutoSaidaRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class ProdutoSaidaService {

	@Autowired
	private ProdutoSaidaRepository produtoRepository;
	@Autowired
	private ProdutoService produtoService;

	public ProdutoSaida salvar(@Valid ProdutoSaidaForm produto) {
		try {
			return this.produtoRepository.save(produto.converter(this.produtoService));
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public ProdutoSaida atualizar(Integer id, @Valid ProdutoSaidaForm produto) {
		try {
			return this.produtoRepository.findById(id).map(p -> {
				p.setPrazoEntrega(produto.getPrazoEntrega());
				p.setTempoPadrao(produto.getTempoPadrao());
				p.setValorVenda(produto.getValorVenda());
				return this.produtoRepository.save(p);
			}).orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public ProdutoSaida buscar(Integer id) {
		try {
			return this.produtoRepository.findById(id)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "produto não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<ProdutoSaida> listar() {
		try {
			return this.produtoRepository.findAll(Sort.by(Direction.ASC, "produto"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Page<ProdutoSaida> listar(int pagina, int quant) {
		try {
			Pageable pageable = PageRequest.of(pagina, quant, Direction.ASC, "produto");
			return this.produtoRepository.findAll(pageable);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.produtoRepository.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
