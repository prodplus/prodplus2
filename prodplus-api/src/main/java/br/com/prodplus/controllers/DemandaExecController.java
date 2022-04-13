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

import br.com.prodplus.models.DemandaExec;
import br.com.prodplus.models.auxiliares.DemandaId;
import br.com.prodplus.services.DemandaExecService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/demandas-exec")
@CrossOrigin("http://localhost:4200")
public class DemandaExecController {

	@Autowired
	private DemandaExecService demandaService;

	@PostMapping
	public ResponseEntity<DemandaExec> salvar(@RequestBody DemandaExec demanda) {
		return ResponseEntity.ok(this.demandaService.salvar(demanda));
	}

	@PostMapping("/multiplos")
	public ResponseEntity<Boolean> salvar(@RequestBody List<DemandaExec> demandas) {
		return ResponseEntity.ok(this.demandaService.salvar(demandas));
	}

	@GetMapping("/{produto}/{ano}/{semana}")
	public ResponseEntity<DemandaExec> buscar(@PathVariable Integer produto,
			@PathVariable Integer ano, @PathVariable Integer semana) {
		return ResponseEntity.ok(this.demandaService.buscar(produto, ano, semana));
	}

	@PostMapping("/buscar")
	public ResponseEntity<List<DemandaExec>> buscar(@RequestBody List<DemandaId> ids) {
		return ResponseEntity.ok(this.demandaService.buscar(ids));
	}

	@GetMapping("/listar/{idProduto}")
	public ResponseEntity<List<DemandaExec>> listar(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.listar(idProduto));
	}

	@GetMapping("/listar/{idProduto}/{ano}")
	public ResponseEntity<List<DemandaExec>> listar(@PathVariable Integer idProduto,
			@PathVariable Integer ano) {
		return ResponseEntity.ok(this.demandaService.listar(idProduto, ano));
	}

	@DeleteMapping("/{idProduto}/{ano}")
	public ResponseEntity<?> excluirAno(@PathVariable Integer idProduto,
			@PathVariable Integer ano) {
		this.demandaService.excluirAno(idProduto, ano);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/media-movel/{idProduto}")
	public ResponseEntity<Integer> mediaMovel(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.demandaService.mediaMovel(idProduto));
	}

}
