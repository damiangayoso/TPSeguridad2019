package ar.edu.unlam.tpseguridad.dao;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;

import ar.edu.unlam.tpseguridad.modelo.Autentificacion;
import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.SaltUsuario;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Usuario consultarUsuario(Usuario usuario) {

		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.uniqueResult();
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void crearUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();	
		usuario.setRol("Usuario");
		usuario.setEstado("Deshabilitado");
		session.save(usuario);
		String passHashed = "";
		Usuario usuarioHashed = new Usuario();
		
		usuarioHashed = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.uniqueResult();
		
		//GENERO Y GUARDO EL SALT DEL USUARIO	
		byte[] saltBytes = SaltHashedPassword.getNextSalt();
		String salt = SaltHashedPassword.convertirSalt(saltBytes);
		
		SaltUsuario saltUsuario = new SaltUsuario();
		
		saltUsuario.setIdUsuario(usuarioHashed.getId());
		saltUsuario.setSalt(salt);
		
		session.save(saltUsuario);

		//Comienzo la creacion del password Salt Hashed
		try {
			passHashed = SaltHashedPassword.generarHash(usuarioHashed,saltBytes);
		} catch (UnsupportedEncodingException e) {		
			e.printStackTrace();
		}
		
		usuarioHashed.setPassword(passHashed);				
		session.update(usuarioHashed);
		
		//GENERO REGISTRO DE AUTENTIFICACION		
		Autentificacion auth = new Autentificacion();
		auth.setEmail(usuarioHashed.getEmail());
		auth.setEstado("NO ACTIVADO");
		
		DateFormat dateFormatAuth = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String fechaAuth = dateFormatAuth.format(new Date());
		
		auth.setFecha(fechaAuth);
		
		Usuario userAuth = new Usuario();
		userAuth.setEmail(usuario.getEmail());
		userAuth.setPassword(usuario.getEmail());
		String authHash = "";
		
		try {
			authHash = SaltHashedPassword.generarHash(userAuth, saltBytes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		auth.setAutentificador(authHash);
		
		session.save(auth);
		
		//GENERO EL EMAIL PARA HABILITAR AL USUARIO
		
		String destinatario = usuario.getEmail();
		String asunto = "ACTIVACION DE CUENTA!";
		String cuerpo = "Por favor ingrese a: http://localhost:8080/TP_Seguridad/autentificarUsuario/"+auth.getAutentificador();
		MensajeriaEmail.EnviarEmail(destinatario, asunto, cuerpo);
		
		//PARTE QUE GUARDA EN EL LOG LA CREACION DEL USUARIO
		
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@Override
	public boolean validarSetNewPassword(String newPass1, String newPass2) {
		if(newPass1.equals(newPass2)) {			
			return true;
			}		
		return false;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Registro> obtenerRegistros(Long id) {
	
		final Session session = sessionFactory.getCurrentSession();
		List<Registro> resultado = 	session.createCriteria(Registro.class)
													.add(Restrictions.eq("idUsuario", id))
													.list();
		
		return resultado;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Usuario> obtenerUsuarios() {
	
		final Session session = sessionFactory.getCurrentSession();
		List<Usuario> resultado = 	session.createCriteria(Usuario.class)
													.add(Restrictions.eq("rol", "Usuario"))
													.list();
		
		return resultado;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Usuario recuperarPassConsulta(String email) {
		final Session session = sessionFactory.getCurrentSession();	

		Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();

		return usuario;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Usuario consultarUsuarioLogin(Usuario usuario){
		final Session session = sessionFactory.getCurrentSession();	
		Base64 codec = new Base64();
		Usuario usuarioTest = (Usuario) session.createCriteria(Usuario.class)
		.add(Restrictions.eq("email", usuario.getEmail()))
		.uniqueResult();
		
		////REVISANDO ACA
		//Busco saltUsuario
		SaltUsuario saltUsuario = (SaltUsuario) session.createCriteria(SaltUsuario.class)
				.add(Restrictions.eq("idUsuario", usuarioTest.getId()))
				.uniqueResult();
		String salt = saltUsuario.getSalt();
		String HashPass = "";

		byte[] saltPass = Base64Utils.decodeFromString(salt);

		try {
			HashPass = SaltHashedPassword.generarHash(usuario, saltPass);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(usuarioTest.getPassword().equals(HashPass)) {
			System.out.println(usuarioTest.getPassword());
			System.out.println(HashPass);
			return usuarioTest;
		}
		
		return null;
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean autentificarUsuario(String auth,String fecha) {
		final Session session = sessionFactory.getCurrentSession();
		Autentificacion autentificacion = (Autentificacion) session.createCriteria(Autentificacion.class)
				.add(Restrictions.eq("autentificador", auth))
				.uniqueResult();	
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		
		Date d1 = null;
		Date d2 = null;
		
		try {
			d1= format.parse(fecha);
			d2= format.parse(autentificacion.getFecha());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;         
		long diffMinutes = diff / (60 * 1000) % 60;         
		long diffHours = diff / (60 * 60 * 1000);
		
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");         
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");         
		System.out.println("Time in hours: " + diffHours + " hours."); 
		
		if(diffHours >= 1) {

			return false;
		}
		else {

			autentificacion.setEstado("Activado");
			session.update(autentificacion);
			
			Usuario user = (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", autentificacion.getEmail()))
					.uniqueResult();
			
			user.setEstado("Habilitado");
			session.update(user);

			return true;
		}		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void recuperarClave(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();			
		String passHashed = "";
		Usuario usuarioHashed = new Usuario();
						
		usuarioHashed = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.uniqueResult();
		
		//GENERO Y GUARDO EL SALT DEL USUARIO
		byte[] saltBytes = SaltHashedPassword.getNextSalt();
		String salt = SaltHashedPassword.convertirSalt(saltBytes);
		
		SaltUsuario saltUsuario = new SaltUsuario();
		
		saltUsuario.setIdUsuario(usuarioHashed.getId());
		saltUsuario.setSalt(salt);
		
		//No lo guardo para que no existan duplicados y rompa en el login al cambiar la clave		
		//session.save(saltUsuario);

		//Comienzo la creacion del password Salt Hashed
		try {
			passHashed = SaltHashedPassword.generarHash(usuarioHashed,saltBytes);
		} catch (UnsupportedEncodingException e) {		
			e.printStackTrace();
		}
		
		usuarioHashed.setPassword(passHashed);			
		session.update(usuarioHashed);

		//GENERO REGISTRO DE AUTENTIFICACION		
		Autentificacion auth = new Autentificacion();
		auth.setEmail(usuarioHashed.getEmail());
		auth.setIdUsuario(usuarioHashed.getId());
		
		DateFormat dateFormatAuth = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String fechaAuth = dateFormatAuth.format(new Date());
		
		auth.setFecha(fechaAuth);
		
		Usuario userAuth = new Usuario();
		userAuth.setEmail(usuario.getEmail());
		userAuth.setPassword(usuario.getEmail());
		String authHash = "";
		
		try {
			authHash = SaltHashedPassword.generarHash(userAuth, saltBytes);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		auth.setAutentificador(authHash);		
		session.save(auth);
				
		//GENERO EL EMAIL PARA HABILITAR AL USUARIO		
		String destinatario = usuario.getEmail();
		String asunto = "Recuperacion de password";
		String cuerpo = "Por favor ingrese a: http://localhost:8080/TP_Seguridad/autentificarSetNewPassword/"+auth.getAutentificador();
		MensajeriaEmail.EnviarEmail(destinatario, asunto, cuerpo);		
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@Override
	public Autentificacion autentificarSetNewPassword(String auth, String fecha) {
		final Session session = sessionFactory.getCurrentSession();
		Autentificacion autentificacion = (Autentificacion) session.createCriteria(Autentificacion.class)
				.add(Restrictions.eq("autentificador", auth))
				.uniqueResult();	
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		
		Date d1 = null;
		Date d2 = null;
		
		try {
			d1= format.parse(fecha);
			d2= format.parse(autentificacion.getFecha());
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;         
		long diffMinutes = diff / (60 * 1000) % 60;         
		long diffHours = diff / (60 * 60 * 1000);
		
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");         
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");         
		System.out.println("Time in hours: " + diffHours + " hours."); 
		
		if(diffHours >= 1) {
			//return "Invalid";			
			return autentificacion;
		}
		else {
			//autentificacion.setEstado("Activado");
			//session.update(autentificacion);
			
			Usuario user = (Usuario) session.createCriteria(Usuario.class)
					.add(Restrictions.eq("email", autentificacion.getEmail()))
					.uniqueResult();
			
			//user.setEstado("Habilitado");
			//session.update(user);
			
			//return autentificacion.getEmail();
			System.out.println("autentificarSetNewPassword");
			System.out.println("id:"+autentificacion.getIdUsuario());
			System.out.println(autentificacion.getEmail());
			return autentificacion;
		}

	}

	
	@Override
	public void saveNewPassword(Long id, String email, String newPass1) {		
		final Session session = sessionFactory.getCurrentSession();							
		Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();		
		
		usuario.setPassword(newPass1);
		session.update(usuario);
									
		//////////////////////////////////////////////////////////
		//final Session session = sessionFactory.getCurrentSession();	
		//usuario.setRol("Usuario");
		//usuario.setEstado("Deshabilitado");
		//session.save(usuario);
		String passHashed = "";
		Usuario usuarioHashed = new Usuario();
		
		usuarioHashed = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", usuario.getEmail()))
				.uniqueResult();
		
				
		//GENERO Y GUARDO EL SALT DEL USUARIO	
		byte[] saltBytes = SaltHashedPassword.getNextSalt();
		String salt = SaltHashedPassword.convertirSalt(saltBytes);
		
		//Busco saltUsuario
		SaltUsuario saltUsuario = (SaltUsuario) session.createCriteria(SaltUsuario.class)
				.add(Restrictions.eq("idUsuario", usuarioHashed.getId()))
				.uniqueResult();
						
		saltUsuario.setSalt(salt);		
		session.update(saltUsuario);
		
		//Comienzo la creacion del password Salt Hashed
		try {
			passHashed = SaltHashedPassword.generarHash(usuarioHashed,saltBytes);
		} catch (UnsupportedEncodingException e) {		
			e.printStackTrace();
		}
		
		usuarioHashed.setPassword(passHashed);				
		session.update(usuarioHashed);
		/////////////////////////////////////////////////////////
		
		
		System.out.println("saveNewPassword");
		System.out.println("id recibido por parametro: "+id);
		System.out.println("clave recibida por parametro: "+newPass1);
		System.out.println("id de usuario encontrado por criteria: "+usuario.getId());
		System.out.println("email de usuario encontrado por criteria: "+usuario.getEmail());
		System.out.println("password de usuario encontrado por criteria: "+usuario.getPassword());
		
		Registro registro = new Registro();
		registro.setIdUsuario(usuario.getId());
		registro.setRegistro(usuario.getEmail()+" Modifico su Clave.");
			
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String fecha = dateFormat.format(new Date());
		
		registro.setFecha(fecha);
		session.save(registro);							
	}	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

}
		

