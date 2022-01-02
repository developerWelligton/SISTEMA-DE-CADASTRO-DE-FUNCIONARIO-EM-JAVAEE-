<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>  
    <%@page buffer="8192kb" autoFlush="true" %>
    
<!DOCTYPE html>
<html lang="en">  
 
 <jsp:include page="head.jsp"></jsp:include>

  <body>
 <!-- Pre-loader start -->
				   <jsp:include page="themeLoader.jsp"></jsp:include> 			   
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
           			<jsp:include page="nav.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                   <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      <jsp:include page="page-header.jsp"></jsp:include>
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body"> 
                                    
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
												  
													<div class="card-block">
													<h4 class="sub-title"> Cadastro de Usuário</h4>
													 
														 
 <!-- DEFINIR UM ESTILO DE LAYOUT PARA USAR EM TODO O SISTEMA -->
														
														<form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath()%>/ServletUsuarioController" method="post" id="FormUser">
                                                            
                                                            <input type="hidden" name="acao" id="acao" value="">
                                                            <!--  
                                                            get: consultar/deletar
                                                            post: salvar/atualizar 
                                                             -->
                                                                
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="id"  id="id" class="form-control" placeholder="Enter ID" readonly="readonly" value="${modolLogin.id}" >
                                                                <span class="form-bar"></span>
                                                                <label class="float-label" >ID:</label>
                                                                 
                                                            </div>
                                                            <!-- IMAGEM UPLOAD -->
                                                            <div class="form-group form-default input-group mb-4"> 
	                                                            	<div class="input-group-prepend">
	                                                            	
	                         
																	   <c:if test="${modolLogin.fotouser != '' && modolLogin.fotouser != null}">
																	      
																	        <img alt="Imagem user" id="fotoembase64"  src="${modolLogin.fotouser} " width="70px">
																	       </a> 
																	    </c:if>  
																	     
																	    <c:if test="${modolLogin.fotouser == '' || modolLogin.fotouser == null}">
																	    
																	        <img alt="Imagem user"  id="fotoembase64" src="assets\images\avatar.png" width="70px">  
																	        
																      </c:if> 
																  
															     	  
															  		</div>  
															  		<input accept="image/*" id="fileFoto" name="fileFoto" onchange="visualizarImg('fotoembase64','fileFoto');" style="margin-top: 50px; margin-left: 5px" type="file" class="form-control-file" id="" >
                                                            </div>
                                                            
                                                             <div class="form-group form-default form-static-label">
                                                                <input type="text" name="nome"   id="nome" class="form-control"  value="${modolLogin.nome}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Nome</label>
                                                            </div>
                                                            
                                                            
                                                             <div class="form-group form-default form-static-label">
                                                                <input type="text" name="datanascimento"   id="datanascimento" class="form-control"  value=" ${modolLogin.datanascimento}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data Nascimento</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="dataAdmissao"   id="dataAdmissao" class="form-control"  value=" ${modolLogin.dataAdemissao}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data Admissão</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="datademissao"   id="datademissao" class="form-control"  value="${modolLogin.dataDemissao}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Data Demissão</label>
                                                            </div>
                                                             
                                                             
                                                               <div class="form-group form-default form-static-label">
                                                                <input type="text" name="rendamensal"   id="rendamensal" class="form-control"   value="${modolLogin.rendamensal}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Renda Mensal</label>
                                                             </div>  
                                                           
                                                            
                                                             
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="email" name="email"  id="email" class="form-control"  placeholder="Entre com email"  autocomplete="off"  value="${modolLogin.email}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">E-mail:</label>
                                                            </div>
                                                            
															<div class="form-group form-default form-static-label" required> 
																<select class="form-control"
																	aria-label="Default select example" name="perfil" required>
																	<option disabled="disabled"  >[Selecione o Perfil]</option>
																	
																	<option value="ADMIN" <%  
																	
																	ModelLogin modelLogin =(ModelLogin) request.getAttribute("modolLogin");
																	
										 
																	if(modelLogin!=null &&  modelLogin.getPerfil().equals("ADMIN")){
																		out.print(" ");
																		out.print("selected=\"selected\""); 
																		out.print(" ");
																	 
																	} %> > Admin </option>
																	
																	<option value="SECRETARIA" <%  
																	
																	  modelLogin =(ModelLogin) request.getAttribute("modolLogin");
																	
																	if(modelLogin!=null &&  modelLogin.getPerfil().equals("SECRETARIA")){
																		out.print(" ");
																		out.print("selected=\"selected\"");  
																		out.print(" ");
																		
																	} %> >Secretário 	</option>
																	
																	<option value="AUXILIAR" <% 
																		
																	  modelLogin =(ModelLogin) request.getAttribute("modolLogin");
																 
																	
																	if(modelLogin!=null &&  modelLogin.getPerfil().equals("AUXILIAR")){
																		out.print(" ");
																		out.print("selected=\"selected\""); 
																		out.print("");
																		
																	} %> >Auxiliar		</option>
																	
																</select>
																<span class="form-bar"></span>
																<label class="float-label">Perfil</label>
																
															</div>
															
															<div class="form-group form-default form-static-label">
                                                                <input onblur="pesquisaCep();" type="text" name="cep"  id="cep" class="form-control"   autocomplete="off" value="${modolLogin.cep}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Cep</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="logradouro"  id="logradouro" class="form-control"   autocomplete="off" value="${modolLogin.logradouro}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Logradouro</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="bairro"  id="bairro" class="form-control"      autocomplete="off" value="${modolLogin.bairro}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Bairro</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="localidade"  id="localidade" class="form-control"    autocomplete="off" value="${modolLogin.localidade}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Localidade</label>
                                                            </div>
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="text" name="uf"  id="uf" class="form-control"    autocomplete="off" value="${modolLogin.uf} "required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Estado</label>
                                                            </div>
                                                             <div class="form-group form-default form-static-label">
                                                                <input type="text" name="numero"  id="numero" class="form-control"    autocomplete = "off" value="${modolLogin.numero}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Numero</label>
                                                            </div>
															<div class="form-group form-default form-static-label">
                                                                <input type="text" name="login"  id="login" class="form-control"    autocomplete="off"    value="${modolLogin.login}"required >
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Login</label>
                                                            </div>
                                                            
                                                            <div class="form-group form-default form-static-label">
                                                                <input type="password" name="senha"  id="senha" class="form-control" placeholder="Entre com a senha"     autocomplete="off"  value="${modolLogin.senha}" required>
                                                                <span class="form-bar"></span>
                                                                <label class="float-label">Senha</label>
                                                            </div>
                                                            	
                                                             <div class="form-group form-default form-static-label">
                                                             	<input type="radio" name="sexo" checked="checked" value="MASCULINO" <%
                                                             	modelLogin = (ModelLogin) request.getAttribute("modolLogin");
                                                             	
                                                             	if(modelLogin!=null &&  modelLogin.getSexo().equals("MASCULINO")){
																	out.print(" ");
																	 out.print("checked=\"checked\""); 
																	out.print("");
																	
																} 
                                                             	
                                                             	%> />Masculino
                                                             	<input type="radio" name="sexo"  value="FEMININO" <%
                                                             	modelLogin = (ModelLogin) request.getAttribute("modolLogin");
                                                             	
                                                             	if(modelLogin!=null &&  modelLogin.getSexo().equals("FEMININO")){
																	out.print(" ");
																	 out.print("checked=\"checked\""); 
																	out.print("");
																	
																} 
                                                             	
                                                             	%>/>Feminino
                                                             </div>
                                                             
                                                            
                                                            
                          <!-- BOTAO -->                                  
                                                            <button class="btn btn-primary waves-effect waves-light" onclick="LimparForm();"  >Novo</button>
                                                            <button type="submit"  class="btn btn-success">Salvar</button>
                                                            <button type="button" class="btn btn-danger" onclick="criarDeleteComAjax();">Excluir</button>
                                                        	<button type="button" class="btn btn-secundary" data-toggle="modal" data-target="#exampleModalUsuario" style="background-color: pink"> Consultar</button>
                                                        	
                                                        	<c:if test="${modolLogin.id > 0 }"> 
                                                        		<a href="<%=request.getContextPath()%>/ServletTelefone?iduser=${modolLogin.id}" class="btn btn-primary waves-effect waves-light" >Adicionar Telefone</a>
                                                       		 </c:if>	
                                                        </form>

													</div>
												</div>
											</div>
										</div>
										 
									<span id="msg"> ${msg}</span>
									
										<div style="height: 300px; overflow: scroll">
											<!-- Tabela -->
											<table class="table" id="tabelaresultadosview">
												<thead>
													<tr>
														<th scope="col">ID</th>
														<th scope="col">Nome</th>
														<th scope="col">Ver</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items='${modelLogins}' var='ml'>
														<tr>
															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td> <a class="btn btn btn-success" href="<%= request.getContextPath()%>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a></td>
														</tr> 
													</c:forEach>
												</tbody>
											</table>
										</div>
										
										<nav aria-label="Page navigation example">
										  <ul class="pagination"> 
										  	
										  	<%
										  		int totalPagina = (int) request.getAttribute("totalPagina");
										  	
										  		for(int p = 0; p < totalPagina; p++){
										  			//consultar dados paginados
										  			String url = request.getContextPath()+ "/ServletUsuarioController?acao=paginar&pagina="+ (p * 5);
										  			out.print(" <li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p+1)+"</a></li> ");
										  			
										  		} 
										  	%> 
										  </ul>
										</nav>



									</div>
                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
     
     <jsp:include page="javascriptFile.jsp"></jsp:include>
     
     
     
		     <!-- Modal -->
		<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Consultar usuário </h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		      <!-- pesquisa input -->
		        <div class="input-group mb-3">
				  <input id="nomeBusca" type="text" class="form-control" placeholder="Nome" aria-label="Recipient's username" aria-describedby="basic-addon2">
				  <div class="input-group-append">
				    <button class="btn btn btn-success" type="button" onclick="buscarUsuario()">Buscar</button>
				  </div>
				</div>
				<!-- Lista de usuario scroll 5 elementos  -->
				<div style="height:300px; overflow: scroll" > 
					<!-- Tabela -->
					<table class="table" id="tabelaresultados">
					  <thead>
					    <tr>
					      <th scope="col">ID</th>
					      <th scope="col">Nome</th>
					      <th scope="col">Ver</th> 
					    </tr>
					  </thead>
					  <tbody>
					    	
					  </tbody>
					</table> 
				</div>
				
				<!-- PAGINAÇÃO DO MODAL -->
				<nav aria-label="Page navigation example"> 
					<ul class="pagination" id="ulPaginacaoUserAjax"> 
					
					</ul>
				 </nav> 
				 
				 
				<span id="totalResultados"></span> 
		      </div>
		      
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
		     
		      </div>
		    </div>
		  </div>
		</div>
     
     <script type="text/javascript">
     
     
     
     $("#rendamensal").maskMoney({showSymbol:true, symbol:"R$ ", decimal:",",thousands:"."});
   
   	
     const formatter = new Intl.NumberFormat('pt-BR',{
    	 currency:'BRL',
    	 minimumFractionDigits : 2
     });
     
     $("#rendamensal").val(formatter.format($("#rendamensal").val()));
     //
         var datademissao = $("#datademissao").val(); 
      
      if(datademissao != null && datademissao!=''){
    	  
    	  var dateFormat = new Date(datademissao); 
    	  $("#datademissao").val(dateFormat.toLocaleDateString('pt-BR', {timeZone:'UTC'})); 
     
      }
      
      $("#nome").focus();
         
     $( function() { 
    	 
   	  $("#datademissao").datepicker({
   		    dateFormat: 'dd/mm/yy',
   		    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
   		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
   		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
   		    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
   		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
   		    nextText: 'Próximo',
   		    prevText: 'Anterior'
   		});
   } );
     
     //
         var dataAdmissao = $("#dataAdmissao").val(); 
      
      if(dataAdmissao != null && dataAdmissao!=''){
    	  
    	  var dateFormat = new Date(dataAdmissao); 
    	  $("#dataAdmissao").val(dateFormat.toLocaleDateString('pt-BR', {timeZone:'UTC'})); 
     
      }
      
      $("#nome").focus();
         
     $( function() { 
    	 
   	  $("#dataAdmissao").datepicker({
   		    dateFormat: 'dd/mm/yy',
   		    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
   		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
   		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
   		    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
   		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
   		    nextText: 'Próximo',
   		    prevText: 'Anterior'
   		});
   } );
	   
    
     //
      var dataNascimento = $("#datanascimento").val(); 
      
      if(dataNascimento != null && dataNascimento!=''){
    	  
    	  var dateFormat = new Date(dataNascimento); 
    	  $("#datanascimento").val(dateFormat.toLocaleDateString('pt-BR', {timeZone:'UTC'})); 
     
      }
      
      $("#nome").focus();
         
     $( function() { 
    	 
   	  $("#datanascimento").datepicker({
   		    dateFormat: 'dd/mm/yy',
   		    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
   		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
   		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
   		    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
   		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
   		    nextText: 'Próximo',
   		    prevText: 'Anterior'
   		});
   } );
     
      
       //permitir só numero
     	$("#numero").keypress(function (event){
     		return /\d/.test(String.fromCharCode(event.keyCode))
     	})
     	
     	  //permitir só numero
     	$("#cep").keypress(function (event){
     		return /\d/.test(String.fromCharCode(event.keyCode))
     	})
     	
     
     
     
     
     	function pesquisaCep(){
     		var cep = $("#cep").val();
     		//Fazer uma requisição ajax para poder receber(capturar) o json que vem do via cep
     		
     		 //Consulta o webservice viacep.com.br/
	       	 $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {
	       		 if (!("erro" in dados)) {
                     //Atualiza os campos com os valores da consulta.
                     //setando campos da tela
                     $("#cep").val(dados.cep);
                     $("#logradouro").val(dados.logradouro);
                     $("#bairro").val(dados.bairro);
                     $("#localidade").val(dados.localidade);
                     $("#uf").val(dados.uf); 
                 } //end if.
                 else {
                     //CEP pesquisado não foi encontrado.
                     limpa_formulário_cep();
                     alert("CEP não encontrado.");
                 }
	         });
     	}
     	
     	
     	
     	function visualizarImg(fotoembase64, filefoto){ 
     		
     		var preview = document.getElementById(fotoembase64);
     		var fileUser = document.getElementById(filefoto).files[0];
     		var reader = new FileReader();
     		
     		reader.onloadend = function (){
     			preview.src = reader.result;//Carrega a foto na tela;
     		};
     		
     		if(fileUser){
     			reader.readAsDataURL(fileUser);//preview da imagem 
     		}else{
     			preview.src = '';
     		}
     	}
     
     	
     	
     	function verEditar(id){
     		 
     		var urlAction = document.getElementById('FormUser').action; 
     	 
     		//redirecionamento com js
     		window.location.href = urlAction + '?acao=buscarEditar&id='+id;//faz um get 
     	}
     	
     
     	
     	function buscarUserPagAjax(url){//mandar o parâmetro para servlet doGet: &acao=buscarUserAjaxPage&
     	 
     		var urlAction = document.getElementById('FormUser').action;	
     		var nomeBusca = document.getElementById('nomeBusca').value;
     		  
     		$.ajax({  
     			
  				method:"get",//cai no get da servlet
  				url: urlAction,//pra poder passar os parâmetros 
  				data: url,
  				success: function(response,textStatus, xhr){
  					
  					var json = JSON.parse(response);
  					 
  					 
  					$( "#tabelaresultados >  tbody > tr" ).each( function(){
  					  this.parentNode.removeChild( this ); 
  					});
  					
  					
				 $("#ulPaginacaoUserAjax > li").remove();
				 
  					 
					for(var p = 0; p < json.length; p++){ 
  						$('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+' </td>  <td>'+json[p].nome+' </td> <td><button type="button" class="btn btn-info" onclick="verEditar('+json[p].id+')">Ver </button> </td></tr>')
  						
  					} 	 
					
					document.getElementById('totalResultados').textContent = "Quantidade de registros: " + json.length;
  					 
						var totalPagina = xhr.getResponseHeader("totalPagina"); 
						
						for(var p = 0; p < totalPagina; p++){ 
								var url = 'nomeBusca=' + nomeBusca +'&acao=buscarUserAjaxPage&pagina='+(p*5); 
								//ESSA FUNCIONALIDADE TEM QUE FICAR IGUAL A OUTRA QUE CHAMA BUSCAR
							$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link"  href="#" onclick="buscarUserPagAjax(\''+url+'\')">'+(p+1)+'</a></li>');
						  
						} 
						 
  				}
  				
  			}).fail(function(xhr,status,erroThrown){ 
  				alert("Erro ao buscar usuario por nome:" + xhr.reponseText)
  				
  			});
     		
     	}
     
     
	     function buscarUsuario(){
	    	 
	  		var nomeBusca = document.getElementById('nomeBusca').value;
	  		 
	  		//Validando que tem que ter valor pra buscar no banco
	  		if(nomeBusca != null && nomeBusca!='' && nomeBusca.trim() != '' ){ 
	  			//redirecionamento para servlet
	  			var urlAction = document.getElementById('FormUser').action;	
	  			
	  			$.ajax({ 
	  				
	      				method:"get",//cai no get da servlet
	      				url: urlAction,//pra poder passar os parâmetros
	      				data:"nomeBusca="+nomeBusca+ '&acao=buscarUserAjax', // passar dados
	      				
	      				//testeStatus: 200 , xhr: Pega os dados do cabeçalho
	      				success: function(response, textStatus, xhr){
	      					
	      					var json = JSON.parse(response); 
	      					//console.log(json[0].id)
	      					
	 						 	$( "#tabelaresultados >  tbody > tr" ).each( function(){
			  					  this.parentNode.removeChild( this ); 
			  					});
	      					
	 						 $("#ulPaginacaoUserAjax > li").remove();
	      					
	 						 
	 						for(var p = 0; p < json.length; p++){
	 							 
	      						$('#tabelaresultados > tbody').append('<tr> <td>'+json[p].id+' </td>  <td>'+json[p].nome+' </td> <td><button type="button" class="btn btn-info" onclick="verEditar('+json[p].id+')">Ver </button> </td></tr>')
	      						
	      					} 	
	 						
	 						//contar quantidade de registro
	 						document.getElementById('totalResultados').textContent = "Quantidade de registros: " + json.length  ;
	 						
	      					 
	 						var totalPagina = xhr.getResponseHeader("totalPagina");
	 						
	 				 		 
	 							for(var p = 0; p < totalPagina; p++){
	 							
	 							//&acao=buscarUserAjaxPage
	 		//ATENÇÃO COM A URL e HREF pos a pagina nao pode ser carregada!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 		//
	 								var url = 'nomeBusca=' + nomeBusca+'&acao=buscarUserAjaxPage&pagina='+(p * 5);
	 							     
	 								 
	 							//ASPAS DUPLAS NAS CLASSE, ASPAS SIMPLES EM PARÂMETROS
	 							
	 							//href recarrega a pagina entao é necessário fazer um ajax
	 								$("#ulPaginacaoUserAjax").append('<li class="page-item"> <a class="page-link"  href="#" onclick="buscarUserPagAjax(\''+url+'\')">'+(p+1)+'</a></li>');
								 
	 							//<li class=\"page-item\"><a class=\"page-link\" href=\""+ url +"\">"+(p+1)+"</a></li>
	 							
	 							}
	      					  
	      				}
	      				
	      			}).fail(function(xhr,status,erroThrown){
	      				
	      				alert("Erro ao buscar usuario por nome:" + xhr.reponseText)
	      				
	      			});
	  		}
	  		 
	  	}
     
      
     	/*Apos isso
 		1 - Criar uma função js
 		2 - Uma servlet pra isso
 		3 - SQL  de busca  
 		
 		nao pode ter direcionamento quando tem modal
 		é preciso manter o model para trabalhar com dados
 		
 			 Mesmo formulário usa-se a mesma servlet
 		*/
     
     
     
     	function criarDeleteComAjax(){ 
     		
     		if(confirm('Deseja realmente excluir os dados ajax?')){
     			//captura action do form para usar a servlet
     			
     			var urlAction = document.getElementById('FormUser').action;
     			var idUser = document.getElementById('id').value;
     			
     			$.ajax({
     				
     				method:"get",//cai no get da servlet
     				url:urlAction,
     				data:"id="+idUser + '&acao=deletarajax',
     				success: function(response){
     					 
     					LimparForm(); 
     					document.getElementById("msg").textContent = response; 
     				}
     				
     			}).fail(function(xhr,status,erroThrown){
     				
     				alert("Erro ao deletar usuario por id:" + xhr.reponseText)
     				
     			});
     			
     		}
     	}	 

     
     
     
     /*
     	function criarDelete() {
     		//validação da exclusão de registro
     		if(confirm('Deseja realmente excluir os dados?')){
     			document.getElementById("FormUser").method='get';
         		document.getElementById("acao").value = 'deletar';	
         		document.getElementById("FormUser").submit();
     		}
     		 
		}
     */
     	function LimparForm() {
			var elementos = document.getElementById("FormUser").elements;//Retorna os elementos html dentro do forms
	 
			for(p=0; p<elementos.length; p++){ 
				elementos[p].value = ''; 
			}
			
		}
     
     function LimparModal() {
			var elementos = document.getElementById("tabelaresultados").elements;//Retorna os elementos html dentro do forms
	 
			for(p=0; p<elementos.length; p++){ 
				elementos[p].value = ''; 
			}
			
		}
     $(function() {
    	    $('#currency').maskMoney();
    	  })
 	 
     </script>
</body>

</html>
