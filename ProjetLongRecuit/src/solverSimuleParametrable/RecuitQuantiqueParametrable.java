package solverSimuleParametrable;



import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


public class RecuitQuantiqueParametrable extends RecuitSimuleP { 				// pas touche à cette classe !!!
																		// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Temperature T;
	public ConstanteK K;
	public double meilleureEnergie;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double energiePrec;										// probleme nous renvoie des doubles

	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration
	public int nombreRepliques;
	public int palier;
	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitQuantiqueParametrable(Temperature T, ConstanteK K, int nombreRepliques, int palier) {
		this.T=T;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.T.nbIteration;						// ainsi on combine le tout facilement
		this.nombreRepliques = nombreRepliques;
		this.palier = palier;
	}

	public Probleme lancer(Probleme probleme) {

		// TODO methode init()
		// init();
		
		/*toujours a implementer :
		 * Gamma variable et initialisation du gamma
		 * Implementation d'une liste circulaire d'états
		 * Implementation d'un shuffle des etats
		 * methodes de calcul de Ec
		 * Implementation des classes couleurs
		 * Enlever les variables de spin???		
		*/
		
		Etat etat = probleme.etats.get(0);
		this.energiePrec = probleme.calculerEnergie() ;
		this.meilleureEnergie = this.energiePrec ;
		double proba = 1;
		
		while(T.modifierT() && this.meilleureEnergie!=0){
			probleme.shuffleEtats();
			
			for (int i = 0; i < nombreRepliques; i++){
				etat = probleme.etats.get(i);
				
				for (int j = 0; j < palier; j++){
					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					double deltaE = probleme.calculerDeltaE(etat, mutation);	// calculer deltaE si la mutation etait acceptee
					
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
			}
		}
		
		return probleme;
	}
}
