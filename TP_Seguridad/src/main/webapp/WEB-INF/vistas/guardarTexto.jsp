<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<header class="header container">
			<h1 class="logo">TP Seguridad</h1>
            <nav>
                 <ul class="container">
                 	<c:if test="${ID==null}">
                    	<li><a class="btn" href="login">Login</a></li>
                    </c:if>
                    <li><a class="btn" href="home">Inicio</a></li>
                    <c:if test="${ROL=='Administrador'}" >
                    	<li><a class="btn" href="verUsuarios">Ver Usuarios</a></li>  
                   	</c:if>
                    <c:if test="${ROL=='Usuario'}" >
                    	<li><a class="btn active white" href="guardarTexto">Guarda un Texto</a></li>
                    	<li><a class="btn" href="verHistorialUsuario">Ver mi Historial</a></li>
                    	<li><a class="btn" href="cambiarPassword">Cambiar Password</a></li>
                    </c:if>
                    	<li><a class="btn" href="cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </nav>
		</header>
<div class = "container">
			<div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
				
				<form:form action="validar-texto" method="POST" modelAttribute="usuario">
			    	<h3 class="form-signin-heading">Guardar Texto.</h3>
					<hr class="colorgraph"><br>

					<form:input path="texto" id="texto" type="text" class="form-control" />  
					
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Guardar Texto</button>
				</form:form>

				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>	
			</div>
		</div>
</body>
</html>