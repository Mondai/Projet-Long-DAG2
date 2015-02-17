package solver.quantique;



import java.util.ArrayList;
import java.util.Collections;

import solver.commun.Etat;
import solver.commun.IRecuit;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;


public class RecuitQuantique implements IRecuit { 				
																		// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Fonction Gamma;
	public ConstanteK K;
	public double meilleureEnergie = Double.MAX_VALUE;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double temperature;
	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration
	public int palier;
	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitQuantique(Fonction Gamma, ConstanteK K, int palier, double temperature) {
		this.Gamma=Gamma;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.Gamma.nbIteration;						// ainsi on combine le tout facilement
		this.palier = palier;		
		this.temperature = temperature;						//en quantique, la température est constante et le Gamma est variable, d'où le fait que Gamma soit une "température"
	}

	public void lancer(Probleme probleme) {

		// TODO methode init()
		// init();
		
		/*toujours a implementer :
		 * (peut-être changer les classes Temperature à un nom plus neutre)
		 * Enlever les variables de spin???		
		*/
		
		int mutationsTentees = 0;
		int mutationsAcceptees = 0;
		
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
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
			//Jr = 0;
			//System.out.println("Energie cinétique : " + Jr); //TEST
			
		//	for (int i = 0; i < nombreRepliques; i++){
			for (Integer p : indiceEtats){	
				
				etat = probleme.etats[p];
				
				//System.out.println("Debut " + p + " : etat "+etat.toString());		//TEST	
				
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
					/*System.out.print("Ep = [" + probleme.etats[0].Ep.calculer(probleme.etats[0]));
					for(int w = 1 ; w < nombreRepliques ; w++){
						System.out.print(","+probleme.etats[w].Ep.calculer(probleme.etats[w]));
					}
					System.out.println("]");*/
					
					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					mutationsTentees++; //permet d'avoir une référence indépendante pour les améliorations de l'algorithme, mais aussi sur son temps
					
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
						
					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
					
					//K.calculerK(deltaE);
								
					if( deltaE <= 0 || deltaEp <= 0){
						
						// System.out.println("acceptee"); //TEST
						mutationsAcceptees++;
						probleme.modifElem(etat, mutation);				// faire la mutation
						double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
						if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
							this.meilleureEnergie = EpActuelle;
							// TEST
							/*
							System.out.print("etat "+p+" : ME = "+this.meilleureEnergie+" , G = "+this.Gamma.t+" , Jr = "+Jr+" .  Ep = [");
							System.out.print(probleme.etats[0].Ep.calculer(probleme.etats[0]));
							for(int w = 1 ; w < nombreRepliques ; w++){
								System.out.print(","+probleme.etats[w].Ep.calculer(probleme.etats[w]));
							}
							System.out.println("]");*/
							// TEST
							if (this.meilleureEnergie == 0){	// fin du programme
								System.out.println("Mutations tentées : " + mutationsTentees);
								System.out.println("Mutations acceptées : " + mutationsAcceptees);
								return;
							}
						}
					} else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						//System.out.println("Proba : " + proba); //TEST
						if (proba >= probleme.gen.nextDouble()) {	
							mutationsAcceptees++;
							probleme.modifElem(etat, mutation);  		// accepter la mutation 
						}
					}
				}
				// TESTTEST
				/*
				System.out.print("etat "+p+" : ME = "+this.meilleureEnergie+" , G = "+this.Gamma.t+ " , Jr = "+Jr +" .  Ep = [");
				System.out.print(probleme.etats[0].Ep.calculer(probleme.etats[0]));
				for(int w = 1 ; w < nombreRepliques ; w++){
					System.out.print(","+probleme.etats[w].Ep.calculer(probleme.etats[w]));
				}
				System.out.println("]");*/
				// TEST
				//System.out.println("Fin " + i + " : etat "+etat.toString()); //TEST	
				

			}
		}
		
		
		System.out.println("Mutations tentées : " + mutationsTentees);
		System.out.println("Mutations acceptées : " + mutationsAcceptees);
		return;
	}
}
