package ar.edu.unlam.tpseguridad.servicios;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tpseguridad.dao.UsuarioDao;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

	@Inject
	private UsuarioDao servicioLoginDao;

	@Override
	public Usuario consultarUsuario (Usuario usuario) {
		return servicioLoginDao.consultarUsuario(usuario);
	}
	
	@Override
	public void cargarUsuario() {
		servicioLoginDao.cargarUsuario();
	}
	
	@Override
	public void crearUsuario(Usuario usuario) {
		servicioLoginDao.crearUsuario(usuario);
	}
	
	@Override
	public Usuario recuperarPassConsulta(String email) {
		return servicioLoginDao.recuperarPassConsulta(email);
	}
	
	public Usuario consultarUsuarioLogin(Usuario usuario) {
		return servicioLoginDao.consultarUsuarioLogin(usuario);
	}
}
