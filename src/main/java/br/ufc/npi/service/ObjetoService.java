package br.ufc.npi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.bean.Amigo;
import br.ufc.npi.bean.Objeto;
import br.ufc.npi.bean.Usuario;
import br.ufc.npi.repositorio.AmigoRepositorio;
import br.ufc.npi.repositorio.ObjetoRepositorio;
import br.ufc.npi.repositorio.UsuarioRepositorio;
import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;

@Service
public class ObjetoService {

	@Autowired
	private ObjetoRepositorio objetoRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private AmigoRepositorio amigoRepositorio;

	@Autowired
	private SendEmailService emailService;

	public boolean adicionarObjeto(Usuario usuario, Objeto objeto) {
		Usuario u = usuarioRepositorio.findOne(usuario.getId());
		if (u.getObjetos().size() == 10) {
			return false;
		} else {
			objetoRepositorio.save(objeto);
			u.getObjetos().add(objeto);
			usuarioRepositorio.save(u);
			return true;
		}
	}

	public Objeto buscarPorId(int id) {
		Objeto objeto = objetoRepositorio.findOne(id);
		if (objeto != null) {
			return objeto;
		}
		return null;
	}

	public void emprestarObjeto(int objeto, int amigo, Usuario usuario) {
		Objeto obj = this.buscarPorId(objeto);
		Amigo amg = amigoRepositorio.findOne(amigo);
		if (obj != null && amg != null) {
			obj.setAmigo(amg);
			obj.setEmprestado(true);
			objetoRepositorio.save(obj);
			EmailBuilder emailBuilder = new EmailBuilder("Manager Loan","naoresponda@naoresponda.com",
					"Empréstimo de Objeto", usuario.getEmail().trim(), "Você emprestou o objeto "
							.concat(obj.getNome()).concat(" para seu amigo ").concat(amg.getNome()));
			Email email = new Email(emailBuilder);
			emailService.sendEmail(email);
		}
	}

	public void devolucao(int id) {
		Objeto obj = this.buscarPorId(id);
		if (obj != null) {
			obj.setEmprestado(false);
			obj.setAmigo(null);
			objetoRepositorio.save(obj);
		}
	}

	public void removerObjeto(Usuario u, int id) {
		Objeto objeto = this.buscarPorId(id);
		Usuario usuario = usuarioRepositorio.findOne(u.getId());

		if (objeto != null && usuario != null) {
			usuario.getObjetos().remove(objeto);
			usuarioRepositorio.save(usuario);
			objetoRepositorio.delete(objeto);
		}
	}

	public List<Objeto> objetosDisponiveisPorUsuario(Usuario usuario) {
		List<Objeto> objetos = objetoRepositorio.objetosDisponiveis(usuario);
		if (!objetos.isEmpty()) {
			return objetos;
		} else {
			return new ArrayList<>();
		}
	}

	public List<Objeto> objetosEmprestadosPorUsuario(Usuario usuario) {
		List<Objeto> objetos = objetoRepositorio.objetosEmprestados(usuario);
		if (!objetos.isEmpty()) {
			return objetos;
		} else {
			return new ArrayList<>();
		}
	}

}
