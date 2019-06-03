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
				<form:form action="validar-login" method="POST" modelAttribute="usuario">
			    	<h3 class="form-signin-heading">Trabajo Practico Seguridad.</h3>
					<hr class="colorgraph"><br>

					<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
					<form:input path="email" id="email" type="email" class="form-control" />
					<form:input path="password" type="password" id="password" class="form-control"/>     		  
					<form:input path="grecaptcharesponse" id="grecaptcharesponse" type="hidden" name="grecaptcharesponse" class="form-control" />
					
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Login</button>
				</form:form>
				<a href="crearCuenta">Crear Cuenta</a>
				<a href="recuperarPassword">Recuperar Password</a>

				<%--Bloque que es visible si el elemento error no estÃ¡ vacÃ­o	--%>
				<c:if test="${not empty clave}">
			        <h4><span>HA RECUPERADO SU CONTRASEÑA: ${clave}</span></h4>
			        <br>
			        <h4><span>POR FAVOR CAMBIE SU CONTRASEÑA LO MAS PRONTO POSIBLE.</span></h4>
			        <br>
		        </c:if>	
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
		<script src="https://www.google.com/recaptcha/api.js?render=6LejlaUUAAAAAKBz8BfxAEmyLbq4v8NOam0eFEqO"></script>
 		<script>
		  grecaptcha.ready(function() {
	      grecaptcha.execute('6LejlaUUAAAAAKBz8BfxAEmyLbq4v8NOam0eFEqO', {action: 'homepage'}).then(function(token) {
	    	document.getElementById('grecaptcharesponse').value=token;
 	     });
 		 });
 	 	</script>
	</body>
</html>
