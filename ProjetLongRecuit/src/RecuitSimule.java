import java.util.List;


public abstract class RecuitSimule implements IRecuit{
	
	// param�tres
	double T;
	double k;
	double meilleureEnergie;
	IListEnergie listEnergie;
	int echantillonage;
	
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
		int z=this.echantillonage;
		
		while(incrT() && meilleureEnergie!=0){
			
			calculerK();
			
			probleme.modifElem();	// faire une mutation
			energieSuiv = probleme.calculerEnergie(); // calculer son �nergie
			proba = Math.exp(-(energieSuiv-energiePrec)/(this.k*this.T));
			if( energieSuiv>energiePrec || proba < probleme.gen.nextDouble()){ 	// cas o� la mutation est refus�e
				probleme.annulerModif();
			}
			else {
				if( energieSuiv<this.meilleureEnergie ){					// cas o� avec une meilleure �nergie globale 
					this.meilleureEnergie = energieSuiv;
					probleme.sauvegarderSolution();
				}
				energiePrec = energieSuiv;
			}
			
			listEnergie.add(meilleureEnergie,this.echantillonage);
			/*if (z == this.echantillonage) {
				//listEnergie.add(energieSuiv);
				listEnergie.add(meilleureEnergie);
				z=1;
			}
			else {
				z++;
			}
			*/
			
		}
		
		return probleme;
	}
	
}
