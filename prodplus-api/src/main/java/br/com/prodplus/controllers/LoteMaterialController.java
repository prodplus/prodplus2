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

import br.com.prodplus.models.LoteMaterial;
import br.com.prodplus.models.forms.LoteMaterialForm;
import br.com.prodplus.services.LoteMaterialService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/lotes-materiais")
@CrossOrigin("http://localhost:4200")
public class LoteMaterialController {

	@Autowired
	private LoteMaterialService loteService;

	@PostMapping
	public ResponseEntity<LoteMaterial> salvar(@RequestBody LoteMaterialForm lote) {
		return ResponseEntity.ok(this.loteService.salvar(lote));
	}

	@PutMapping("/{id}")
	public ResponseEntity<LoteMaterial> atualizar(@PathVariable Integer id,
			@RequestBody LoteMaterialForm lote) {
		return ResponseEntity.ok(this.loteService.atualizar(id, lote));
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoteMaterial> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.loteService.buscar(id));
	}

	@GetMapping("/rastreio/{rastreio}")
	public ResponseEntity<LoteMaterial> buscar(@PathVariable String rastreio) {
		return ResponseEntity.ok(this.loteService.buscar(rastreio));
	}

	@GetMapping("/listar/{idMaterial}")
	public ResponseEntity<List<LoteMaterial>> listar(@PathVariable Integer idMaterial) {
		return ResponseEntity.ok(this.loteService.listar(idMaterial));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.loteService.excluir(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/descartar/{id}")
	public ResponseEntity<?> descartar(@PathVariable Integer id) {
		this.loteService.descartar(id);
		return ResponseEntity.ok().build();
	}

}
