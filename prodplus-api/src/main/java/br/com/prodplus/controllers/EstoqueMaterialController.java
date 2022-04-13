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

import br.com.prodplus.models.EstoqueMaterial;
import br.com.prodplus.services.EstoqueMaterialService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/estoques-materiais")
@CrossOrigin("http://localhost:4200")
public class EstoqueMaterialController {

	@Autowired
	private EstoqueMaterialService estoqueService;

	@PostMapping
	public ResponseEntity<EstoqueMaterial> salvar(@RequestBody EstoqueMaterial estoque) {
		return ResponseEntity.ok(this.estoqueService.salvar(estoque));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EstoqueMaterial> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.estoqueService.buscar(id));
	}

	@PostMapping("/inserir")
	public ResponseEntity<EstoqueMaterial> inserir(@RequestBody EstoqueMaterial estoque) {
		return ResponseEntity.ok(this.estoqueService.inserir(estoque));
	}

	@GetMapping("/listar/{idMaterial}")
	public ResponseEntity<List<EstoqueMaterial>> listar(@PathVariable Integer idMaterial) {
		return ResponseEntity.ok(this.estoqueService.listar(idMaterial));
	}

	@GetMapping("/listar/{idMaterial}/{de}/{ate}")
	public ResponseEntity<List<EstoqueMaterial>> listar(@PathVariable Integer idMaterial,
			@PathVariable LocalDateTime de, @PathVariable LocalDateTime ate) {
		return ResponseEntity.ok(this.estoqueService.listar(idMaterial, de, ate));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.estoqueService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/atual/{idMaterial}")
	public ResponseEntity<EstoqueMaterial> atual(@PathVariable Integer idMaterial) {
		return ResponseEntity.ok(this.estoqueService.atual(idMaterial));
	}

}
