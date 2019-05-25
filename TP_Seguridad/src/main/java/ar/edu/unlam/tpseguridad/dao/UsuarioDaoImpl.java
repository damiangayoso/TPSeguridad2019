package ar.edu.unlam.tpseguridad.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

// implelemtacion del DAO de usuarios, la anotacion @Repository indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.dao
// para encontrar esta clase.
@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	// Como todo dao maneja acciones de persistencia, normalmente estará inyectado el session factory de hibernate
	// el mismo está difinido en el archivo hibernateContext.xml
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public Usuario consultarUsuario(Usuario usuario) {

		// Se obtiene la sesion asociada a la transaccion iniciada en el servicio que invoca a este metodo y se crea un criterio
		// de busqueda de Usuario donde el email y password sean iguales a los del objeto recibido como parametro
		// uniqueResult da error si se encuentran más de un resultado en la busqueda.
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.add(Restrictions.eq("password", usuario.getPassword()))
				.uniqueResult();
	}
	
	@Override
	public void cargarUsuario() {
		final Session session = sessionFactory.getCurrentSession();	
		
		Usuario usuario = new Usuario();
		usuario.setEmail("jose@unlam.edu.ar");
		usuario.setPassword("123456");
		usuario.setRol("Usuario");
		usuario.setEstado("Habilitado");
		usuario.setPregunta("La Respuesta es 1");
		usuario.setRespuesta("1");
		
		Usuario user = (Usuario) session.createCriteria(Usuario.class)
		.add(Restrictions.eq("email", usuario.getEmail()))
		.uniqueResult();
		
		if(user == null) {
			session.save(usuario);
		}
		
		Usuario usuario2 = new Usuario();
		usuario2.setEmail("maria@unlam.edu.ar");
		usuario2.setPassword("123456");
		usuario2.setRol("Usuario");
		usuario2.setEstado("Habilitado");
		usuario2.setPregunta("La Respuesta es 1");
		usuario2.setRespuesta("1");

		
		Usuario user2 = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario2.getEmail()))
				.uniqueResult();
				
				if(user2 == null) {
					session.save(usuario2);
				}
		
		Usuario usuario3 = new Usuario();
		usuario3.setEmail("admin_gonzalo@unlam.edu.ar");
		usuario3.setPassword("123456");
		usuario3.setRol("Administrador");
		usuario3.setEstado("Habilitado");
		usuario3.setPregunta("La Respuesta es 1");
		usuario3.setRespuesta("1");

		
		Usuario user3 = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario3.getEmail()))
				.uniqueResult();
				
				if(user3 == null) {
					session.save(usuario3);
				}
		
		Usuario usuario4 = new Usuario();
		usuario4.setEmail("admin_cintia@unlam.edu.ar");
		usuario4.setPassword("123456");
		usuario4.setRol("Administrador");
		usuario4.setEstado("Habilitado");
		usuario4.setPregunta("La Respuesta es 1");
		usuario4.setRespuesta("1");

		
		Usuario user4 = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario4.getEmail()))
				.uniqueResult();
				
				if(user4 == null) {
					session.save(usuario4);
				}
	}
	
	@Override
	public void crearUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();	
		usuario.setRol("Usuario");
		usuario.setEstado("Habilitado");
		session.save(usuario);
		
		Usuario regUser = new Usuario();
		regUser = (Usuario) session.createCriteria(Usuario.class)
		.add(Restrictions.eq("email", usuario.getEmail()))
		.add(Restrictions.eq("password", usuario.getPassword()))
		.uniqueResult();
		
		Registro registro = new Registro();
		registro.setIdUsuario(regUser.getId());
		registro.setRegistro(regUser.getEmail()+" se registro en el sitio.");
		
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String fecha = dateFormat.format(new Date());
		
		registro.setFecha(fecha);
		session.save(registro);
		
	}
	
	@Override
	public void guardarTexto(Long id,String texto) {
		
		final Session session = sessionFactory.getCurrentSession();	
		Usuario usuario = new Usuario();
		
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		
		usuario.setTexto(texto);
		
		session.update(usuario);
		
		Registro registro = new Registro();
		registro.setIdUsuario(usuario.getId());
		registro.setRegistro(usuario.getEmail()+" registro un nuevo Texto.");
		
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String fecha = dateFormat.format(new Date());
		
		registro.setFecha(fecha);
		session.save(registro);

	}
	
	@Override
	public boolean validarCambiarPassword(Long id,String oldPass,String newPass) {
		
		final Session session = sessionFactory.getCurrentSession();	
		Usuario usuario = new Usuario();
		
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		
		if(usuario.getPassword().equals(oldPass)) {
			if(usuario.getPassword().equals(newPass)) {
				return false;
			}
			else {
				usuario.setPassword(newPass);
				session.update(usuario);
				
				Registro registro = new Registro();
				registro.setIdUsuario(usuario.getId());
				registro.setRegistro(usuario.getEmail()+" Modifico su Clave.");
					
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
				String fecha = dateFormat.format(new Date());
				
				registro.setFecha(fecha);
				session.save(registro);
				
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Registro> obtenerRegistros(Long id) {
	
		final Session session = sessionFactory.getCurrentSession();
		List<Registro> resultado = 	session.createCriteria(Registro.class)
													.add(Restrictions.eq("idUsuario", id))
													.list();
		
		return resultado;
	}
	
	@Override
	public List<Usuario> obtenerUsuarios() {
	
		final Session session = sessionFactory.getCurrentSession();
		List<Usuario> resultado = 	session.createCriteria(Usuario.class)
													.add(Restrictions.eq("rol", "Usuario"))
													.list();
		
		return resultado;
	}
	
	@Override
	public List<Registro> obtenerRegistrosUsuarios(String email) {
	
		final Session session = sessionFactory.getCurrentSession();
		
		Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
							.add(Restrictions.eq("email", email))
							.uniqueResult();
		
		List<Registro> resultado = 	session.createCriteria(Registro.class)
													.add(Restrictions.eq("idUsuario", usuario.getId()))
													.list();
		
		return resultado;
	}
	
	@Override
	public void switchUsuario(String email) {
		
		final Session session = sessionFactory.getCurrentSession();	
		
		Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
		
		if(usuario.getEstado().equals("Habilitado")) {
			usuario.setEstado("Deshabilitado");
			session.update(usuario);
		}
		else {
			usuario.setEstado("Habilitado");
			session.update(usuario);
		}

	}
	
	@Override
	public Usuario recuperarPassConsulta(String email) {
		final Session session = sessionFactory.getCurrentSession();	

		Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();

		return usuario;
	}
}
