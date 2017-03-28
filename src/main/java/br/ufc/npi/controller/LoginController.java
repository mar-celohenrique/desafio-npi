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
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/")
	public ModelAndView index(@RequestParam(required = false) String msg, @RequestParam(required = false) String erro) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject(new Usuario());
		modelAndView.addObject("msg", msg);
		modelAndView.addObject("erro", erro);
		return modelAndView;
	}
	
	@RequestMapping(value = "/logout")
	public String efetuarLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/login/";
	}
	
}
