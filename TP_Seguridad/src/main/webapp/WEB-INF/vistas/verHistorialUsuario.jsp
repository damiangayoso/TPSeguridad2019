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
                    	<li><a class="btn" href="guardarTexto">Guarda un Texto</a></li>
                    	<li><a class="btn active white" href="verHistorialUsuario">Ver mi Historial</a></li>
                    	<li><a class="btn" href="cambiarPassword">Cambiar Password</a></li>
                    </c:if>
                    	<li><a class="btn" href="cerrarSesion">Cerrar Sesion</a></li>
                </ul>
            </nav>
		</header>
<table style="width:100%">
  <tr>
    <th>Nro.Registro</th>
    <th>Descripcion</th> 
    <th>Fecha</th>
  </tr>
<c:set var="count" value="0"/>
<c:forEach items="${Lista}" var="listado">
<c:set var="count" value="${count + 1}"/>
			    <tr>
			      <td>${count}</td>
			      <td>${listado.registro}</td>
			      <td>${listado.fecha}</td>		   
			    </tr>
</c:forEach> 
</table>
</body>
</html>