package br.ufc.npi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.bean.Usuario;
import br.ufc.npi.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(required=false) String msg){
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("msg", msg);
		return modelAndView;
	}
	
	@RequestMapping("/cadastro")
	public ModelAndView cadastro(@RequestParam(required=false) String erro){
		ModelAndView modelAndView = new ModelAndView("cadastro");
		modelAndView.addObject(new Usuario());
		modelAndView.addObject("erro", erro);
		return modelAndView;
	}

	@RequestMapping(path = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(Usuario usuario) {
		ModelAndView modelAndView = null;
		String erro = null;
		String resultado = usuarioService.salvar(usuario);
		if(resultado.contains("login")){
			erro = "Esse login já está em uso";
			modelAndView = new ModelAndView("redirect:/usuario/cadastro");
			modelAndView.addObject("erro", erro);
			return modelAndView;
		}else if(resultado.contains("email")){
			erro = "Esse e-mail já está em uso";
			modelAndView = new ModelAndView("redirect:/usuario/cadastro");
			modelAndView.addObject("erro", erro);
			return modelAndView;
		}
		modelAndView = new ModelAndView("redirect:/usuario/login");
		modelAndView.addObject("msg", "Cadastrado com sucesso!");
		return modelAndView;
	}

	
}
