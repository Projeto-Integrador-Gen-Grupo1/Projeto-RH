package com.generation.projetorh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.projetorh.model.Usuario;
import com.generation.projetorh.model.UsuarioLogin;
import com.generation.projetorh.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent())
			return Optional.empty();

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {

			if (passwordEncoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				usuarioLogin.get().setToken("Usuário autenticado");
				usuarioLogin.get().setNome(usuario.get().getNome());

				return usuarioLogin;
			}
		}

		return Optional.empty();
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isEmpty())
			return Optional.empty();

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}
}