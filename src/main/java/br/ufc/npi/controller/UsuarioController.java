package br.ufc.npi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.bean.Usuario;
import br.ufc.npi.service.ObjetoService;
import br.ufc.npi.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ObjetoService objetoService;

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(Usuario usuario) {
		ModelAndView modelAndView = null;
		String erro = null;
		String resultado = usuarioService.salvar(usuario);
		if (resultado.contains("login")) {
			erro = "Esse login já está em uso";
			modelAndView = new ModelAndView("redirect:/usuario/cadastro");
			modelAndView.addObject("erro", erro);
			return modelAndView;
		} else if (resultado.contains("email")) {
			erro = "Esse e-mail já está em uso";
			modelAndView = new ModelAndView("redirect:/usuario/cadastro");
			modelAndView.addObject("erro", erro);
			return modelAndView;
		}
		modelAndView = new ModelAndView("redirect:/usuario/menu");
		modelAndView.addObject("msg", "Cadastrado com sucesso!");

		return modelAndView;
	}

	@RequestMapping("/menu")
	public ModelAndView menuUsuario(Authentication auth) {
		Usuario usuario = (Usuario) auth.getPrincipal();
		ModelAndView modelAndView = null;
		Usuario u = usuarioService.buscarPorId(usuario.getId());
		modelAndView = new ModelAndView("usuario");
		modelAndView.addObject("objetosDisponiveis", objetoService.objetosDisponiveisPorUsuario(u));
		modelAndView.addObject("objetosEmprestados", objetoService.objetosEmprestadosPorUsuario(u));
		modelAndView.addObject("amigos", u.getAmigos());
		return modelAndView;

	}

}
