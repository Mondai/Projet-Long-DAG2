package solver;

import solverCommun.IRecuit;
import solverCommun.Probleme;

public abstract class RecuitSimule implements IRecuit{
	
	// param�tres
	double T;
	double k;
	protected double meilleureEnergie;
	double energiePrec;
	private IListEnergie listEnergie;
	public IListEnergie listProba;
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
		this.listEnergie.augmenteTaille(); // on incremente le nombre d'iterations
		this.meilleureEnergie = this.energiePrec ;
		double energieSuiv = 0 ;
		double proba = 1;
		
		while(incrT() && this.meilleureEnergie!=0){
			
			//this.listEnergie.add(this.meilleureEnergie);  // choix arbitraire entre meilleure �nergie et �nergie actuelle
			this.listEnergie.add(this.energiePrec);		// choix de l'�nergie actuelle pour le calcul �ventuel de k
			
			//probleme.calculerEnergie(); // pour mettre a jour coloriage.nombreNoeudsConflit
			probleme.modifElem();	// faire une mutation
			
			energieSuiv = probleme.calculerEnergie(); // calculer son �nergie
			this.listEnergie.addTotal(this.energiePrec);
			//System.out.println("energie courante : " + energieSuiv);
			calculerK();
			
			this.listEnergie.augmenteTaille();// on incremente le nombre d'iterations
			
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
			System.out.println(proba);
			
			if (proba>1 ) {
				if (this.listProba.getlistEnergie().size() >0 ) {
				System.out.println(listProba.getlistEnergie().size());
				int taille = listProba.getlistEnergie().size();
				this.listProba.add(listProba.getlistEnergie().get(taille));
				} else {
					this.listProba.add(1);
				}
			} else {
			this.listProba.add(proba);
			}
			
			
			if( energieSuiv > this.energiePrec && (proba < probleme.gen.nextDouble())){ 	
				probleme.annulerModif();	// cas o� la mutation est refus�e
			}
			else {
				if( energieSuiv < this.meilleureEnergie ){	// cas o� avec une meilleure �nergie globale 
					this.meilleureEnergie = energieSuiv;
					probleme.sauvegarderSolution();
					//TEST
					//System.out.println("Meilleure energie: "+this.meilleureEnergie);
					//TEST
				}
				this.energiePrec = energieSuiv;
			}
			//TEST
			//System.out.println(proba);
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
	
	public IListEnergie getListProba() {
		return listProba;
	}
	
	public void setListProba(IListEnergie listProbas) {
		this.listProba = listProbas;
	}
	
}
