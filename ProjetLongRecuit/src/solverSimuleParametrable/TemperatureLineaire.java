package solverSimuleParametrable;

public class TemperatureLineaire extends Temperature {

	double pasLineaire;															// ce sera la modif lineaire
	
	public TemperatureLineaire(int tdebut, int tfinal, int nbIteration) {
		super(tdebut,tfinal,nbIteration); 										// on utilise le constructeur de Temperautre
		this.pasLineaire = (this.Tfinal-this.Tdebut)/this.nbIteration ; 		//calcul de la modif lineaire
}
  public boolean modifierT() {													//modif de T
	  if (this.t < this.Tfinal) 
	  { return false;  }
	  else 
	  { this.t=this.t+this.pasLineaire;											// le pas linearie est negatif :)
	  return true;		  
	  }
  }
}


// approximation du nombre d'iteration car le pas lineaire est est nombre flotant 
// lolilol cours d'archi norme IEEE bref ca passe