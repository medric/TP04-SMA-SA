import java.util.ArrayList;
import java.util.Random;

import core.Position;
import core.Square;

/**
 * 
 */

/**
 * @author medric
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Init base
		Environment environment = new Environment(initSMA(50, 50, 1, 20, 200, 200));
		
		
	}

	public static ArrayList<ArrayList<Square>> initSMA(int gridWidth, 
														int gridHeight, 
														int i, 
														int nbAgents, 
														int nbObjectA, 
														int nbObjectB) {
		ArrayList<ArrayList<Square>> grid;
		
		for(int y = 0; y < gridHeight; y++) {
			ArrayList<Square> columns = new ArrayList<Square>(gridWidth);
			for(int x = 0; x < gridWidth; x++) {
				Square square = new Square(new Position(x, y));
				square.setObject(null);
			}
			grid.add(columns);
		}
		
		return grid;
	}
	
	private Square getRandomSquare(ArrayList<ArrayList<Square>> grid) {
		Random random = new Random();
		int i = random.nextInt();
		int j = random.
	}
}
