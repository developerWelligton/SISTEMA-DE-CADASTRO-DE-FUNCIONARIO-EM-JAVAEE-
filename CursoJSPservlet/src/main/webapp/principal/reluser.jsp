 
<!DOCTYPE html>
<html lang="en"> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <!-- JSTL -->
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>  
 
 

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
													<h4 class="sub-title"> Rel. de Usu�rio</h4>
														<form class="form-material"  action="<%= request.getContextPath()%>/ServletUsuarioController" method="get" id="FormUser">
															<!-- NAO TA RESPEITANDO E NAO CONSEGUE ENVIAR POR PARAMETRO GET -->
															<input type="hidden" name="acao" 
															id="acaoRelatorioImprimirTipo" value="imprimirRelatorioUser">
															 
																  <div class="form-row align-items-center">
																  <!-- DATA INICIAL -->
																    <div class="col-sm-3 my-1">
																      <label class="sr-only" for="dataInicial">Data Inicial</label>
																      <input value="${dataInicial}" type="text" class="form-control" 
																      id="dataInicial"
																      name="dataInicial"
																      placeholder="Indique data Inicial"
																      >
																       
																    </div>
																      <!-- DATA FINAL -->
																    <div class="col-sm-3 my-1">
																      <label class="sr-only" for="dataFinal">Data Final</label> 
																        <div class="input-group-prepend"> 
																        </div>
																        <input value="${dataFinal}" type="text" class="form-control" 
																        id="dataFinal"
																        name="dataFinal"
																        placeholder="Indique data Final"
																         > 
																         
																    </div>
																  <!-- IMPRIMIR RELATORIO -->     
																    <div class="col-auto my-1">
																      <button type="button" onclick="imprimirHtml()" class="btn btn-primary">Imprimir Relat�rio</button>
																      
																      <button type="button" onclick="imprimirPdf()" class="btn btn-primary">Imprimir PDF</button>
																    </div>
																    
																  </div> 
														</form>
														
														<div style="height: 100%; overflow: scroll">
															<!-- Tabela -->
															<table class="table" id="tabelaresultadosview">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Nome</th> 
																	</tr>
																</thead>
																<tbody>
																<!-- listaUser foi pego do setAtribute do sevlet do form -->
																	<c:forEach items='${listaUser}' var='ml'>
																		<tr>
																			<td><c:out value="${ml.id}"></c:out></td>
																			<td><c:out value="${ml.nome}"></c:out></td>
																			
																			 <c:forEach items="${ml.telefones}" var="fone">
																				<tr>
																				 </td>
																					<td style="font-size: 10px"> <c:out value="${fone.numero}">
																					</td>
																					</c:out> 
																				</tr>
																			</c:forEach>
																		</tr>  
																	</c:forEach>
																</tbody>
															</table>
														</div>
													<!--  -->
													</div>
												</div>
											</div> 
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
     
     <script type="text/javascript">
     
	     function imprimirHtml(){
	    	  
	    	document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUser';
	    	 $("#FormUser").submit();	
	    	 
	     }
	     
	     function imprimirPdf(){
	    		
	    	 document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPDF';
	    	 $("#FormUser").submit(); 
	     }
     
     
	     $( function() { 
	    	 var dataNascimento = $("#dataInicial").val(); 
	         
	         if(dataNascimento != null && dataNascimento!=''){
	       	  
	       	  var dateFormat = new Date(dataNascimento); 
	       	  
	       	  $("#dataInicial").val(dateFormat.toLocaleDateString('pt-BR', {timeZone:'UTC'})); 
		       	 
	       	  $("#dataInicial").datepicker({
		    		    dateFormat: 'dd/mm/yy',
		    		    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
		    		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		    		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
		    		    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		    		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		    		    nextText: 'Pr�ximo',
		    		    prevText: 'Anterior'
		    		});
		         }
		    	  
		    } );
	     
	     $( function() { 
	    	 var dataNascimento = $("#dataFinal").val(); 
	         
	         if(dataNascimento != null && dataNascimento!=''){
	       	  
	       	  var dateFormat = new Date(dataNascimento); 
	       	  
	       	  $("#dataFinal").val(dateFormat.toLocaleDateString('pt-BR', {timeZone:'UTC'})); 
		       	 
	       	  $("#dataFinal").datepicker({
		    		    dateFormat: 'dd/mm/yy',
		    		    dayNames: ['Domingo','Segunda','Ter�a','Quarta','Quinta','Sexta','S�bado'],
		    		    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		    		    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S�b','Dom'],
		    		    monthNames: ['Janeiro','Fevereiro','Mar�o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		    		    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		    		    nextText: 'Pr�ximo',
		    		    prevText: 'Anterior'
		    		});
		         }
		    	  
		    } );
     
     </script>
</body>

</html>