<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Bootstrap core CSS -->
	    <link href="css/bootstrap.min.css" rel="stylesheet" >
	    <!-- Bootstrap theme -->
	    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="css/estilos.css">
	</head>
<body>
<div class = "container">
			<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
				
				<form:form action="validar-responderPregunta" method="POST" modelAttribute="usuario">
			    	<h3 class="form-signin-heading">Responder Pregunta.</h3>
					<hr class="colorgraph"><br>
					${usuario.pregunta}
					<form:input path="respuesta" id="respuesta" type="text" class="form-control" />  		  
					<form:input path="email" id="email" type="hidden" class="form-control" value="${usuario.email}" />  		  
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Responder</button>
				</form:form>

				<%--Bloque que es visible si el elemento error no está vacío	--%>
				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>	
			</div>
		</div>
</body>
</html>