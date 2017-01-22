package javax.topgun.competency;

import java.sql.Date;

public class CheckUpUser {
	
	private int idUser;
	private String username;
	private String password;
	private Date birthDate;
	private Date timestamps;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Date timestamps) {
		this.timestamps = timestamps;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	
}
