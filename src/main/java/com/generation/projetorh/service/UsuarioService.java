package com.generation.projetorh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.projetorh.model.Usuario;
import com.generation.projetorh.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return Optional.empty();
        }

        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            return Optional.empty();
        }

        return Optional.of(usuarioRepository.save(usuario));
    }

}
