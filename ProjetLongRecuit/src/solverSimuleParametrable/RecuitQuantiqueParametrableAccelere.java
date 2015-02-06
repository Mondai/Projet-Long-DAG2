package solverSimuleParametrable;



import java.util.ArrayList;
import java.util.Collections;

import solver.GrapheColorieParticule;
import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


public class RecuitQuantiqueParametrableAccelere  { 				
																		// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Temperature Gamma;
	public ConstanteK K;
	public double meilleureEnergie = Double.MAX_VALUE;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double temperature;
	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration
	public int palier;
	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitQuantiqueParametrableAccelere(Temperature Gamma, ConstanteK K, int palier, double temperature) {
		this.Gamma=Gamma;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.Gamma.nbIteration;						// ainsi on combine le tout facilement
		this.palier = palier;		
		this.temperature = temperature;						//en quantique, la température est constante et le Gamma est variable, d'où le fait que Gamma soit une "température"
	}

	public double lancer(Probleme probleme) {

		// TODO methode init()
		// init();
		
		/*toujours a implementer :
		 * (peut-être changer les classes Temperature à un nom plus neutre)
		 * Enlever les variables de spin???		
		*/
		
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
		/*int adjacenceMax = ((GrapheColorieParticule) probleme).getAdjacenceMax() ;
		double[] termeExpPotentiel = new double[2*adjacenceMax+1]; //tableau listant tous les exp(deltaEp/T)
		int k = 0;
		for (int i = -adjacenceMax; i <= adjacenceMax; i++){
			termeExpPotentiel[k] = Math.exp(i/nombreRepliques/T);
			k++;
		}*/

		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}
		
		while(Gamma.modifierT() && this.meilleureEnergie!=0){

			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier

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
								return mutationsTentees ;
							}
						}
					}
					else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						
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
										return mutationsTentees ;
									}
								}
							}
							else{
								proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
							
							
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
		return mutationsTentees ;
	}
}
