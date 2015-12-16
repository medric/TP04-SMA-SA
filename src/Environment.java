import java.util.ArrayList;
import java.util.Random;

public class Environment {
	private ArrayList<ArrayList<Square>> grid;
	private ArrayList<Agent> agents;
	private ArrayList<Item> items;
	private int gridWidth;
	private int gridHeight;

	public int getGridWidth() {
		return this.grid.size();
	}

	public int getGridHeight() {
		return this.grid.get(0).size();
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	/**
	 * Environment
	 * 
	 * @param gridWith
	 * @param gridHeight
	 * @param nbAgents
	 * @param nbItems
	 */
	public Environment(int gridWith, int gridHeight, int nbAgents, int nbItems) {
		this.gridWidth = gridWith;
		this.gridHeight = gridHeight;

		grid = new ArrayList<ArrayList<Square>>();
		this.initGrid();

		setAgents(new ArrayList<Agent>());
		this.initAgents(nbAgents);

		items = new ArrayList<Item>();
		this.initItems(nbItems, "A");
		this.initItems(nbItems, "B");

		// Place
		this.placeAgents();
		this.placeItems();
	}

	/**
	 * Initialize the grid with square
	 */
	private void initGrid() {
		for (int i = 0; i < gridHeight; i++) {
			ArrayList<Square> columns = new ArrayList<Square>(gridWidth);
			for (int j = 0; j < gridWidth; j++) {
				Square square = new Square(new Position(j, i));
				square.setObject(null);
				columns.add(square);
			}
			grid.add(columns);
		}
	}

	/**
	 * Initialize the agents
	 * 
	 * @param nbAgents
	 */
	private void initAgents(int nbAgents) {
		for (int i = 0; i < nbAgents; i++) {
			Agent agent = new Agent(this, i);
			getAgents().add(agent);
		}
	}

	/**
	 * Initialize the items
	 * 
	 * @param nbItems
	 * @param label
	 */
	private void initItems(int nbItems, String label) {
		for (int i = 0; i < nbItems; i++) {
			Item item = new Item(label);
			items.add(item);
		}
	}

	/**
	 * Apply perception
	 * 
	 * @param agent
	 */
	public void applyPerception(Agent agent) {
		agent.setNeighborhood(getNeighbors(agent.getCurrentSquare()));
		agent.move();
	}

	/**
	 * Move Agent
	 * 
	 * @param agent
	 * @param destination
	 */
	public void moveAgent(Agent agent, Square destination) {
		int nextX = destination.getPosition().getX();
		int nextY = destination.getPosition().getY();

		int currentX = agent.getCurrentSquare().getPosition().getX();
		int currentY = agent.getCurrentSquare().getPosition().getY();

		this.grid.get(currentY).get(currentX).setObject(null);

		agent.setCurrentSquare(destination);
		this.grid.get(nextY).get(nextX).setObject(agent);
	}

	/**
	 * Take an item
	 * 
	 * @param agent
	 * @param item
	 */
	public void takeItem(Agent agent, Item item) {
		agent.setItemInPossession(item);
	}

	/**
	 * Leave an item
	 * 
	 * @param agent
	 * @param destination
	 */
	public void leaveItem(Agent agent, Square destination) {
		int nextX = destination.getPosition().getX();
		int nextY = destination.getPosition().getY();
		this.grid.get(nextY).get(nextX).setObject(agent.getItemInPosition());

		agent.setItemInPossession(null);
	}

	/**
	 * Initialize the agents in the grid
	 */
	public void placeAgents() {
		for (Agent agent : getAgents()) {

			boolean placed = true;
			while (placed) {
				Square randomSquare = getRandomSquare();

				if (randomSquare.getObject() == null) {
					agent.setCurrentSquare(randomSquare);
					randomSquare.setObject(agent);
					placed = false;
				}
			}
		}
	}

	/**
	 * Initialize the items in the grid
	 */
	public void placeItems() {
		for (Item item : items) {

			boolean placed = true;
			while (placed) {
				Square randomSquare = getRandomSquare();

				if (randomSquare.getObject() == null) {
					item.setCurrentSquare(randomSquare);
					randomSquare.setObject(item);
					placed = false;
				}
			}
		}
	}

	/**
	 * Get Neighbors
	 * @param square
	 * @return
	 */
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

	/**
	 * Get Random Square
	 * @return
	 */
	private Square getRandomSquare() {

		Random r = new Random();

		int randI = r.nextInt(gridWidth);
		int randJ = r.nextInt(gridHeight);

		return grid.get(randI).get(randJ);
	}

	/**
	 * Rendering the environment
	 */
	public void render() {
		for (int i = 0; i < gridHeight; i++) {
			for (int j = 0; j < gridWidth; j++) {

				Square square = grid.get(i).get(j);

				String displayJ = "";
				String displayI = "";

				if (gridWidth >= 10) {
					displayJ = (j < 10) ? "0" + j : Integer.toString(j);
					displayI = (i < 10) ? "0" + i : Integer.toString(i);
				} else {
					displayJ = Integer.toString(j);
					displayI = Integer.toString(i);
				}

				System.out.print("(" + displayJ + "," + displayI + ")");
				if (Agent.class.isInstance(square.getObject())) {
					Agent agent = (Agent) square.getObject();
					System.out.print(agent.getName());
				} else if (Item.class.isInstance(square.getObject())) {
					Item item = (Item) square.getObject();
					System.out.print(item.getLabel() + "    ");
				} else {
					System.out.print("     ");
				}
			}
			System.out.println();
		}
	}
}
