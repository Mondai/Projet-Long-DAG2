package solverSimuleParametrable;



import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


public class RecuitSimuleParametrable extends RecuitSimuleP { 				// pas touche � cette classe !!!
																		// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Temperature T;
	public ConstanteK K;
	public double meilleureEnergie;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double energiePrec;										// probleme nous renvoie des doubles

	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration

	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitSimuleParametrable(Temperature T, ConstanteK K) {
		this.T=T;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.T.nbIteration;						// ainsi on combine le tout facilement
	}

	public Probleme lancer(Probleme probleme) {

		// TODO methode init()
		// init();
		
		Etat etat = probleme.etats.get(0);
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
		return probleme;
	}
}
