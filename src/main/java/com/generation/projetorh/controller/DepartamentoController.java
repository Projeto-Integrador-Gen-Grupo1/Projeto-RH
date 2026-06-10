package com.generation.projetorh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.projetorh.model.Departamento;
import com.generation.projetorh.repository.DepartamentoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departamentos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DepartamentoController {

	@Autowired
	private DepartamentoRepository departamentoRepository;

	@GetMapping
	public ResponseEntity<List<Departamento>> getAll() {
		return ResponseEntity.ok(departamentoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Departamento> getById(@PathVariable Long id) {
		return departamentoRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Departamento>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(
				departamentoRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Departamento> post(
			@Valid @RequestBody Departamento departamento) {
		return ResponseEntity.status(201)
				.body(departamentoRepository.save(departamento));
	}

	@PutMapping
	public ResponseEntity<Departamento> put(
			@Valid @RequestBody Departamento departamento) {
		return departamentoRepository.findById(departamento.getId())
				.map(resposta -> ResponseEntity.ok(
						departamentoRepository.save(departamento)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		if (departamentoRepository.existsById(id)) {
			departamentoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}
}