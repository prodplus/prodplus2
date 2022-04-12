package br.com.prodplus.services;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.DemandaPrev;
import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.DemandaId;
import br.com.prodplus.repositories.DemandaPrevRepository;
import br.com.prodplus.utils.DemandasUtils;
import br.com.prodplus.utils.PrevisaoMediasMoveis;
import br.com.prodplus.utils.PrevisaoNeural;
import br.com.prodplus.utils.PrevisaoNeuralU;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class DemandaPrevService {

	@Autowired
	private DemandaPrevRepository demandaRepository;
	@Autowired
	private DemandaExecService demandaService;
	@Autowired
	private ProdutoService produtoService;

	public DemandaPrev salvar(@Valid DemandaPrev demanda) {
		try {
			return this.demandaRepository.save(demanda);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void salvar(List<DemandaPrev> demandas) {
		try {
			this.demandaRepository.saveAll(demandas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaPrev> listar(Integer idProduto) {
		try {
			List<DemandaPrev> demandas = this.demandaRepository.findAllByIdProduto(idProduto);
			Collections.sort(demandas);
			return demandas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaPrev> listar(Integer idProduto, Integer ano) {
		try {
			List<DemandaPrev> demandas = this.demandaRepository
					.findAllByIdProdutoAndIdAno(idProduto, ano);
			Collections.sort(demandas);
			return demandas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public void excluirAno(Integer idProduto, Integer ano) {
		try {
			List<DemandaPrev> demandas = this.listar(idProduto, ano);
			this.demandaRepository.deleteAll(demandas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	/**
	 * Previsão neural, através do uso de uma rede neural.
	 * 
	 * @param idProduto
	 * @return
	 */
	public List<DemandaPrev> previsao(Integer idProduto) {
		try {
			Produto produto = this.produtoService.buscar(idProduto);
			List<DemandaExec> demandas = this.demandaService.listar(idProduto);
			return PrevisaoNeural.previsao(demandas, produto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	/**
	 * Previsão funcional, através do cálculo de médias móveis.
	 * 
	 * @param idProduto
	 * @return
	 */
	public List<DemandaPrev> previsaoF(Integer idProduto) {
		try {
			List<DemandaPrev> previsoes = new ArrayList<>();
			for (int i = 0; i < 52; i++) {
				LocalDate data = LocalDate.now().plusWeeks(i);
				Integer semanaAno = data.get(WeekFields.ISO.weekOfWeekBasedYear());
				List<DemandaExec> filtradas = this.demandaService.listarPorSemana(semanaAno);
				Double quantidade = Double.valueOf(DemandasUtils.mediaMovel(filtradas, semanaAno));
				DemandaPrev nova = new DemandaPrev(idProduto, data.getYear(), semanaAno,
						quantidade);
				previsoes.add(nova);
			}
			return previsoes;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaPrev> previsaoX(Integer idProduto) {
		try {
			Produto produto = this.produtoService.buscar(idProduto);
			List<DemandaExec> demandas = this.demandaService.listar(idProduto);
			return PrevisaoMediasMoveis.previsao(demandas, produto);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public DemandaPrev previsaoYU(Integer idProduto, int semana, int ano) {
		try {
			Produto produto = this.produtoService.buscar(idProduto);
			List<DemandaExec> demandas = this.demandaService.listarPorSemana(semana);
			if (demandas != null && !demandas.isEmpty()) {
				List<DemandaExec> filtradas = demandas.stream()
						.filter(d -> d.getQuantidade() != null).collect(Collectors.toList());
				if (filtradas != null && !filtradas.isEmpty()) {
					Collections.sort(filtradas);
					return this.demandaRepository.findById(new DemandaId(idProduto, ano, semana))
							.orElse(PrevisaoNeuralU.previsao(filtradas, produto));
				} else {
					return this.demandaRepository.findById(new DemandaId(idProduto, ano, semana))
							.orElse(new DemandaPrev(idProduto, ano, semana, null));
				}
			} else {
				return this.demandaRepository.findById(new DemandaId(idProduto, ano, semana))
						.orElse(new DemandaPrev(idProduto, ano, semana, null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaPrev> previsaoY(Integer idProduto) {
		try {
			LocalDate referencia = LocalDate.now();
			List<DemandaPrev> previsoes = new ArrayList<>();
			for (int i = 0; i < 10; i++) {
				previsoes.add(this.previsaoYU(idProduto,
						referencia.get(WeekFields.ISO.weekOfWeekBasedYear()),
						referencia.getYear()));
				referencia = referencia.plusWeeks(1);
			}
			return previsoes;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public DemandaPrev previsaoU(Integer idProduto) {
		try {
			List<DemandaExec> demandas = this.demandaService.listar(idProduto);
			LocalDate hoje = LocalDate.now();
			Integer semanaAno = hoje.get(WeekFields.ISO.weekOfWeekBasedYear());
			Double quantidade = Double.valueOf(DemandasUtils.mediaMovel(demandas, semanaAno));
			return new DemandaPrev(idProduto, hoje.getYear(), semanaAno, quantidade);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

}
