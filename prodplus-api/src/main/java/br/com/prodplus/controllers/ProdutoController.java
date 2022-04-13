package br.com.prodplus.controllers;

import java.util.List;
import java.util.Set;

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

import br.com.prodplus.models.Material;
import br.com.prodplus.models.Produto;
import br.com.prodplus.models.auxiliares.OrganoNode;
import br.com.prodplus.models.auxiliares.Quantidade;
import br.com.prodplus.services.ProdutoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/produtos")
@CrossOrigin("http://localhost:4200")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@PostMapping
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
		return ResponseEntity.ok(this.produtoService.salvar(produto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@PathVariable Integer id,
			@RequestBody Produto produto) {
		return ResponseEntity.ok(this.produtoService.atualizar(id, produto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.produtoService.buscar(id));
	}

	@GetMapping("/listar/{ativos}")
	public ResponseEntity<List<Produto>> listar(@PathVariable boolean ativos) {
		return ResponseEntity.ok(this.produtoService.listar(ativos));
	}

	@GetMapping("/listar/{pagina}/{quant}/{ativos}")
	public ResponseEntity<Page<Produto>> listar(@PathVariable boolean ativos,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.produtoService.listar(ativos, pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{ativos}/{descricao}")
	public ResponseEntity<Page<Produto>> listar(@PathVariable String descricao,
			@PathVariable boolean ativos, @PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.produtoService.listar(descricao, ativos, pagina, quant));
	}

	@DeleteMapping("/ativar/{id}")
	public ResponseEntity<?> ativar(@PathVariable Integer id) {
		this.produtoService.ativar(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.produtoService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/materiais/{id}")
	public ResponseEntity<Set<Quantidade<Material>>> getMateriais(@PathVariable Integer id) {
		return ResponseEntity.ok(this.produtoService.getMateriais(id));
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<Set<Quantidade<Produto>>> getProdutos(@PathVariable Integer id) {
		return ResponseEntity.ok(this.produtoService.getProdutos(id));
	}

	@PutMapping("/materiais/{id}")
	public ResponseEntity<Produto> setMateriais(@PathVariable Integer id,
			@RequestBody Set<Quantidade<Material>> materiais) {
		return ResponseEntity.ok(this.produtoService.setMateriais(id, materiais));
	}

	@PutMapping("/produtos/{id}")
	public ResponseEntity<Produto> setProdutos(@PathVariable Integer id,
			@RequestBody Set<Quantidade<Produto>> produtos) {
		return ResponseEntity.ok(this.produtoService.setProdutos(id, produtos));
	}

	@GetMapping("/organo/{id}/{quantidade}")
	public ResponseEntity<OrganoNode> getOrgano(@PathVariable Integer id,
			@PathVariable int quantidade) {
		return ResponseEntity.ok(this.produtoService.getOrgano(id, quantidade));
	}

}
