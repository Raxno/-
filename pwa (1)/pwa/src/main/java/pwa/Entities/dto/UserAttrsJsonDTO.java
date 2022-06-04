package pwa.Entities.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserAttrsJsonDTO {

	private boolean owner;
	private String role;
	
	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
