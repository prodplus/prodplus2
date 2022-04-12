package br.com.prodplus.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.EstoqueProduto;
import br.com.prodplus.models.LoteProduto;
import br.com.prodplus.models.enums.TipoEntrada;
import br.com.prodplus.models.forms.LoteProdutoForm;
import br.com.prodplus.repositories.LoteProdutoRepository;
import br.com.prodplus.utils.GeradorRastreio;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class LoteProdutoService {

	@Autowired
	private LoteProdutoRepository loteRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private EstoqueProdutoService estoqueService;

	public LoteProduto salvar(@Valid LoteProdutoForm lote) {
		try {
			LoteProduto loteM = lote.converter(this.produtoService, this);
			if (loteM.getQuantAtual() > 0)
				this.acertaEstoque(loteM);
			else
				loteM.setAtivo(false);
			loteM.setRastreio(GeradorRastreio.loteProduto(loteM));
			return this.loteRepository.save(loteM);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.getCause();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private void acertaEstoque(LoteProduto lote) {
		EstoqueProduto novo = new EstoqueProduto(null, lote.getProducao(), 0.0,
				lote.getQuantAtual(), TipoEntrada.AUTOMATICA, lote.getProduto());
		this.estoqueService.inserir(novo);
	}

	private void descartar(LoteProduto lote) {
		EstoqueProduto novo = new EstoqueProduto(null, lote.getProducao(), 0.0,
				lote.getQuantAtual() * -1, TipoEntrada.AUTOMATICA, lote.getProduto());
		this.estoqueService.inserir(novo);
	}

	public LoteProduto buscar(Integer id) {
		try {
			return this.loteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.getCause();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public LoteProduto atualizar(Integer id, LoteProdutoForm lote) {
		try {
			return this.loteRepository.findById(id).map(l -> {
				this.descartar(l);
				l.setAtivo(lote.isAtivo());
				l.setCustoTotal(l.getCustoTotal());
				l.setProducao(lote.getProducao());
				l.setProduto(this.produtoService.buscar(lote.getProduto()));
				l.setQuantAtual(lote.getQuantAtual());
				l.setQuantInicial(lote.getQuantInicial());
				l.setRastreio(GeradorRastreio.loteProduto(l));
				this.acertaEstoque(l);
				return this.loteRepository.save(l);
			}).orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote não localizado!",
					e.getCause());
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.getCause();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public LoteProduto buscar(String rastreio) {
		try {
			return this.loteRepository.findByRastreio(rastreio)
					.orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.getCause();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<LoteProduto> listar(Integer idProduto) {
		try {
			return this.loteRepository.findAllByProdutoIdAndAtivo(idProduto, true,
					Sort.by(Sort.Direction.ASC, "producao"));
		} catch (Exception e) {
			e.getCause();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			LoteProduto lote = this.buscar(id);
			this.descartar(lote);
			this.loteRepository.delete(lote);
		} catch (Exception e) {
			e.getCause();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
