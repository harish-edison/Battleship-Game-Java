
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class BattleShips implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid theGrid = new Grid();
	private final int GRIDSIZE = 10;
	private PlayerData gameData = new PlayerData();

	public static void main(String[] args) {
		BattleShips battleShips = new BattleShips();
		battleShips.showOptions();
	}

	public void showOptions() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you wish to restart an old game(y/n)");
		char res = sc.next().charAt(0);
		if (res == 'y') {
			resumeGame();
		} else {
			askForPlayerName();
		}
		sc.close();

	}

	private void askForPlayerName() {
		String name;
		this.theGrid.setTheGrid();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name");
		name = sc.nextLine();
		gameData.setPlayerName(name);
		this.startGame();
	}

	private void startGame() {
		int row, column;

		System.out.println("Please select Mode, Press 'd' for debug and 'n' for normal");
		Scanner sc = new Scanner(System.in);
		char c = sc.next().charAt(0);
		if (c == 'd')
			this.theGrid.display();
		int i = 0;

		boolean flag = true;
		while (i < GRIDSIZE && gameData.getPoints() != PlayerData.MAX_SCORE) {
			System.out.println("Press 's' to save the game and exit, press 'y' to continue");
			char d = sc.next().charAt(0);
			if (d == 's') {
				saveGame();
				flag = false;
				break;
			}
			System.out.println("Please enter the next square to fire at:");
			row = sc.nextInt();
			column = sc.nextInt();

			boolean isShipOn, isSquareDestroyed;
			isSquareDestroyed = this.theGrid.checkSquare(row, column);
			isShipOn = this.theGrid.isThereAShipOn(row, column);

			if (!isShipOn) {
				if (isSquareDestroyed) {
					i--;
					System.out.println("This square has already been destroyed");
				} else {
					System.out.println("No ships hit!");
				}

			} else {
				Ship ship = this.theGrid.getTheShip(row, column);
				System.out.println(ship.getType() + " was destroyed,  " + ship.getPoints() + " points gained");
				gameData.updateScore(ship.getPoints());
				gameData.addShipToList(ship.getType());

				this.theGrid.removeShip(ship);

			}
			i++;
		}
		if (flag)
			gameData.displayScore();
		sc.close();

	}

	private void saveGame() {
		try {
			FileOutputStream fout = new FileOutputStream("BattleShips.txt");
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(this);
			out.flush();
			out.close();
			System.out.println("The game has been saved. Thanks for playing");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void resumeGame() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("BattleShips.txt"));
			BattleShips bShip = (BattleShips) in.readObject();
			bShip.startGame();
			in.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Starting new Game...");
			askForPlayerName();
		}
	}
}
