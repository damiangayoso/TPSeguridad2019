package ar.edu.unlam.tpseguridad.modelo;

public class UsuarioDTO {
	private String email;
	private String password;
	private String pregunta;
	private String respuesta;
	private String grecaptcharesponse;
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
	public String getGrecaptcharesponse() {
		return grecaptcharesponse;
	}
	public void setGrecaptcharesponse(String grecaptcharesponse) {
		this.grecaptcharesponse = grecaptcharesponse;
	}
	
}
