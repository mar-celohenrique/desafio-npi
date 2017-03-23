package br.ufc.npi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.bean.Usuario;
import br.ufc.npi.criptografia.Criptografia;
import br.ufc.npi.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	@Autowired
	private Criptografia criptografar;

	public String salvar(Usuario usuario) {
		if (this.buscarPorLogin(usuario.getLogin()) != null) {
			return "login";
		} else if (this.buscarPorEmail(usuario.getEmail()) != null) {
			return "email";
		}
		usuario.setSenha(criptografar.criptografarSenha(usuario.getSenha()));
		usuarioRepositorio.save(usuario);
		return "sucesso";
	}

	public void excluir(Integer id) {
		usuarioRepositorio.delete(id);
	}

	public Usuario buscarPorId(Integer id) {
		return usuarioRepositorio.findById(id);
	}

	public Usuario buscarPorLogin(String login) {
		return usuarioRepositorio.findByLogin(login);
	}

	public Usuario buscarPorEmail(String email) {
		return usuarioRepositorio.findByEmail(email);
	}

	public Usuario logar(String login, String senha) {
		Usuario usuario = usuarioRepositorio.findByLogin(login);
		if (usuario != null) {
			if (criptografar.criptografarSenha(senha).equals(usuario.getSenha())) {
				return usuario;
			}
		}
		return null;
	}

}
