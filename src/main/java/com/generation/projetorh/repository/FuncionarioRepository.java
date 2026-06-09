package com.generation.projetorh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.projetorh.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	public List<Funcionario> findAllByNomeContainingIgnoreCase(String nome);

	public List<Funcionario> findAllByCargoContainingIgnoreCase(String cargo);

}