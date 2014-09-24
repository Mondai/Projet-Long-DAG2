
/*
 * Variante de l'algorithme de recuit
 * On procède par parlliers de températures ie on effectue un certain nombre (M) d'itérations
 * à la même température avant de la modifier 
 */

public class Recuit {

	// Paramètres de l'algorithme de recuit
	int N; // nombre de palliers
	int M; // nombre d'itérations à chaque pallier
	int W;
	double T;
	double k;
	double[] tabDeltaE;
	double TFin;
	double TDeb;
	double probaDeb;
	double probaFin;
	double energie;
	double meilleureEnergie;
	double[] energieMoy;
	
	public Recuit(int N, int M) {
		
		this.probaDeb=0.3;
		this.probaFin=0.01;
		this.N=N;
		this.M=M;
		this.W=100;
		this.energie=0;
		this.tabDeltaE = new double[this.W];
		this.energieMoy = new double[N*M];
		
	}

	
	public Probleme iterer(Probleme probleme) {
		
		// Initialisation
		
		double deltaE = 0;
		int l=0;
		while (l<this.W){			
			probleme.modifElem();
			double nouvelleEnergie = probleme.calculerEnergie();
			double dE = nouvelleEnergie-this.energie;
			if (dE>0){
				deltaE=(l*deltaE+dE)/((double)(l+1.));
				this.tabDeltaE[l] = dE;
				l++;
			}			
		}	
			
		this.k = deltaE;
		this.TDeb = (-1/Math.log(this.probaDeb));
		this.T = this.TDeb;
		this.TFin = (-1/Math.log(this.probaFin));
		double coefProba = Math.pow((this.TFin/this.TDeb),(1./(double)(this.N-1)));
		
		int compteurPallier = 0; // Compte le nombre d'itérations effectuées à un pallier de température
		int compteurDeltaE = 0;
		double probaAcceptation = 0.0;
		
		// Itérations
		for(int j=1 ; j<=this.N*this.M ; j++) {
			// Mutation élémentaire
			probleme.modifElem();
			double nouvelleEnergie = probleme.calculerEnergie();
			deltaE = nouvelleEnergie-this.energie;
			
			if (deltaE>=0){
				// Mise à jour de k
				this.k = (this.W*this.k-this.tabDeltaE[compteurDeltaE]+deltaE)/((double)this.W) ;
				this.tabDeltaE[compteurDeltaE] = deltaE;
				compteurDeltaE = (compteurDeltaE + 1) % this.W;
				
				probaAcceptation = Math.exp(-(deltaE)/(this.k*this.T));

				
			}	
			
			// Examen de l'effet de la modification effectuée
			if (deltaE>0 && (Math.random() > probaAcceptation)) {
				probleme.annulerModif();
			}		
			else { 
				this.energie = nouvelleEnergie;
				if(nouvelleEnergie < this.meilleureEnergie) {
					this.meilleureEnergie=nouvelleEnergie;
					probleme.sauvegarderSolution();
				} 
			}
			
			compteurPallier++;			
			
			
			// Mise à jour de T au bout de M itérations sur le même pallier
			if (compteurPallier==this.M) {
			
			// Mise à jour de la température
			this.T= this.T * coefProba;
				
			// Remise à zéro du compteur
			compteurPallier = 0;
			
			}
			
			
			// Impression de l'énergie courante (commenter ou décommenter)
				// System.out.println((double)((int)(this.energie*1000))/1000);
			
			energieMoy[j - 1] += this.energie;
			
		}
		
		// Retour de la solution
		return probleme; 
	
	}
}
