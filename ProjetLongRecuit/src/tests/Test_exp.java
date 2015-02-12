package tests;

// Test des fonctions approchees d'exponentiel
public class Test_exp {

	public static double exp1(double x) {
		  x = 1.0 + x / 256.0;
		  x *= x; x *= x; x *= x; x *= x;
		  x *= x; x *= x; x *= x; x *= x;
		  return x;
	}
	
	public static double exp2(double val) {
		final long tmp = (long) (1512775 * val + 1072632447);
		return Double.longBitsToDouble(tmp << 32);
	}
	
	public static void main(String[] args) {
		
		double[] tab = {-1000,-100,-10,-1,-0.1,-0.01,-0.001,-0.0001,-0.00001, 0.00001, 0.0001, 0.001, 0.01, 0.1, 1, 10, 100};
		
		for(int i = 0 ; i<tab.length ; i++){
			double val = Math.random()*tab[i];
			System.out.println("exp( "+val+" ) :");
			System.out.println("  exp-> "+Math.exp(val));
			System.out.println("  exp1-> "+exp1(val));
			System.out.println("  exp2-> "+exp2(val));
		}

	}

}
