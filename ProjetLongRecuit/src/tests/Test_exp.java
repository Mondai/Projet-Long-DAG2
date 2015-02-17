package tests;

// Test des fonctions approchees d'exponentiel
/* Les performances en temps des 2 exponentielles sont presque pareilles, par contre on a les erreurs de calculs suivant sur certaines plages de valeurs:
  	*   exp( 1000 ) :
    	exp -> Infinity					 <- positif
    	exp1-> 6.790731620590713E176     <- positif
    	exp2-> -6.162916874664818E-183	 <- negatif
    	
    *	exp( -5.209971843526424E-6 ) :
  		exp-> 0.9999947900417283		 <- proba de rejet <0.001%
  		exp1-> 0.9999947900416788		 <- idem
  		exp2-> 0.9710040092468262		 <- proba de rejet= 3%
  	
  	*	exp( -1000 ) :
  		exp-> 0.0						 <- proba de rejet = 100%
  		exp1-> 4.104312118349824E118	 <- proba de rejet =   0%  
  		exp2-> -1.6562896245449562E182	 <- proba de rejet = 100%   (mais bizarre quand meme)
  	
 */

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
		
		double[] tab = {-1000,-100,-10,-1,-0.1,-0.01,-0.001,-0.0001,-0.0001, 0.00001, 0.0001, 0.001, 0.01, 0.1, 1, 10, 100};
		
		for(int i = 0 ; i<tab.length ; i++){
			double val = Math.random()*tab[i];
			System.out.println("exp( "+val+" ) :");
			System.out.println("  exp-> "+Math.exp(val));
			System.out.println("  exp1-> "+exp1(val));
			System.out.println("  exp2-> "+exp2(val));
		}
		
		System.out.println("teste grands nombres");
		for(int i = 1; i < 1000000000; i*=10){
			System.out.println("exp( "+-i+" ) :");
			System.out.println("  exp-> "+Math.exp(-i));
			System.out.println("  exp1-> "+exp1(-i));
			System.out.println("  exp2-> "+exp2(-i));
		}
		
		long startTime = System.nanoTime();
		for(int i = 0; i<1000000000; i++){
			exp1(-Math.random());
		}
		long endTime = System.nanoTime();
		System.out.println("exp1 10^9 duree = "+(endTime-startTime)/1000000000+" s");
		
		startTime = System.nanoTime();
		for(int i = 0; i<1000000000; i++){
			exp2(-Math.random());
		}
		endTime = System.nanoTime();
		System.out.println("exp2 10^9 duree = "+(endTime-startTime)/1000000000+" s");

	}

}
