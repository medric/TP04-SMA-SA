import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Agent {
	private static final double K_PLUS = 0.1;
	private static final double K_MINUS = 0.3;
	private static final int SIZE_MEMORY = 9;

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

	public Agent(Environment environment, String name) {
		this.name = name;
		this.setEnvironment(environment);
		this.neighborhood = new ArrayList<Square>(4);
		this.shortTermMemory = new Stack<String>();	
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

	public void perceive() {
		this.environment.applyPerception(this);
	}

	public void move() {
		Square neighbor = getRandomNeighbor();

		if (hasItemInPossession() && neighbor.getObject() != null && neighbor.getObject().getClass().equals(Item.class)) {
			Item item = (Item) neighbor.getObject();
			shortTermMemory.add(item.getLabel());
		} else {
			shortTermMemory.add("0");
		}
		
		if(shortTermMemory.size() >= SIZE_MEMORY) {
			shortTermMemory.remove(0);
		}
		
		// Si le voisin est un item et que l'agent n'a pas d'item en main
		if (neighbor.getObject() != null && neighbor.getObject().getClass().equals(Item.class)) {
			take(neighbor);
		}

		// Si le voisin est null
		if (neighbor.getObject() == null) {
			leave(neighbor);
		}
	}

	public void take(Square destination) {
		if (!hasItemInPossession()) {
			Item item = (Item) destination.getObject();
			double fp = getProportionOfItemInShortMemory(item.getLabel());
			System.out.println("FP : " + fp);
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

	public void leave(Square destination) {
		if (hasItemInPossession()) {
			double fd = getProportionOfItemNeighborhood(this.getItemInPosition().getLabel());
			double probLeave = fd / (K_MINUS + fd);
			System.out.println("FD : "+ fd);
			probLeave *= probLeave;

			Random r = new Random();
			double rand = r.nextDouble();

			if (rand <= probLeave) {
				this.environment.leaveItem(this, destination);
				this.environment.moveAgent(this, destination);
			} else {
				this.environment.moveAgent(this, destination);
			}
		} else {
			this.environment.moveAgent(this, destination);
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

	public boolean hasItemInPossession() {
		return this.itemInPossession != null;
	}

	private double getProportionOfItemInShortMemory(String label) {
		int count = 0;
		for (int i = 0; i < shortTermMemory.size(); i++) {
			if (shortTermMemory.get(i).equals(label)) {
				count++;
			}
		}
		
		if (shortTermMemory.size() != 0) {
			return count / shortTermMemory.size();
		}else {
			return 1;
		}
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

		if (shortTermMemory.size() != 0) {
			return count / shortTermMemory.size();
		}else {
			return 1;
		}
	}

	private Square getRandomNeighbor() {
		Random r = new Random();
		int index = r.nextInt(this.neighborhood.size());
		return neighborhood.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
