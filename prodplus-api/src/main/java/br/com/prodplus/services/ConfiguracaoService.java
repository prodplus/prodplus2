package br.com.prodplus.services;

import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.prodplus.models.Configuracao;
import br.com.prodplus.models.auxiliares.Turno;
import br.com.prodplus.repositories.ConfiguracaoRepository;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Service
public class ConfiguracaoService {

	@Autowired
	private ConfiguracaoRepository configRepository;

	public Configuracao salvar(@Valid Configuracao config) {
		try {
			config.setId(1);
			return this.configRepository.save(config);
		} catch (ValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "erro de validação!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Configuracao buscar() {
		try {
			return this.configRepository.findById(1)
					.orElseThrow(() -> new EntityNotFoundException());
		} catch (EntityNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "config não localizada!",
					e.getCause());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	public Configuracao inserirTurno(String periodo, Turno turno) {
		try {
			Configuracao config = this.buscar();
			if (periodo.equals("SEMANA")) {
				if (verificaInserir(config.getTurnosSemana(), turno))
					config.getTurnosSemana().add(turno);
			} else if (periodo.equals("SABADO")) {
				if (verificaInserir(config.getTurnosSabado(), turno))
					config.getTurnosSabado().add(turno);
			} else if (periodo.equals("DOMINGO")) {
				if (verificaInserir(config.getTurnosDomingo(), turno))
					config.getTurnosDomingo().add(turno);
			}
			return this.configRepository.save(config);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"ocorreu um erro no servidor!", e.getCause());
		}
	}

	private static boolean verificaInserir(Set<Turno> turnos, Turno turno) {
		for (Turno t : turnos)
			if (t.equals(turno))
				return false;
		return true;
	}

}
