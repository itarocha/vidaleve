package br.itarocha.vida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("agenda")
public class AgendaController {
	
	private static final String PG_CALENDARIO = "agenda/index";
	
	@RequestMapping()
	public ModelAndView calendario() {
		ModelAndView mv = new ModelAndView(PG_CALENDARIO);
		return mv;
	}
	
}
