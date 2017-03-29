package br.ufc.npi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.bean.Usuario;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String index(){
		return "usuario";
	}
	
	@RequestMapping("/cadastro")
	public ModelAndView cadastro(@RequestParam(required = false) String erro) {
		ModelAndView modelAndView = new ModelAndView("cadastro");
		modelAndView.addObject(new Usuario());
		modelAndView.addObject("erro", erro);
		return modelAndView;
	}


}
