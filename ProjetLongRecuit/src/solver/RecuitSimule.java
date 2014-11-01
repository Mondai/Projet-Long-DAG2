package solver;

public abstract class RecuitSimule implements IRecuit{
	
	// paramï¿½tres
	double T;
	double k;
	private double meilleureEnergie;
	double energiePrec;
	private IListEnergie listEnergie;
	private int nbPoints; //nombre d'itérations au total ( changement de palier ET itération sur palier )
	
	// mï¿½thodes abstraites 
	abstract void calculerK();	// recalculer k ï¿½ chaque itï¿½ration si besoin 
	abstract void init();		// initialisation
	abstract boolean incrT();	// incrï¿½mentation de T ï¿½ chaque itï¿½ration, return false si condition d'arret atteinte 
	
	
	public RecuitSimule(){		// constructeur
	}
	
	
	
	public Probleme lancer(Probleme probleme){
		
		init();
		
		this.energiePrec = probleme.calculerEnergie() ;
		this.getListEnergie().augmenteTaille(); // on incremente le nombre d'iterations
		this.setMeilleureEnergie(this.energiePrec) ;
		double energieSuiv = 0 ;
		double proba = 1;
		
		while(incrT() && this.getMeilleureEnergie()!=0){
			
			this.getListEnergie().add(this.getMeilleureEnergie());  // choix arbitraire entre meilleure énergie et énergie actuelle
			calculerK();
			
			probleme.modifElem();	// faire une mutation
			energieSuiv = probleme.calculerEnergie(); // calculer son ï¿½nergie
			this.getListEnergie().augmenteTaille();// on incremente le nombre d'iterations
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
			if( energieSuiv>this.energiePrec && (proba<probleme.gen.nextDouble())){ 	
				probleme.annulerModif();	// cas oï¿½ la mutation est refusï¿½e
			}
			else {
				if( energieSuiv<this.getMeilleureEnergie() ){	// cas oï¿½ avec une meilleure ï¿½nergie globale 
					this.setMeilleureEnergie(energieSuiv);
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
	public double getMeilleureEnergie() {
		return meilleureEnergie;
	}
	public void setMeilleureEnergie(double meilleureEnergie) {
		this.meilleureEnergie = meilleureEnergie;
	}
	public int getNbPoints() {
		return nbPoints;
	}
	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}
	public IListEnergie getListEnergie() {
		return listEnergie;
	}
	public void setListEnergie(IListEnergie listEnergie) {
		this.listEnergie = listEnergie;
	}
	
}
