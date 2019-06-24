package ar.edu.unlam.tpseguridad.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tpseguridad.dao.UsuarioDao;
import ar.edu.unlam.tpseguridad.modelo.Autentificacion;
import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {
	@Inject
	private UsuarioDao servicioUsuarioDao;
	
	@Override
	public void guardarTexto (Long id,String texto) {
		servicioUsuarioDao.guardarTexto(id, texto);
	}
	
	@Override
	public boolean validarCambiarPassword(Long id,String oldPass, String newPass) {
		return servicioUsuarioDao.validarCambiarPassword(id, oldPass, newPass);
	}

	@Override
	public boolean validarSetNewPassword(String newPass1, String newPass2) {
		return servicioUsuarioDao.validarSetNewPassword(newPass1, newPass2);		
	}
	
	@Override
	public List<Registro> obtenerRegistros(Long id){
		return servicioUsuarioDao.obtenerRegistros(id);
	}
	
	@Override
	public List<Usuario> obtenerUsuarios(){
		return servicioUsuarioDao.obtenerUsuarios();
	}
	
	@Override
	public List<Registro> obtenerRegistrosUsuarios(String email){
		return servicioUsuarioDao.obtenerRegistrosUsuarios(email);
	}
	
	@Override
	public void switchUsuario(String email) {
		servicioUsuarioDao.switchUsuario(email);
	}
	
	@Override
	public boolean autentificarUsuario(String auth,String fecha) {
		return servicioUsuarioDao.autentificarUsuario(auth, fecha);
	}

	@Override
	public Autentificacion autentificarSetNewPassword(String auth, String fecha) {
		return servicioUsuarioDao.autentificarSetNewPassword(auth, fecha);		
	}

	@Override
	public void saveNewPassword(Long id, String email, String newPass1) {
		servicioUsuarioDao.saveNewPassword(id, email, newPass1);		
	}
	
}
