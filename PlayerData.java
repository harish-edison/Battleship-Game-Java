
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Stream;

public class PlayerData implements Serializable {

	private static final long serialVersionUID = 1L;
	private int points;
	private String playerName;
	private ArrayList<String> destroyedShips = new ArrayList<>();
	public static final int MAX_SCORE = 30;

	public PlayerData() {
		this.points = 0;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String userName) {
		this.playerName = userName;
	}

	public ArrayList<String> getDestroyedShipsList() {
		return destroyedShips;
	}

	public void addShipToList(String destroyedShip) {
		destroyedShips.add(destroyedShip);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void updateScore(int points) {
		this.points += points;
	}

	public void displayScore() {
		System.out.println();
		System.out.println("Well Played!!! Your score is " + this.points);
		if (points != 0) {
			System.out.print(this.playerName + ", you destroyed ");
			destroyedShips.stream().forEach(e -> System.out.print(e + "  "));
			System.out.println();
		} else {
			System.out.println(this.playerName + " were unable to destroy any enemy ship.");
		}

		saveScoreToFile();
		printScoreFromFile();
	}

	private void printScoreFromFile() {
		System.out.println();
		System.out.println("High Scores in this game are:");
		System.out.println();
		String fileName = "highScores.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveScoreToFile() {

		String fileName = "highScores.txt";
		try {

			ArrayList<String> scoreList = new ArrayList<>();
			try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
				stream.forEach(e -> scoreList.add(e));
			}
			TreeMap<Integer, ArrayList<String>> map = new TreeMap<>();
			if (scoreList.size() > 0)
				scoreList.remove(0);
			scoreList.add(this.playerName + ":" + this.points + "");
			for (String line : scoreList) {
				String output = line.substring(line.lastIndexOf('\n') + 1);
				String segments[] = output.split(":");
				int index = Integer.parseInt(segments[1]);
				if (map.containsKey(index)) {
					ArrayList<String> value = map.get(index);
					value.add(segments[0]);
					map.put(index, value);
				} else {
					ArrayList<String> value = new ArrayList<>();
					value.add(segments[0]);
					map.put(index, value);
				}
			}

			FileWriter fw = new FileWriter(fileName, false);
			fw.write("UserName:Score\n");
			for (Integer entry : map.descendingKeySet()) {
				ArrayList<String> lines = map.get(entry);
				for (String val : lines) {
					String temp = val + ":" + entry + "\n";
					fw.write(temp);

				}
			}
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
