import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Agent {
	private static final double K_PLUS = 0.1;
	private static final double K_MINUS = 0.3;
	private static final int SIZE_MEMORY = 9;
	private static final int NUMBER_NEIGHBOR = 4;

	private String name;
	private Environment environment;
	private Square currentSquare;
	private Stack<String> shortTermMemory;
	private ArrayList<Square> neighborhood;
	private Item itemInPossession;

	public ArrayList<Square> getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(ArrayList<Square> neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Stack<String> getShortTermMemory() {
		return shortTermMemory;
	}

	public void setShortTermMemory(Stack<String> shortTermMemory) {
		this.shortTermMemory = shortTermMemory;
	}

	public Square getCurrentSquare() {
		return this.currentSquare;
	}

	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
		this.currentSquare.setObject(this);
	}

	public Item getItemInPosition() {
		return itemInPossession;
	}

	public void setItemInPossession(Item itemInPossession) {
		this.itemInPossession = itemInPossession;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Agent(Environment environment, String name) {
		this.name = name;
		this.setEnvironment(environment);
		this.neighborhood = new ArrayList<Square>(4);
		this.shortTermMemory = new Stack<String>();
		this.initMemory();
	}

	public void perceive() {
		this.environment.applyPerception(this);
	}
	
	private void initMemory() {
		for (int i = 0; i <= SIZE_MEMORY; i++) {
			this.shortTermMemory.add("0");
		}
	}

	private boolean hasItemInPossession() {
		return this.itemInPossession != null;
	}

	public void move() {
		Square neighbor = getRandomNeighbor();

		if (neighbor.getObject() != null && neighbor.getObject().getClass().equals(Item.class)) {
			Item item = (Item) neighbor.getObject();
			shortTermMemory.add(item.getLabel());
		} else {
			shortTermMemory.add("0");
		}

		if (shortTermMemory.size() >= SIZE_MEMORY) {
			shortTermMemory.remove(0);
		}

		// Si le voisin est un item et que l'agent n'a pas d'item
		if (neighbor.getObject() != null && neighbor.getObject().getClass().equals(Item.class)) {
			take(neighbor);
		}

		// Si le voisin est null
		if (neighbor.getObject() == null) {
			leave(neighbor);
		}
	}

	private void take(Square destination) {
		if (!hasItemInPossession()) {
			Item item = (Item) destination.getObject();
			double fp = getProportionOfItemInShortMemory(item.getLabel());
			double probTake = K_PLUS / (K_PLUS + fp);
			probTake *= probTake;

			Random r = new Random();
			double rand = r.nextDouble();

			if (rand <= probTake) {
				this.setItemInPossession((Item) destination.getObject());
				this.environment.takeItem(this, item);
				this.environment.moveAgent(this, destination);
			}
		}
	}

	private void leave(Square destination) {
		if (hasItemInPossession()) {
			double fd = getProportionOfItemNeighborhood(this.getItemInPosition().getLabel());
			double probLeave = fd / (K_MINUS + fd);
			probLeave *= probLeave;

			Random r = new Random();
			double rand = r.nextDouble();

			if (rand <= probLeave) {
				this.environment.leaveItem(this, destination);
			} else {
				this.environment.moveAgent(this, destination);
			}
		} else {
			this.environment.moveAgent(this, destination);
		}
	}

	private Square getRandomNeighbor() {
		Random r = new Random();
		int index = r.nextInt(this.neighborhood.size());
		return neighborhood.get(index);
	}

	private double getProportionOfItemInShortMemory(String label) {
		double count = 0;
		for (int i = 0; i < shortTermMemory.size(); i++) {
			if (shortTermMemory.get(i).equals(label)) {
				count++;
			}
		}

		return count / shortTermMemory.size();
	}

	private double getProportionOfItemNeighborhood(String label) {
		double count = 0;
		for (int i = 0; i < neighborhood.size(); i++) {
			Object obj = neighborhood.get(i).getObject();
			if (obj != null && obj.getClass().equals(Item.class)) {
				Item item = (Item) obj;

				if (item.getLabel().equals(label)) {
					count++;
				}
			}
		}

		return count / NUMBER_NEIGHBOR;
	}
}
