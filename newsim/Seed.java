package newsim;

/**
 * @author William Booker
 * Based on work by Kevin and Mark
 * Speciation Simulation v.2.0 - 10/12/16
 * For use in Dr. Hougen's REAL Lab
 */
public class Seed {
	//objects of type Seed will input two arrays, the first is a list of 
	//means around which to be distributed, the second is a list of std dev
	//of those means
	
	//if the array is empty, that is a special case, instead of all seeds having a size of zero,
	//which is what would happen normally, I will consider, no means equivalent to a uniform distribution
	
	protected final int seedmaxenergy = 2; //PA
	//Uniform Seed Distribution
	protected static final double uniformMinSize = 1; //P3
	protected static final double uniformMaxSize = 10; //P3
	
	double size;
	double energy;
		
	Seed(double[] means, double[] stDevs) {
		if(means.length == 0)//then do a uniform distribution
			this.size = (Math.random() * (uniformMaxSize-uniformMinSize)) + uniformMinSize; //P3
		else {
			//otherwise pick a random mean to be distributed around
			int index = Random.integer(means.length);
			this.size = Random.normal(means[index], stDevs[index]);
		}
		this.energy = Math.random() * seedmaxenergy; //PA
	}

}
