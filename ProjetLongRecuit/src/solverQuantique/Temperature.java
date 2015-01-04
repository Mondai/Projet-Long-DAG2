package solverQuantique;

public abstract  class Temperature {       

	double Tdebut; 				// utile pour le constructeur
	double Tfinal;
	int nbIteration ; 
	
	
	double t;  					// c'est la temperature actuelle
	
	
	 boolean  modifierT() {
		return false;} 		//change la temperature et indique si on est à la temperature finale (true = on continue)
		
				
	 public Temperature(double tdebut, double tfinal, int nbIteration) {   // c'est le constructeur
		Tdebut = tdebut;
		Tfinal = tfinal;
		this.nbIteration = nbIteration;
		this.t = tdebut;
	}
	
	
}
