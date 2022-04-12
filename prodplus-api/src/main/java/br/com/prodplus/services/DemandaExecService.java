package br.com.prodplus.services;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.auxiliares.DemandaId;
import br.com.prodplus.repositories.DemandaExecRepository;
import br.com.prodplus.utils.DemandasUtils;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class DemandaExecService {

	@Autowired
	private DemandaExecRepository demandaRepository;

	public DemandaExec salvar(@Valid DemandaExec demanda) {
		try {
			return this.demandaRepository.save(demanda);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "demanda já cadastrada!",
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

	public boolean salvar(List<DemandaExec> demandas) {
		try {
			this.demandaRepository.saveAll(demandas);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public DemandaExec buscar(Integer produto, Integer ano, Integer semana) {
		try {
			return this.demandaRepository.findById(new DemandaId(produto, ano, semana))
					.orElse(new DemandaExec(produto, ano, semana, null));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaExec> buscar(List<DemandaId> ids) {
		try {
			List<DemandaExec> demandas = new ArrayList<>();
			ids.forEach(
					id -> demandas.add(this.buscar(id.getProduto(), id.getAno(), id.getSemana())));
			return demandas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaExec> listar(Integer idProduto) {
		try {
			List<DemandaExec> demandas = this.demandaRepository.findAllByIdProduto(idProduto);
			Collections.sort(demandas);
			return demandas;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaExec> listar(Integer idProduto, Integer ano) {
		try {
			List<DemandaExec> demandas = this.demandaRepository
					.findAllByIdProdutoAndIdAno(idProduto, ano);
			if (demandas == null || demandas.isEmpty()) {
				long semanasAno = LocalDate.of(ano, Month.JUNE, 1)
						.range(WeekFields.ISO.weekOfWeekBasedYear()).getMaximum();
				List<DemandaExec> aSalvar = new ArrayList<>();
				for (int i = 1; i <= semanasAno; i++)
					aSalvar.add(new DemandaExec(idProduto, ano, i, null));
				this.salvar(aSalvar);
				Collections.sort(aSalvar);
				return aSalvar;
			}
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
			List<DemandaExec> demandas = this.listar(idProduto, ano);
			this.demandaRepository.deleteAll(demandas);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Integer mediaMovel(Integer idProduto) {
		try {
			Integer semanasAno = LocalDate.now()
					.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
			if (semanasAno < 52)
				semanasAno++;
			else
				semanasAno = 1;
			return DemandasUtils.mediaMovel(this.listar(idProduto), semanasAno);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public List<DemandaExec> listarPorSemana(int semana) {
		return this.demandaRepository.findAllByIdSemana(semana);
	}

}
