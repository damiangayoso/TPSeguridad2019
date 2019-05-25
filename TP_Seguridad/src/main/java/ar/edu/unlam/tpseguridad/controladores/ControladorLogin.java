package ar.edu.unlam.tpseguridad.controladores;

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

import ar.edu.unlam.tpseguridad.modelo.Usuario;
import ar.edu.unlam.tpseguridad.servicios.ServicioLogin;

@Controller
public class ControladorLogin {

	@Inject
	private ServicioLogin servicioLogin;
	
	@RequestMapping("/login")
	public ModelAndView irALogin() {
		
		servicioLogin.cargarUsuario();
		ModelMap modelo = new ModelMap();

		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);

		return new ModelAndView("login", modelo);
	}

	@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
		ModelMap model = new ModelMap();

		Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);
		if (usuarioBuscado != null) {
			
			if(usuarioBuscado.getEstado().equals("Habilitado")) {
			
			request.getSession().setAttribute("EMAIL", usuarioBuscado.getEmail());
			request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
			request.getSession().setAttribute("ID", usuarioBuscado.getId());
			request.getSession().setAttribute("TEXTO", usuarioBuscado.getTexto());
			return new ModelAndView("redirect:/home");
			
			}
			else {
				model.put("error", "El Usuario ha sido Deshabilitado hasta nuevo Aviso.");
			}
		} else {
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

		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);

		return new ModelAndView("crearCuenta", modelo);

	}
	
	@RequestMapping(path = "/validar-crearCuenta", method = RequestMethod.POST)
	public ModelAndView validarCrearCuenta(@ModelAttribute("usuario") Usuario usuario) {
		ModelMap model = new ModelMap();
		
		if(usuario.getEmail() == "" || usuario.getPassword() == "") {
			model.put("error", "Usuario o Contraseña estan Vacios.");
			return new ModelAndView("crearCuenta", model);
		}
		
		Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);
		
		if (usuarioBuscado == null) {
			servicioLogin.crearUsuario(usuario);
			return new ModelAndView("redirect:/login");
		} else {
			
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
			return new ModelAndView("login",model);
		} else {
			
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
		
		return new ModelAndView("redirect:/login");

	}
}
