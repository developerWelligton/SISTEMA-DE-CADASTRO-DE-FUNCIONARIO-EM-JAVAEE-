package servlets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;

import beandto.BeanDtoGraficoQtdColabor;
import beandto.BeanDtoGraficoSalarioUser; 
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import util.ReportUtil;
  
 
@MultipartConfig  
@WebServlet("/ServletUsuarioController")
public class ServletUsuarioController extends ServletGenericUtil {
	//preparar imagem para upload > front tipo de envio de dado (enctype="multpart/form-data")> accept="image/*" onchange
	private static final long serialVersionUID = 1L;
       
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	
  
    public ServletUsuarioController() {
        super();
        
    }

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			//para cada ação uma rotina
			
			String acao = request.getParameter("acao"); 
 /*DELETAR*/				
			if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
				
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				request.setAttribute("msg", "Excluido com sucesso");//ANTES DE REDIRECIONAR
				
				//CARREGA E PASSA O ATRIBUTO
				List<ModelLogin> modelLoginsPASSA = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));	 
				request.setAttribute("modelLogins", modelLoginsPASSA);
				
				
				//colocar em todos os lugares antes de redirecionar
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				
				//deletando ou nao redirecionar para usuario 
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
 /*DELETAR AJAX*/				 
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);
				
				 
				//com ajax a resposta é desse jeito
				response.getWriter().write("Excluido com sucesso");
 /*BUSCAR USER AJAX*/				 
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
				//chegou até aqui faça o método de pesquisa
				String nomeBusca = request.getParameter("nomeBusca"); 
				
				//retorna lista de json
				List <ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca,super.getUserLogado(request));  
				
				ObjectMapper mapper = new ObjectMapper();
				
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				//adicionar para o cabeçalho
				response.addHeader("totalPagina", ""+daoUsuarioRepository.consultaUsuarioListTotalPaginacaoModal(nomeBusca, super.getUserLogado(request)));
				
				response.getWriter().write(json);
				 
				
				//com ajax a resposta é desse jeito
				// response.getWriter().write( );
				
/*BUSCAR USER AJAX PAGE*/					 
			} else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {
				 
				 
				
				String nomeBusca = request.getParameter("nomeBusca"); 
				
				//parâmetro pagina vinda do ajax
				String pagina = request.getParameter("pagina");
				
				//retorna lista de json
				List <ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioListOffset(nomeBusca,super.getUserLogado(request), Integer.parseInt(pagina) );  
				
				ObjectMapper mapper = new ObjectMapper();
				
				String json = mapper.writeValueAsString(dadosJsonUser);
				
				//adicionar para o cabeçalho
				response.addHeader("totalPagina", "" +daoUsuarioRepository.consultaUsuarioListTotalPaginacaoModal(nomeBusca, super.getUserLogado(request)));
				 
				response.getWriter().write(json);
				 
				
				//com ajax a resposta é desse jeito
				// response.getWriter().write( );
 /*BUSCAR EDITAR*/				 
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
				 String id = request.getParameter("id");
				 
			     ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request));
			 
			     List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			     request.setAttribute("modelLogins", modelLogins);
			     
			    request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modolLogin", modelLogin);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				
				
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
 /*LISTAR USER*/	
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
				 
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request)); 
				request.setAttribute("msg", "usuarios carregados");
				request.setAttribute("modelLogins", modelLogins); 
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request))); 
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
 /*DOWNLOAD FOTO*/	
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downLoadFoto")) {
				
				String idUser = request.getParameter("id");
				
				ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(idUser, super.getUserLogado(request) );
				
				System.out.println(modelLogin.getFotouser());
				if(modelLogin.getFotouser() != null && !modelLogin.getFotouser().isEmpty()) {
					
					response.setHeader("Content-Disposition","attachment;filename=arquivo."+ modelLogin.getExtensaofotouser());
					new Base64();
					response.getOutputStream().write(Base64.decodeBase64(modelLogin.getFotouser().split("\\,")[1]));
					
				}
				 
/*PAGINAR*/	
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				
				Integer offset = Integer.parseInt( request.getParameter("pagina")) ;
				
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
				
				 
				request.setAttribute("modelLogins",  modelLogins);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				//deletando ou nao redirecionar para usuario 
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {
				
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				//tratando entrada de dados
				if(dataInicial == null || dataInicial.isEmpty() 
						&& dataFinal == null || dataFinal.isEmpty()) {
					//carregar todos os usuarios
					
					//pegar os dados da tela
					//PARÂMETRO listaUser
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
				}else {
					request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel2(super.getUserLogado(request),dataInicial, dataFinal));
				}
				 
				request.setAttribute("dataInicial", dataInicial);
				request.setAttribute("dataFinal", dataFinal); 
				request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);
			}
			
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPDF")) {
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				
				List<ModelLogin> modelLogins = null;
				
				//SE NAO TIVER DATA
				if(dataInicial == null || dataInicial.isEmpty() 
						&& dataFinal == null || dataFinal.isEmpty()) {
					modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));
				}else {
					modelLogins = daoUsuarioRepository.consultaUsuarioListRel2(super.getUserLogado(request),dataInicial, dataFinal);
					
				}
				//$P{PARAM_SUB_REPORT}  + "sub_report_user.jasper"
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("PARAM_SUB_REPORT",request.getServletContext().getRealPath("relatorio") + File.separator);
				//cuidado com endereço correto
				
				byte[] relatorio = new ReportUtil().geraRelatorioPDF(modelLogins,"rel-user-jsp",params,request.getServletContext());
				
				response.setHeader("Content-Disposition","attachment;filename=arquivo.pdf"); 
				response.getOutputStream().write(relatorio);
			}
			else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoSalario")) {
				
				String dataInicial = request.getParameter("dataInicial");
				String dataFinal = request.getParameter("dataFinal");
				//se a condição for nula ou vazia
				if(dataInicial == null || dataInicial.isEmpty() 
						&& dataFinal == null || dataFinal.isEmpty()) { 
					
				 BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository
						 .montarGraficoMediaSalario(super.getUserLogado(request));
				 	
				 	ObjectMapper mapper = new ObjectMapper();
					
					String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
					  
					response.getWriter().write(json);
					 
					
				}else { 
					
					 BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = daoUsuarioRepository
							 .montarGraficoMediaSalario(super.getUserLogado(request), dataInicial, dataFinal);
					 	
					 	ObjectMapper mapper = new ObjectMapper();
						
						String json = mapper.writeValueAsString(beanDtoGraficoSalarioUser);
						  
						response.getWriter().write(json);
					
				}

			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("graficoQuantidadeColabor")) {
				BeanDtoGraficoQtdColabor beanDtoGraficoQtdColabor = daoUsuarioRepository
						 .montarGraficoQuantidadeCargo(super.getUserLogado(request));
				 	
				 	ObjectMapper mapper = new ObjectMapper();
					
					String json = mapper.writeValueAsString(beanDtoGraficoQtdColabor);
					  
					response.getWriter().write(json);
			}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("mediaAdemissaoDemissao")) {
				 
				
				double media = daoUsuarioRepository
						.montarMediaAdemissaoDemissao(super.getUserLogado(request));
				 
				 	
				 	ObjectMapper mapper = new ObjectMapper();
					
					String json = mapper.writeValueAsString(media);
					  
					response.getWriter().write(json);}
			else {
				//SEMPRE ANTES DO REDIRECIONAMENTO
				
				 
				//CARREGA E PASSA O ATRIBUTO
				List<ModelLogin> PASSA = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));	 
				request.setAttribute("modelLogins", PASSA);
				
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				//deletando ou nao redirecionar para usuario 
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
				
				   
					 
				    
			}
			 
			 
		} catch (Exception e) {
			// impressao de erro no console
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response); // TODO: handle exception
		}

 	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String msg = "Operação realizada com sucesso!";
			
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			String perfil = request.getParameter("perfil");
			
			String sexo = request.getParameter("sexo");
			
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			 
			
			String datanascimento = request.getParameter("datanascimento");
			
			String dataAdemissao = request.getParameter("dataAdmissao");
			String datademissao = request.getParameter("datademissao");
			
			String rendaMensal = request.getParameter("rendamensal"); // id do campo no html
 			//String pattern = "dd/MM/yyyy";
 			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern); 
 			//Date date =  simpleDateFormat.parse(dataNascimento); 
			
			String date2 =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datanascimento)); 
			java.sql.Date date3 = java.sql.Date.valueOf(date2);
			
			//ademissao
			String dateA =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataAdemissao)); 
			java.sql.Date dateAA = java.sql.Date.valueOf(dateA);
			
			
			//demissao
			String dateD =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(datademissao)); 
			java.sql.Date dateDD = java.sql.Date.valueOf(dateD);
			  
			 
			   
			if ((rendaMensal == null && rendaMensal.isEmpty()) && rendaMensal.equals("")) {
				
			  
			} else {
				rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");
			}
			 
			
			  
				Long idteste;

				if ((id != null && !id.isEmpty()) && !id.equals("")) {
					idteste = Long.parseLong(id);
				} else {
					idteste = null;
				}

			ModelLogin modelLogin = new ModelLogin();
			// se a gente nao tem ele
			// O dado da tela vem como nulo(String)

			modelLogin.setId(idteste);
			modelLogin.setNome(nome);
			modelLogin.setEmail(email);
			modelLogin.setLogin(login);
			modelLogin.setSenha(senha);//select count(1)>0 from model_login where upper(login) = upper('admin');
			
			modelLogin.setPerfil(perfil);
			
			modelLogin.setSexo(sexo);
			
			modelLogin.setCep(cep);
			modelLogin.setLogradouro(logradouro);
			modelLogin.setBairro(bairro);
			modelLogin.setLocalidade(localidade);
			modelLogin.setUf(uf); 
			modelLogin.setNumero(numero);  
			//se existe data e quer trocar não dar ele quebra!!!!
			modelLogin.setDatanascimento(date3);
			
			modelLogin.setDataAdemissao(dateAA);
			modelLogin.setDataDemissao(dateDD);
			
			Double salario = Double.parseDouble(rendaMensal);
			modelLogin.setRendamensal(salario);
			 
			 
			 
		  
			 
			 

			  
			
			//Se ja existe usuario com mesmo login ?
			//é um novo login?
			//Então pode cadastrar
			//se ele existe e é nulo mandar uma msg
			
			//classe específica para  upload
			if(ServletFileUpload.isMultipartContent(request)) {
			
				Part part = request.getPart("fileFoto");/*Pegar foto da tela*/
				byte[] foto = IOUtils.toByteArray(part.getInputStream());  //Converte imagem para byte
				
				//Se ele realmente tiver foto faça
				if(part.getSize() > 0){
					new Base64();
					String imagemBase64 = "data:image/"+ part.getContentType().split("\\/")[1] + ";base64,"+Base64.encodeBase64String(foto);
					
					modelLogin.setFotouser(imagemBase64);//foto do usuario
					modelLogin.setExtensaofotouser(part.getContentType().split("\\/")[1]);//part.get.. busca a extensão: gravar as imagens informando o tipo dele
				
				} 
			}
			
			
			if(daoUsuarioRepository.validaLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
				msg = "Ja existe usuario com o mesmo login, informe outro login";
			}else {
				
				if(modelLogin.isNovo()) {
					msg = "Gravado com sucesso!";
				}else {
					msg = "Atualizado com sucesso!";
				}
				
				modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin,super.getUserLogado(request)); 
			}
			
			//CARREGA E PASSA O ATRIBUTO ANTES DE REDIRECIONAR
			List<ModelLogin> PASSA = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));	 
			request.setAttribute("modelLogins",PASSA);

			// redirecionar para o caminho certo
			// manter os dados informados na tela e ${objeto.atributo}
			request.setAttribute("msg",msg);
			request.setAttribute("modolLogin",modelLogin);
			
			
			//colocar em todos os lugares antes de redirecionar
			request.setAttribute("totalPagina",daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
			//deletando ou nao redirecionar para usuario 
			
			request.getRequestDispatcher("principal/usuario.jsp").forward(request,response); 
		} catch (Exception e) {
			// impressao de erro no console
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg",e.getMessage());
			redirecionar.forward(request, response);
		}

	}

}


/*
 **** BUGS
 		• Update da foto, não é possível atualizad foto
 		
 	
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 */

