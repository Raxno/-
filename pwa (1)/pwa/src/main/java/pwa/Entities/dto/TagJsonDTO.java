package pwa.Entities.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagJsonDTO {

	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
