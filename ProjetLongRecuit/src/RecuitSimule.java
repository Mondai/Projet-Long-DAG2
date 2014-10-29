
public abstract class RecuitSimule implements IRecuit{
	
	// paramï¿½tres
	double T;
	double k;
	double meilleureEnergie;
	double energiePrec;
	IListEnergie listEnergie;
	int nbPoints; //nombre d'itérations au total
	
	// mï¿½thodes abstraites 
	abstract void calculerK();	// recalculer k ï¿½ chaque itï¿½ration si besoin 
	abstract void init();		// initialisation
	abstract boolean incrT();	// incrï¿½mentation de T ï¿½ chaque itï¿½ration, return false si condition d'arret atteinte 
	
	
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
			
			this.listEnergie.add(this.meilleureEnergie);  // choix arbitraire entre meilleure énergie et énergie actuelle
			calculerK();
			
			probleme.modifElem();	// faire une mutation
			energieSuiv = probleme.calculerEnergie(); // calculer son ï¿½nergie
			this.listEnergie.augmenteTaille();// on incremente le nombre d'iterations
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
			if( energieSuiv>this.energiePrec && (proba<probleme.gen.nextDouble())){ 	
				probleme.annulerModif();	// cas oï¿½ la mutation est refusï¿½e
			}
			else {
				if( energieSuiv<this.meilleureEnergie ){	// cas oï¿½ avec une meilleure ï¿½nergie globale 
					this.meilleureEnergie = energieSuiv;
					probleme.sauvegarderSolution();
				}
				this.energiePrec = energieSuiv;
			}
			
			
			

		}
		
		return probleme;
	}
	
	public String toString(){
		return "Recuit Simulé";
	}
	
}
