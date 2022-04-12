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

import br.com.prodplus.models.EstoqueMaterial;
import br.com.prodplus.models.Material;
import br.com.prodplus.models.enums.TipoEntrada;
import br.com.prodplus.repositories.EstoqueMaterialRepository;
import br.com.prodplus.utils.EstoquesUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class EstoqueMaterialService {

	@Autowired
	private EstoqueMaterialRepository estoqueRepository;
	@Autowired
	private MaterialService materialService;

	public EstoqueMaterial salvar(@Valid EstoqueMaterial estoque) {
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

	private void salvarTodos(List<EstoqueMaterial> estoques) {
		for (EstoqueMaterial est : estoques)
			this.salvar(est);
	}

	public EstoqueMaterial buscar(Integer id) {
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

	public EstoqueMaterial inserir(EstoqueMaterial estoque) {
		EstoqueMaterial atual = this.atual(estoque.getMaterial().getId());
		List<EstoqueMaterial> estoques = this.listar(estoque.getMaterial().getId());
		if (estoque.getData().isAfter(atual.getData()) || estoques == null || estoques.isEmpty()) {
			estoque.setQuantidade(atual.getQuantidade() + estoque.getEntrada());
			return this.estoqueRepository.save(estoque);
		} else {
			estoques.add(this.salvar(estoque));
			List<EstoqueMaterial> calculado = EstoquesUtils.calculaEstoquePeriodoM(estoques);
			this.salvarTodos(calculado);
			Collections.sort(calculado);
			Collections.reverse(calculado);
			return calculado.get(0);
		}
	}

	public List<EstoqueMaterial> listar(Integer idMaterial) {
		try {
			return this.estoqueRepository.findAllByMaterialIdOrderByDataDesc(idMaterial);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<EstoqueMaterial> listar(Integer idMaterial, LocalDateTime de, LocalDateTime ate) {
		try {
			List<EstoqueMaterial> estoques = this.estoqueRepository
					.findAllByMaterialIdAndDataBetween(idMaterial, de, ate);
			Collections.sort(estoques);
			return estoques;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
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

	public EstoqueMaterial atual(Integer idMaterial) {
		Material material = this.materialService.buscar(idMaterial);
		return this.estoqueRepository.findFirstByMaterialIdOrderByDataDesc(idMaterial)
				.orElse(new EstoqueMaterial(null, LocalDateTime.now(), 0.0, 0.0,
						TipoEntrada.AUTOMATICA, material));
	}

}
