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
	</head>
	<body>
		<header class="header container">
			<h1 class="logo">TP Seguridad</h1>	      
		</header>
		<div class = "container">
		${clave.id}${clave.email}
			<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">								
				<form:form action="validar-setNewPassword" method="POST" modelAttribute="clave">
			    	<h3 class="form-signin-heading">Ingrese su nueva clave</h3>
					<hr class="colorgraph"><br>
					
					<form:hidden path="id" value="${clave.id}"/>
					Ingrese su nueva clave:
					<form:input path="newPass1" id="newPass1" type="password" class="form-control" />  
					Repita su nueva clave:
					<form:input path="newPass2" id="newPass2" type="password" class="form-control" /> 
					
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Cambiar Password</button>
				</form:form>
	
				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>	
			</div>
		</div>
	</body>
</html>

<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>