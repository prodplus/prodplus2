package br.com.prodplus.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prodplus.models.Material;
import br.com.prodplus.models.auxiliares.Estrutura;
import br.com.prodplus.models.auxiliares.Quantidade;
import br.com.prodplus.services.EstruturaService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/estruturas")
@CrossOrigin("http://localhost:4200")
public class EstruturaController {

	@Autowired
	private EstruturaService estruturaService;

	@GetMapping("/quantidade-material/{idProduto}/{quant}")
	public ResponseEntity<Set<Quantidade<Material>>> getQuantidadeMaterial(
			@PathVariable Integer idProduto, @PathVariable int quant) {
		return ResponseEntity.ok(this.estruturaService.getQuantidadeMaterial(idProduto, quant));
	}

	@GetMapping("/estrutura/{idProduto}")
	public ResponseEntity<List<Estrutura>> getEstrutura(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.estruturaService.getEstrutura(idProduto));
	}

}
