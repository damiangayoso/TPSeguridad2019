package ar.edu.unlam.tpseguridad.servicios;

import ar.edu.unlam.tpseguridad.modelo.Usuario;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(Usuario usuario);
	void cargarUsuario();
	void crearUsuario(Usuario usuario);
	Usuario recuperarPassConsulta(String email);
	Usuario consultarUsuarioLogin(Usuario usuario);
	void recuperarClave(Usuario usuario);
	
}
