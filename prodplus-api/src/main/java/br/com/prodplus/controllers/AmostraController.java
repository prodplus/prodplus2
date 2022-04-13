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

import br.com.prodplus.models.Amostra;
import br.com.prodplus.models.auxiliares.AmostraId;
import br.com.prodplus.services.AmostraService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/amostras")
@CrossOrigin("http://localhost:4200")
public class AmostraController {

	@Autowired
	private AmostraService amostraService;

	@PostMapping
	public ResponseEntity<Amostra> salvar(@RequestBody Amostra amostra) {
		return ResponseEntity.ok(this.amostraService.salvar(amostra));
	}

	@GetMapping("/{idProduto}/{idProcesso}")
	public ResponseEntity<Amostra> buscar(@PathVariable Integer idProduto,
			@PathVariable Integer idProcesso) {
		return ResponseEntity.ok(this.amostraService.buscar(new AmostraId(idProduto, idProcesso)));
	}

	@GetMapping("/listar-processo/{idProcesso}")
	public ResponseEntity<List<Amostra>> listarPorProcesso(@PathVariable Integer idProcesso) {
		return ResponseEntity.ok(this.amostraService.listarPorProcesso(idProcesso));
	}

	@GetMapping("/listar-produto/{idProduto}")
	public ResponseEntity<List<Amostra>> listarPorProduto(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.amostraService.listarPorProduto(idProduto));
	}

	@GetMapping("/tempo-medio/{idProduto}/{idProcesso}")
	public ResponseEntity<Integer> getTempoMedio(@PathVariable Integer idProduto,
			@PathVariable Integer idProcesso) {
		return ResponseEntity
				.ok(this.amostraService.getTempoMedio(new AmostraId(idProduto, idProcesso)));
	}

	@GetMapping("/tempo-normal/{idProduto}/{idProcesso}")
	public ResponseEntity<Integer> getTempoNormal(@PathVariable Integer idProduto,
			@PathVariable Integer idProcesso) {
		return ResponseEntity
				.ok(this.amostraService.getTempoNormal(new AmostraId(idProduto, idProcesso)));
	}

	@GetMapping("/tempo-padrao/{idProduto}/{idProcesso}")
	public ResponseEntity<Integer> getTempoPadrao(@PathVariable Integer idProduto,
			@PathVariable Integer idProcesso) {
		return ResponseEntity
				.ok(this.amostraService.getTempoPadrao(new AmostraId(idProduto, idProcesso)));
	}

	@DeleteMapping("/{idProduto}/{idProcesso}")
	public ResponseEntity<?> excluir(@PathVariable Integer idProduto,
			@PathVariable Integer idProcesso) {
		this.amostraService.excluir(new AmostraId(idProduto, idProcesso));
		return ResponseEntity.ok().build();
	}

}
