 
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
													<h4 class="sub-title"> Rel. de Usuário</h4>
													  
														 <form class="form-material" enctype="multipart/form-data" action="<%= request.getContextPath()%>/ServletUsuarioController" method="post" id="FormUser">
															 <input type="hidden" name="acao" id="acao" value="">
															 
															 <div class="form-group form-default form-static-label">
	                                                                <input type="text" name="mediacolaborador"  id="mediacolaborador" class="form-control"  readonly="readonly" value="${modolLogin.res}" > 
	                                                                <span class="form-bar"></span>
	                                                                <label class="float-label" >Media de dias dos colaboradores na empresa</label> 
	                                                            </div>
	                                                            
														 </form>
                                                            
                                                            

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
     
     <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> 
     
		<script type="text/javascript">
		
		
		function imprimirMedia(){
			var urlAction = document.getElementById('FormUser').action;
			
			$.ajax({

				method : "get",//cai no get da servlet
				url : urlAction,
				data :  '&acao=mediaAdemissaoDemissao',
				success : function(response) {
					 
					var json = JSON.parse(response);
					var media = json;
					var arredondado = parseFloat(media.toFixed(1));
					
					 document.getElementById('mediacolaborador').value = arredondado;
					 
					 
					 
					 
				}

			}).fail(function(xhr, status, erroThrown) {

				alert("Erro ao buscar dados para o grafico" + xhr.reponseText)

			});
		}
		
		
		
		function gerarGrafico() {
			 
			var urlAction = document.getElementById('FormUser').action;
			//var dataInicial = document.getElementById('dataInicial').value;
			//var dataFinal = document.getElementById('dataFinal').value;
			
			  
			$.ajax({

					method : "get",//cai no get da servlet
					url : urlAction,
					data :  '&acao=graficoAdemissaoDemissao',
					success : function(response) {
						 
						var json = JSON.parse(response);
						
						 alert(json.perfils);
						 alert(json.qtdColabors);
						 
						 
					}

				}).fail(function(xhr, status, erroThrown) {

					alert("Erro ao buscar dados para o grafico" + xhr.reponseText)

				});
 

			}
		 
		/*
		function gerarGrafico(){
			const myChart = new Chart(
				    document.getElementById('myChart'),
				    { 
				    	  type: 'doughnut',
						  data: {
							  labels: [
								    'TI',
								    'RH',
								    'Marketing'
								  ],
								  datasets: [{
								    label: 'Quantidade de colaboradores ativos por diretoria',
								    //quantidade de membros
								    data: [100,100, 100],
								    backgroundColor: [
								      'rgb(255, 99, 132)',
								      'rgb(54, 162, 235)',
								      'rgb(255, 205, 86)'
								    ],
								    hoverOffset: 4
								  }]
								},
						  plugins: [{
							  id: 'custom_canvas_background_color',
							  beforeDraw: (chart) => {
							    const ctx = chart.canvas.getContext('2d');
							    ctx.save();
							    ctx.globalCompositeOperation = 'destination-over';
							    ctx.fillStyle = 'lightGreen';
							    ctx.fillRect(0, 0, chart.width, chart.height);
							    ctx.restore();
							  }
							}],
						}
				  );
		}
		
 */
		
 imprimirMedia();
		</script>
</body>

</html>
