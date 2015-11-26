import java.util.ArrayList;

public class Agent {
	private Environment environment;
	private ArrayList<String> shortTermMemory;
	private ArrayList<Square> neighborhood; 
	
	public ArrayList<Square> getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(ArrayList<Square> neighborhood) {
		this.neighborhood = neighborhood;
	}

	public Agent(Environment environment, int shortTermMemorySize) {
		this.setEnvironment(environment);
		this.shortTermMemory = new ArrayList<String>(shortTermMemorySize);
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
	}
	
	public void take() {	
	}
	
	public void leave() {	
	}
	
	public void addElementToMemory(String label) {
		this.shortTermMemory.add(label);
	}
	
	public void removeElementToMemory(String label) {
		this.shortTermMemory.remove(label);
	}
}
