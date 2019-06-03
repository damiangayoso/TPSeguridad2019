<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	    <link href="css/bootstrap.min.css" rel="stylesheet" >
	    <link href="css/bootstrap-theme.min.css" rel="stylesheet">    
	    <link rel="stylesheet" href="css/estilos.css">
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
					<input type="checkbox" onclick="myFunction()"/>Mostrar Contrase�a
					<span id="passstrength"></span>
					<br/>				     		  
					Pregunta de Seguridad:
					<form:input path="pregunta" id="pregunta" type="text" class="form-control" />		
					Respuesta:
					<form:input path="respuesta" id="respuesta" type="text" class="form-control" />
					
					<form:input path="grecaptcharesponse" id="grecaptcharesponse" type="hidden" name="grecaptcharesponse" class="form-control" />
					
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Crear</button>
				</form:form>

				<%--Bloque que es visible si el elemento error no estÃ¡ vacÃ­o	--%>
				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>	
			</div>
		</div>
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		
		<script>
		$('#password').keyup(function(e) {
		     var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
		     var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
		     var enoughRegex = new RegExp("(?=.{6,}).*", "g");
		     if (false == enoughRegex.test($(this).val())) {
		             $('#passstrength').html('Hacen Falta mas Caracteres!');
		     } else if (strongRegex.test($(this).val())) {
		             $('#passstrength').className = 'ok';
		             $('#passstrength').html('Fuerte!');
		     } else if (mediumRegex.test($(this).val())) {
		             $('#passstrength').className = 'alert';
		             $('#passstrength').html('Media!');
		     } else {
		             $('#passstrength').className = 'error';
		             $('#passstrength').html('Debil!');
		     }
		     return true;
		});
		</script>
		
		<script>
		function myFunction() {
			  var x = document.getElementById("password");
			  if (x.type === "password") {
			    x.type = "text";
			  } else {
			    x.type = "password";
			  }
			}
		</script>
		
		<%--
		--%>
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