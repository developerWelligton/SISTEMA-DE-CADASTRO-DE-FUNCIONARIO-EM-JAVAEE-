package dao;

import java.sql.Connection;
 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import beandto.BeanDtoGraficoQtdColabor;
import beandto.BeanDtoGraficoSalarioUser; 
import connection.SingleConnection;
import model.ModelLogin;
import model.ModelTelefone;



public class DAOUsuarioRepository {
	
	private Connection connection;
	
 
	
	
	public DAOUsuarioRepository() {
		 connection = SingleConnection.getConnection();
	}
	
	 
	public double  montarMediaAdemissaoDemissao(Long userLogado) throws Exception{
		 
			String sql = "SELECT  count(*) as total, sum(dataadmissao - datademissao) AS quantidade_dias FROM model_login where datademissao is not null and usuario_id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setLong(1, userLogado);
			
			ResultSet rst = preparedStatement.executeQuery();
			
			double quantColabor = 0;
			double somaDosDias = 0;
			
			
			// BeanDtomediarotatividade beanDtomediarotatividade = new BeanDtomediarotatividade();
					
			while(rst.next()) {
				int qtdColabor = rst.getInt("total");
				int qtdDias = rst.getInt("quantidade_dias");
				
			 	quantColabor =   qtdColabor;
				 somaDosDias =   qtdDias;
			}
		 
			//beanDtomediarotatividade.setQtdColar(quantColabor);
			//beanDtomediarotatividade.setSumDay(somaDosDias);
			 
			 double resmedia = somaDosDias/quantColabor; 
			return resmedia;
		}
//	
	public BeanDtoGraficoQtdColabor montarGraficoQuantidadeCargo(Long userLogado) throws Exception{
		
		String sql = "select count(*)as quant_colabor, perfil from model_login where usuario_id = ? group by perfil having perfil is not null";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet rst = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>(); 
		List<Integer> qtdColabors = new ArrayList<Integer>();
		
		
		BeanDtoGraficoQtdColabor beanDtoGraficoQtdColabor = new BeanDtoGraficoQtdColabor(); 
				
		while(rst.next()) {
			int qtdColabor = rst.getInt("quant_colabor");
			String perfil = rst.getString("perfil");
			
			perfils.add(perfil);
			qtdColabors.add(qtdColabor);
		}
		 beanDtoGraficoQtdColabor.setPerfils(perfils);
		 beanDtoGraficoQtdColabor.setQtdColabors(qtdColabors);
		 
		return  beanDtoGraficoQtdColabor;
	}
	
	
	//data
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado, String dataInicial, String dataFinal) throws Exception {
		
		String sql = "select avg(rendamensal) as media_salarial,perfil from model_login where usuario_id = ? and datanascimento >= ? and datanascimento <= ? group by perfil having perfil in ('ADMIN') OR perfil in ('AUXILIAR') OR perfil in ('SECRETARIA')";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		 
		//TRATA DATA INICIAL
		String date2 =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial));  
		java.sql.Date date3 = java.sql.Date.valueOf(date2);
		//TRATA DATA FINAL
		String date4 =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal));  
		java.sql.Date date5 = java.sql.Date.valueOf(date4);
		
		   
		preparedStatement.setDate(2, date3);
		preparedStatement.setDate(3, date5);
		
		ResultSet rst = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while(rst.next()) {
			Double media_salarial = rst.getDouble("media_salarial");//igual do banco
			String perfil = rst.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
		 
	}
	
	
	public BeanDtoGraficoSalarioUser montarGraficoMediaSalario(Long userLogado) throws Exception  {
		
		String sql = "select avg(rendamensal) as media_salarial,perfil from model_login where usuario_id = ? group by perfil having perfil in ('ADMIN') OR perfil in ('AUXILIAR') OR perfil in ('SECRETARIA')";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setLong(1, userLogado);
		
		ResultSet rst = preparedStatement.executeQuery();
		
		List<String> perfils = new ArrayList<String>();
		List<Double> salarios = new ArrayList<Double>();
		
		BeanDtoGraficoSalarioUser beanDtoGraficoSalarioUser = new BeanDtoGraficoSalarioUser();
		
		while(rst.next()) {
			Double media_salarial = rst.getDouble("media_salarial");//igual do banco
			String perfil = rst.getString("perfil");
			
			perfils.add(perfil);
			salarios.add(media_salarial);
		}
		
		beanDtoGraficoSalarioUser.setPerfils(perfils);
		beanDtoGraficoSalarioUser.setSalarios(salarios);
		
		return beanDtoGraficoSalarioUser;
	}
	
	
 /*GRAVAR USUARIO*/		
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {
		
			if (objeto.isNovo()) {// Grava um novo
				 
			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero,   datanascimento, rendamensal, datademissao, dataadmissao) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?, ?, ?, ?, ?)";
			PreparedStatement preparedSql = connection.prepareStatement(sql);
			
			preparedSql.setString(1,objeto.getLogin()); 
			preparedSql.setString(2,objeto.getSenha());
			preparedSql.setString(3,objeto.getNome());
			preparedSql.setString(4,objeto.getEmail());
			 
			
			preparedSql.setLong(5, userLogado);	
			
			//select> servlet post,gravardao
			//COMPONENTE(name) > CONTROLLER > DAO ccd
			//modifica todas as querys
			preparedSql.setString(6,objeto.getPerfil());
			
			preparedSql.setString(7,objeto.getSexo());
			
			preparedSql.setString(8,objeto.getCep());
			preparedSql.setString(9,objeto.getLogradouro());
			preparedSql.setString(10,objeto.getBairro());
			preparedSql.setString(11,objeto.getLocalidade());
			preparedSql.setString(12,objeto.getUf());
			preparedSql.setString(13,objeto.getNumero()); 
			//preparedSql.setDouble(14,objeto.getRendamensal()); 
			
			preparedSql.setDate(14, objeto.getDatanascimento());
			  
			preparedSql.setDouble(15, objeto.getRendamensal());
			preparedSql.setDate(16, objeto.getDataAdemissao());
			preparedSql.setDate(17, objeto.getDataDemissao());
			
			 
			 
			
			
			preparedSql.execute();
			
			connection.commit();
			
				//se estiver foto e usuario novo 
				if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
					sql = "update model_login set fotouser = ? , extensaofotoUser= ? where login = ?";
					preparedSql = connection.prepareStatement(sql);
					preparedSql.setString(1,objeto.getFotouser());
					preparedSql.setString(2,objeto.getExtensaofotouser());
					preparedSql.setString(3,objeto.getLogin());
					
					preparedSql.execute();
					
					connection.commit();
				}
			
			 
			//Criar uma daousuariorepository no servlet 
			}else {//se estiver atualizando usuario
				String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep= ?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? , datanascimento=?, rendamensal=?, datademissao=?, dataadmissao=?  WHERE id = " +objeto.getId()+ ";";
				
				PreparedStatement prepareSql = connection.prepareStatement(sql);
				
				prepareSql.setString(1,objeto.getLogin());
				prepareSql.setString(2,objeto.getSenha());
				prepareSql.setString(3,objeto.getNome());
				prepareSql.setString(4,objeto.getEmail());
				prepareSql.setString(5,objeto.getPerfil());
				prepareSql.setString(6,objeto.getSexo());

				prepareSql.setString(7,objeto.getCep());
				prepareSql.setString(8,objeto.getLogradouro());
				prepareSql.setString(9,objeto.getBairro());
				prepareSql.setString(10,objeto.getLocalidade());
				prepareSql.setString(11,objeto.getUf());
				prepareSql.setString(12,objeto.getNumero());
				//prepareSql.setDouble(13,objeto.getRendamensal());
				prepareSql.setDate(13, objeto.getDatanascimento());
				
				prepareSql.setDouble(14,objeto.getRendamensal());
				
				prepareSql.setDate(15,objeto.getDataAdemissao());
				prepareSql.setDate(16,objeto.getDataDemissao());
				 
				
				 
				prepareSql.executeUpdate();
				connection.commit();
				
					//se estiver foto e usuario novo 
					if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
						sql = "update model_login set fotouser = ? , extensaofotoUser= ? where id = ?";
						prepareSql = connection.prepareStatement(sql);
						prepareSql.setString(1, objeto.getFotouser());
						prepareSql.setString(2, objeto.getExtensaofotouser());
						//quando a gente atualiza ele ja tem os dados basta buscar
						prepareSql.setLong(3, objeto.getId());
						
						prepareSql.execute();
						
						connection.commit();
					}
			}
		 
		return this.consultaUsuario(objeto.getLogin(), userLogado);
		
		
	}
 /*TOTAL PAGINA*/		
	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = " select count(1) as total from model_login where usuario_id= " + userLogado; 
		
		PreparedStatement statement = connection.prepareStatement(sql);

		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();//para poder abrir o resultado
		
		Double cadastros = resultado.getDouble("total");
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		if(resto > 0) {
			pagina++;
		}
		
		
		return pagina.intValue();	
	}
 /*CONSULTA USUARIO LIST PAGINADA*/		
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception{
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		//consulta onde o admin é falso , logo criar outra consulta
		String sql = " select * from model_login where useradmin is false and usuario_id="+userLogado+" order by nome offset "+offset+" limit 5; ";
		
		PreparedStatement statement = connection.prepareStatement(sql);

		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email")); 
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome")); 
			
			modelLogin.setPerfil(resultado.getString("perfil"));
			
			modelLogin.setSexo(resultado.getString("sexo"));
			
			//nao esquece retorno
			retorno.add(modelLogin);
		}
		
		return retorno;
		//agora so preparar o json
	}
 
 //CONSULTAR TODOS OS USUARIOS COM LIMITE DE 5
		public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception{
			
			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			//consulta onde o admin é falso , logo criar outra consulta
			
			String sql = "select * from model_login where useradmin is false and usuario_id="+userLogado+" limit 5 ";
			
			PreparedStatement statement = connection.prepareStatement(sql);
 
			
			ResultSet resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setEmail(resultado.getString("email")); 
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));  
				modelLogin.setPerfil(resultado.getString("perfil")); 
				modelLogin.setSexo(resultado.getString("sexo"));
				
				//nao esquece retorno
				retorno.add(modelLogin);
			}
			
			return retorno;
			//agora so preparar o json
		}

	//RELATORIO - CONSULTAR TODOS USUARIOS 
		public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {

			List<ModelLogin> retorno = new ArrayList<ModelLogin>();
			// consulta onde o admin é falso , logo criar outra consulta

			String sql = "select * from model_login where useradmin is false and usuario_id=" + userLogado;
					 

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) {

				ModelLogin modelLogin = new ModelLogin();
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setNome(resultado.getString("nome"));
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setPerfil(resultado.getString("perfil"));
				modelLogin.setSexo(resultado.getString("sexo"));
				modelLogin.setDatanascimento(resultado.getDate("datanascimento"));
				
				modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
				//dataademissao e demissao
				
				modelLogin.setTelefones(this.listFone(modelLogin.getId()));
				// nao esquece retorno
				retorno.add(modelLogin);
			}

			return retorno;
			//agora so preparar o json
		}

 //RELATORIO LISTAR POR FAIXA DE DATA - CONSULTAR TODOS USUARIOS 
				public List<ModelLogin> consultaUsuarioListRel2(Long userLogado, String dataInicial, String dataFinal) throws Exception {

					List<ModelLogin> retorno = new ArrayList<ModelLogin>();
					// consulta onde o admin é falso , logo criar outra consulta

					//LISTAR POR FAIXA DE DATA
					String sql = "select * from model_login where useradmin is false and usuario_id=" + userLogado + "and  datanascimento >= ? and datanascimento <= ?";
							 

					PreparedStatement statement = connection.prepareStatement(sql);
					
					//TRATA DATA INICIAL
					String date2 =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial));  
					java.sql.Date date3 = java.sql.Date.valueOf(date2);
					//TRATA DATA FINAL
					String date4 =  new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal));  
					java.sql.Date date5 = java.sql.Date.valueOf(date4);
					
					statement.setDate(1,date3);
					statement.setDate(2,date5);

					ResultSet resultado = statement.executeQuery();

					while (resultado.next()) {

						ModelLogin modelLogin = new ModelLogin();
						modelLogin.setEmail(resultado.getString("email"));
						modelLogin.setNome(resultado.getString("nome"));
						modelLogin.setId(resultado.getLong("id"));
						modelLogin.setLogin(resultado.getString("login"));
						modelLogin.setPerfil(resultado.getString("perfil"));
						modelLogin.setSexo(resultado.getString("sexo"));
						modelLogin.setDatanascimento(resultado.getDate("datanascimento"));
						//dataademissao e demissao
						modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
						
						modelLogin.setTelefones(this.listFone(modelLogin.getId()));
						// nao esquece retorno
						retorno.add(modelLogin);
					}

					return retorno;
					//agora so preparar o json
				}
//LISTAR TELEFONE
		public List<ModelTelefone> listFone(Long idUserPai) throws Exception{
			List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();
			
			
			String sql = "select * from telefone where usuario_pai_id = ?";
			 
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, idUserPai);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				ModelTelefone modelTelefone  = new ModelTelefone();
				
				modelTelefone.setId(resultSet.getLong("id"));
				modelTelefone.setNumero(resultSet.getString("numero"));
				modelTelefone.setUsuario_cad_id(this.consultaUsuarioID(resultSet.getLong("usuario_cad_id")));
				modelTelefone.setUsuario_pai_id(this.consultaUsuarioID(resultSet.getLong("usuario_pai_id")));
				
				retorno.add(modelTelefone);
			}
			
			return retorno;
			
		}
		
		
		
 //CONSULTAR USUARIO LIST TOTAL PAGINA MODAL  
		public int consultaUsuarioListTotalPaginacaoModal(String nome, Long userLogado) throws Exception{
			
			 
			String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id= ? ";
			
			PreparedStatement statement = connection.prepareStatement(sql); 
			statement.setString(1, "%"+nome+"%");
			statement.setLong(2, userLogado);
			
			ResultSet resultado = statement.executeQuery();
			
			resultado.next();//para poder abrir o resultado
			
			Double cadastros = resultado.getDouble("total");
			
			Double porpagina = 5.0;
			
			Double pagina = cadastros / porpagina;
			
			Double resto = pagina % 2;
			
			if(resto > 0) {
				pagina++;
			}
			
			
			return pagina.intValue();	
			 
		}	

	//CONSULTAR USUARIO  String nome, Long userLogado
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception{
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select *from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id= ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql); 
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email")); 
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome")); 
			modelLogin.setPerfil(resultado.getString("perfil")); 
			modelLogin.setSexo(resultado.getString("sexo"));
			
			//nao esquece retorno
			retorno.add(modelLogin);
		}
		
		return retorno;
		//agora so preparar o json
	}
	
	// Se eu vou consultar eu vou ter que retornar, para retornar tem um certo modelo, no caso modelo login
	
	//O id não vou ter porque ele vai ser gerado quando chegar no banco
	//O login vai ser sempre único, entao ja tenho o identificador de login = String login
	//
	
	//CONSULTAR USUARIO LIKE POR NOME COM OFFSET
	public List<ModelLogin> consultaUsuarioListOffset(String nome, Long userLogado, int offset) throws Exception{
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id= ? offset "+offset+" limit 5";
		 
		PreparedStatement statement = connection.prepareStatement(sql); 
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado); 
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {
			
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email")); 
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome"));
			 
			modelLogin.setPerfil(resultado.getString("perfil"));
			
			modelLogin.setSexo(resultado.getString("sexo"));
			
			//nao esquece retorno
			retorno.add(modelLogin);
		}
		
		return retorno;
		//agora so preparar o json
	}
	
	//CONSULTAR usuario : LOGIN
		public ModelLogin consultaUsuario(String login ) throws Exception{
			
			ModelLogin modelLogin = new ModelLogin(); 
			String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			 
			
			//passar essa expressão "statement.executeQuery();" para um objeto de resultSet  
			ResultSet resultado = statement.executeQuery();
			
			//se tem resultado
			while(resultado.next()) {
				//criar um objeto
				//se consultar e achar no banco já retorna esse objeto
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("Nome"));
				modelLogin.setSenha(resultado.getString("senha"));
				
				modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
				
				modelLogin.setPerfil(resultado.getString("perfil"));
				
				modelLogin.setSexo(resultado.getString("sexo"));
			 
			}
			
			return modelLogin; 
		}
		       //CONSULTAR usuario Logado: Login
				public ModelLogin consultaUsuarioLogado(String login ) throws Exception{
					
					ModelLogin modelLogin = new ModelLogin(); 
					String sql = "select * from model_login where upper(login) = upper('"+login+"')";
					
					PreparedStatement statement = connection.prepareStatement(sql);
					 
					
					//passar essa expressão "statement.executeQuery();" para um objeto de resultSet  
					ResultSet resultado = statement.executeQuery();
					
					//se tem resultado
					while(resultado.next()) {
						//criar um objeto
						//se consultar e achar no banco já retorna esse objeto
						modelLogin.setId(resultado.getLong("id"));
						modelLogin.setEmail(resultado.getString("email"));
						modelLogin.setLogin(resultado.getString("login"));
						modelLogin.setNome(resultado.getString("Nome"));
						modelLogin.setSenha(resultado.getString("senha")); 
						
						modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
						
						modelLogin.setPerfil(resultado.getString("perfil"));
						
						modelLogin.setSexo(resultado.getString("sexo"));
						
						modelLogin.setFotouser(resultado.getString("fotouser"));
						
						modelLogin.setCep(resultado.getString("cep"));
						modelLogin.setLogradouro(resultado.getString("logradouro"));
						modelLogin.setBairro(resultado.getString("bairro"));
						modelLogin.setLocalidade(resultado.getString("localidade"));
						modelLogin.setUf(resultado.getString("uf"));
						modelLogin.setNumero(resultado.getString("numero")); 
						//modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
						
						modelLogin.setDatanascimento(resultado.getDate("datanascimento"));
						
						
						
						modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
					}
					
					return modelLogin; 
				}
	
	//CONSULTAR LOGIN (ADMISTRADO)
	public ModelLogin consultaUsuario(String login,Long userLogado) throws Exception{
		
		ModelLogin modelLogin = new ModelLogin(); 
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id="+userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		 
		
		//passar essa expressão "statement.executeQuery();" para um objeto de resultSet  
		ResultSet resultado = statement.executeQuery();
		
		//se tem resultado
		while(resultado.next()) {
			//criar um objeto
			//se consultar e achar no banco já retorna esse objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("Nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			
			modelLogin.setPerfil(resultado.getString("perfil"));
			
			modelLogin.setSexo(resultado.getString("sexo"));
			
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
			//modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
			modelLogin.setDatanascimento(resultado.getDate("datanascimento"));
			 
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
		}
		
		return modelLogin; 
	}

//CONSULTAR POR ID E USUARIO LOGADO
	public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();
		
		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id= ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);
		// passar essa expressão "statement.executeQuery();" para um objeto de resultSet
		ResultSet resultado = statement.executeQuery();

		// se tem resultado
		while (resultado.next()) {
			// criar um objeto
			// se consultar e achar no banco já retorna esse objeto
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("Nome"));
			modelLogin.setSenha(resultado.getString("senha"));
			
			modelLogin.setPerfil(resultado.getString("perfil"));
			
			modelLogin.setSexo(resultado.getString("sexo"));
			
			modelLogin.setFotouser(resultado.getString("fotouser"));
			//precisa carregar a extensão para download
			
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero")); 
			
			//modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			
			modelLogin.setDatanascimento(resultado.getDate("datanascimento")); 
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			modelLogin.setDataAdemissao(resultado.getDate("dataadmissao"));
			modelLogin.setDataDemissao(resultado.getDate("datademissao"));
		 
		}

		return modelLogin;

	}
	
//CONSULTAR POR ID
		public ModelLogin consultaUsuarioID(Long id ) throws Exception {

			ModelLogin modelLogin = new ModelLogin();
			
			String sql = "select * from model_login where id = ? and useradmin is false ";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id); 
			// passar essa expressão "statement.executeQuery();" para um objeto de resultSet
			ResultSet resultado = statement.executeQuery();

			// se tem resultado
			while (resultado.next()) {
				// criar um objeto
				// se consultar e achar no banco já retorna esse objeto
				modelLogin.setId(resultado.getLong("id"));
				modelLogin.setEmail(resultado.getString("email"));
				modelLogin.setLogin(resultado.getString("login"));
				modelLogin.setNome(resultado.getString("Nome"));
				modelLogin.setSenha(resultado.getString("senha"));
				
				modelLogin.setPerfil(resultado.getString("perfil"));
				
				modelLogin.setSexo(resultado.getString("sexo"));
				
				modelLogin.setFotouser(resultado.getString("fotouser"));
				//precisa carregar a extensão para download
				
				modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
				
				modelLogin.setCep(resultado.getString("cep"));
				modelLogin.setLogradouro(resultado.getString("logradouro"));
				modelLogin.setBairro(resultado.getString("bairro"));
				modelLogin.setLocalidade(resultado.getString("localidade"));
				modelLogin.setUf(resultado.getString("uf"));
				modelLogin.setNumero(resultado.getString("numero")); 
				
				//modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
				
				modelLogin.setDatanascimento(resultado.getDate("datanascimento"));
				
				modelLogin.setRendamensal(resultado.getDouble("rendamensal"));
			}

			return modelLogin;

		}
		
	
	
	//se existe usuario com mesmo login cadastrado e nao permitir gravar
	//é identificada no servlet para poder enviar para tela!
	public boolean validaLogin(String login) throws Exception {
		String sql = "select count(1)>0 as existe from model_login where upper(login) = upper('"+login+"')";
		
		PreparedStatement statement = connection.prepareStatement(sql);  
		ResultSet resultado = statement.executeQuery();
		
		 resultado.next();
		 //para ele entrar nos resultados do sql
		 return resultado.getBoolean("existe");
	 
		
		 
		
	}
	
	//deletar usuario
	public void deletarUser(String idUser) throws Exception{
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false ";
		PreparedStatement preparesql = connection.prepareStatement(sql);
		preparesql.setLong(1, Long.parseLong(idUser));
		preparesql.executeUpdate(); 
		connection.commit();
	}


	 
	
	
	
	
	
}
