

import java.io.Serializable;
import java.util.Random;


public class Grid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int GRID_WIDTH = 10;
	private final int GRID_HEIGHT = 10;
	private Square[][] grid = new Square[GRID_WIDTH][GRID_HEIGHT];
	Random random = new Random();

	public void display() {
		for (int k = 2; k <= 10; k = k + 2) {
			switch (k) {
			case 2:
				System.out.print("Aircraft Carrier	:");
				break;
			case 4:
				System.out.print("Battleship		:");
				break;
			case 6:
				System.out.print("Submarine		:");
				break;
			case 8:
				System.out.print("Destroyer		:");
				break;
			case 10:
				System.out.print("Patrol boat		:");
				break;
			}
			for (int i = 0; i < GRID_WIDTH; i++) {
				for (int j = 0; j < GRID_HEIGHT; j++) {
					if (grid[i][j].isThereAShip() && grid[i][j].getTheShip().getPoints() == k) {
						System.out.print("(" + i + "," + j + ") ");
					}
				}
			}
			System.out.println();
		}
	}

	public boolean checkSquare(int row, int column) {
		return grid[row][column].isFiredAt();
	}

	public boolean isThereAShipOn(int row, int column) {
		grid[row][column].setFiredAt(true);
		return grid[row][column].isThereAShip();
	}

	public Ship getTheShip(int row, int column) {
		return grid[row][column].getTheShip();
	}

	public void removeShip(Ship ship) {
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				if (grid[i][j].isThereAShip()
						&& grid[i][j].getTheShip().getPoints() == ship.getPoints()) {
					grid[i][j].setTheShip(null);
				}
			}
		}
	}

	public void setTheGrid() {
		initializeGrid();
		// Placing the Ships
		Ship s1 = new Ship(5, "Aircraft Carrier", 2);
		this.placeTheShip(s1);

		Ship s2 = new Ship(4, "Battleship", 4);
		this.placeTheShip(s2);

		Ship s3 = new Ship(3, "Submarine", 6);
		this.placeTheShip(s3);

		Ship s4 = new Ship(2, "Destroyer", 8);
		this.placeTheShip(s4);

		Ship s5 = new Ship(1, "Patrol boat", 10);
		this.placeTheShip(s5);

	}

	private void initializeGrid() {
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				grid[i][j] = new Square();
			}
		}
	}

	private void placeTheShip(Ship ship) {
		int row = random.nextInt(GRID_WIDTH);
		int column = random.nextInt(GRID_HEIGHT);
		while (grid[row][column].isThereAShip()) {
			row = random.nextInt(GRID_WIDTH);
			column = random.nextInt(GRID_HEIGHT);
		}

		if (canBePlacedHorizontally(ship, row, column)) {
			for (int i = 0; i < ship.getLength(); i++) {
				grid[row][column + i].setTheShip(ship);
			}
		} else if (canBePlacedVertically(ship, row, column)) {
			for (int i = 0; i < ship.getLength(); i++) {
				grid[row + i][column].setTheShip(ship);
			}
		} else {
			placeTheShip(ship);
		}
	}

	private boolean canBePlacedHorizontally(Ship ship, int row, int column) {
		for (int i = 0; i < ship.getLength(); i++) {
			if (column + i >= GRID_HEIGHT || grid[row][column + i].isThereAShip()) {
				return false;
			}
		}
		return true;
	}

	private boolean canBePlacedVertically(Ship ship, int row, int column) {
		for (int i = 0; i < ship.getLength(); i++) {
			if (row + i >= GRID_HEIGHT || grid[row + i][column].isThereAShip()) {
				return false;
			}
		}
		return true;
	}

}
