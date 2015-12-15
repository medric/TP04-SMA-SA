import java.util.Random;

public class Main {

	private static final int NUMBER_OF_ITERATION = 20000000;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Environment environment = new Environment(50, 50, 20, 200);
		Environment environment = new Environment(50, 50, 20, 200);
		System.out.println("Environnement initiale :");
		environment.render();
		for (int k = 0; k < NUMBER_OF_ITERATION; k++) {
			Random rand = new Random();
			int randStack = rand.nextInt(environment.getAgents().size());
			environment.applyPerception(environment.getAgents().get(randStack));
			//System.out.println("Itération : " + k);
			//environment.render();
		}
		System.out.println("-----------------------------------------------");
		System.out.println("---------- Après " + NUMBER_OF_ITERATION + " itérations ----------");
		System.out.println("-----------------------------------------------");
		environment.render();
	}
}
