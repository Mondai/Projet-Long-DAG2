package solverSimuleParametrable;

public class ConstanteKUnitaire extends ConstanteK {   // surtout pour tester, mais vraiment utile en soi

 public ConstanteKUnitaire() { }
 
 void calculerK(double deltaE) {
	 this.k=deltaE; }
 }


// K vaut deltaE donc la proba vaut toujours exp(-1/T) 

// idem on utilise pas les possibilite de paramatrage et ceci pourait 
// être fait plus simplement sans utiliser une classe a pamametarge