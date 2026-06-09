package com.generation.projetorh.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.projetorh.model.Departamento;
import com.generation.projetorh.repository.DepartamentoRepository;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Optional<Departamento> cadastrarDepartamento(Departamento departamento) {
       
        boolean nomeExiste = departamentoRepository.findAllByNomeContainingIgnoreCase(departamento.getNome())
                .stream()
                .anyMatch(d -> d.getNome().equalsIgnoreCase(departamento.getNome()));

        if (nomeExiste) {
            return Optional.empty();
        }

        return Optional.of(departamentoRepository.save(departamento));
    }
}
