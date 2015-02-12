package solver.parametres;

public abstract  class Fonction {       

	double Tdebut; 				// utile pour le constructeur
	double Tfinal;
	public int nbIteration ; 
	
	
	public double t;  					// c'est la temperature actuelle
	
	
	 public boolean  modifierT() {
		return false;} 		//change la temperature et indique si on est à la temperature finale (true = on continue)
		
	public void init(){
		this.t = this.Tdebut;
	}
	 
	 public Fonction(double tdebut, double tfinal, int nbIteration) {   // c'est le constructeur
		this.Tdebut = tdebut;
		this.Tfinal = tfinal;
		this.nbIteration = nbIteration;
		this.t = tdebut;
	}
	
	
}
