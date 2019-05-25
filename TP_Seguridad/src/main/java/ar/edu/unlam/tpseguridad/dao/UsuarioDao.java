package ar.edu.unlam.tpseguridad.dao;

import java.util.List;

import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	Usuario consultarUsuario (Usuario usuario);
	void cargarUsuario();
	void crearUsuario(Usuario usuario);
	void guardarTexto(Long id,String texto);
	boolean validarCambiarPassword(Long id,String oldPass, String newPass);
	List<Registro> obtenerRegistros(Long id);
	List<Usuario> obtenerUsuarios();
	List<Registro> obtenerRegistrosUsuarios(String email);
	void switchUsuario(String email);
	Usuario recuperarPassConsulta(String email);
}
