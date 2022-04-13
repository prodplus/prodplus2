package br.com.prodplus.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.services.SimulacaoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/simulacoes")
@CrossOrigin("http://localhost:4200")
public class SimulacaoController {

	@Autowired
	private SimulacaoService simulacaoService;

	@GetMapping("/custos/{produto}")
	public ResponseEntity<List<Estrutura>> getCustos(@PathVariable Integer produto) {
		return ResponseEntity.ok(this.simulacaoService.getCustos(produto));
	}

	@GetMapping("/tempo-padrao/{produto}")
	public ResponseEntity<List<Estrutura>> getTempoPadrao(@PathVariable Integer produto) {
		return ResponseEntity.ok(this.simulacaoService.getTempoPadrao(produto));
	}

}
