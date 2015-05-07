package solver.simule;



import solver.commun.Etat;
import solver.commun.IRecuit;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;
import solver.quantique.MutableDouble;

/**
 * Classe impl�mentant le Recuit Simul�, qui prend en param�tres une temp�rature et un K modulables.
 * <p>
 * Le recuit est une variante de la descente par gradient.
 * La diff�rence avec une variante stochastique normale est que la probabilit� va changer au fur � mesure du temps,
 * forte au d�but et faible � la fin, en fonction de la temp�rature.
 */
public class RecuitSimule_Iter extends RecuitSimule { 				

	// abstract void init();

	/**
	 * On envoie les param�tres modulables.
	 * @param T
	 * Fonction temp�rature cr��e au pr�alable.
	 * @param K
	 * Constante k cr��e au pr�alable.
	 */
	public RecuitSimule_Iter(Fonction T, ConstanteK K) {
		super(T,K);
	}

	/**
	 * Effectue le recuit simul� sur un �tat du probl�me(le premier �tat du probl�me).
	 * Le recuit va penser � une mutations possible au probl�me. Si elle est positive(diminue l'�nergie potentielle) 
	 * alors on va l'effectuer, sinon on va l'effectuer avec une probabilit� d�pendante de la temp�rature, de la
	 * diff�rence d'�nergie potentielle de la mutation et de k.
	 * On r�it�re le processus jusqu'� avoir trouv� une r�ponse voulue ou un nombre d'it�ration maximale.
	 * <p>
	 * Pour ce qui est de l'utilisation de ce recuit, il faut cr�er une Fonction temp�rature, une Constante k et un Probl�me 
	 * au pr�alable. On initialise le recuit avec les deux premiers et on lance ensuite le recuit en lui envoyant le probl�me.
	 * A la fin de lancer, on peut obtenir les r�sultats sur la variable probl�me modifi�e.
	 * @param probleme
	 * Le probl�me sur lequel on veut effectuer le recuit.
	 */
	public void lancer(Probleme probleme, MutableDouble mTentees, MutableDouble mAcceptees) {
		this.lancer( probleme, mTentees, mAcceptees,0);
	}
	
	/**
	 * Effectue le recuit simul� sur un �tat du probl�me.
	 * Le recuit va penser � une mutations possible pour l'�tat en question. Si elle est positive(diminue l'�nergie potentielle) 
	 * alors on va l'effectuer, sinon on va l'effectuer avec une probabilit� d�pendante de la temp�rature, de la
	 * diff�rence d'�nergie potentielle de la mutation et de k.
	 * On r�it�re le processus jusqu'� avoir trouv� une r�ponse voulue ou un nombre d'it�ration maximale.
	 * <p>
	 * Pour ce qui est de l'utilisation de ce recuit, il faut cr�er une Fonction temp�rature, une Constante k et un Probl�me 
	 * au pr�alable. On initialise le recuit avec les deux premiers et on lance ensuite le recuit en lui envoyant le probl�me.
	 * A la fin de lancer, on peut obtenir les r�sultats sur la variable probl�me modifi�e.
	 * @param probleme
	 * Le probl�me sur lequel on veut effectuer le recuit.
	 * @param i
	 * L'�tat du probl�me que l'on veut am�liorer par recuit simul�. Si cet �tat n'existe pas, on effectue le recuit sur le 
	 * premier �tat du probl�me.
	 */
	public void lancer(Probleme probleme, MutableDouble mTentees, MutableDouble mAcceptees, int i) {

		double mutationsTentees = 0. ;
		double mutationsAcceptees = 0. ;
		
		// init();
		if (i >= probleme.etats.length) i = 0;
		Etat etat = probleme.etats[i];
		this.energiePrec = probleme.calculerEnergiePotentielle()  ;
		this.meilleureEnergie = this.energiePrec ;
		double proba = 1;
		double deltaE = 0;
		
		while(T.modifierT() && this.meilleureEnergie!=0){
			
			mutationsTentees++;
			
			MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
			
			deltaE = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaE si la mutation etait acceptee
			
			K.calculerK(deltaE);
			
			if( deltaE <= 0){
				mutationsAcceptees++;
				probleme.modifElem(etat, mutation);				// faire la mutation
				this.energiePrec += deltaE;						// mettre a jour l'energie
				if( this.energiePrec < this.meilleureEnergie ){	// mettre a jour la meilleur energie
					this.meilleureEnergie = this.energiePrec;
				}
			} else {
				proba = Math.exp(-deltaE / (this.K.k * this.T.t));	// calcul de la proba
				if (proba >= probleme.gen.nextDouble()) {
					mutationsAcceptees++;
					probleme.modifElem(etat, mutation);  		// accepter la mutation 
					this.energiePrec += deltaE;					// mettre a jour l'energie
				}
			}
		}
		
		mTentees.setValue(mutationsTentees);
		mAcceptees.setValue(mutationsAcceptees);
		
		return;
	}
}