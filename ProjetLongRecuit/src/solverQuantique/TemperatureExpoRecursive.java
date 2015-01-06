package solverQuantique;

public class TemperatureExpoRecursive extends Temperature {

	double facteur; 
	                                            
	
	public TemperatureExpoRecursive(double tdebut, double tfinal, int nbIteration, double coef ) {
		super(tdebut,tfinal,nbIteration); 						// on utilise le constructeur de Temperautre
		this.facteur = Math.exp(-coef/this.nbIteration) ; 		// on normalise le coef, voir explication en bas
}
  public boolean modifierT() {									//modif de T
	  if (this.t < this.Tfinal) 
	  { return false;  }
	  else 
	  { 
		  this.t=(this.t-this.Tfinal)*this.facteur+this.Tfinal;   // calcul moins loud mais moins precis, mais assez precis je pense
		  return true;		  
	  }
  }
}


//voir commentaire de TemperatureExpoExplicite pour les explications