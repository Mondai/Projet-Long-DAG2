package solver.simule;



import solver.commun.Etat;
import solver.commun.IRecuit;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;

/**
 * Classe implémentant le Recuit Simulé, qui prend en paramètres une température et un K modulables.
 * <p>
 * Le recuit est une variante de la descente par gradient.
 * La différence avec une variante stochastique normale est que la probabilité va changer au fur à mesure du temps,
 * forte au début et faible à la fin, en fonction de la température.
 */
public class RecuitSimule implements IRecuit { 				
	/**
	 * Fonction température modulable.
	 * @see Fonction
	 */
	public Fonction T;
	/**
	 * Constante k modulable.
	 * @see Constante
	 */
	public ConstanteK K;
	/**
	 * Meilleure énergie potentielle(la plus basse possible) atteinte par le recuit simulé.
	 */
	public double meilleureEnergie;		
	/**
	 * Energie précédente gardée en mémoire par le recuit.
	 */
	public double energiePrec;

	/**
	 * Nombre maximal d'itérations si la solution n'est pas trouvée, en redondance avec T.nbIteration
	 */
	public int nbMaxIteration; 							
	
	// abstract void init();

	/**
	 * On envoie les paramètres modulables.
	 * @param T
	 * Fonction température créée au préalable.
	 * @param K
	 * Constante k créée au préalable.
	 */
	public RecuitSimule(Fonction T, ConstanteK K) {
		this.T=T;
		this.K=K;
		this.nbMaxIteration=this.T.nbIteration;
	}

	/**
	 * Effectue le recuit simulé sur un état du problème(le premier état du problème).
	 * Le recuit va penser à une mutations possible au problème. Si elle est positive(diminue l'énergie potentielle) 
	 * alors on va l'effectuer, sinon on va l'effectuer avec une probabilité dépendante de la température, de la
	 * différence d'énergie potentielle de la mutation et de k.
	 * On réitère le processus jusqu'à avoir trouvé une réponse voulue ou un nombre d'itération maximale.
	 * <p>
	 * Pour ce qui est de l'utilisation de ce recuit, il faut créer une Fonction température, une Constante k et un Problème 
	 * au préalable. On initialise le recuit avec les deux premiers et on lance ensuite le recuit en lui envoyant le problème.
	 * A la fin de lancer, on peut obtenir les résultats sur la variable problème modifiée.
	 * @param problem
	 * Le problème sur lequel on veut effectuer le recuit.
	 */
	public void lancer(Probleme probleme) {
		this.lancer(probleme,0);
	}
	
	/**
	 * Effectue le recuit simulé sur un état du problème.
	 * Le recuit va penser à une mutations possible pour l'état en question. Si elle est positive(diminue l'énergie potentielle) 
	 * alors on va l'effectuer, sinon on va l'effectuer avec une probabilité dépendante de la température, de la
	 * différence d'énergie potentielle de la mutation et de k.
	 * On réitère le processus jusqu'à avoir trouvé une réponse voulue ou un nombre d'itération maximale.
	 * <p>
	 * Pour ce qui est de l'utilisation de ce recuit, il faut créer une Fonction température, une Constante k et un Problème 
	 * au préalable. On initialise le recuit avec les deux premiers et on lance ensuite le recuit en lui envoyant le problème.
	 * A la fin de lancer, on peut obtenir les résultats sur la variable problème modifiée.
	 * @param problem
	 * Le problème sur lequel on veut effectuer le recuit.
	 * @param i
	 * L'état du problème que l'on veut améliorer par recuit simulé. Si cet état n'existe pas, on effectue le recuit sur le 
	 * premier état du problème.
	 */
	public void lancer(Probleme probleme, int i) {

		// init();
		if (i >= probleme.etats.length) i = 0;
		Etat etat = probleme.etats[i];
		this.energiePrec = probleme.calculerEnergie() ;
		this.meilleureEnergie = this.energiePrec ;
		double proba = 1;
		
		while(T.modifierT() && this.meilleureEnergie!=0){
			
			MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
			
			double deltaE = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaE si la mutation etait acceptee
			
			K.calculerK(deltaE);
			
			if( deltaE <= 0){
				probleme.modifElem(etat, mutation);				// faire la mutation
				this.energiePrec += deltaE;						// mettre a jour l'energie
				if( this.energiePrec < this.meilleureEnergie ){	// mettre a jour la meilleur energie
					this.meilleureEnergie = this.energiePrec;
				}
			} else {
				proba = Math.exp(-deltaE / (this.K.k * this.T.t));	// calcul de la proba
				if (proba >= probleme.gen.nextDouble()) {
					probleme.modifElem(etat, mutation);  		// accepter la mutation 
					this.energiePrec += deltaE;					// mettre a jour l'energie
				}
			}
		}
		return;
	}
}
