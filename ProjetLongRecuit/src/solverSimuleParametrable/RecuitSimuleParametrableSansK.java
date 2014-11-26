package solverSimuleParametrable;



import solverCommun.Probleme;


public class RecuitSimuleParametrableSansK extends RecuitSimuleP { 				// pas touche à cette classe !!!
																		// creer vos propres Temperature
	public Temperature T;
	public double bestEnergy;											// en soit, nos energies pourraient etre des Int, mais bon 
	public double previousEnergy;										// probleme nous renvoie des doubles
	public double actualEnergy ; 
	
	public int nbMaxIteration; 							// nombre maximale d'iteration si la solution n'est pas trouvee, redondance avec t.nbIteration

	// abstract void init(); 								// initialisation // mais de quoi ?

	public RecuitSimuleParametrableSansK(Temperature T) {
		this.T=T;												// contructeur : on lui donne la facon de calculer l'energie, K et tout le blabla
																// en creant une classe dedie et reutilisable qui extends temperature
		this.nbMaxIteration=this.T.nbIteration;						// ainsi on combine le tout facilement
	}

	public Probleme lancer(Probleme probleme) {

		// init();

		this.previousEnergy = probleme.calculerEnergie(); 							
		this.bestEnergy = this.previousEnergy;
		double deltaEnergy ; 									// une varaible utile dans la bouble, modifiee a chaque bouble 
		// double proba;
		while (T.modifierT() && this.bestEnergy != 0) {
			
			probleme.modifElem();								 // faire une mutation
			actualEnergy = probleme.calculerEnergie(); 			// calculer sa nouvelle energie
			deltaEnergy = this.actualEnergy-this.previousEnergy;
			
			// proba = Math.exp(-deltaEnergy / (this.K.k * this.T.t));
			// c'est l'expression de la proba
			
			if (deltaEnergy>0) 									//  ATTENTION GROSSE CONDITION : si l'energie n'est pas meilleure	
				{ 						
					if ((Math.exp(-deltaEnergy/ this.T.t))< probleme.gen.nextDouble()) 			
						{ probleme.annulerModif(); } 						// cas ou la mutation est refusee
			} 
			else 	{											// dans ce cas, on garde la modif, car energie meilleure ou proba acceptee
				if (this.actualEnergy < this.bestEnergy) 		 //  si on vient de trouver une energie meilleure que la best
					{ probleme.sauvegarderSolution(); 			// on sauvegarde la configuration et l'energie minale
					this.bestEnergy=this.actualEnergy;}			
				this.previousEnergy = this.actualEnergy;		// puis comme y a eu une modif, on change la previousEnergy
			}
			
			// System.out.println(proba);       				// si besoin de debug
		}

		return probleme;
	}

}


/* ici, on n'utilise pas de constanteK, et on optimise donc un peu le code :)
  
  */
