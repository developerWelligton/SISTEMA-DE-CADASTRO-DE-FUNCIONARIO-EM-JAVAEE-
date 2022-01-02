package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;
import model.ModelTelefone;

 
	
 
@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {//pra gente usar o método do usuario logado
	
	private static final long serialVersionUID = 1L;
    
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
	private DAOTelefoneRepository daoTelefoneRepository = new DAOTelefoneRepository();
			
	
    public ServletTelefone() {
        super();
       
    }

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
	 
	  
		try { 
			
			
				String acao = request.getParameter("acao");
				
				if(acao != null && !acao.isEmpty() && acao.equals("excluir")) {
					
					String idfone = request.getParameter("id");
					
					String userPai = request.getParameter("userPai");
					
					 
					//Método do banco
					daoTelefoneRepository.deleteFone(Long.parseLong(idfone));
					
					ModelLogin modelLogin =  daoUsuarioRepository.consultaUsuarioID(Long.parseLong(userPai));
					
					List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone( modelLogin.getId());
					 
					//pegar na tela esses valores
					request.setAttribute("modelTelefones", modelTelefones);
					
					request.setAttribute("modelLogin", modelLogin);
					request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);
					
					return;
					
				}
				
				String iduser = request.getParameter("iduser");
				
				if(iduser != null && !iduser.isEmpty()) {
				  
				ModelLogin modelLogin =  daoUsuarioRepository.consultaUsuarioID(Long.parseLong(iduser));
				
				List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone( modelLogin.getId());
				 
				//pegar na tela esses valores
				request.setAttribute("modelTelefones", modelTelefones);
				
				request.setAttribute("modelLogin", modelLogin);
				request.getRequestDispatcher("/principal/telefone.jsp").forward(request, response);
				
					}else {
						//SEMPRE ANTES DO REDIRECIONAMENTO
						
						//CARREGA E PASSA O ATRIBUTO
						List<ModelLogin> PASSA = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));	 
						
						request.setAttribute("modelLogins", PASSA);
						
						request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
						 
						
						request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					}
				
			 } catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
			
			
		 
	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
		//pega o nome do banco pra associar
			String usuario_pai_id =  request.getParameter("id");
			String numero = request.getParameter("numero");
			
			if(!daoTelefoneRepository.existeFone(numero,Long.valueOf(usuario_pai_id))) {
				
			 
			
				ModelTelefone modelTelefone = new ModelTelefone();
				
				modelTelefone.setNumero(numero); 
				modelTelefone.setUsuario_pai_id(daoUsuarioRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id))); 
				modelTelefone.setUsuario_cad_id(super.getUserLogadoObjeto(request));
					
				daoTelefoneRepository.gravaTelefone(modelTelefone);
				 
				request.setAttribute("msg", "Salvo com sucesso");  
			
			}else {
				request.setAttribute("msg", "Telefone já existe!");  
			}
			List<ModelTelefone> modelTelefones = daoTelefoneRepository.listFone(Long.parseLong(usuario_pai_id)); 
			
			ModelLogin modelLogin =  daoUsuarioRepository.consultaUsuarioID(Long.parseLong(usuario_pai_id)); 
			
			request.setAttribute("modelLogin", modelLogin);
			//pegar na tela esses valores
			request.setAttribute("modelTelefones", modelTelefones);
			request.getRequestDispatcher("principal/telefone.jsp").forward(request, response);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
