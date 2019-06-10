package ar.edu.unlam.tpseguridad.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Clase que modela el concepto de Usuario, la anotacion @Entity le avisa a hibernate que esta clase es persistible
// el paquete ar.edu.unlam.tallerweb1.modelo esta indicado en el archivo hibernateCOntext.xml para que hibernate
// busque entities en él
@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;
	private String rol;
	private String texto;
	private String estado;
	private String pregunta;
	private String respuesta;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getPregunta() {
		return pregunta;
	}
	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}
