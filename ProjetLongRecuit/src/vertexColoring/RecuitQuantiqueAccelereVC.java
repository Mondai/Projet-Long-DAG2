package vertexColoring;



import java.util.ArrayList;
import java.util.Collections;

import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;


public class RecuitQuantiqueAccelereVC  { 				
																		// creer vos propres Temperature, ConstanteK et trucs pour les graphes
	public Fonction Gamma;
	public ConstanteK K;
	public double meilleureEnergie = Double.MAX_VALUE;									// en soit, nos energies pourraient etre des Int, mais bon 
	public double temperature;
	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration
	public int palier;
	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitQuantiqueAccelereVC(Fonction Gamma, ConstanteK K, int palier, double temperature) {
		this.Gamma=Gamma;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
		this.K=K;												// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.Gamma.nbIteration;						// ainsi on combine le tout facilement
		this.palier = palier;		
		this.temperature = temperature;						//en quantique, la temp�rature est constante et le Gamma est variable, d'o� le fait que Gamma soit une "temp�rature"
	}

	public double lancer(Probleme probleme) {

		// TODO methode init()
		// init();
		
		/*toujours a implementer :
		 * (peut-�tre changer les classes Temperature � un nom plus neutre)
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
		

		//pas convenable avec la g�n�ralit� du solver 
		//permet de ne pas avoir � recalculer l'exponentiel de l'�nergie potentielle � chaque fois
		int adjacenceMax = ((GrapheColorieParticule) probleme).getAdjacenceMax() ;
		double[] termeExpPotentiel = new double[2*adjacenceMax+1]; //tableau listant tous les exp(deltaEp/T)
		int k = 0;
		for (double i = -adjacenceMax; i <= adjacenceMax; i++){
			termeExpPotentiel[k] = exp(i/nombreRepliques/this.temperature);
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
				termeExpCinetique[k] = exp(i*Jr/this.temperature);
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
					mutationsTentees++; //permet d'avoir une r�f�rence ind�pendante pour les am�liorations de l'algorithme, mais aussi sur son temps
					
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEcUB = probleme.calculerDeltaEcUB(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
					//System.out.println("UB : " + deltaEcUB)	;
					//diff�rences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEcUB*Jr;
					//K.calculerK(deltaE);
					/*System.out.println("deltaEp " + deltaEp);
					System.out.println("k " + (deltaEp+adjacenceMax));
					System.out.println("proba deltaEp " + Math.exp(((float) deltaEp)/nombreRepliques / (this.K.k * this.temperature)));
					System.out.println("Tableau " + termeExpPotentiel[deltaEp+adjacenceMax]);*/
	
					
					if(deltaEp <= 0){
						//deltaE ici n'est pas le bon, il d�pend de EcUB
						mutationsAcceptees++;
						probleme.modifElem(etat, mutation);				// faire la mutation
						double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
						
						if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
							this.meilleureEnergie = EpActuelle;
							//System.out.println("ME = "+this.meilleureEnergie);
							if (this.meilleureEnergie == 0){	// fin du programme
								//System.out.println("Mutations tent�es : " + mutationsTentees);
								//System.out.println("Mutations accept�es UB : " + mutationsAccepteesUB);
								//System.out.println("Mutations accept�es : " + mutationsAcceptees);
								return mutationsTentees ;
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
										//System.out.println("Mutations tent�es : " + mutationsTentees);
										//System.out.println("Mutations accept�es UB : " + mutationsAccepteesUB);
										//System.out.println("Mutations accept�es : " + mutationsAcceptees);
										return mutationsTentees ;
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
		
		
		//System.out.println("Mutations tent�es : " + mutationsTentees);
		//System.out.println("Mutations accept�es UB : " + mutationsAccepteesUB);
		//System.out.println("Mutations accept�es : " + mutationsAcceptees);
		return mutationsTentees ;
	}
	
	double exp1(double x) {
		  x = 1.0 + x / 256.0;
		  x *= x; x *= x; x *= x; x *= x;
		  x *= x; x *= x; x *= x; x *= x;
		  return x;
	}
	
	public static double exp(double val) {
		final long tmp = (long) (1512775 * val + 1072632447);
		return Double.longBitsToDouble(tmp << 32);
	}
}


