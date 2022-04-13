package br.com.prodplus.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.prodplus.models.Feriado;
import br.com.prodplus.services.FeriadoService;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@RestController
@RequestMapping("/feriados")
public class FeriadoController {

	@Autowired
	private FeriadoService feriadoService;

	@PostMapping
	public ResponseEntity<Feriado> salvar(@RequestBody Feriado feriado) {
		return ResponseEntity.ok(this.feriadoService.salvar(feriado));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Feriado> buscar(@PathVariable Integer id) {
		return ResponseEntity.ok(this.feriadoService.buscar(id));
	}

	@GetMapping("/data/{data}")
	public ResponseEntity<Feriado> buscar(@PathVariable LocalDate data) {
		return ResponseEntity.ok(this.feriadoService.buscar(data));
	}

	@GetMapping("/listar/{pagina}/{quant}/{de}")
	public ResponseEntity<Page<Feriado>> listar(@PathVariable LocalDate de,
			@PathVariable int pagina, @PathVariable int quant) {
		return ResponseEntity.ok(this.feriadoService.listar(de, pagina, quant));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Integer id) {
		this.feriadoService.exluir(id);
		return ResponseEntity.ok().build();
	}

}
