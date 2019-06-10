package ar.edu.unlam.tpseguridad.controladores;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tpseguridad.dao.VerifyRecaptchaImpl;
import ar.edu.unlam.tpseguridad.modelo.TipoLog;
import ar.edu.unlam.tpseguridad.modelo.Usuario;
import ar.edu.unlam.tpseguridad.modelo.UsuarioDTO;
import ar.edu.unlam.tpseguridad.modelo.UtilesLog;
import ar.edu.unlam.tpseguridad.servicios.ServicioLogin;
import ar.edu.unlam.tpseguridad.servicios.ServicioValidacionV2;

@Controller
public class ControladorLogin {
	//log
	UtilesLog log = new UtilesLog();
	//enum
	TipoLog tipo = TipoLog.INFO;			

	
	@Inject
	private ServicioLogin servicioLogin;
	
	@Inject
	private ServicioValidacionV2 servicioValidacionV2;
	
	@RequestMapping("/login")
	public ModelAndView irALogin() {
		System.out.println("ENTRO LOGIN!!!!!!!!!!!");

		servicioLogin.cargarUsuario();
		ModelMap modelo = new ModelMap();

		UsuarioDTO usuario = new UsuarioDTO();
		modelo.put("usuario", usuario);
		System.out.println("VA A LA VISTA!!!!!!!!!!!");

		return new ModelAndView("login", modelo);
	}

	@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("usuario") UsuarioDTO usuarioDTO, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		
		Usuario usuario = new Usuario();
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setPassword(usuarioDTO.getPassword());
		usuario.setPregunta(usuarioDTO.getPregunta());
		usuario.setRespuesta(usuarioDTO.getRespuesta());
		
		try {
			if(!VerifyRecaptchaImpl.verify(usuarioDTO.getGrecaptcharesponse())) {
				model.put("error", "ERROR CON EL RECAPTCHA! ES UN BOT!.");
				log.registrarInfo(getClass(), tipo, "Error de captcha");
				return new ModelAndView("login", model);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Usuario usuarioBuscado = servicioLogin.consultarUsuarioLogin(usuario);
		if (usuarioBuscado != null) {
			
			if(usuarioBuscado.getEstado().equals("Habilitado")) {
			
			request.getSession().setAttribute("EMAIL", usuarioBuscado.getEmail());
			request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
			request.getSession().setAttribute("ID", usuarioBuscado.getId());
			request.getSession().setAttribute("TEXTO", usuarioBuscado.getTexto());
			return new ModelAndView("redirect:/home");
			
			}
			else {
				log.registrarInfo(getClass(), tipo, "Usuario deshabilitado");
				model.put("error", "El Usuario ha sido Deshabilitado hasta nuevo Aviso.");
			}
		} else {
			log.registrarInfo(getClass(), tipo, "Usuario o clave incorrecta");
			model.put("error", "Usuario o clave incorrecta");
		}
		return new ModelAndView("login", model);
	}

	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public ModelAndView irAHome() {
		return new ModelAndView("home");
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView inicio() {
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(path = "/crearCuenta", method = RequestMethod.GET)
	public ModelAndView crearCuenta() {
		ModelMap modelo = new ModelMap();

		UsuarioDTO usuario = new UsuarioDTO();
		modelo.put("usuario", usuario);

		return new ModelAndView("crearCuenta", modelo);

	}
	
	@RequestMapping(path = "/validar-crearCuenta", method = RequestMethod.POST)
	public ModelAndView validarCrearCuenta(@ModelAttribute("usuario") UsuarioDTO usuarioDTO) {
		ModelMap model = new ModelMap();
		
		Usuario usuario = new Usuario();
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setPassword(usuarioDTO.getPassword());
		usuario.setPregunta(usuarioDTO.getPregunta());
		usuario.setRespuesta(usuarioDTO.getRespuesta());
		
		try {
			if(!VerifyRecaptchaImpl.verify(usuarioDTO.getGrecaptcharesponse())) {
				model.put("error", "ERROR CON EL RECAPTCHA! ES UN BOT!.");
				return new ModelAndView("login", model);
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!servicioValidacionV2.passwordVerificar(usuario.getPassword())) {
			model.put("error", "Contraseña solo puede contener [a-z A-Z 0-9].");
			return new ModelAndView("crearCuenta", model);
		}
		
		if(usuario.getEmail() == "" || usuario.getPassword() == "") {
			model.put("error", "Usuario o Contraseña estan Vacios.");
			return new ModelAndView("crearCuenta", model);
		}
		
		Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);
		
		if (usuarioBuscado == null) {
			servicioLogin.crearUsuario(usuario);
			log.registrarInfo(getClass(), tipo, "Alta de usuario");
			return new ModelAndView("redirect:/login");
		} else {
			log.registrarInfo(getClass(), tipo, "Usuario ya existe");
			model.put("error", "Usuario ya Existe.");
		}
		
		return new ModelAndView("crearCuenta", model);
	}
	
	@RequestMapping(path = "/recuperarPassword", method = RequestMethod.GET)
	public ModelAndView recuperarPassword() {
		ModelMap modelo = new ModelMap();

		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);

		return new ModelAndView("recuperarPassword", modelo);

	}
	
	@RequestMapping(path = "/validar-recuperarPassword", method = RequestMethod.POST)
	public ModelAndView validarRecuperarPassword(@ModelAttribute("usuario") Usuario usuario) {
		ModelMap model = new ModelMap();
		Usuario usuarioR = new Usuario();
		
		if(usuario.getEmail().equals("")) {
			model.put("error", "Usuario esta Vacios.");
			return new ModelAndView("recuperarPassword", model);
		}
		
		Usuario usuarioBuscado = servicioLogin.recuperarPassConsulta(usuario.getEmail());
		

		if (usuarioBuscado != null) {
			
			usuarioR.setPregunta(usuarioBuscado.getPregunta());
			usuarioR.setEmail(usuarioBuscado.getEmail());
			model.put("usuario", usuarioR);
			return new ModelAndView("responderPregunta", model);
			
		} else {
			log.registrarInfo(getClass(), tipo, "Usuario inexistente");
			model.put("error", "Usuario no Existe.");
		}
		return new ModelAndView("recuperarPassword", model);
	}
	
	@RequestMapping(path = "/validar-responderPregunta", method = RequestMethod.POST)
	public ModelAndView validarResponderPregunta(@ModelAttribute("usuario") Usuario usuario) {
		ModelMap model = new ModelMap();
		Usuario usuarioR = new Usuario();
		Usuario usuarioLogin = new Usuario();
		
		Usuario usuarioBuscado = servicioLogin.recuperarPassConsulta(usuario.getEmail());
		
		if (usuarioBuscado.getRespuesta().equals(usuario.getRespuesta())) {
			
			model.put("clave", usuarioBuscado.getPassword());
			model.put("usuario", usuarioLogin);
			log.registrarInfo(getClass(), tipo, "Recuperacion exitosa");
			return new ModelAndView("login",model);
		} else {
			log.registrarInfo(getClass(), tipo, "La respuesta no coincide");
			model.put("error", "La Respuesta no coincide.");
		}
		
		usuarioR.setPregunta(usuarioBuscado.getPregunta());
		usuarioR.setEmail(usuarioBuscado.getEmail());
		model.put("usuario", usuarioR);
		return new ModelAndView("responderPregunta", model);
	}
	
	@RequestMapping(path = "/cerrarSesion", method = RequestMethod.GET)
	public ModelAndView cerrarSesion(HttpServletRequest request) {

		request.getSession().removeAttribute("EMAIL");
		request.getSession().removeAttribute("ROL");
		request.getSession().removeAttribute("ID");
		request.getSession().removeAttribute("TEXTO");
		
		log.registrarInfo(getClass(), tipo, "Usuario cierra cesion");
		return new ModelAndView("redirect:/login");

	}
}
