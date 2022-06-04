package pwa.Entities.Models;

public class Tag {
	private Long id;
	private Long photo_id;
	private String value;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(Long photo_id) {
		this.photo_id = photo_id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
