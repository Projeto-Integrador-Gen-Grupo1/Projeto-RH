package com.generation.projetorh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.projetorh.repository.FuncionarioRepository;
import com.generation.projetorh.repository.PostagemRepository;

@RestController
@RequestMapping("/Funcionarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Funcionario>> getAll(){
		return ResponseEntity.ok(FuncionarioRepository.findAll());
	}
	
}
