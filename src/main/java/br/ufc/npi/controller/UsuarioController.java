package br.ufc.npi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.bean.Login;
import br.ufc.npi.bean.Usuario;
import br.ufc.npi.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(required = false) String msg, @RequestParam(required = false) String erro) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject(new Usuario());
		modelAndView.addObject("msg", msg);
		modelAndView.addObject("erro", erro);
		return modelAndView;
	}

	@RequestMapping("/cadastro")
	public ModelAndView cadastro(@RequestParam(required = false) String erro) {
		ModelAndView modelAndView = new ModelAndView("cadastro");
		modelAndView.addObject(new Usuario());
		modelAndView.addObject("erro", erro);
		return modelAndView;
	}

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
		modelAndView = new ModelAndView("redirect:/usuario/login");
		modelAndView.addObject("msg", "Cadastrado com sucesso!");
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(Login loginForm, HttpSession session) {
		ModelAndView modelAndView = null;
		Usuario usuario = usuarioService.logar(loginForm.getLogin(), loginForm.getSenha());
		if (usuario != null) {
			session.setAttribute("usuarioLogado", usuario);
			modelAndView = new ModelAndView("redirect:/usuario/menu");
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("redirect:/usuario/login");
			modelAndView.addObject("erro", "Dados inconsistentes!");
			return modelAndView;
		}
	}
	
	@RequestMapping(value = "/logout")
	public String efetuarLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/usuario/login";
	}

	@RequestMapping("/menu")
	public ModelAndView menuUsuario(HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView = null;
		if (usuario != null) {
			modelAndView = new ModelAndView("usuario");
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("permissao");
			return modelAndView;
		}
	}

}
