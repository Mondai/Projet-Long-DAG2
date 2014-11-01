package solver;

public abstract class RecuitSimule implements IRecuit{
	
	// param�tres
	double T;
	double k;
	private double meilleureEnergie;
	double energiePrec;
	private IListEnergie listEnergie;
	private int nbPoints; //nombre d'it�rations au total ( changement de palier ET it�ration sur palier )
	
	// m�thodes abstraites 
	abstract void calculerK();	// recalculer k � chaque it�ration si besoin 
	abstract void init();		// initialisation
	abstract boolean incrT();	// incr�mentation de T � chaque it�ration, return false si condition d'arret atteinte 
	
	
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
			
			this.getListEnergie().add(this.getMeilleureEnergie());  // choix arbitraire entre meilleure �nergie et �nergie actuelle
			calculerK();
			
			probleme.modifElem();	// faire une mutation
			energieSuiv = probleme.calculerEnergie(); // calculer son �nergie
			this.getListEnergie().augmenteTaille();// on incremente le nombre d'iterations
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
			if( energieSuiv>this.energiePrec && (proba<probleme.gen.nextDouble())){ 	
				probleme.annulerModif();	// cas o� la mutation est refus�e
			}
			else {
				if( energieSuiv<this.getMeilleureEnergie() ){	// cas o� avec une meilleure �nergie globale 
					this.setMeilleureEnergie(energieSuiv);
					probleme.sauvegarderSolution();
				}
				this.energiePrec = energieSuiv;
			}
			
			
			

		}
		
		return probleme;
	}
	
	public String toString(){
		return "Recuit Simul�";
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
