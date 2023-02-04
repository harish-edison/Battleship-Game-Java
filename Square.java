

import java.io.Serializable;

public class Square implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int number;
	private boolean firedAt;
	private Ship theShip;
	
	public Square() {
		setFiredAt(false);
		setTheShip(null);
	}
	
	public boolean isThereAShip() {
		if(theShip == null) {
			return false;
		}
		return true;
	}
	
	//getters and setters
	
	
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return this.number;
	}

	public boolean isFiredAt() {
		return firedAt;
	}

	public void setFiredAt(boolean firedAt) {
		this.firedAt = firedAt;
	}

	public Ship getTheShip() {
		return theShip;
	}

	public void setTheShip(Ship theShip) {
		this.theShip = theShip;
	}
}
