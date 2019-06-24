package ar.edu.unlam.tpseguridad.modelo;

public class ClaveDTO {

	private String oldPass;
	private String newPass;
	
	private Long id;
	private String email;
	private String newPass1;
	private String newPass2;
	
	
	public String getOldPass() {
		return oldPass;
	}
	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	
	/////////////////////////////////////////////////
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNewPass1() {
		return newPass1;
	}
	public void setNewPass1(String newPass1) {
		this.newPass1 = newPass1;
	}
	
	public String getNewPass2() {
		return newPass2;
	}
	public void setNewPass2(String newPass2) {
		this.newPass2 = newPass2;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	
}
