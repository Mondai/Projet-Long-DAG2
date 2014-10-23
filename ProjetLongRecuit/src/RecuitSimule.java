
public abstract class RecuitSimule implements IRecuit{
	
	// param�tres
	double T;
	double k;
	double meilleureEnergie;
	double energiePrec;
	IListEnergie listEnergie;
	
	// m�thodes abstraites 
	abstract void calculerK();	// recalculer k � chaque it�ration si besoin 
	abstract void init();		// initialisation
	abstract boolean incrT();	// incr�mentation de T � chaque it�ration, return false si condition d'arret atteinte 
	
	
	public RecuitSimule(){		// constructeur
	}
	
	
	
	public Probleme lancer(Probleme probleme){
		
		init();
		
		this.energiePrec = probleme.calculerEnergie() ;
		this.listEnergie.augmenteTaille(); // on incremente le nombre d'iterations
		this.meilleureEnergie = this.energiePrec ;
		double energieSuiv = 0 ;
		double proba = 1;
		
		while(incrT() && this.meilleureEnergie!=0){
			
			this.listEnergie.add(this.energiePrec);
			calculerK();
			
			probleme.modifElem();	// faire une mutation
			energieSuiv = probleme.calculerEnergie(); // calculer son �nergie
			this.listEnergie.augmenteTaille();// on incremente le nombre d'iterations
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
			if( energieSuiv>this.energiePrec || proba < probleme.gen.nextDouble()){ 	// cas o� la mutation est refus�e
				probleme.annulerModif();
			}
			else {
				if( energieSuiv<this.meilleureEnergie ){					// cas o� avec une meilleure �nergie globale 
					this.meilleureEnergie = energieSuiv;
					probleme.sauvegarderSolution();
				}
				this.energiePrec = energieSuiv;
			}
			
			
			

		}
		
		return probleme;
	}
	
}
