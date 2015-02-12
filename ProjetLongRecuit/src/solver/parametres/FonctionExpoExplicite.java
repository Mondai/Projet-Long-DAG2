package solver.parametres;

public class FonctionExpoExplicite extends Fonction {

	double coefPente; 
	int k;                                              // k est le nombre d 'iteartion effectue
	
	public void init(){
		super.init();
		this.k=0;
	}
	
	public FonctionExpoExplicite(double tdebut, double tfinal, int nbIteration, double coef ) {
		super(tdebut,tfinal,nbIteration); 				// on utilise le constructeur de Temperautre
		this.k=0;
		this.coefPente = -coef/this.nbIteration ; 		// on normalise le coef, voir explication en bas
}
  public boolean modifierT() {							//modif de T
	  if (this.t < this.Tfinal) 
	  { return false;  }
	  else 
	  { 
		  this.k++;
		  this.t=Tfinal+(Tdebut-Tfinal)*Math.exp(coefPente*this.k);   // calcul lourd mais precis
		  return true;		  
	  }
  }
}


// pas de calcul recurssif, on utilise directement la formule de porc !  donc on a besoin de k pour savoir ou on en est

// pourquoi ce coef ? il permet de choisir la pente independammment de Tdebut, Tfin et nbIter
// en effet, T(0) = Tdebut 
// et T(nbIter) = Tfinal+(Tdebut-Tfinal)*exp(coefPente*nbIter)
//				= Tfinal+(Tdebut-Tfinal)*exp(-coef*nbIter/nbIter)
//              = Tfinal+(Tdebut-Tfinal)*exp(-coef)
// or exp(-coef) est proche de zero
//              == Tfinal

// le coef est a prendre plutot entre 4 et 6,  exp(-4) = 0.02 exp(-6) = 0.0025
// de cette facon on a une bonne exponentielle, qui ne reste pas trop lontemps vers Tfin mais quand même un peu
// c'est modulable :)