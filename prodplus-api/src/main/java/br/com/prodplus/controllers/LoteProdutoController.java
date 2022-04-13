package br.com.prodplus.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.prodplus.models.LoteProduto;
import br.com.prodplus.models.forms.LoteProdutoForm;
import br.com.prodplus.services.LoteProdutoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/lotes-produtos")
@CrossOrigin("http://localhost:4200")
public class LoteProdutoController {

	@Autowired
	private LoteProdutoService loteService;

	@PostMapping
	public ResponseEntity<LoteProduto> salvar(@RequestBody LoteProdutoForm lote) {
		return ResponseEntity.ok(this.loteService.salvar(lote));
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoteProduto> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.loteService.buscar(id));
	}

	@GetMapping("/rastreio/{rastreio}")
	public ResponseEntity<LoteProduto> buscar(@PathVariable String rastreio) {
		return ResponseEntity.ok(this.loteService.buscar(rastreio));
	}

	@PutMapping("/{id}")
	public ResponseEntity<LoteProduto> atualizar(@PathVariable Integer id,
			@RequestBody LoteProdutoForm lote) {
		return ResponseEntity.ok(this.loteService.atualizar(id, lote));
	}

	@GetMapping("/listar/{idProduto}")
	public ResponseEntity<List<LoteProduto>> listar(@PathVariable Integer idProduto) {
		return ResponseEntity.ok(this.loteService.listar(idProduto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.loteService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
