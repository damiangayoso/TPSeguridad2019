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
                    	<li><a class="btn active white" href="login">Login</a></li>
                    </c:if>
                    <li><a class="btn active white" href="home">Inicio</a></li>
                    <c:if test="${ROL=='Administrador'}" >
                    	<li><a class="btn" href="verUsuarios">Ver Usuarios</a></li>  
                   	</c:if>
                    <c:if test="${ROL=='Usuario'}" >
                    	<li><a class="btn" href="guardarTexto">Guarda un Texto</a></li>
                    	<li><a class="btn" href="verHistorialUsuario">Ver mi Historial</a></li>
                    	<li><a class="btn" href="cambiarPassword">Cambiar Password</a></li>
                    </c:if>
                    	<li><a class="btn" href="cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </nav>
		</header>
	
		<div class = "container">
			<h1>Bienvenido!</h1>
			<span>${EMAIL}</span>
			<span>${ROL}</span>
			<h3>Tu texto es:</h3>
			<span>${TEXTO} </span>
		</div>
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>