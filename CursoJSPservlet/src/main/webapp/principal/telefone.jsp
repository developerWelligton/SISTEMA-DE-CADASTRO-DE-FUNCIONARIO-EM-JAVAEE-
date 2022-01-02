 
<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
														<h4 class="sub-title">Cadastro de Telefone</h4>
														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletTelefone"
															method="post" id="FormTelefone">
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" placeholder="Enter ID"
																	readonly="readonly" value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID
																	User</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input readonly="readonly" type="text" name="nome"
																	id="nome" class="form-control" required="required"
																	placeholder="Entre com nome" value="${modelLogin.nome}">
																<span class="form-bar"></span> <label
																	class="float-label">Nome</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="numero" id="numero"
																	class="form-control" required="required"
																	placeholder="Entre com telefone"> <span
																	class="form-bar"></span> <label class="float-label">Telefone</label>
															</div>
															<button type="submit" class="btn btn-success">Salvar</button>
														</form>
														<span id="msg"> ${msg}</span>

														<div style="height: 300px; overflow: scroll">
															<!-- Tabela -->
															<table class="table" id="tabelaresultadosview">
																<thead>
																	<tr>
																		<th scope="col">ID</th>
																		<th scope="col">Numero</th>
																		<th scope="col">Excluir</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items='${modelTelefones}' var='f'>
																		<tr>
																			<td><c:out value="${f.id}"></c:out></td>
																			<td><c:out value="${f.numero}"></c:out></td>
																			<td><a class="btn btn btn-success"
																				href="<%=request.getContextPath()%>/ServletTelefone?acao=excluir&id=${f.id}&userPai=${modelLogin.id}">Excluir</a></td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>

													</div>
												</div>
											</div>


										</div>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="javascriptFile.jsp"></jsp:include>
	    
	    <script type="text/javascript">
     
       //permitir s� numero
     	$("#numero").keypress(function (event){
     		return /\d/.test(String.fromCharCode(event.keyCode))
     	})
     	
     	  
     	
     
     	</script> 
      
</body>

</html>
