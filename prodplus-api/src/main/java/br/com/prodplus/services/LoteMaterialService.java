package br.com.prodplus.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.EstoqueMaterial;
import br.com.prodplus.models.LoteMaterial;
import br.com.prodplus.models.enums.TipoEntrada;
import br.com.prodplus.models.forms.LoteMaterialForm;
import br.com.prodplus.repositories.LoteMaterialRepository;
import br.com.prodplus.utils.GeradorRastreio;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class LoteMaterialService {

	@Autowired
	private LoteMaterialRepository loteRepository;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private EstoqueMaterialService estoqueService;

	public LoteMaterial salvar(@Valid LoteMaterialForm lote) {
		try {
			LoteMaterial loteM = lote.converter(this.materialService, this);
			if (loteM.getQuantAtual() > 0)
				this.acertaEstoque(loteM);
			else
				loteM.setAtivo(false);
			loteM.setRastreio(GeradorRastreio.loteMaterial(loteM));
			return this.loteRepository.save(loteM);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private void acertaEstoque(LoteMaterial lote) {
		if (lote.getEntrada() != null) {
			EstoqueMaterial novo = new EstoqueMaterial(null, lote.getEntrada(), 0.0,
					lote.getQuantInicial(), TipoEntrada.AUTOMATICA, lote.getMaterial());
			this.estoqueService.inserir(novo);
		}
	}

	public LoteMaterial atualizar(Integer id, LoteMaterialForm lote) {
		try {
			return this.loteRepository.findById(id).map(l -> {
				l.setAtivo(lote.isAtivo());
				l.setCustoTotal(lote.getCustoTotal());
				l.setCustoUnitario(lote.getCustoUnitario());
				l.setEntrada(lote.getEntrada());
				l.setMaterial(this.materialService.buscar(lote.getMaterial()));
				l.setPedido(lote.getPedido());
				l.setQuantAtual(lote.getQuantAtual());
				l.setQuantInicial(lote.getQuantInicial());
				l.setRastreio(GeradorRastreio.loteMaterial(l));
				return this.loteRepository.save(l);
			}).orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote material não localizado!",
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

	public LoteMaterial buscar(Integer id) {
		try {
			return this.loteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote material não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public LoteMaterial buscar(String rastreio) {
		try {
			return this.loteRepository.findByRastreio(rastreio)
					.orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote material não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<LoteMaterial> listar(Integer idMaterial) {
		try {
			return this.loteRepository.findAllByMaterialIdAndAtivo(idMaterial, true,
					Sort.by(Sort.Direction.ASC, "pedido"));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(Integer id) {
		try {
			this.loteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "não é possível excluir o lote!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void descartar(Integer id) {
		try {
			this.loteRepository.findById(id).map(l -> {
				if (l.getEntrada() != null && l.getEntrada().isBefore(LocalDateTime.now())) {
					EstoqueMaterial novo = new EstoqueMaterial(null, LocalDateTime.now(), 0.0,
							l.getQuantAtual() * -1, TipoEntrada.AUTOMATICA, l.getMaterial());
					return this.estoqueService.inserir(novo);
				} else
					return null;
			}).orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "lote material não localizado!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
