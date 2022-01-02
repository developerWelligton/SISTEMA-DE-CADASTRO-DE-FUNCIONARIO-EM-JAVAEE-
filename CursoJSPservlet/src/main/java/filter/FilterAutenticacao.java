package filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.SingleConnection;
import dao.DAOVercionadorBanco;

@WebFilter
public class FilterAutenticacao implements Filter {
	private static Connection connection;
     
    public FilterAutenticacao() {
     
    }

	 
 

	 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		try {  
			 HttpServletRequest req = (HttpServletRequest) request;
			 HttpSession session = req.getSession();
			 
			 String usuarioLogado =   (String) session.getAttribute("usuario");
			 
			 
			 String urlParaAutenticar = req.getServletPath();
			 //System.out.println(urlParaAutenticar);
			 if(usuarioLogado == null ||   
					 //("/principal/Controlador02")
					 //principal/usuario.jsp PEGOU
					 // antes so tinha um equalIgnoreCAse
				!urlParaAutenticar.equalsIgnoreCase("/principal/Controlador02")) { 
				 	 
				 	RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				 	request.setAttribute("msg", "Por favor realize o login Voce está no filter");
				 	redireciona.forward(request, response);
				 	 
				 	return;
			 }else {
				 
				 chain.doFilter(request, response);
			 } 
			 
			 connection.commit();
			 
		}catch (Exception e) {
			 e.printStackTrace();
					 RequestDispatcher redirecionar = request.getRequestDispatcher("/erro.jsp");
					 request.setAttribute("msg", e.getMessage());
					 redirecionar.forward(request, response);
				 try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		 
	}

	 
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnection.getConnection();
		
		DAOVercionadorBanco daoVercionadorBanco = new DAOVercionadorBanco();
		
		String caminhoPastaSQL = fConfig.getServletContext().getRealPath("versionadobancosql") + File.separator;
	
		File[] filesSql = new File(caminhoPastaSQL).listFiles();
		
		try {
			for (File file : filesSql) { 
				boolean arquivoJaRodado = daoVercionadorBanco.arquivoSqlRodado(file.getName());
				 
				if(!arquivoJaRodado) {
					FileInputStream entradaArquivo = new FileInputStream(file);
					
					Scanner lerarquivo = new Scanner(entradaArquivo,"UTF-8");
					
					
					StringBuilder sql = new StringBuilder();
					
					while(lerarquivo.hasNext()) {
						
						sql.append(lerarquivo.nextLine());
						sql.append("\n");
						
					}
					
					connection.prepareStatement(sql.toString()).execute();
					daoVercionadorBanco.gravaArquivoSqlRodado(file.getName());
					connection.commit();
					lerarquivo.close();
				}
			
			}
			
			
			
		} catch (Exception e) {
			 try {
				connection.rollback();
			} catch (SQLException e1) { 
				e1.printStackTrace();
			}
			 e.printStackTrace();
		}
		 
	}

}
