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

import br.com.prodplus.models.Material;
import br.com.prodplus.services.MaterialService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/materiais")
@CrossOrigin("http://localhost:4200")
public class MaterialController {

	@Autowired
	private MaterialService materialService;

	@PostMapping
	public ResponseEntity<Material> salvar(@RequestBody Material material) {
		return ResponseEntity.ok(this.materialService.salvar(material));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Material> atualizar(@PathVariable Integer id,
			@RequestBody Material material) {
		return ResponseEntity.ok(this.materialService.atualizar(id, material));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Material> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.materialService.buscar(id));
	}

	@GetMapping("/listar")
	public ResponseEntity<List<Material>> listar() {
		return ResponseEntity.ok(this.materialService.listar());
	}

	@GetMapping("/listar/{pagina}/{quant}")
	public ResponseEntity<Page<Material>> listar(@PathVariable int pagina,
			@PathVariable int quant) {
		return ResponseEntity.ok(this.materialService.listar(pagina, quant));
	}

	@GetMapping("/listar/{pagina}/{quant}/{descricao}")
	public ResponseEntity<Page<Material>> listar(@PathVariable String descricao,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.materialService.listar(descricao, pagina, quant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.materialService.excluir(id);
		return ResponseEntity.ok().build();
	}

}
