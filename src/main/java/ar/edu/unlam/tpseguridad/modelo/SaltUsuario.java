package ar.edu.unlam.tpseguridad.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SaltUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idUsuario;
	private String Salt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getSalt() {
		return Salt;
	}
	public void setSalt(String salt) {
		Salt = salt;
	}
	
}
