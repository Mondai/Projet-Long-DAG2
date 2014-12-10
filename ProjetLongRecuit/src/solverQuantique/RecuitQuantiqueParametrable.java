package solverQuantique;



/* A LIRE AVANT TOUT :
*SVP NE PAS MODIFIER CETTE CLASSE ! clonez la et donnez un nom de genre " RecuitQuantiqueParametrableV2 "
* MERCI
*/




public class RecuitQuantiqueParametrable extends ProblemeQuantique { 				

	public Temperature T;  
	public ConstanteK K;
	public ParticuleQuantique particuleQuantique;
	public int nbReplique ;
	public int nbMaxIteration;
	public MutationElementaire mutation ;

	/*
	 * creez vos propres Temperature, ConstanteK, ParticuleQuantiques et MutationElementaire ( ce sont des classes abstraites )
	 */

	public RecuitQuantiqueParametrable(Temperature T, ConstanteK K, ParticuleQuantique particuleQuantique, MutationElementaire mutation) {
		this.T=T;												
		this.nbMaxIteration=this.T.nbIteration;						
		this.particuleQuantique=particuleQuantique;
		this.nbReplique=this.particuleQuantique.nbReplique ;
		this.mutation=mutation;
	}

	public void initialiser(boolean choix) {
		/* initialiser les energies pour la particule ( soit random, soit utiliser un resultat precedent, ie  )
		 * a coder selon le choix
		 */

	}

	public ParticuleQuantique lancer() {

		double deltaEc  ;
		double deltaEp ;
		double deltaE  ;
		double proba ; 

		while(T.modifierT() //&& pas de Ep à 0 
				){

			for ( int numeroRepliqueModifiee = 1; (numeroRepliqueModifiee<=this.nbReplique); numeroRepliqueModifiee++) {


				// A CODER : trouver une mutation possible sur numeroRepliqueModifiee

				deltaEc = this.particuleQuantique.energieTotale.calculerDeltaE(this.particuleQuantique, numeroRepliqueModifiee,  mutation);	// calculer deltaE si la mutation etait acceptee
				deltaEp = this.particuleQuantique.energieTotale.calculerDeltaE(this.particuleQuantique, numeroRepliqueModifiee,  mutation);	// calculer deltaE si la mutation etait acceptee
				deltaE = deltaEp+deltaEc ;
				K.calculerK(deltaE);
				proba = Math.exp(-deltaE / (this.K.k * this.T.t));	// calcul de la proba
				

				if( deltaE <= 0 || proba>= Math.random()){  			// ah les trucs random high quality a rajouter
					// A CODER, effectuer la mutation dans la representation de l'etat 				
					this.particuleQuantique.representationsEtat[numeroRepliqueModifiee].actualEp += deltaEp;
					this.particuleQuantique.actualEc += deltaEc;
					
					if( this.particuleQuantique.representationsEtat[numeroRepliqueModifiee].actualEp < 			// si l'Ep est la meilleure, on conserve
							this.particuleQuantique.representationsEtat[numeroRepliqueModifiee].bestEp ){	
							this.particuleQuantique.representationsEtat[numeroRepliqueModifiee].bestEp =
								this.particuleQuantique.representationsEtat[numeroRepliqueModifiee].actualEp;
						}
				}
			}
		}
		return this.particuleQuantique;
	}
	/*
	 *  pour la condition de la boucle while :
	 *  modifierT change la temperature et renvoie un bool qui indique si on arrive a la temperature finale
	 *  on verifie aussi que la solution n'est pas trouvee (ie il y a une Ep a 0), a ecrire
	 * 
	 * mutation pas encore codee pour quantique
	 * 
	 */

}
