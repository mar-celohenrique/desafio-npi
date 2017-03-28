package br.ufc.npi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.bean.Objeto;
import br.ufc.npi.bean.Usuario;
import br.ufc.npi.service.ObjetoService;
import br.ufc.npi.service.UsuarioService;

@Controller
@RequestMapping("/objetos")
public class ObjetoController {

	@Autowired
	private ObjetoService objetoService;
	
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/")
	public ModelAndView index(HttpSession session, @RequestParam(required = false) String erro) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			Usuario u = usuarioService.buscarPorId(usuario.getId());
			modelAndView = new ModelAndView("objetos");
			modelAndView.addObject(new Objeto());
			modelAndView.addObject("objetos", u.getObjetos());
			modelAndView.addObject("erro", erro);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}
	
	@RequestMapping("/salvar")
	public ModelAndView salvar(HttpSession session, Objeto objeto) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			modelAndView = new ModelAndView("redirect:/objetos/");
			modelAndView.addObject("amigos", usuario.getAmigos());
			objeto.setUsuario(usuario);
			boolean resultado = objetoService.adicionarObjeto(usuario, objeto);
			if(!resultado){
				modelAndView.addObject("erro", "Você não pode mais adicionar objetos!");
			}
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
			modelAndView = new ModelAndView("redirect:/objetos/");
			modelAndView.addObject("amigos", usuario.getAmigos());
			objetoService.removerObjeto(usuario, id);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}
	
	@RequestMapping(path = "/devolucao/{id}")
	public ModelAndView devolucao(HttpSession session, @PathVariable int id) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			modelAndView = new ModelAndView("redirect:/usuario/menu");
			modelAndView.addObject("amigos", usuario.getAmigos());
			objetoService.devolucao(id);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}
	
	@RequestMapping(path = "/emprestar")
	public ModelAndView emprestar(HttpSession session, @RequestParam int objeto, @RequestParam int amigo) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			modelAndView = new ModelAndView("redirect:/usuario/menu");
			modelAndView.addObject("amigos", usuario.getAmigos());
			objetoService.emprestarObjeto(objeto, amigo, usuario);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}
	
}
