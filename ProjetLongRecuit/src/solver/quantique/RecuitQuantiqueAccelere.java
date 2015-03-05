package solver.quantique;



import java.util.ArrayList;
import java.util.Collections;

import solver.commun.Etat;
import solver.commun.Expo;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;

/**
 * {@inheritDoc}
 * <p>
 * On utilise ici la limite supérieure de la différence d'énergie cinétique locale pour réduire les temps de calcul.
 * Cependant, cela demande que le problème dispose d'une borne supériere d'énergie cinétique pour toute mutation possible effectuée.
 *
 */
public class RecuitQuantiqueAccelere extends RecuitQuantique{ 				

	public RecuitQuantiqueAccelere(Fonction Gamma, ConstanteK K, int palier, double temperature) {
		super(Gamma, K, palier, temperature);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Ici on utilise la limite supérieure de la différence d'énergie cinétique locale pour déterminer
	 * dans le cas où notre différence d'énergie potentielle est non négative, si la différence d'énergie totale
	 * liée à cette borne supérieure est négative, et sinon faire un premier calcul de probabilité.
	 * Cela va accélérer les calculs puisque la borne supérieure se calcule facilement, en temps constant.
	 * 
	 */
	public void lancer(Probleme probleme) {

		this.init();
		
		double mutationsTentees = 0;
		double mutationsAccepteesUB = 0;
		double mutationsAcceptees = 0;
		
		double Jr = 0;
		double deltaEp = 0;
		double deltaEcUB = 0;
		double deltaE = 0;
		double EpActuelle = 0;
		double deltaEc = 0;

		int nombreRepliques = probleme.etats.length;
		
		Etat etat = probleme.etats[0];
		Etat previous = probleme.etats[nombreRepliques-1];
		Etat next = probleme.etats[1];
		for (int i = 0; i < nombreRepliques; i++){	// initialisation de meilleureEnergie
			double energie = probleme.etats[i].Ep.calculer(probleme.etats[i]) ;
			if (energie < this.meilleureEnergie){
				this.meilleureEnergie = energie ;
			}

		}


		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}
		
		while(Gamma.modifierT() && this.meilleureEnergie!=0){

			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
			long startTime = System.nanoTime();
			
			for (Integer p : indiceEtats){	
				
				etat = probleme.etats[p];
				
				if(p == 0){
					previous = probleme.etats[nombreRepliques-1];
				}
				else{
					previous = probleme.etats[p-1];
				}
				
				if (p == nombreRepliques - 1){
					next = probleme.etats[0];
				}
				else{
					next = probleme.etats[p+1];
				}
				
				for (int j = 0; j < this.palier; j++){
					
					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					mutationsTentees++; //permet d'avoir une référence indépendante pour les améliorations de l'algorithme, mais aussi sur son temps
					
					deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					deltaEcUB = probleme.calculerDeltaEcUB(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					deltaE = deltaEp/nombreRepliques - deltaEcUB*Jr;
					//K.calculerK(deltaE);

					if(deltaEp <= 0){
						//deltaE ici n'est pas le bon, il dépend de EcUB
						mutationsAcceptees++;
						probleme.modifElem(etat, mutation);				// faire la mutation
						EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
						
						if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
							this.meilleureEnergie = EpActuelle;
							if (this.meilleureEnergie == 0){	// fin du programme
								System.out.print(mutationsTentees+" ");
								//System.out.println("Mutations tentées : " + mutationsTentees);
								//System.out.println("Mutations acceptées UB : " + mutationsAccepteesUB);
								//System.out.println("Mutations acceptées : " + mutationsAcceptees);
								return;
							}
						}
					}
					else {
						if (deltaE < 0) proba = 1;
						else proba = Expo.expf(-deltaE / (this.K.k * this.temperature));
						
						if (proba >= probleme.gen.nextDouble()) {	
							mutationsAccepteesUB++;

							deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);
							deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
							
							if( deltaE <= 0){
								
								mutationsAcceptees++;
								probleme.modifElem(etat, mutation);				// faire la mutation
								EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
								
							}
							else{
								proba = Expo.expf(-deltaE / (this.K.k * this.temperature));
							
								if (proba >= probleme.gen.nextDouble()) {
									mutationsAcceptees++;
									probleme.modifElem(etat, mutation);  		// accepter la mutation 
								}
							}

						}
					}
				}

			}
			long endTime = System.nanoTime();
			System.out.println("duree = "+(endTime-startTime)/1000000+" s");
		}
		
		System.out.print(mutationsTentees+" ");
		//System.out.println("Mutations tentées : " + mutationsTentees);
		//System.out.println("Mutations acceptées UB : " + mutationsAccepteesUB);
		//System.out.println("Mutations acceptées : " + mutationsAcceptees);
		return;
	}
	
}
