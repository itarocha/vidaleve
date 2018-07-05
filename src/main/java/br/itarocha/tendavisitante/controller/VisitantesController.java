package br.itarocha.tendavisitante.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.itarocha.spring.security.model.UsuarioSistema;
import br.itarocha.spring.security.service.UserService;
import br.itarocha.spring.service.Mensagens;
import br.itarocha.spring.util.CampoPesquisa;
import br.itarocha.spring.util.PageWrapper;
import br.itarocha.tendavisitante.model.EstadoCivil;
import br.itarocha.tendavisitante.model.FaixaEtaria;
import br.itarocha.tendavisitante.model.HorarioCulto;
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.model.StatusVisita;
import br.itarocha.tendavisitante.model.UnidadeFederacao;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.service.FaixaEtariaService;
import br.itarocha.tendavisitante.service.HorarioCultoService;
import br.itarocha.tendavisitante.service.PessoaService;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Controller
@RequestMapping("visitantes")
public class VisitantesController {
	
	private static final String PAGINA = "visitantes";
	private static final String PAGINA_INDEX = "visitantes/index";
	private static final String PAGINA_EDIT = "visitantes/edit";
	private static final String PAGINA_REDIRECT = "redirect:/visitantes";
	private static final String NOME_CLASSE = "Visitante";
	
	@Autowired
	private PessoaService service;
	
	@Autowired
	private FaixaEtariaService faixasEtariasService;
	
	@Autowired
	private HorarioCultoService horarios; 
	
	@Autowired
	private UserService users;
	
	@Autowired
	private Mensagens mensagens;	
	
	@RequestMapping
	public ModelAndView pesquisar(	Authentication auth, 
									@ModelAttribute("filtro") FiltroPesquisa filtro,
									@PageableDefault(size=20) Pageable pageRequest) {

		ModelAndView mv = new ModelAndView(PAGINA_INDEX);
		Collection<StatusVisita> statusV = new ArrayList<StatusVisita>();
		//status.add(StatusVisita.AGUARDANDO_CONTATO_LIDER);
		statusV.add(StatusVisita.AGUARDANDO_VINCULACAO_PG);
		statusV.add(StatusVisita.SEM_INTERESSE);
		
		PageWrapper<Pessoa> page = new PageWrapper<Pessoa>(service.filtrarVisitantes(filtro, filtro.getStatus(), pageRequest), "/visitantes");
		
		mv.addObject("lista",page);
		return mv;
	}
	
	private boolean userHasAuthority(String authority, Authentication auth)
	{
	    for (GrantedAuthority grantedAuthority :  auth.getAuthorities()) {
	        if (authority.equals(grantedAuthority.getAuthority())) {
	            return true;
	        }
	    }
	    return false;
	}	
	
	@RequestMapping("/new")
	public ModelAndView novoVisitante(){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		Pessoa v = service.buildVisitante();
		mv.addObject("model", v);
		return mv;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public String salvarVisitante(	@Valid @ModelAttribute("model") Pessoa model, 
									BindingResult bindingResult, 
									final RedirectAttributes attributes,
									Authentication auth){
		
		if (bindingResult.hasErrors()){
			for(ObjectError o : bindingResult.getAllErrors()){
				System.out.println("Erro: "+o.toString());
			}
			return PAGINA_EDIT;
		}
		try{
			if ((model.getVisita() != null) ) {
				
				if (model.getVisita().getInteressadoPg()) {
					// se visitante.visita.pg == null
					model.getVisita().setStatus(StatusVisita.AGUARDANDO_VINCULACAO_PG);
					// else
					//visitante.getVisita().setStatus(StatusVisita.AGUARDANDO_CONTATO_LIDER);
				} else {
					model.getVisita().setStatus(StatusVisita.SEM_INTERESSE);
				}
				model.getVisita().setDataHoraStatus(Calendar.getInstance().getTime());
				
				if ((model.getVisita().getId() == null) ) {
					UsuarioSistema u = (UsuarioSistema) auth.getPrincipal();
					//model.getVisita().setUsuarioCadastro(users.findUserById( u.getId()) );
					model.getVisita().setDataHoraCadastro(Calendar.getInstance().getTime());
				}
			}
			service.gravarVisitante(model);
			attributes.addFlashAttribute("mensagem", mensagens.getMensagemGravacaoSucesso(NOME_CLASSE));
			return PAGINA_REDIRECT;
		}catch(IllegalArgumentException e){
			//errors.rejectValue("nome",null, e.getMessage());
			System.out.println("ERRO: "+e.getMessage());
			return PAGINA_EDIT;
		}
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") Pessoa model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		mv.addObject("model", model);
		return mv;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes){
		service.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Visitante excluído");
		return PAGINA_REDIRECT;
	}

	@ModelAttribute("todosSexo")
	public List<Sexo> todosSexo(){
		return Arrays.asList(Sexo.values());
	}
	
	@ModelAttribute("todosEstadoCivil")
	public List<EstadoCivil> todosEstadoCivil(){
		return Arrays.asList(EstadoCivil.values());
	}

	@ModelAttribute("todosUF")
	public List<UnidadeFederacao> todosUF(){
		return Arrays.asList(UnidadeFederacao.values());
	}
	
	@ModelAttribute("listaFaixasEtarias")
	public List<FaixaEtaria> listaFaixasEtarias(){
		return faixasEtariasService.findAll();
	}

	@ModelAttribute("listaHorarios")
	public List<HorarioCulto> listaHorarios(){
		return horarios.findAll();
	}
	
	@ModelAttribute("camposPesquisa")
	public List<CampoPesquisa> listaCamposPesquisa(){
		List<CampoPesquisa> campos = new ArrayList<CampoPesquisa>();

		campos.add(new CampoPesquisa("tb.nome","text", "Nome"));
		campos.add(new CampoPesquisa("tb.cpf","text", "CPF"));
		campos.add(new CampoPesquisa("tb.data_nascimento","date", "Nascimento"));
		campos.add(new CampoPesquisa("b.nome","text", "Bairro"));
		campos.add(new CampoPesquisa("c.nome","text", "Cidade"));
		campos.add(new CampoPesquisa("c.uf","text", "UF"));
		campos.add(new CampoPesquisa("tb.titulo","text", "Título"));
		campos.add(new CampoPesquisa("tb.secao","text", "Seção"));
		campos.add(new CampoPesquisa("tb.zona","text", "Zona Eleitoral"));
		campos.add(new CampoPesquisa("tb.ligou","text", "Ligou (S/N)" ));
		campos.add(new CampoPesquisa("u.name","text", "Usuário que ligou"));
		
		return campos;
	}
	
}


//<td th:text="${#dates.format(item.dataVisita, 'dd/MM/yyyy')}"></td>