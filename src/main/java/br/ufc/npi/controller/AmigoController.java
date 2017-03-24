package br.ufc.npi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.bean.Amigo;
import br.ufc.npi.bean.Usuario;
import br.ufc.npi.service.AmigoService;
import br.ufc.npi.service.UsuarioService;

@Controller
@RequestMapping("/amigos")
public class AmigoController {

	@Autowired
	private AmigoService amigoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/")
	public ModelAndView index(HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			Usuario u = usuarioService.buscarPorId(usuario.getId());
			modelAndView = new ModelAndView("amigos");
			modelAndView.addObject(new Amigo());
			modelAndView.addObject("amigos", u.getAmigos());
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}
	
	
	@RequestMapping("/salvar")
	public ModelAndView salvar(HttpSession session, Amigo amigo) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			modelAndView = new ModelAndView("redirect:/amigos/");
			modelAndView.addObject("amigos", usuario.getAmigos());
			amigoService.adicionarAmigo(usuario, amigo);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}
	
	@RequestMapping(path = "/excluir/{id}")
	public ModelAndView excluir(HttpSession session, @PathVariable int id) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			modelAndView = new ModelAndView("redirect:/amigos/");
			modelAndView.addObject("amigos", usuario.getAmigos());
			amigoService.removerAmigo(usuario, id);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}

}
