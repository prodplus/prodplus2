package br.com.prodplus.controllers;

import java.time.LocalDateTime;
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

import br.com.prodplus.models.EstoqueProduto;
import br.com.prodplus.services.EstoqueProdutoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/estoques-produtos")
@CrossOrigin("http://localhost:4200")
public class EstoqueProdutoController {

	@Autowired
	private EstoqueProdutoService estoqueService;

	@PostMapping
	public ResponseEntity<EstoqueProduto> salvar(@RequestBody EstoqueProduto estoque) {
		return ResponseEntity.ok(this.estoqueService.salvar(estoque));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstoqueProduto> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.estoqueService.buscar(id));
	}

	@GetMapping("/listar/{idProduto}")
	public ResponseEntity<List<EstoqueProduto>> listar(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.estoqueService.listar(idProduto));
	}

	@GetMapping("/listar/{idProduto}/{de}/{ate}")
	public ResponseEntity<List<EstoqueProduto>> listar(@PathVariable Integer idProduto,
			@PathVariable LocalDateTime de, @PathVariable LocalDateTime ate) {
		return ResponseEntity.ok(this.estoqueService.listar(idProduto, de, ate));
	}

	@PostMapping("/inserir")
	public ResponseEntity<EstoqueProduto> inserir(@RequestBody EstoqueProduto estoque) {
		return ResponseEntity.ok(this.estoqueService.inserir(estoque));
	}

	@GetMapping("/atual/{idProduto}")
	public ResponseEntity<EstoqueProduto> atual(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.estoqueService.atual(idProduto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.estoqueService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
