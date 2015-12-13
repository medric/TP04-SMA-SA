import java.util.ArrayList;
import java.util.Random;

public class Environment {
	public static Random RANDOM_GENERATOR = new Random();
	private ArrayList<ArrayList<Square>> grid;
	private ArrayList<Agent> agents;
	private ArrayList<Item> items;
	private int gridWidth;
	private int gridHeight;

	/**
	 * 
	 * @param nbStacks
	 */
	public Environment(int gridWith, int gridHeight, int nbAgents, int nbItems) {
		this.gridWidth = gridWith;
		this.gridHeight = gridHeight;

		grid = new ArrayList<ArrayList<Square>>();
		this.initGrid();

		agents = new ArrayList<Agent>();
		this.initAgents(nbAgents);

		items = new ArrayList<Item>();
		this.initItems(nbItems, "A");
		this.initItems(nbItems, "B");

		// Place
		this.placeAgents();
		this.placeItems();
	}

	private void initGrid() {
		for (int y = 0; y < gridHeight; y++) {
			ArrayList<Square> columns = new ArrayList<Square>(gridWidth);
			for (int x = 0; x < gridWidth; x++) {
				Square square = new Square(new Position(x, y));
				square.setObject(null);
				columns.add(square);
			}
			grid.add(columns);
		}
	}

	private void initAgents(int nbAgents) {
		for (int i = 0; i < nbAgents; i++) {
			String name = "Agent_" + i;
			Agent agent = new Agent(this, 10, name);
			agents.add(agent);
		}
	}

	private void initItems(int nbItems, String label) {
		for (int i = 0; i < nbItems; i++) {
			Item item = new Item(label);
			items.add(item);
		}
	}

	public void applyPerception(Agent agent) {
		agent.setNeighborhood(getNeighbors(agent.getCurrentSquare()));
	}

	public void moveAgent(Square destination) {
	}

	public void placeAgents() {
		// int cursor = 0;
		for (Agent agent : agents) {

			boolean placed = true;
			while (placed) {
				Square randomSquare = getRandomSquare();

				if (randomSquare == null) {
					agent.setCurrentSquare(randomSquare);
					randomSquare.setObject(agent);
					placed = false;
				}
			}

			// TODO : logique ? randomSquare.isFree ??
			/*
			 * while(randomSquare == null || randomSquare.isFree() && cursor <
			 * this.getGridWidth() * this.getGridHeight()) { randomSquare =
			 * getRandomSquare(); cursor++; }
			 */

			/*
			 * if(randomSquare.isFree()) { randomSquare.setObject(agent);
			 * agent.setCurrentSquare(randomSquare); }
			 */
		}
	}

	public void placeItems() {
		int cursor = 0;
		for (Item item : items) {
			Square randomSquare = getRandomSquare();

			while (randomSquare.isFree() && cursor < this.getGridWidth() * this.getGridHeight()) {
				randomSquare = getRandomSquare();
				cursor++;
			}

			if (randomSquare.isFree()) {
				randomSquare.setObject(item);
				item.setCurrentSquare(randomSquare);
			}
		}
	}

	public void move() {

	}

	public int getGridWidth() {
		return this.grid.size();
	}

	public int getGridHeight() {
		return this.grid.get(0).size();
	}

	public ArrayList<Square> getNeighbors(Square square) {
		ArrayList<Square> neighbors = new ArrayList<Square>();

		// right neighbor
		if (square.getPosition().getX() < gridWidth - 1) {
			neighbors.add(grid.get(square.getPosition().getY()).get(square.getPosition().getX() + 1));
		}

		// left neighbor
		if (square.getPosition().getX() > 0) {
			neighbors.add(grid.get(square.getPosition().getY()).get(square.getPosition().getX() - 1));
		}

		// bottom neighbor
		if (square.getPosition().getY() < gridWidth - 1) {
			neighbors.add(grid.get(square.getPosition().getY() + 1).get(square.getPosition().getX()));
		}

		// top neighbor
		if (square.getPosition().getY() > 0) {
			neighbors.add(grid.get(square.getPosition().getY() - 1).get(square.getPosition().getX()));
		}

		return neighbors;
	}

	private Square getRandomSquare() {

		/*
		 * int i = RANDOM_GENERATOR.nextInt(); int j =
		 * RANDOM_GENERATOR.nextInt(); return grid.get(i).get(j);
		 */

		// int valeur = valeurMin + r.nextInt(valeurMax - valeurMin)

		Random r = new Random();

		int randI = r.nextInt(gridWidth - 1);
		int randJ = r.nextInt(gridHeight - 1);

		return (Square) grid.get(randI).get(randJ).getObject();
	}

	/**
	 * Rendering the environment
	 */
	private void render() {

		System.out.print("-----------------------------------Iteration suivante-----------------------------------");
	}
}
