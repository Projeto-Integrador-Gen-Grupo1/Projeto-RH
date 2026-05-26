package com.generation.projetorh.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.generation.projetorh.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	// Query Method para a PARTE 9: buscar por título (ignorando maiúsculas/minúsculas)
	public List<Funcionario> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}