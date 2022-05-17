package br.com.prodplus.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prodplus.models.DemandaPrev;
import br.com.prodplus.services.DemandaPrevService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/demandas-prev")
@CrossOrigin("http://localhost:4200")
public class DemandaPrevController {

	@Autowired
	private DemandaPrevService demandaService;

	@PostMapping
	public ResponseEntity<DemandaPrev> salvar(@RequestBody DemandaPrev demanda) {
		return ResponseEntity.ok(this.demandaService.salvar(demanda));
	}

	@PostMapping("/multiplos")
	public ResponseEntity<?> salvar(@RequestBody List<DemandaPrev> demandas) {
		this.demandaService.salvar(demandas);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/listar/{idProduto}")
	public ResponseEntity<List<DemandaPrev>> listar(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.listar(idProduto));
	}

	@GetMapping("/listar/{idProduto}/{ano}")
	public ResponseEntity<List<DemandaPrev>> listar(@PathVariable Integer idProduto,
			@PathVariable Integer ano) {
		return ResponseEntity.ok(this.demandaService.listar(idProduto, ano));
	}

	@DeleteMapping("/{idProduto}/{ano}")
	public ResponseEntity<?> excluirAno(@PathVariable Integer idProduto,
			@PathVariable Integer ano) {
		this.demandaService.excluirAno(idProduto, ano);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/previsao/{idProduto}")
	public ResponseEntity<List<DemandaPrev>> previsao(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.previsao(idProduto));
	}

	@GetMapping("/previsao-f/{idProduto}")
	public ResponseEntity<List<DemandaPrev>> previsaoF(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.previsaoF(idProduto));
	}

	@GetMapping("/previsao-x/{idProduto}")
	public ResponseEntity<List<DemandaPrev>> previsaoX(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.previsaoX(idProduto));
	}

	@GetMapping("/previsao-yu/{idProduto}/{semana}/{ano}")
	public ResponseEntity<DemandaPrev> previsaoYU(@PathVariable Integer idProduto,
			@PathVariable int semana, @PathVariable int ano) {
		return ResponseEntity.ok(this.demandaService.previsaoYU(idProduto, semana, ano));
	}

	@GetMapping("/previsao-y/{idProduto}")
	public ResponseEntity<List<DemandaPrev>> previsaoY(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.previsaoY(idProduto));
	}

	@GetMapping("/previsao-u/{idProduto}")
	public ResponseEntity<DemandaPrev> previsaoU(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.previsaoU(idProduto));
	}

}
