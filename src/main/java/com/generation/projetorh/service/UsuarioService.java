package com.generation.projetorh.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.projetorh.model.Usuario;
import com.generation.projetorh.model.UsuarioLogin;
import com.generation.projetorh.repository.UsuarioRepository;
import com.generation.projetorh.security.JwtService;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired	// Inversão/Injeção de Dependência
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent())
			return Optional.empty();

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		if (!usuarioLogin.isPresent()) {
			return Optional.empty();
		}

		/*	O login aqui é um objeto que foi encontrado dentro do Optional */
		UsuarioLogin login = usuarioLogin.get();
		
		try {
			
			/*	O authenticationManager é um objeto que tem o método AUTHENTICATE 
			 * 	que permite VALIDAR e AUTENTICAr um usuário (email, senha) conforme 
			 * 	a configuração feita na Security Config. */
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(login.getUsuario(), login.getSenha()));

			return usuarioRepository.findByUsuario(login.getUsuario())
				.map(usuario -> construirRespostaLogin(login, usuario));

		} catch (Exception e) {

			return Optional.empty();
		}
	}

	private UsuarioLogin construirRespostaLogin(UsuarioLogin usuarioLogin, Usuario usuario) {
		
		/*	Como o nome do método diz, aqui recebemos os dados do banco através do 
		 * 	argumento USUARIO, mas precisamos enviar para o Cliente, um objeto da
		 * 	classe USUARIOLOGIN, devido a questão do Token.
		 * 
		 * 	Em outras palavras, entre um Objeto Usuario {id, nome, foto, email, senha, postagens}
		 * 	e sai um objeto UsuarioLogin { id, nome, foto, token } 
		 * */
		
		usuarioLogin.setId(usuario.getId());
		usuarioLogin.setNome(usuario.getNome());
		usuarioLogin.setFoto(usuario.getFoto());
		usuarioLogin.setSenha("");
		usuarioLogin.setToken(gerarToken(usuario.getUsuario()));	
		return usuarioLogin;
		
	}

	private String gerarToken(String usuario) {
		/* Utilizamos o método generateToken da classe de Serviço 
		 * para montar o Token a partir do email do usuario. */ 
		
		return "Bearer " + jwtService.generateToken(usuario);
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isEmpty())
			return Optional.empty();
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByUsuario(usuario.getUsuario());
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);
        }

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	}

}