package com.generation.projetorh.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // <-- Faltava esse import!
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.projetorh.model.Funcionario;

import jakarta.validation.Valid;

import com.generation.projetorh.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionarios") 
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Funcionario>> getAll(){
		return ResponseEntity.ok(funcionarioRepository.findAll()); 
	}
	
	@PostMapping
	public ResponseEntity<Funcionario> post(@Valid @RequestBody Funcionario funcionario) {
		funcionario.setId(null);
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionario));
	}
	
	@PutMapping
	public ResponseEntity<Funcionario> put(@Valid @RequestBody Funcionario funcionario) {
		return funcionarioRepository.findById(funcionario.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(funcionarioRepository.save(funcionario)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		
		if(funcionario.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		funcionarioRepository.deleteById(id);
	}

}