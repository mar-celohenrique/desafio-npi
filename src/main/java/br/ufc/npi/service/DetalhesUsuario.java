package br.ufc.npi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufc.npi.bean.Usuario;
import br.ufc.npi.repositorio.UsuarioRepositorio;

@Service
public class DetalhesUsuario implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário nao encontrado");
		}

		return usuario;
	}

}
