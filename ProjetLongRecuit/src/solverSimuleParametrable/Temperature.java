package solverSimuleParametrable;

public abstract  class Temperature {       

	double Tdebut; 				// utile pour le constructeur
	double Tfinal;
	int nbIteration ; 
	
	
	double t;  					// c'est la temperature actuelle
	
	
	 boolean  modifierT() {
		return false;} 		//change la temperature et indique si on est à la temperature finale (true = on continue)
		
	public void init(){
		this.t = this.Tdebut;
	}
	 
	 public Temperature(double tdebut, double tfinal, int nbIteration) {   // c'est le constructeur
		this.Tdebut = tdebut;
		this.Tfinal = tfinal;
		this.nbIteration = nbIteration;
		this.t = tdebut;
	}
	
	
}
