package ar.edu.unlam.tpseguridad.servicios;

import java.util.List;

import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

public interface ServicioUsuario {

	void guardarTexto(Long id,String texto);
	boolean validarCambiarPassword(Long id,String oldPass, String newPass);
	public List<Registro> obtenerRegistros(Long id);
	public List<Usuario> obtenerUsuarios();
	public List<Registro> obtenerRegistrosUsuarios(String email);
	public void switchUsuario(String email);
	boolean autentificarUsuario(String auth,String fecha);
}
