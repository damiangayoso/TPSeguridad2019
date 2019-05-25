<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	<!-- Bootstrap core CSS -->
	    <link href="css/bootstrap.min.css" rel="stylesheet" >
	    <!-- Bootstrap theme -->
	    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
	</head>
	<body>
		<div class = "container">
			<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
				
				<form:form action="validar-crearCuenta" method="POST" modelAttribute="usuario">
			    	<h3 class="form-signin-heading">Crear Cuenta.</h3>
					<hr class="colorgraph"><br>
					E-Mail:
					<form:input path="email" id="email" type="email" class="form-control" />
					Password:
					<form:input path="password" type="password" id="password" class="form-control"/>     		  
					Pregunta de Seguridad:
					<form:input path="pregunta" id="pregunta" type="text" class="form-control" />		
					Respuesta:
					<form:input path="respuesta" id="respuesta" type="text" class="form-control" />
					
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Crear</button>
				</form:form>

				<%--Bloque que es visible si el elemento error no está vacío	--%>
				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>	
			</div>
		</div>
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>