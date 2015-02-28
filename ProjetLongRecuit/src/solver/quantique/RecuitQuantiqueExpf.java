
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
	 * Dérivé du recuitQuantique utilisant expf au lieu de Mathexp pour accélérer
	 * 
	 * @see RecuitSimule, Particule, Etat
	 */
	public class RecuitQuantiqueExpf implements IRecuit { 				

		/**
		 * Fonction Gamma modulable. Représente l'effet tunnel.
		 * @see Fonction
		 */
		public Fonction Gamma;
		/**
		 * Constante k modulable.
		 * @see Constante
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
		public RecuitQuantiqueExpf(Fonction Gamma, ConstanteK K, int palier, double temperature) {
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
		 * @param problem
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
						double deltaEc = probleme.calculerDeltaEc(etat, previous, next, mutation);  // calculer deltaIEc si la mutation etait acceptee
							
						//différences du hamiltonien total
						//multiplier deltaIEc par JGamma
						double deltaE = deltaEp/nombreRepliques - deltaEc*Jr;
						
						//K.calculerK(deltaE);
									
						if( deltaE <= 0 || deltaEp <= 0){
							
							mutationsAcceptees++;
							probleme.modifElem(etat, mutation);				// faire la mutation
							double EpActuelle = etat.Ep.calculer(etat);		// energie potentielle temporelle
							if( EpActuelle < this.meilleureEnergie ){		// mettre a jour la meilleur energie
								this.meilleureEnergie = EpActuelle;
								if (this.meilleureEnergie == 0){	// fin du programme
									System.out.println("Mutations tentées : " + mutationsTentees);
									System.out.println("Mutations acceptées : " + mutationsAcceptees);
									return;
								}
							}
						} else {
							proba = expf(-deltaE / (this.K.k * this.temperature));	// calcul de la proba
							if (proba >= probleme.gen.nextDouble()) {	
								mutationsAcceptees++;
								probleme.modifElem(etat, mutation);  		// accepter la mutation 
							}
						}
					}
					

				}
			}
			
			
			System.out.println("Mutations tentées : " + mutationsTentees);
			System.out.println("Mutations acceptées : " + mutationsAcceptees);
			return;
		}
	

	
	
	public static double exp1(double x) {
		x = 1.0 + x / 256.0;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		x *= x;
		return x;
	}

	public static double exp2(double val) {
		final long tmp = (long) (1512775 * val + 1072632447);
		return Double.longBitsToDouble(tmp << 32);
	}
	
	public static double expf(double val) {
		if (val >= 0)
			return 1;
		if (val > -2.5)
			return exp1(val);
		if (val > -700)
			return exp2(val);
		else
			return 0;
	}
}
