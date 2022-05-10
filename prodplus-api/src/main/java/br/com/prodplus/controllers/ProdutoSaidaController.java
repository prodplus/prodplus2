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

import br.com.prodplus.models.ProdutoSaida;
import br.com.prodplus.models.forms.ProdutoSaidaForm;
import br.com.prodplus.services.ProdutoSaidaService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/produtos-saida")
public class ProdutoSaidaController {

	@Autowired
	private ProdutoSaidaService produtoService;

	@PostMapping
	public ResponseEntity<ProdutoSaida> salvar(@RequestBody ProdutoSaidaForm produto) {
		return ResponseEntity.ok(this.produtoService.salvar(produto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoSaida> atualizar(@PathVariable Integer id,
			@RequestBody ProdutoSaidaForm produto) {
		return ResponseEntity.ok(this.produtoService.atualizar(id, produto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoSaida> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.produtoService.buscar(id));
	}

	@GetMapping("/listar")
	public ResponseEntity<List<ProdutoSaida>> listar() {
		return ResponseEntity.ok(this.produtoService.listar());
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<ProdutoSaida>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.produtoService.listar(pagina, quant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.produtoService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
