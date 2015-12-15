import java.util.ArrayList;
import java.util.Random;

public class Agent {
	private static final double K_PLUS = 0.1;
	private static final double K_MINUS = 0.3;

	private String name;
	private Environment environment;
	private Square currentSquare;
	private ArrayList<String> shortTermMemory;
	private ArrayList<Square> neighborhood;
	private Item itemInPossession;
	private int moveStep;
	private int shortTermMemorySize;

	public ArrayList<Square> getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(ArrayList<Square> neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Agent(Environment environment, int shortTermMemorySize, String name) {
		this.name = name;
		this.setEnvironment(environment);
		this.shortTermMemorySize = shortTermMemorySize;
		this.shortTermMemory = new ArrayList<String>(shortTermMemorySize);
		this.neighborhood = new ArrayList<Square>(4);
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public ArrayList<String> getShortTermMemory() {
		return shortTermMemory;
	}

	public void setShortTermMemory(ArrayList<String> shortTermMemory) {
		this.shortTermMemory = shortTermMemory;
	}

	public void perceive() {
		this.environment.applyPerception(this);
	}

	public void move() {
		Square neighbor = getRandomNeighbor();

		// If place is free
		if(neighbor.getObject() == null) {
			this.environment.moveAgent(this, neighbor);
		}
		
		// If Item
		/*if (neighbor.getObject() != null && neighbor.getObject().getClass().equals(Item.class)) {
			take(neighbor);
		} else if (neighbor.isFree()) { // If neighbor square is free try to
										// leave Item in possession
			leave(neighbor);
		}*/
		
		// If Agent ? ne rien faire ?
		
		// TODO : gestion pile mémoire
		/*if (hasItemItemInPossession()) {
			shortTermMemory.add(itemInPossession.getLabel());
		} else {
			shortTermMemory.add("0");
		}*/
	}

	public void take(Square destination) {
		if (!hasItemItemInPossession()) {
			double probTake = K_PLUS / (K_PLUS + getProportionOfItemInShortMemory("A"));
			probTake *= probTake;

			Random r = new Random();
			double rand = r.nextDouble();

			if (rand <= probTake) {
				this.setItemInPossession((Item) destination.getObject());
				//environment.move();
			}
		}
	}

	public void leave(Square destination) {
		if (hasItemItemInPossession()) {
			double probLeave = getProportionOfItemNeighborhood("A") / (K_MINUS + getProportionOfItemNeighborhood("A"));
			probLeave *= probLeave;

			Random r = new Random();
			double rand = r.nextDouble();

			if (rand <= probLeave) {
				this.setItemInPossession(null);
				//environment.move();
			}
		}
	}

	public void addElementToMemory(String label) {
		this.shortTermMemory.add(label);
	}

	public void removeElementToMemory(String label) {
		this.shortTermMemory.remove(label);
	}

	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
		this.currentSquare.setObject(this);
	}

	public Square getCurrentSquare() {
		return this.currentSquare;
	}

	public Item getItemInPosition() {
		return itemInPossession;
	}

	public void setItemInPossession(Item itemInPossession) {
		this.itemInPossession = itemInPossession;
	}

	public boolean hasItemItemInPossession() {
		return this.itemInPossession != null;
	}

	public int getMoveStep() {
		return moveStep;
	}

	public void setMoveStep(int moveStep) {
		this.moveStep = moveStep;
	}

	private double getProportionOfItemInShortMemory(String label) {
		int count = 0;
		for (int i = 0; i < shortTermMemory.size(); i++) {
			if (shortTermMemory.get(i).equals(label)) {
				count++;
			}
		}

		return count / shortTermMemory.size();
	}

	private double getProportionOfItemNeighborhood(String label) {
		int count = 0;
		for (int i = 0; i < neighborhood.size(); i++) {
			Object obj = neighborhood.get(i).getObject();
			if (obj != null && obj.getClass().equals(Item.class)) {
				Item item = (Item) obj;

				if (item.getLabel().equals(label)) {
					count++;
				}
			}
		}

		return count / neighborhood.size();
	}

	private Square getRandomNeighbor() {
		Random r = new Random();
		int index = r.nextInt(this.neighborhood.size());
		return neighborhood.get(index);
	}

	private boolean isShortMemoryFull() {
		return this.shortTermMemory.size() == shortTermMemorySize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
