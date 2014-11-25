package solver;

import java.util.List;

import solverCommun.IRecuit;
import solverCommun.Probleme;

public abstract class RecuitSimule implements IRecuit{
	
	// paramï¿½tres
	double T;
	double k;
	double probaMoyenne; //même principe que le k, afin de lisser la courbe des probas
	protected double meilleureEnergie;
	double energiePrec;
	private IListEnergie listEnergie;
	public IListEnergie listProba;
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
		this.meilleureEnergie = this.energiePrec ;
		double energieSuiv = 0 ;
		double proba = 1;
		
		while(incrT() && this.meilleureEnergie!=0){
			
			probleme.modifElem();	// faire une mutation
			
			energieSuiv = probleme.calculerEnergie(); // calculer son ï¿½nergie
			calculerK();
			
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
	
			if( energieSuiv > this.energiePrec && (proba < probleme.gen.nextDouble())){ 	
				probleme.annulerModif();	// cas oï¿½ la mutation est refusï¿½e
			}
			else {
				if( energieSuiv < this.meilleureEnergie ){	// cas oï¿½ avec une meilleure ï¿½nergie globale 
					this.meilleureEnergie = energieSuiv;
					probleme.sauvegarderSolution();
				}
				this.energiePrec = energieSuiv;
			}
		}
		
		return probleme;
	}
	
	/*
	public Probleme lancer(Probleme probleme, ListEnergie listEnergie){
		
		init();
		
		this.energiePrec = probleme.calculerEnergie() ;
		this.listEnergie.augmenteTaille(); // on incremente le nombre d'iterations
		this.meilleureEnergie = this.energiePrec ;
		double energieSuiv = 0 ;
		double proba = 1;
		
		while(incrT() && this.meilleureEnergie!=0){
			
			this.listEnergie.add(this.meilleureEnergie);  // choix arbitraire entre meilleure énergie et énergie actuelle
			//this.listEnergie.add(this.energiePrec);		// choix de l'énergie actuelle pour le calcul éventuel de k
			
			//probleme.calculerEnergie(); // pour mettre a jour coloriage.nombreNoeudsConflit
			probleme.modifElem();	// faire une mutation
			
			energieSuiv = probleme.calculerEnergie(); // calculer son ï¿½nergie
			this.listEnergie.addTotal(this.energiePrec);
			//System.out.println("energie courante : " + energieSuiv);
			calculerK();
			
			this.listEnergie.augmenteTaille();// on incremente le nombre d'iterations
			
			proba = Math.exp(-(energieSuiv-this.energiePrec)/(this.k*this.T));
			System.out.println(proba);
			
			// Ajustement de la liste de taille tailleFenetre générant une moyenne glissante de probas
			this.listProba.addTotal(proba);
			calculerProbaMoyenne(proba);
			this.listProba.add(this.probaMoyenne);
			
			
			
			if( energieSuiv > this.energiePrec && (proba < probleme.gen.nextDouble())){ 	
				probleme.annulerModif();	// cas oï¿½ la mutation est refusï¿½e
			}
			else {
				if( energieSuiv < this.meilleureEnergie ){	// cas oï¿½ avec une meilleure ï¿½nergie globale 
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
	}*/
	
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
	
	public IListEnergie getListProba() {
		return listProba;
	}
	
	public void setListProba(IListEnergie listProbas) {
		this.listProba = listProbas;
	}
	
	public void calculerProbaMoyenne(double proba){
		int taille = this.getListProba().getTaille();
		List<Double> list = this.getListProba().getlistEnergieTotale();
		int tailleL = list.size();
		int fenetreK = this.getListProba().getFenetreK();
		//System.out.println(list);
		if (proba>1) {
			proba = 1;
		} else {}
		
		if (taille == 1){
			this.probaMoyenne = proba;
		} else if (taille <= fenetreK ){
			this.probaMoyenne = (this.probaMoyenne*(tailleL-1)+ proba) /tailleL;  // moyenne des probas
		} else{
			// Moyenne des probas
			this.probaMoyenne = (this.probaMoyenne*fenetreK - list.get(0) + proba )/ fenetreK;
		}
		System.out.println("Proba Moyenne : " + this.probaMoyenne);
	}
	
	
}
