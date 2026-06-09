package com.generation.projetorh.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.generation.projetorh.model.Funcionario;
import com.generation.projetorh.repository.DepartamentoRepository;
import com.generation.projetorh.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public Optional<Funcionario> cadastrarFuncionario(Funcionario funcionario) {
        
        if (funcionario.getDepartamento() == null || funcionario.getDepartamento().getId() == null) {
            return Optional.empty();
        }

        boolean departamentoExiste = departamentoRepository.existsById(funcionario.getDepartamento().getId());
        
        if (!departamentoExiste) {
            return Optional.empty();
        }

        return Optional.of(funcionarioRepository.save(funcionario));
    }
}
