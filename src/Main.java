import java.util.ArrayList;
import java.util.Random;

/**
 * @author medric
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Environment environment = new Environment(50, 50, 20, 200);
		Environment environment = new Environment(5, 5, 2, 2);
		
		for (int k = 0; k < 3; k++) {
			Random rand = new Random();
			int randStack = rand.nextInt(environment.getAgents().size());
			environment.applyPerception(environment.getAgents().get(randStack));
			System.out.println("Itération : " + k);
			environment.render();
		}
	}
}
