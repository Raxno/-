package pwa.Entities.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RatingJsonDTO {

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
