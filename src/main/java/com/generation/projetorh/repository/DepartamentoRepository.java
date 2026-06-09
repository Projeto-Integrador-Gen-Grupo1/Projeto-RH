package com.generation.projetorh.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.projetorh.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
	public List<Departamento> findAllByNomeContainingIgnoreCase(String nome);

}
