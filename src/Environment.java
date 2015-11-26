import java.util.ArrayList;
import java.util.Random;

import core.Agent;
import core.Square;

public class Environment {
	private ArrayList<ArrayList<Square>> grid;
	private Random randomGenerator;
	
	/**
	 * 
	 * @param nbStacks
	 */
	public Environment(ArrayList<ArrayList<Square>> grid) {
		this.grid = grid;
		this.randomGenerator = new Random();
	}
	
	public void applyPerception(Agent robot) {
		
		for (int i = 0; i < this.grid.size(); i++) {
			for (int j = this.grid.get(i).size() - 1; j >= 0; j--) {
				// Current square
				Square currentSquare = this.grid.get(i).get(j);	
				
				
			}
		}
	}
	
	public void placeAgents(ArrayList<Agent> agents) {		
		int cursor = 0;
		for(Agent agent : agents) {
			Square randomSquare = this.grid.get(randomGenerator.nextInt(this.grid.size())).get(randomGenerator.nextInt(this.grid.get(0).size()));
			
			while(randomSquare.isFree() && cursor < this.grid.size() * this.grid.get(0).size()) {
				randomSquare = this.grid.get(randomGenerator.nextInt(this.grid.size())).get(randomGenerator.nextInt(this.grid.get(0).size()));
				cursor++;
			}
			
			if(randomSquare.isFree()) {
				randomSquare.setObject(agent);
				agent.setCurrentSquare(randomSquare);
			}
		}
	}
	public void move() {
		
	}
	
	/**
	 * Rendering the environment
	 */
	private void render() {
		

		System.out.print("-----------------------------------Iteration suivante-----------------------------------");
	}
}
