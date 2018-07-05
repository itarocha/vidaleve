package br.itarocha.tendavisitante;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.itarocha.spring.repository.RoleRepository;
import br.itarocha.spring.repository.UserRepository;
import br.itarocha.spring.security.model.Role;
import br.itarocha.spring.security.model.Usuario;
import br.itarocha.spring.security.service.UserService;
import br.itarocha.tendavisitante.model.DiaSemana;
import br.itarocha.tendavisitante.model.Endereco;
import br.itarocha.tendavisitante.model.EstadoCivil;
import br.itarocha.tendavisitante.model.FaixaEtaria;
import br.itarocha.tendavisitante.model.FuncaoLideranca;
import br.itarocha.tendavisitante.model.HorarioCulto;
import br.itarocha.tendavisitante.model.PerfilPequenoGrupo;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.model.UnidadeFederacao;
import br.itarocha.tendavisitante.repository.DiaSemanaRepository;
import br.itarocha.tendavisitante.repository.FaixaEtariaRepository;
import br.itarocha.tendavisitante.repository.FuncaoLiderancaRepository;
import br.itarocha.tendavisitante.repository.HorarioCultoRepository;
import br.itarocha.tendavisitante.repository.PequenoGrupoRepository;
import br.itarocha.tendavisitante.repository.PerfilPequenoGrupoRepository;
import br.itarocha.tendavisitante.repository.PessoaRepository;
import br.itarocha.tendavisitante.service.PequenoGrupoService;
import br.itarocha.tendavisitante.service.PessoaService;
import br.itarocha.util.email.MailServiceImpl;

@Component
public class Carga {
	
	@Autowired
	private FaixaEtariaRepository faixasEtarias;
	
	@Autowired
	private FuncaoLiderancaRepository funcoesLideranca;
	
	@Autowired
	private PerfilPequenoGrupoRepository perfisPG;

	@Autowired
	private DiaSemanaRepository diasRepo;

	@Autowired
	private PequenoGrupoRepository pgsr;
	
	@Autowired
	private PessoaRepository pessoas;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roles;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private HorarioCultoRepository horarios;
	
	@Autowired
	private PessoaService ps;
	
	@Autowired
	private PequenoGrupoService pgs;
	
	@Autowired
	private MailServiceImpl mailClient;

	
	public void popular() {
		//inicializaDatabase();
	}
	
	//@PostConstruct
	public void inicializaDatabase(){

		pgsr.deleteAll();
		pessoas.deleteAll();
		
		userRepository.deleteAll();
		roles.deleteAll();
		
		perfisPG.deleteAll();
		PerfilPequenoGrupo pCriancas = perfisPG.save(new PerfilPequenoGrupo("Crianças"));
		PerfilPequenoGrupo pJovens = perfisPG.save(new PerfilPequenoGrupo("Jovens e Adolescentes"));
		PerfilPequenoGrupo pCasais = perfisPG.save(new PerfilPequenoGrupo("Casais"));
		PerfilPequenoGrupo pCasaisJovens = perfisPG.save(new PerfilPequenoGrupo("Casais Jovens"));
		PerfilPequenoGrupo pFamilia = perfisPG.save(new PerfilPequenoGrupo("Família"));
		perfisPG.save(new PerfilPequenoGrupo("Homens"));
		perfisPG.save(new PerfilPequenoGrupo("Mulheres"));
		PerfilPequenoGrupo pGeral = perfisPG.save(new PerfilPequenoGrupo("Geral"));
		
		diasRepo.deleteAll();
		DiaSemana diaDom = diasRepo.save(new DiaSemana(1,"Domingo", "DOM"));
		DiaSemana diaSeg = diasRepo.save(new DiaSemana(2,"Segunda", "SEG"));
		DiaSemana diaTer = diasRepo.save(new DiaSemana(3,"Terça", "TER"));
		DiaSemana diaQua = diasRepo.save(new DiaSemana(4,"Quarta", "QUA"));
		DiaSemana diaQui = diasRepo.save(new DiaSemana(5,"Quinta", "QUI"));
		DiaSemana diaSex = diasRepo.save(new DiaSemana(6,"Sexta", "SEX"));
		DiaSemana diaSab = diasRepo.save(new DiaSemana(7,"Sábado", "SAB"));
		
		funcoesLideranca.deleteAll();
		funcoesLideranca.save(new FuncaoLideranca("Diaconisa"));
		FuncaoLideranca diacono = funcoesLideranca.save(new FuncaoLideranca("Diácono"));
		FuncaoLideranca lider = funcoesLideranca.save(new FuncaoLideranca("Líder"));
		FuncaoLideranca pastor = funcoesLideranca.save(new FuncaoLideranca("Pastor"));
		funcoesLideranca.save(new FuncaoLideranca("Pastora"));
		FuncaoLideranca presbitero = funcoesLideranca.save(new FuncaoLideranca("Presbítero"));
		
		funcoesLideranca.save(new FuncaoLideranca("Ator *"));
		funcoesLideranca.save(new FuncaoLideranca("Operador de Áudio *"));
		funcoesLideranca.save(new FuncaoLideranca("Recepcionista *"));
		funcoesLideranca.save(new FuncaoLideranca("Faxineiro *"));
		funcoesLideranca.save(new FuncaoLideranca("Atendente de Biblioteca *"));
		funcoesLideranca.save(new FuncaoLideranca("Atendente Financeiro *"));
		funcoesLideranca.save(new FuncaoLideranca("Artesão *"));
		funcoesLideranca.save(new FuncaoLideranca("Auxiliar de Serviços Gerais *"));
		funcoesLideranca.save(new FuncaoLideranca("Redator *"));
		funcoesLideranca.save(new FuncaoLideranca("Revisor *"));
		funcoesLideranca.save(new FuncaoLideranca("Guitarrista *"));
		funcoesLideranca.save(new FuncaoLideranca("Baterista *"));
		funcoesLideranca.save(new FuncaoLideranca("Baixista *"));
		funcoesLideranca.save(new FuncaoLideranca("Cantor * "));
		funcoesLideranca.save(new FuncaoLideranca("Back vocals *"));
		funcoesLideranca.save(new FuncaoLideranca("Responsável pelo Louvor *"));
		funcoesLideranca.save(new FuncaoLideranca("Evangelista *"));
		funcoesLideranca.save(new FuncaoLideranca("Saxofonista *"));
		funcoesLideranca.save(new FuncaoLideranca("Copeiro *"));
		funcoesLideranca.save(new FuncaoLideranca("Arquiteto *"));
		funcoesLideranca.save(new FuncaoLideranca("Programador de Software *"));
		funcoesLideranca.save(new FuncaoLideranca("Líder de Oração *"));
		funcoesLideranca.save(new FuncaoLideranca("Líder de Ação Social *"));
		
		faixasEtarias.deleteAll();
		faixasEtarias.save(new FaixaEtaria("Criança"));
		faixasEtarias.save(new FaixaEtaria("Adolescente"));
		faixasEtarias.save(new FaixaEtaria("Jovem"));
		faixasEtarias.save(new FaixaEtaria("Adulto Jovem"));
		FaixaEtaria adulto = faixasEtarias.save(new FaixaEtaria("Adulto"));
		faixasEtarias.save(new FaixaEtaria("Senhor(a)"));
		faixasEtarias.save(new FaixaEtaria("Idoso"));
		
		Role admin = roles.save(new Role("ADMIN"));
		roles.save(new Role("USUARIO"));
		roles.save(new Role("GERENTE"));
		roles.save(new Role("SECRETARIA"));
		roles.save(new Role("VOLUNTARIO"));
		
		horarios.deleteAll();
		horarios.save(new HorarioCulto(diaDom, "08:30"));
		horarios.save(new HorarioCulto(diaDom, "10:00"));
		horarios.save(new HorarioCulto(diaDom, "17:30"));
		horarios.save(new HorarioCulto(diaDom, "19:30"));
		horarios.save(new HorarioCulto(diaQua, "20:00"));
		
		
		Usuario usuario = new Usuario();
		usuario.setEmail("itarocha@gmail.com");
		usuario.setName("Itamar Rocha");
		usuario.setLastName("Chaves Junior");
		usuario.setPassword("123456");
		
	    //Role userRole = roleRepository.findByRole("ADMIN");
	    //userRepository.save(usuario);	    
		
		
		//System.out.println(new BCryptPasswordEncoder().encode("123456"));
		
		
		//Role role = roles.findByRole("ADMIN");
		//usuario.getRoles().add(admin);
		
		userService.saveUser(usuario);

		usuario.setRoles(new HashSet<Role>(Arrays.asList(admin)));
		//userService.saveUser(usuario);
		userRepository.save(usuario);
		
		Endereco endereco;
		Pessoa p = null;
		
		//1
		endereco = new Endereco("Rua José Ayube",289,null,"Fundinho","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Rodrigo Veiga","rodrigoveiga@saldaterra.org.br", "34 9106-1067", null, EstadoCivil.CASADO, adulto, pastor, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSeg, "20:00", pGeral);
		
		
		//2
		endereco = new Endereco("Rua das Ipoméias",357,null,"Cidade Jardim","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Márcio Heleno","marcioho@gmail.com", "34 99668-9999", "34 99215-3443", EstadoCivil.CASADO, adulto, diacono, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSeg, "20:00", pCasais);
		
		//3
		endereco = new Endereco("Rua João Marçal Dionísio",615,null,"Jardim Karaíba","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Alexandre Leonardo B. Borges","alexandrelbb@me.com", "34 3255-4486", "34 9948-1498", EstadoCivil.CASADO, adulto, pastor, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSeg, "19:00", pCriancas);
		
		//4
		endereco = new Endereco("Av. Alexandre Ribeiro Guimaraes",315,"Apto 1301","Saraiva","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Floriano Sulzbeck Guimarães","florianos@netsite.com.br", "34 3234-4115", "34 9106-7115", EstadoCivil.CASADO, adulto, diacono, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSeg, "20:00", pFamilia);
		
		//5
		endereco = new Endereco("Rua Eduardo Marques",1196,null,"Osvaldo","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Elias Tenêncio","eliastenencio@gmail.com", "34 9224-1921", null, EstadoCivil.CASADO, adulto, lider, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaQui, "17:30", pCasais);

		//6
		endereco = new Endereco("Rua Carneiro",172,null,"Bom Jesus","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Mauro Sérgio Toledo de Almeida","mauro_almeida@nacionalnet.com.br", "34 9976-4590", "34 3223-5732", EstadoCivil.CASADO, adulto, pastor, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSeg, "19:30", pFamilia);
		
		//7
		endereco = new Endereco("Rua Aracaty",300,null,"Jardim Karaíba","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Olgávaro Jr.","olgavarojr@saldaterra.org.br", "34 9801-4222", "34 3223-5732", EstadoCivil.CASADO, adulto, pastor, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSex, "20:00", pFamilia);

		//8
		endereco = new Endereco("R. Delminda Vilela de Andrade",186,null,"Morada da Colina","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Cleudair Nery Junior","cneryjr@yahoo.com.br", "34 8834-3446", "34 3223-5732", EstadoCivil.CASADO, adulto, pastor, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSab, "20:00", pFamilia);
		
		//9
		endereco = new Endereco("Av. Frederico Tibery",186,null,"Tibery","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Sidney Araújo","sidneymiguel@hotmail.com", "34 9192-3364", "34 3257-3718", EstadoCivil.CASADO, adulto, diacono, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaSeg, "20:00", pCasais);

		//10
		endereco = new Endereco("Condômino terra nova 1",194,null,"Jd California","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Hítala","hitalajmr@hotmail.com", "34 91124645", "34 9112-4645", EstadoCivil.CASADO, adulto, lider, Sexo.FEMININO, endereco));
		pgs.salvar(p, diaSeg, "20:00", pGeral);
		
		endereco = new Endereco("R. Fádua Barcha Gustin",375,"Ap. 504","Cidade Jardim","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Ricardo Melo","drricardomelo@hotmail.com", "34 9966-0703", "34 9678-5997", EstadoCivil.CASADO, adulto, lider, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaQua, "20:00", pJovens);

		endereco = new Endereco("Rua Josefina de Souza leite",103,null,"Nova Uberlândia","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Antônio (Zila)","acrjunior88@gmail.com", "34 9946-8188", "34 9105-7232", EstadoCivil.CASADO, adulto, presbitero, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaQua, "20:00", pJovens);
		

		endereco = new Endereco("Av. Pará",236,null,"Bairro Brasil","34800-000","Uberlândia",UnidadeFederacao.MG);
		p = ps.gravarVoluntario(ps.buildVoluntario("Pedro Paulo Prado de Almeida","pedropaulo_prado@yahoo.com.br", "34 9946-8188", "34 9102-3952", EstadoCivil.CASADO, adulto, lider, Sexo.MASCULINO, endereco));
		pgs.salvar(p, diaQua, "20:00", pGeral);
		
		
		//15	Rua das Alamandas 849	Cidade Jardim	Gabriel Labeca Ferreira Nogueira Borges	9215-8550	labeca_g@yahoo.com.br	Presbítero				Geral	6ª Feira	20h00	12
		//16	Rua 29 de Outubro Nº 360 - Apto 03	Copacabana	Rombledo	8835-1932	rombledo@gmail.com						2ª Feira	20h00	
		//17	Rua Sapucaí, 80, apto. 102	Patrimônio	Marcelo	9198-6265	marcelo.consultorti@gmail.com	Diácono				Jovens casais	2ª Feira	20h00	7
		//18	Rua do Carpinteiro 322	Planalto	Rodrigo Rangel e Pereira	3233-6200/ 9106-5218	rodrigopereira@netsite.com.br	Pastor				homens	5ª Feira	07h00	6
		//19	Rua XV de Novembro, 409 ap.2	Fundinho	Sara Vargas	3233-6200/ 9106-5219	saravargas@netsite.com.br	Pastora	zila			Mulheres	6ª Feira	08h30	7
		//20	Rua XV de Novembro Nº 409 Ap 02 	Tabajaras	Gilberto Costa Arruda	9188-7447	gil.c7@hotmail.com; connect@saldaterra.org.br	Líder	Lucas Vargas			Jovens / Adol.	2ª Feira	19h30	10
		//21	Av. Floriano Peixoto 1919 Ap 201	Aparecida	Gilberto Costa Arruda	9188-7447	gil.c7@hotmail.com; connect@saldaterra.org.br					Jovens / Adol.	5ª Feira	19h30	
		//22	Av. Paes Leme, 1217	Martins	Lucas Fernandes	99168-9773	lucas.podoski.f@gmail.com		Agatha Felice			Jovens / Adol.	2ª Feira	20h00	12
		//23	Av. Segismundo Pereira, 3333 	Sta. Mônica	Cecília Braga	9159-7136	Cecília.bragaferreira@hotmail.com					Jovens / Adol.	2ª Feira	20h00	12
		//24	R Antônio Resendes Chaves 355 ap01 (antiga 24)	Sta. Mônica	Francisco de Assis Gomes (Chikim)	9828-0153	chikimgomes@gmail.com	Presbítero	Tiago			Geral	4ª Feira	20h00	
		//25	Av. San Marino, 580	Jd. Europa	Homero Avelar	9694-7935	'hoavelar@yahoo.com.br'					Geral	4ª Feira	19h30	
		//26	Rua Antônio de Oliveira Gouveia,275 	Jardim América 02	Alessandro Ferreira	99239-7030	alessandrosferreira@gmail.com	Pastor	Ana			Geral	2ª Feira	20h00	
		//27	Rua Antônio Crescêncio, 201 casa 2	Bairro Aparecida	Marco Túlio	98862-8243	mtuliobarbosa@gmail.com 		Daiara			Jovens	Sab.	17h00	
		//28	Rua Berlim,270	Jardim Europa	Juliano	99188-8090	jnevesborges@gmail.com		Cris			Jovens	6ª Feira	19h30	
		//29	Rua Serra do Roncador,144 	São Jorge	Lucian	99916-1039	luuciants@gmail.com		Angelina			Jovens	6ª Feira	21h00	
		//30	Rua do Comerciário,81 	Jardim das Palmeiras	Thaís Onofre	99197-0265	thais_onofre_caixeta@hotmail.com					Jovens	Sab.	17h30	
		//31	Av Feliciano de Morais,765 	Bairro Aparecida	Felipe	99162-1492	felipebatistap@gmail.com					Jovens	5ª Feira	20h00	
		//32	Rua Capela, 959 	Jardim Brasília	Vinícios	9652-4422	viniciusgps@hotmail.com					Jovens	Sab.	17h00	
		//33	Rua Paraná, 812, ap. 201, bloco 3A - Res. Ulisses	Bairro Brasil	Eliane Izidoro	99767-0212	eliane.izidoro@gmail.com						Sab.	18h30	6
		//34	Av. Indaia, 655	Planalto	Elizângela	98806-9128	elisangela_basica@hotmail.com						2ª Feira	20h00	
		//35	Rua Paschoal Caparelli 240  	Morada da Colina	Roberto Silva	99645-6077	robertocsilva12@icloud.com	Pastor	Fátima (esposa)			Casais	2ª Feira	20h00	20
		//36	Rua Eduardo de Oliveira, 206, apt.1604	Lídice	Adolfo	99902-4600	adolfonoroes@yahoo.com.br	Pastor	Gisely		gisellyrnoroes@yahoo.com.br	Jovens adultos	2ª Feira	20h00	8
		//37	Rua Angra dos Reis 122	Gravatás	Alonso	99177-3497	alonso.s.c.12@gmail.com	Voluntário	Juliana (esposa)			Jovens adultos	Sab.	18h00	5
		//38	Rua da Carioca, 854, Bloco 1, Ap 201	Copacabana	Bruno Morais	99122-5778 / 99161-0278	moragro.bruno@gmail.com	Lider	Carol (esposa)	99161-0278		Geral	Quarta	19:30	6
		//39	Rua das Primaveras	Cidade Jardim	Luiz Terencio	98814-7971		Lider							
		//40	Rua do Samba 106	Guarani	Claudia Moreira	99107-3901	eufrasiofl@hotmail.com		Eufrasio	991470-9988	eufrasiofl@hotmail.com	Casais		20:00	14		
		
	}
	
}
