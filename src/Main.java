import java.util.Random;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Environment environment = new Environment(50, 50, 20, 200);
		Environment environment = new Environment(4, 4, 2, 2);
		System.out.println("Environnement initiale :");
		environment.render();
		for (int k = 0; k < 1000; k++) {
			Random rand = new Random();
			int randStack = rand.nextInt(environment.getAgents().size());
			environment.applyPerception(environment.getAgents().get(randStack));
			//System.out.println("Itération : " + k);
			//environment.render();
		}
		System.out.println("-----------------");
		environment.render();
	}
}
