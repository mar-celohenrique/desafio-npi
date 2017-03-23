package br.ufc.npi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.bean.Amigo;
import br.ufc.npi.bean.Objeto;
import br.ufc.npi.bean.Usuario;
import br.ufc.npi.repositorio.AmigoRepositorio;
import br.ufc.npi.repositorio.ObjetoRepositorio;
import br.ufc.npi.repositorio.UsuarioRepositorio;

@Service
public class AmigoService {

	@Autowired
	private AmigoRepositorio amigoRepositorio;
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	@Autowired
	private ObjetoRepositorio objetoRepositorio;

	public void adicionarAmigo(Usuario usuario, Amigo amigo) {
		Usuario u = usuarioRepositorio.findOne(usuario.getId());
		amigoRepositorio.save(amigo);
		u.getAmigos().add(amigo);
		usuarioRepositorio.save(u);
	}

	public void removerAmigo(Usuario u, Amigo amigo) {
		Amigo a = amigoRepositorio.findOne(amigo.getId());
		Usuario usuario = usuarioRepositorio.findOne(u.getId());

		usuario.getAmigos().remove(a);

		for (int i = 0; i < usuario.getObjetos().size(); i++) {
			if (usuario.getObjetos().get(i).getAmigo().equals(a)) {
				Objeto objeto = objetoRepositorio.findOne(usuario.getObjetos().get(i).getId());
				objeto.setEmprestado(false);
				objeto.setAmigo(null);
				objetoRepositorio.save(objeto);
			}
		}
		
		usuarioRepositorio.save(usuario);
		amigoRepositorio.delete(a);
	}

}
