package solverSimuleParametrable;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import solver.GrapheColorie;
import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


public class RecuitQuantiqueParametrable extends RecuitSimuleP { 				
																		// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Temperature Gamma;
	public ConstanteK K;
	public double meilleureEnergie = Double.MAX_VALUE;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double temperature;
	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration
	public int palier;
	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitQuantiqueParametrable(Temperature Gamma, ConstanteK K, int palier, double temperature) {
		this.Gamma=Gamma;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.Gamma.nbIteration;						// ainsi on combine le tout facilement
		this.palier = palier;		
		this.temperature = temperature;						//en quantique, la temp�rature est constante et le Gamma est variable, d'o� le fait que Gamma soit une "temp�rature"
	}

	public Probleme lancer(Probleme probleme) {

		// TODO methode init()
		// init();
		
		/*toujours a implementer :
		 * Gamma variable et initialisation du gamma (peut-�tre changer les classes Temperature � un nom plus neutre) //fait
		 * Implementation d'une liste circulaire d'�tats // ou plutot d'un traitement circulaire au niveau de Ec (s�lection de next et previous faite ---)
		 * Implementation d'un shuffle des etats //fait
		 * methodes de calcul de Ec //fait
		 * Implementation des classes couleurs //fait
		 * Enlever les variables de spin???		
		*/
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
			//System.out.println("Energie cin�tique : " + Jr); //TEST
			
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
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
					

					//diff�rences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
					
					//K.calculerK(deltaE);
				
					if (this.meilleureEnergie == 0) break;
					
					if( deltaE <= 0 || deltaEp <= 0){
						
						// System.out.println("acceptee"); //TEST
						probleme.modifElem(etat, mutation);				// faire la mutation
						double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
						if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
							this.meilleureEnergie = EpActuelle;
							// TEST
							System.out.print("etat "+p+" : ME = "+this.meilleureEnergie+" , G = "+this.Gamma.t+" , Jr = "+Jr+" .  Ep = [");
							System.out.print(probleme.etats[0].Ep.calculer(probleme.etats[0]));
							for(int w = 1 ; w < nombreRepliques ; w++){
								System.out.print(","+probleme.etats[w].Ep.calculer(probleme.etats[w]));
							}
							System.out.println("]");
							// TEST
						}
					} else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						//System.out.println("Proba : " + proba); //TEST
						if (proba >= probleme.gen.nextDouble()) {							
							probleme.modifElem(etat, mutation);  		// accepter la mutation 
						}
					}
				}
				// TESTTEST
				System.out.print("etat "+p+" : ME = "+this.meilleureEnergie+" , G = "+this.Gamma.t+ " , Jr = "+Jr +" .  Ep = [");
				System.out.print(probleme.etats[0].Ep.calculer(probleme.etats[0]));
				for(int w = 1 ; w < nombreRepliques ; w++){
					System.out.print(","+probleme.etats[w].Ep.calculer(probleme.etats[w]));
				}
				System.out.println("]");
				// TEST
				//System.out.println("Fin " + i + " : etat "+etat.toString()); //TEST	
			}
		}
		
		return probleme;
	}
}
