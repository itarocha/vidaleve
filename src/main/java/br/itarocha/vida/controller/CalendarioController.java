package br.itarocha.vida.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.itarocha.tendavisitante.model.Evento;
import br.itarocha.vida.model.ConsultaAgenda;
import br.itarocha.vida.service.ConsultaService;

@RestController
@RequestMapping("api")
public class CalendarioController {

	@Autowired
	ConsultaService consultas;
	
	@CrossOrigin()
	@RequestMapping("/eventos")
	public List<ConsultaAgenda> eventos(@RequestParam String start, @RequestParam String end, HttpSession session){
		//System.out.println("sys/api/eventos : "+start + " " + end);

		List<ConsultaAgenda> lista = new ArrayList<ConsultaAgenda>();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dini = sd.parse(start);
			Date dfim = sd.parse(end);
			
			lista = consultas.getAgenda(dini, dfim);
			
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return lista;
	}

	@CrossOrigin()
	@RequestMapping("/eventos2")
	public List<Evento> eventos2(){
		List<Evento> lista = new ArrayList<Evento>();
		lista.add(new Evento(125L, "Dia dos Namorados","2017-06-12"));
		lista.add(new Evento(320L, "C 08:30","2017-06-18"));
		lista.add(new Evento(322L, "C 10:00","2017-06-18"));
		lista.add(new Evento(354L, "C 17:30","2017-06-18"));
		lista.add(new Evento(355L, "C 20:00","2017-06-18"));
		lista.add(new Evento(221L, "Feriado","2017-06-26"));
		lista.add(new Evento(425L, "Itamar Rocha Chaves Junior","2017-06-29"));
		
		return lista;
	}

	@CrossOrigin()
	@RequestMapping("/eventos_por_dia")
	public List<Evento> novodia(@RequestParam String dia) throws ParseException{
		
		
		/*
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = new GregorianCalendar();
		Date diaCulto = sdf.parse(dia);
		c.setTime(diaCulto);
		System.out.println("****************** "+dia);
		// Extrai o dia da semana
		int ordemDiaSemana = c.get(Calendar.DAY_OF_WEEK);
		System.out.println("****************** O dia da semana é "+ordemDiaSemana);// Domingo é 1
		Dia diaSemana = Dia.values()[--ordemDiaSemana];
		System.out.println("****************** O dia da semana ENUM é "+diaSemana);// Domingo é 1
		// Verifica se tem programação para essa data no banco (HorarioCulto)
		
		List<HorarioCulto> horariosCulto = horarios.findByDiaOrderByHorario(diaSemana);
		
		List<Evento> lista = new ArrayList<Evento>();
		for (HorarioCulto h : horariosCulto){
			h.setId(0L);
		}
		
		// Identificar os horarios de culto com o id do dia de culto 
		List<DataCulto> cultos = cultosRepository.findByDataCulto(diaCulto);
		for(DataCulto dc : cultos){
			System.out.println(dc.getId() + " *** " + dc.getHorarioCulto().getHorario());
			for (HorarioCulto h : horariosCulto){
				if (h.getHorario().equals(dc.getHorarioCulto().getHorario())){
					System.out.println("achouuuuuuuuuuuuuuuuuuu");
					h.setId(dc.getId());
					break;
				}
			}
		}
		
		for (HorarioCulto h : horariosCulto){
			lista.add(new Evento(h.getId(), h.getHorario(), dia, false));
		}
		*/
		List<Evento> lista = new ArrayList<Evento>();
		lista.add(new Evento(125L, "Dia dos Namorados","2017-06-12"));
		lista.add(new Evento(320L, "C 08:30","2017-06-18"));
		lista.add(new Evento(322L, "C 10:00","2017-06-18"));
		lista.add(new Evento(354L, "C 17:30","2017-06-18"));
		lista.add(new Evento(355L, "C 20:00","2017-06-18"));
		lista.add(new Evento(221L, "Feriado","2017-06-26"));
		lista.add(new Evento(425L, "Itamar Rocha Chaves Junior","2017-06-29"));
		
		return lista;
	}

	/*
	@RequestMapping("/selecionar")
	public CultoSelecionado selecionar(@RequestParam Long id, HttpSession session) throws ParseException{
		
		DataCulto culto =  cultosRepository.findById(id);
		
		CultoSelecionado cultoSelecionado = new CultoSelecionado();
		if (culto != null) {
			cultoSelecionado.setId(culto.getId());
			cultoSelecionado.setDataCulto(culto.getDataCulto());
			cultoSelecionado.setHorario(culto.getHorarioCulto().getHorario());
			session.setAttribute("cultoSelecionado", cultoSelecionado );
		}

		return cultoSelecionado;
	}
	*/
}

//https://fullcalendar.io/docs/event_data/events_function/
//ShoppingCart cart = (ShoppingCart)request.getSession().setAttribute("cart",value);
