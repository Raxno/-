package pwa.Entities.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class Profile implements Serializable {

	private static final long serialVersionUID = -630962474388642916L;
	
	private Long id;
	private String email;
	private String firstName;
	private String lastName;
	private String nickname;
	private String password;
	private boolean enabled;
	private String token;
	private Set<Role> profileRoles;
    private String activationCode;
	
	
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
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Set<Role> getProfileRoles() {
		if (profileRoles == null) {
			profileRoles = new HashSet<>();
		}
		return profileRoles;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", nickname=" + nickname + ", password=" + password + "]";
	}
	
	public String getHighLevelRole() {

	    List<String> allRoles = new ArrayList<>();

	    for (Role role : this.getProfileRoles()) {
            allRoles.add(role.toString());
        }

	    if (allRoles.contains(Role.ADMIN.toString())) {
	        return Role.ADMIN.toString();
	    } else if(allRoles.contains(Role.MODERATOR.toString())) {
	        return Role.MODERATOR.toString();
	    } else {
	        return Role.USER.toString();
	    }

	}
	
	public List<String> getRolesList() {
	    List<String> list = new ArrayList<>();

	    for (Role role : this.getProfileRoles()) {
            list.add(role.toString());
        }

	    return list;
	}
	
	public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (obj == this)
            return true;

        if (!(obj instanceof Profile))
        return false;

        Profile user = (Profile)obj;

        if (user.hashCode() == this.hashCode())
            return true;

        return false;
    }

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	
	public String getActivationCode() {
        return activationCode;
    }
}
