
public abstract class RecuitSimule implements IRecuit{
	
	// paramètres
	double T;
	double k;
	double meilleureEnergie;
	
	// méthodes abstraites 
	abstract void calculerK();	// recalculer k à chaque itération si besoin 
	abstract void initT();		// initialisation de T
	abstract boolean incrT();	// incrémentation de T à chaque itération, return false si condition d'arret atteinte 
	
	public RecuitSimule(){		// constructeur
	}
	
	public Probleme lancer(Probleme probleme){
		
		double energiePrec = probleme.calculerEnergie() ;
		this.meilleureEnergie = energiePrec ;
		double energieSuiv = 0 ;
		double proba = 1;
		
		initT();
		
		while(incrT() && meilleureEnergie!=0){
			
			probleme.modifElem();	// faire une mutation
			energieSuiv = probleme.calculerEnergie(); // calculer son énergie
			proba = Math.exp(-(energieSuiv-energiePrec)/(this.k*this.T));
			if( energieSuiv>energiePrec || proba<Math.random()){ 	// cas où la mutation est refusée
				probleme.annulerModif();
			}
			else {
				if( energieSuiv<this.meilleureEnergie ){					// cas où avec une meilleure énergie globale 
					this.meilleureEnergie = energieSuiv;
					probleme.sauvegarderSolution();
				}
				energiePrec = energieSuiv;
			}
			
			calculerK();
		}
		
		return probleme;
	}
	
}
