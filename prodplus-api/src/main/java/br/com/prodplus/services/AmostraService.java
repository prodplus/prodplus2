package br.com.prodplus.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.auxiliares.AmostraId;
import br.com.prodplus.repositories.AmostraRepository;
import br.com.prodplus.utils.TemposUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class AmostraService {

	@Autowired
	private AmostraRepository amostraRepository;
	@Autowired
	private ConfiguracaoService configService;

	public Amostra salvar(@Valid Amostra amostra) {
		try {
			return this.amostraRepository.save(amostra);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void salvar(List<Amostra> amostras) {
		for (Amostra a : amostras)
			this.salvar(a);
	}

	public Amostra buscar(AmostraId id) {
		try {
			return this.amostraRepository.findById(id).orElse(new Amostra(id));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Amostra> listarPorProcesso(Integer idProcesso) {
		try {
			return this.amostraRepository.findAllByIdProcesso(idProcesso);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<Amostra> listarPorProduto(Integer idProduto) {
		try {
			return this.amostraRepository.findAllByIdProduto(idProduto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Integer getTempoMedio(AmostraId id) {
		try {
			return this.amostraRepository.findById(id).map(a -> TemposUtils.getTempoMedio(a))
					.orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "amostra não localizada!",
					e.getCause());
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "amostra vazia!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Integer getTempoNormal(AmostraId id) {
		try {
			return this.amostraRepository.findById(id).map(a -> TemposUtils.getTempoNormal(a))
					.orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "amostra não localizada!",
					e.getCause());
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "amostra vazia!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Integer getTempoPadrao(AmostraId id) {
		try {
			return this.amostraRepository.findById(id)
					.map(a -> TemposUtils.getTempoPadrao(a, this.configService.buscar()))
					.orElseThrow(EntityNotFoundException::new);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "amostra não localizada!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluir(AmostraId id) {
		try {
			Amostra a = this.amostraRepository.findById(id)
					.orElseThrow(EntityNotFoundException::new);
			this.amostraRepository.delete(a);
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "amostra não localizada!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
