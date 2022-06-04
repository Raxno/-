package pwa.Entities.Models;

import java.io.Serializable;

public class Relationships implements Serializable {

	private static final long serialVersionUID = -630962474388642916L;	//copypast
	
	private Long id;
	private Long profile_id;
	private Long target_id;
	private Status status;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(Long profile_id) {
		this.profile_id = profile_id;
	}

	public Long getTarget_id() {
		return target_id;
	}

	public void setTarget_id(Long target_id) {
		this.target_id = target_id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Profile [id=" + id + ", profile_id=" + profile_id + 
				", target_id=" + target_id + ", status=" + status + "]";
	}
}
