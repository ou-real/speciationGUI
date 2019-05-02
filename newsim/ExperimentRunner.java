package newsim;
import java.io.IOException;

/**
 * @author William Booker
 * Based on work by Kevin and Mark
 * Speciation Simulation v.2.0 - 10/12/16
 */
public class ExperimentRunner {
	
	final static Bird.SelectionType sexual = Bird.SelectionType.SexualSelection;
	final static Bird.SelectionType random = Bird.SelectionType.RandomSelection;
	
	static double[] means = {1, 4, 5};
	static double[] stDevs = {.25, .5, .5};
	
	final static Bird.InheritanceMethod avg = Bird.InheritanceMethod.Averaging;
	final static Bird.InheritanceMethod pseudo = Bird.InheritanceMethod.Pseudomeiosis;
	final static Bird.InheritanceMethod meiosis = Bird.InheritanceMethod.Meiosis;
	
	final static Gene.Type incomplete = Gene.Type.IncompleDominance;
	final static Gene.Type complete = Gene.Type.CompleteDominance;

	public static void main(String[] args) throws IOException {
		System.out.println("Starting New Simulation");
		
		
		
		
		
		
		/*for(int meanLocation = 0; meanLocation<=20; meanLocation++)
			for(int i=0; i<3; i++)
				new Simulation(sexual, new double[] {(double)meanLocation*.5}, stDevs, pseudo, complete);*/
		//this one worked for 3.5 to 8.5, weird that it does not revolve around 5.5, it revolves around 6
		
		
		
//		for(int i=0; i<10; i++)
//			new Simulation(sexual, means, stDevs, pseudo, complete); 
		
		
		for(int i=0; i<1; i++) {
			new Simulation(sexual, meiosis, complete, means, stDevs, "fakeDateTime"); 
		}
		
//		for(int i=0; i<5; i++) {
//			new Simulation(sexual, new double[] {1.0}, new double[] {1.0}, pseudo, complete); 
//		}
		
		
		System.out.println("Finishing Simulation");
	}
}











