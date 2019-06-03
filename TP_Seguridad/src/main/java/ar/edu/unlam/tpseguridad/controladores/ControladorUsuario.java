package ar.edu.unlam.tpseguridad.controladores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unlam.tpseguridad.modelo.ClaveDTO;
import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.Usuario;
import ar.edu.unlam.tpseguridad.modelo.UsuarioDTO;
import ar.edu.unlam.tpseguridad.servicios.ServicioLogin;
import ar.edu.unlam.tpseguridad.servicios.ServicioUsuario;
import ar.edu.unlam.tpseguridad.servicios.ServicioValidacionV2;

@Controller
public class ControladorUsuario {
	@Inject
	private ServicioUsuario servicioUsuario;
	
	@Inject
	private ServicioValidacionV2 servicioValidacionV2;
	
	@Inject
	private ServicioLogin servicioLogin;

	@RequestMapping("/guardarTexto")
	public ModelAndView guardarTexto() {

		ModelMap modelo = new ModelMap();

		Usuario usuario = new Usuario();
		
		modelo.put("usuario", usuario);

		return new ModelAndView("guardarTexto", modelo);
	}
	
	@RequestMapping(path = "/validar-texto", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		
		Long id = (Long) request.getSession().getAttribute("ID");
		
		//aqui van validaciones para evitar que el texto contenga codigo malicioso
		
		servicioUsuario.guardarTexto(id, usuario.getTexto());
		
		request.getSession().setAttribute("TEXTO", usuario.getTexto());
		
		return new ModelAndView("home", model);
	}
	
	@RequestMapping("/cambiarPassword")
	public ModelAndView cambiarPassword() {

		ModelMap modelo = new ModelMap();

		ClaveDTO clave = new ClaveDTO();
		
		modelo.put("clave", clave);

		return new ModelAndView("cambiarPassword", modelo);
	}
	
	@RequestMapping(path = "/validar-cambiarPassword", method = RequestMethod.POST)
	public ModelAndView validarCambiarPassword(@ModelAttribute("clave") ClaveDTO clave, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("ID");
		
		boolean resultado = servicioUsuario.validarCambiarPassword(id, clave.getOldPass(), clave.getNewPass());
		
		if(!servicioValidacionV2.passwordVerificar(clave.getNewPass())) {
			model.put("error", "Contraseņa solo puede contener [a-z A-Z 0-9].");
			return new ModelAndView("cambiarPassword", model);
		}
		
		if(resultado) {
			return new ModelAndView("home", model);
		}
		
		model.put("error", "La Clave Repetida o no cohincide su vieja clave.");
		return new ModelAndView("cambiarPassword", model);
	}
	
	@RequestMapping("/verHistorialUsuario")
	public ModelAndView verHistorialUsuario(HttpServletRequest request) {

		ModelMap modelo = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("ID");
		
		List<Registro> listaRegistros = servicioUsuario.obtenerRegistros(id);
		modelo.put("Lista", listaRegistros);

		return new ModelAndView("verHistorialUsuario", modelo);
	}
	
	@RequestMapping("/verUsuarios")
	public ModelAndView verUsuarios(HttpServletRequest request) {

		ModelMap modelo = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("ID");
		
		List<Usuario> listaUsuarios = servicioUsuario.obtenerUsuarios();
		modelo.put("Lista", listaUsuarios);

		return new ModelAndView("verUsuarios", modelo);
	}
	
	@RequestMapping(path="/verUsuarioEspecifico/{email}", method = RequestMethod.GET)
	public ModelAndView verUsuarioEspecifico(@PathVariable String email,HttpServletRequest request,RedirectAttributes redirectAtt) {

		ModelMap modelo = new ModelMap();	
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		Long id = (Long) request.getSession().getAttribute("ID");
		List<Registro> listaRegistro;

		if(request.getSession().getAttribute("ROL").equals("Administrador")) {
			
			listaRegistro = servicioUsuario.obtenerRegistrosUsuarios(email);
			redirectAtt.addFlashAttribute("listaRegistro",listaRegistro);
		}
		else {
			return new ModelAndView("login", modelo);
		}
		
		modelo.put("Lista", listaRegistro);

		return new ModelAndView("redirect:/verUsuarioEspecifico");
	}
	
	@RequestMapping("/verUsuarioEspecifico")
	public ModelAndView verUsuarioEspecifico(HttpServletRequest request,@ModelAttribute("listaRegistro") List<Registro> listaRegistro) {
		ModelMap modelo = new ModelMap();
		modelo.put("Lista", listaRegistro);
		return new ModelAndView("/verUsuarioEspecifico",modelo);
	}
	
	@RequestMapping(path="/deshabilitarUsuario/{email}", method = RequestMethod.GET)
	public ModelAndView deshabilitarUsuario(@PathVariable String email,HttpServletRequest request) {
		ModelMap modelo = new ModelMap();	
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		Long id = (Long) request.getSession().getAttribute("ID");
		
		if(request.getSession().getAttribute("ROL").equals("Administrador")) {

			servicioUsuario.switchUsuario(email);
			return new ModelAndView("redirect:/verUsuarios", modelo);
		}
		else {
			return new ModelAndView("/login", modelo);
		}
	}
	
	@RequestMapping(path="/habilitarUsuario/{email}", method = RequestMethod.GET)
	public ModelAndView habilitarUsuario(@PathVariable String email,HttpServletRequest request) {
		ModelMap modelo = new ModelMap();	
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		Long id = (Long) request.getSession().getAttribute("ID");
		
		if(request.getSession().getAttribute("ROL").equals("Administrador")) {

			servicioUsuario.switchUsuario(email);
			return new ModelAndView("redirect:/verUsuarios", modelo);
		}
		else {
			return new ModelAndView("/login", modelo);
		}
	}
	
	@RequestMapping(path="/autentificarUsuario/{auth}", method = RequestMethod.GET)
	public ModelAndView autentificarUsuario(@PathVariable String auth,HttpServletRequest request) {
		ModelMap modelo = new ModelMap();	
		UsuarioDTO usuario = new UsuarioDTO();
		modelo.put("usuario", usuario);
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		String fecha = dateFormat.format(new Date());

		boolean resultado = servicioUsuario.autentificarUsuario(auth,fecha);
	
		if(resultado) {

			return new ModelAndView("redirect:/login", modelo);
		}else {

			return new ModelAndView("/error", modelo);
		}

	}
}
