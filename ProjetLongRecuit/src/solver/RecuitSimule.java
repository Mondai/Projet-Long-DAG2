package solver;

import java.util.List;

import solverCommun.Etat;
import solverCommun.IRecuit;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;

public abstract class RecuitSimule implements IRecuit{
	
	// param�tres
	double T;
	double k;
	double probaMoyenne; //m�me principe que le k, afin de lisser la courbe des probas
	protected double meilleureEnergie;
	double energiePrec;
	private int nbPoints; //nombre d'it�rations au total ( changement de palier ET it�ration sur palier )
	
	// m�thodes abstraites 
	abstract void calculerK();	// recalculer k � chaque it�ration si besoin 
	
	abstract void init();		// initialisation
	abstract boolean incrT();	// incr�mentation de T � chaque it�ration, return false si condition d'arret atteinte 
	
	
	public RecuitSimule(){		// constructeur
	}
	
	
	
	public Probleme lancer(Probleme probleme){
		
		init();
		
		Etat etat = probleme.etats.get(0);
		this.energiePrec = probleme.calculerEnergie() ;
		this.meilleureEnergie = this.energiePrec ;
		double proba = 1;
		
		while(incrT() && this.meilleureEnergie!=0){
			
			MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
			
			double deltaE = probleme.calculerDeltaE(etat, mutation);	// calculer deltaE si la mutation etait acceptee
			
			calculerK();
			
			if( deltaE <= 0){
				probleme.modifElem(etat, mutation);				// faire la mutation
				this.energiePrec += deltaE;						// mettre a jour l'energie
				if( this.energiePrec < this.meilleureEnergie ){	// mettre a jour la meilleur energie
					this.meilleureEnergie = this.energiePrec;
				}
			} else {
				proba = Math.exp(-deltaE / (this.k * this.T));	// calcul de la proba
				if (proba >= probleme.gen.nextDouble()) {
					probleme.modifElem(etat, mutation);  		// accepter la mutation 
					this.energiePrec += deltaE;					// mettre a jour l'energie
				}
			}
		}
		return probleme;
	}
	
	
	public Probleme lancer(Probleme probleme, ListEnergie listEnergie, ListEnergie listProba){
		
		init();
		
		Etat etat = probleme.etats.get(0);
		this.energiePrec = probleme.calculerEnergie() ;
		listEnergie.augmenteTaille(); // on incremente le nombre d'iterations
		this.meilleureEnergie = this.energiePrec ;
		double proba = 1;
		
		while(incrT() && this.meilleureEnergie!=0){
			
			listEnergie.add(this.meilleureEnergie);  // choix arbitraire entre meilleure �nergie et �nergie actuelle
			//this.listEnergie.add(this.energiePrec);		// choix de l'�nergie actuelle pour le calcul �ventuel de k
			
			//probleme.calculerEnergie(); // pour mettre a jour coloriage.nombreNoeudsConflit
			MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
			
			double deltaE = probleme.calculerDeltaE(etat, mutation);	// calculer deltaE si la mutation etait acceptee
			listEnergie.addTotal(this.energiePrec+deltaE);
			//System.out.println("energie courante : " + energieSuiv);
			calculerK();
			
			listEnergie.augmenteTaille();// on incremente le nombre d'iterations
			
			proba = Math.exp(-deltaE/(this.k*this.T));
			
			// Ajustement de la liste de taille tailleFenetre g�n�rant une moyenne glissante de probas
			listProba.addTotal(proba);
			calculerProbaMoyenne(proba, listProba);
			listProba.add(this.probaMoyenne);

			if( deltaE <= 0){
				probleme.modifElem(etat, mutation);				// faire la mutation
				this.energiePrec += deltaE;						// mettre a jour l'energie
				if( this.energiePrec < this.meilleureEnergie ){	// mettre a jour la meilleur energie
					this.meilleureEnergie = this.energiePrec;
				}
			} else {
				proba = Math.exp(-deltaE / (this.k * this.T));	// calcul de la proba
				if (proba >= probleme.gen.nextDouble()) {
					probleme.modifElem(etat, mutation);  		// accepter la mutation 
					this.energiePrec += deltaE;					// mettre a jour l'energie
				}
			}
		}
		// V�rification de l'atteinte d'�nergie nulle � la fin
		listEnergie.z=listEnergie.echantillonage;
		listEnergie.add(this.meilleureEnergie);
		
		
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
	
	public void calculerProbaMoyenne(double proba, ListEnergie listProba){
		int taille = listProba.getTaille();
		List<Double> list = listProba.getlistEnergieTotale();
		int tailleL = list.size();
		int fenetreK = listProba.getFenetreK();
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
		//System.out.println("Proba Moyenne : " + this.probaMoyenne);
	}
	
	
}
