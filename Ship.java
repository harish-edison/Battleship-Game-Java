

import java.io.Serializable;

public class Ship implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int length;
	private String type;
	private int points;
	
	public Ship(int length, String type, int points) {
		this.length = length;
		this.type = type;
		this.points = points;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}
