package solver.quantique;


import java.util.ArrayList;
import java.util.Collections;

import solver.commun.Etat;
import solver.commun.IRecuit;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;
import solver.parametres.ConstanteK;
import solver.parametres.Fonction;

/**
 * Classe implémentant le Recuit Quantique, qui prend en paramètres un Gamma et un K modulables ainsi qu'une température constante
 * et un nombre d'itérations consécutives sur un état.
 * <p>
 * Le recuit quantique est une variante du recuit simulé. Au lieu d'avoir une seule instance du problème, on en a ici plusieurs
 * qui peuvent intéragir entre elles.
 * On va parler d'une particule, qui disposera de plusieurs états. Ces états influenceront chacun deux autres états dans une chaîne
 * liée invariante. On modifiera les états dans un ordre aléatoire, chaque état subiera le même nombre d'essais de mutations.
 * 
 * @see RecuitSimule, Particule, Etat
 */
public class RecuitQuantique implements IRecuit { 				

	/**
	 * Fonction Gamma modulable. Représente l'effet tunnel.
	 * @see Fonction
	 */
	public Fonction Gamma;
	/**
	 * Constante k modulable.
	 * @see ConstanteK
	 */
	public ConstanteK K;
	/**
	 * Meilleure énergie(potentielle + cinétique) atteinte par le recuit simulé.
	 */
	public double meilleureEnergie = Double.MAX_VALUE;
	/**
	 * Température du recuit intervenant dans les calculs de probabilité, constante dans le recuit quantique.
	 */
	public double temperature;
	
	/**
	 * Nombre maximal d'itérations si la solution n'est pas trouvée, en redondance avec T.nbIteration
	 */
	public int nbMaxIteration;
	/**
	 * Nombre d'itérations consécutives sur un seul état.
	 */
	public int palier;
	
	public int mutationTentess;


	/**
	 * Réinitialise Gamma et K au début de lancer().
	 */
	protected void init(){
		this.Gamma.init();
		this.K.init();
		meilleureEnergie = Double.MAX_VALUE;
	}

	/**
	 * On envoie les paramètres modulables.
	 * @param Gamma
	 * Fonction Gamma effet tunnel créée au préalable.
	 * @param K
	 * Constante k créée au préalable.
	 * @param palier
	 * Nombre d'itérations consécutives sur un seul état.
	 * @param temperature
	 * Température constante du recuit quantique.
	 */
	public RecuitQuantique(Fonction Gamma, ConstanteK K, int palier, double temperature) {
		this.Gamma=Gamma;
		this.K=K;
		this.nbMaxIteration=this.Gamma.nbIteration;
		this.palier = palier;		
		this.temperature = temperature;
	}


	/**
	 * Effectue le recuit quantique sur le problème.
	 * Le recuit quantique va préparer les variables, puis circuler aléatoirement sur une chaîne liée invariante connectant les 
	 * divers états du problèmes.
	 * Ensuite il va penser à une mutations possible à l'état. Si elle est positive(diminue l'énergie potentielle) 
	 * alors on va l'effectuer, sinon on va l'effectuer avec une probabilité dépendante de la température, de la
	 * différence d'énergie potentielle de la mutation et de k.
	 * On réitère le processus un certain nombre de fois puis on change d'état.
	 * On réitère le tout jusqu'à avoir trouvé une réponse voulue ou un nombre d'itération maximale.
	 * <p>
	 * Pour ce qui est de l'utilisation de ce recuit, il faut créer une Fonction Gamma, une Constante k et un Problème 
	 * au préalable. On initialise le recuit avec les deux premiers ainsi qu'une température et un palier constant,
	 * et on lance ensuite le recuit en lui envoyant le problème.
	 * A la fin de lancer, on peut obtenir les résultats sur la variable problème modifiée.
	 * @param probleme
	 * Le problème sur lequel on veut effectuer le recuit quantique.
	 */
	public void lancer(Probleme probleme) {

		this.init();
		
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
			//System.out.println(energie);

		}
		
		//System.out.println("EC : " + probleme.Ec.calculer(probleme));

		double proba = 1;

		// tableau des indices des etats a parcourir dans un certain ordre
		ArrayList<Integer> indiceEtats = new ArrayList<Integer>(); 
		for( int i = 0; i < nombreRepliques ; i++){
			indiceEtats.add(i);
		}
		
		while(Gamma.modifierT() && this.meilleureEnergie!=0){
			
			Collections.shuffle(indiceEtats, probleme.gen);	// melanger l'ordre de parcours des indices
			double Jr = -this.temperature/2*Math.log(Math.tanh(this.Gamma.t/nombreRepliques/this.temperature));	// calcul de Jr pour ce palier
			
			//TODO
			int testEc = 0;
			int A = 0;
			double DE = 0;
			
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
				//System.out.println("palier : " + this.palier);
				
				for (int j = 0; j < this.palier; j++){

					MutationElementaire mutation = probleme.getMutationElementaire(etat);	// trouver une mutation possible
					mutationsTentees++; //permet d'avoir une référence indépendante pour les améliorations de l'algorithme, mais aussi sur son temps
					
					double deltaEp = probleme.calculerDeltaEp(etat, mutation);	// calculer deltaEp si la mutation etait acceptee
					double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
					
					//différences du hamiltonien total
					//multiplier deltaIEc par JGamma
					double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
					/*
					System.out.println("DEP : " + deltaEp);
					System.out.println("DEC : " + deltaEc);
					System.out.println("DE : " + deltaE);
					System.out.println("EP : " + probleme.calculerEnergiePotentielle());
					*/
					
					//TODO
					testEc += probleme.calculerDeltaEc(etat, previous, next, mutation);
					DE+=deltaE;
					
					//System.out.println("DeltaEc "+deltaEc*Jr);
					
					//K.calculerK(deltaE);
								
					if( deltaE <= 0 || deltaEp <= 0){
						
						//TODO
						A++;
						
						mutationsAcceptees++;
						probleme.modifElem(etat, mutation);				// faire la mutation
						double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
						if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
							this.meilleureEnergie = EpActuelle;
							System.out.println("meilleureEnergie = "+ this.meilleureEnergie);
							System.out.println("mutationsTentees = "+ mutationsTentees);
							if (this.meilleureEnergie == 0){	// fin du programme
								System.out.println("Mutations tentées : " + mutationsTentees);
								System.out.println("Mutations acceptées : " + mutationsAcceptees);
								this.mutationTentess=mutationsTentees;
								return;
							}
						}
					} else {
						proba = Math.exp(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
						if (proba >= probleme.gen.nextDouble()) {	
							mutationsAcceptees++;
							
							//TODO
							A++;
							
							probleme.modifElem(etat, mutation);  		// accepter la mutation 
						}
					}
				}
				

			}
			
			//TODO
			//System.out.println(testEc/((double)this.palier*nombreRepliques));
			//System.out.println("A : "+A);
			//System.out.println("DE : "+DE/((double)this.palier*nombreRepliques));
		}
		
		
		System.out.println("Mutations tentées : " + mutationsTentees);
		System.out.println("Mutations acceptées : " + mutationsAcceptees);
		this.mutationTentess=mutationsTentees;
		return;
	}

	public double getMeilleureEnergie() {
		return meilleureEnergie;
	}

	public int getMutationTentess() {
		return mutationTentess;
	}
	
	
	
	
	
}
