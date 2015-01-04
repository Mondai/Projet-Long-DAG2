package solverSimuleParametrable;

public abstract class Sorties {

	Resultats resultats ; 
	
	public Sorties() {
	}
	
	public abstract void initialisation
			(double tdebut, double tfinal,
			int nbMaxIteration, String nameT, String nameK) ;
	
	public abstract void sauvegarderResultat() ;
	
	public abstract Resultats rendreResultats() ;

	
	
	
	// putain de probleme de polymorphisme !!!
	
}
