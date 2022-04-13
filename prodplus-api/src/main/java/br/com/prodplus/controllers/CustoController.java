package br.com.prodplus.controllers;

import java.math.BigDecimal;
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

import br.com.prodplus.models.Custo;
import br.com.prodplus.services.CustoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/custos")
@CrossOrigin("http://localhost:4200")
public class CustoController {

	@Autowired
	private CustoService custoService;

	@PostMapping
	public ResponseEntity<Custo> salvar(@RequestBody Custo custo) {
		return ResponseEntity.ok(this.custoService.salvar(custo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Custo> atualizar(@PathVariable Integer id, @RequestBody Custo custo) {
		return ResponseEntity.ok(this.custoService.atualizar(id, custo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Custo> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.custoService.buscar(id));
	}

	@GetMapping("/listar/{ativos}")
	public ResponseEntity<List<Custo>> listar(@PathVariable boolean ativos) {
		return ResponseEntity.ok(this.custoService.listar(ativos));
	}

	@GetMapping("/listar/{pagina}/{quant}/{ativos}")
	public ResponseEntity<Page<Custo>> listar(@PathVariable boolean ativos,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.custoService.listar(pagina, quant, ativos));
	}

	@DeleteMapping("/ativar/{id}")
	public ResponseEntity<?> ativar(@PathVariable Integer id) {
		this.custoService.ativar(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.custoService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/total")
	public ResponseEntity<BigDecimal> getTotalMes() {
		return ResponseEntity.ok(this.custoService.getTotalMes());
	}

}
