
public abstract class RecuitSimule implements IRecuit{
	
	// param�tres
	double T;
	double k;
	double meilleureEnergie;
	
	// m�thodes abstraites 
	abstract void calculerK();	// recalculer k � chaque it�ration si besoin 
	abstract void initT();		// initialisation de T
	abstract boolean incrT();	// incr�mentation de T � chaque it�ration, return false si condition d'arret atteinte 
	
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
			energieSuiv = probleme.calculerEnergie(); // calculer son �nergie
			proba = Math.exp(-(energieSuiv-energiePrec)/(this.k*this.T));
			if( energieSuiv>energiePrec || proba<Math.random()){ 	// cas o� la mutation est refus�e
				probleme.annulerModif();
			}
			else {
				if( energieSuiv<this.meilleureEnergie ){					// cas o� avec une meilleure �nergie globale 
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
