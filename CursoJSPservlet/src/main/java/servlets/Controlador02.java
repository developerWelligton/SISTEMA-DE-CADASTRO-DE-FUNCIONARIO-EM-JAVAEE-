package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DAOLoginRepository;
import dao.DAOUsuarioRepository;
import model.ModelLogin;

 
@WebServlet(urlPatterns = {"/principal/Controlador02", "/Controlador02"})
public class Controlador02 extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();
	
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate();
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}else {
			//tela branca!
			doPost(request, response);
		}
		
		 
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  
				 String login = request.getParameter("login");
				 String senha = request.getParameter("senha");
				 String url = request.getParameter("url");
				 try {
			 
				 if(login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
					
					 ModelLogin modelLogin = new ModelLogin();
					 modelLogin.setLogin(login);
					 modelLogin.setSenha(senha);
					 
					  
						if(daoLoginRepository.validarAutenticacao(modelLogin)) {
							// System.out.println("Validando...");
							 //System.out.println(url);
							 
							 modelLogin = daoUsuarioRepository.consultaUsuarioLogado(login);
		//PARÂMETROS
							 request.getSession().setAttribute("usuario", modelLogin.getLogin());
							 request.getSession().setAttribute("perfil", modelLogin.getPerfil());//front back
							 request.getSession().setAttribute("imagemUser", modelLogin.getFotouser());
							 
							 
							 if(url == null || url.equals("null") || url.equals("http://localhost:8008/CursoJSPservlet/") ) {
								 url = "principal/principal.jsp";
							 }
							 //System.out.println(url);
							 
							 
							 RequestDispatcher redirecionar = request.getRequestDispatcher("principal/principal.jsp");
							 redirecionar.forward(request, response);
							 
						 }else {
							 RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
							 request.setAttribute("msg", "Informe o login e senha corretamente!");
							 redirecionar.forward(request, response); 
						 }
				 
					 
				 }else {
					 RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
					 request.setAttribute("msg", "Informe o login e senha corretamente!");
					 redirecionar.forward(request, response);
				 }
		 }catch (Exception e) {
			 //impressao de erro no console
			 RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			 request.setAttribute("msg", e.getMessage());
			 redirecionar.forward(request, response); 
			}
		 
		 
		 
	}

}
