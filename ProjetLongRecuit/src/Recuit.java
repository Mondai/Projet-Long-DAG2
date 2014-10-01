
/*
 * Variante de l'algorithme de recuit
 * On proc�de par palliers de temp�ratures ie on effectue un certain nombre (M) d'it�rations
 * � la m�me temp�rature avant de la modifier 
 */

public class Recuit {

	// Param�tres de l'algorithme de recuit
	int N; // nombre de palliers
	int M; // nombre d'it�rations � chaque pallier
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
		
		int compteurPallier = 0; // Compte le nombre d'it�rations effectu�es � un pallier de temp�rature
		int compteurDeltaE = 0;
		double probaAcceptation = 0.0;
		
		probleme.sauvegarderSolution();  // sauvegarder la première solution
		this.meilleureEnergie = probleme.calculerEnergie(); 
		
		// It�rations
		for(int j=1 ; j<=this.N*this.M ; j++) {
			// Mutation �l�mentaire
			probleme.modifElem();
			double nouvelleEnergie = probleme.calculerEnergie(); 
			deltaE = nouvelleEnergie-this.energie;
			
			if (deltaE>=0){
				// Mise � jour de k
				this.k = (this.W*this.k-this.tabDeltaE[compteurDeltaE]+deltaE)/((double)this.W) ;
				this.tabDeltaE[compteurDeltaE] = deltaE;
				compteurDeltaE = (compteurDeltaE + 1) % this.W;
				
				probaAcceptation = Math.exp(-(deltaE)/(this.k*this.T));

				
			}	
			
			// Examen de l'effet de la modification effectu�e
			if (deltaE>0 && (Math.random() > probaAcceptation)) {
				probleme.annulerModif();
			}		
			else { 
				this.energie = nouvelleEnergie;
				if(nouvelleEnergie < this.meilleureEnergie) {
					this.meilleureEnergie=nouvelleEnergie;
					probleme.sauvegarderSolution();
					if(nouvelleEnergie == 0){   // return si energie = 0
						return probleme;
					}
				} 
			}
			
			compteurPallier++;			
			
			
			// Mise � jour de T au bout de M it�rations sur le m�me pallier
			if (compteurPallier==this.M) {
			
			// Mise � jour de la temp�rature
			this.T= this.T * coefProba;
				
			// Remise � z�ro du compteur
			compteurPallier = 0;
			
			}
			
			
			// Impression de l'�nergie courante (commenter ou d�commenter)
				// System.out.println((double)((int)(this.energie*1000))/1000);
			
			energieMoy[j - 1] += this.energie;
			
		}
		
		// Retour de la solution
		return probleme; 
	
	}
}
