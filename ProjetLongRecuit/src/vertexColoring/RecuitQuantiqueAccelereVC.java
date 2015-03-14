package vertexColoring;



import java.util.ArrayList;
import java.util.Collections;

import solver.commun.Etat;
import solver.commun.Expo;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;
import solver.quantique.RecuitQuantiqueAccelere;

/**
 * {@inheritDoc}
 * <p>
 * On utilise ici de plus certaines propriétés propres au coloriage de graphe, pour créer des tableaux pour garder
 * les exponentielles calculées en mémoire. Cependant, après amélioration du calcul de l'exponentiel, l'amélioration donnée par cette classe
 * est devenu moindre si ce n'est négatif.
 */
public class RecuitQuantiqueAccelereVC extends RecuitQuantiqueAccelere { 				

	public RecuitQuantiqueAccelereVC(Fonction Gamma, ConstanteK K, int palier, double temperature) {
		super(Gamma, K, palier, temperature);
	}
	
	/**
	 * {@inheritDoc}
	 * <p>
	 * De plus, ici on garde les exponentielles en mémoire dans des tableaux à taille prédéterminée théoriquement.
	 * Il y a un tableau pour les énergies potentielles crée au début du recuit, et un tableau pour les énergies cinétiques
	 * mis à jour à chaque fois que JGamma change.
	 */
	public void lancer(Probleme probleme) {

		this.init();
		
		double mutationsTentees = 0;
		double mutationsAccepteesUB = 0;
		double mutationsAcceptees = 0;

		
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
		

		//pas convenable avec la généralité du solver 
		//permet de ne pas avoir à recalculer l'exponentiel de l'énergie potentielle à chaque fois
		int adjacenceMax = ((GrapheColorieParticule) probleme).getAdjacenceMax() ;
		double[] termeExpPotentiel = new double[2*adjacenceMax+1]; //tableau listant tous les exp(deltaEp/T)
		int k = 0;
		for (double i = -adjacenceMax; i <= adjacenceMax; i++){
			termeExpPotentiel[k] = Expo.expf(i/nombreRepliques/this.temperature);
			k++;
		}

		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}
		
		while(Gamma.modifierT() && this.meilleureEnergie!=0){

			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
			int v = ((GrapheColorie) ((GrapheColorieParticule) probleme).etats[0]).getCouleurs().length;
			double[] termeExpCinetique = new double[8*(v-1)+1]; //tableau listant tous les exp(deltaEp/T)
			k = 0;
			for (double i = -4*(v-1); i <= 4*(v-1); i++){
				termeExpCinetique[k] = Expo.expf(i*Jr/this.temperature);
				k++;
			}

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
					
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEcUB = probleme.calculerDeltaEcUB(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
					//System.out.println("UB : " + deltaEcUB)	;
					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEcUB*Jr;
					//K.calculerK(deltaE);
					/*System.out.println("deltaEp " + deltaEp);
					System.out.println("k " + (deltaEp+adjacenceMax));
					System.out.println("proba deltaEp " + Math.exp(((float) deltaEp)/nombreRepliques / (this.K.k * this.temperature)));
					System.out.println("Tableau " + termeExpPotentiel[deltaEp+adjacenceMax]);*/
	
					
					if(deltaEp <= 0){
						//deltaE ici n'est pas le bon, il dépend de EcUB
						mutationsAcceptees++;
						probleme.modifElem(etat, mutation);				// faire la mutation
						double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
						
						if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
							this.meilleureEnergie = EpActuelle;
							//System.out.println("ME = "+this.meilleureEnergie);
							if (this.meilleureEnergie == 0){	// fin du programme
								//System.out.println("Mutations tentées : " + mutationsTentees);
								//System.out.println("Mutations acceptées UB : " + mutationsAccepteesUB);
								//System.out.println("Mutations acceptées : " + mutationsAcceptees);
								return; 
							}
						}
					}
					else {
						//long startTime = System.nanoTime();
						//proba = Math.exp(-deltaE / (this.K.k * this.temperature));
						proba = termeExpCinetique[(int) (deltaEcUB+4*(v-1))]/termeExpPotentiel[(int) (deltaEp+adjacenceMax)];
						//proba = Math.exp(deltaEcUB*Jr / (this.K.k * this.temperature))/termeExpPotentiel[(int) (deltaEp+adjacenceMax)];	// calcul de la proba
						/*long endTime = System.nanoTime();
						System.out.println("duree = "+(endTime-startTime)+" ns");
						System.out.println("deltaE " + deltaE);*/
						
						if (proba >= probleme.gen.nextDouble()) {	
							mutationsAccepteesUB++;

							double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);
							deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
							
							if( deltaE <= 0){
								
								mutationsAcceptees++;
								probleme.modifElem(etat, mutation);				// faire la mutation
								double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
								
								if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
									this.meilleureEnergie = EpActuelle;
									// System.out.println("ME = "+this.meilleureEnergie); //TEST
									if (this.meilleureEnergie == 0){	// fin du programme
										//System.out.println("Mutations tentées : " + mutationsTentees);
										//System.out.println("Mutations acceptées UB : " + mutationsAccepteesUB);
										//System.out.println("Mutations acceptées : " + mutationsAcceptees);
										return;
									}
								}
							}
							else{
								//proba = Math.exp(-deltaE / (this.K.k * this.temperature));
								proba = termeExpCinetique[(int) (deltaEc+4*(v-1))]/termeExpPotentiel[(int) (deltaEp+adjacenceMax)];
								//proba = Math.exp(deltaEc*Jr / (this.K.k * this.temperature))/termeExpPotentiel[(int) (deltaEp+adjacenceMax)];	// calcul de la proba
							
							
								if (proba >= probleme.gen.nextDouble()) {
									mutationsAcceptees++;
									probleme.modifElem(etat, mutation);  		// accepter la mutation 
								}
							}

						}
					}
				}

			}
		}
		
		
		//System.out.println("Mutations tentées : " + mutationsTentees);
		//System.out.println("Mutations acceptées UB : " + mutationsAccepteesUB);
		//System.out.println("Mutations acceptées : " + mutationsAcceptees);
		return;
	}
}


