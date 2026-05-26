package com.generation.projetorh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.projetorh.model.Funcionario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class FuncionarioController {
	@Autowired
	private FuncionarioRepository funcionarioRepository;

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

}
