package br.com.prodplus.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prodplus.models.Processo;
import br.com.prodplus.services.ProcessoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/processos")
@CrossOrigin("http://localhost:4200")
public class ProcessoController {

	@Autowired
	private ProcessoService processoService;

	@PostMapping
	public ResponseEntity<Processo> salvar(@RequestBody Processo processo) {
		return ResponseEntity.ok(this.processoService.salvar(processo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Processo> atualizar(@PathVariable Integer id,
			@RequestBody Processo processo) {
		return ResponseEntity.ok(this.processoService.atualizar(id, processo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Processo> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.processoService.buscar(id));
	}

	@GetMapping("/listar/{ativos}")
	public ResponseEntity<List<Processo>> listar(@PathVariable boolean ativos) {
		return ResponseEntity.ok(this.processoService.listar(ativos));
	}

	@GetMapping("/listar/{pagina}/{quant}/{ativos}")
	public ResponseEntity<Page<Processo>> listar(@PathVariable boolean ativos,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.processoService.listar(ativos, pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{ativos}/{descricao}")
	public ResponseEntity<Page<Processo>> listar(@PathVariable String descricao,
			@PathVariable boolean ativos, @PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.processoService.listar(descricao, ativos, pagina, quant));
	}

	@DeleteMapping("/ativar/{id}")
	public ResponseEntity<?> ativar(@PathVariable Integer id) {
		this.processoService.ativar(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.processoService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
