
public class RecuitSimuleExponentiel extends RecuitSimule{
	
	// décroissance de T entre Tdeb et Tfin avec un pas linéaire et reste N itérations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;
	int N;			// nombre d'itération par palier.
	int compteur; 	//compteur sur N
	double facteur;
	int nbPoints;
	
	public RecuitSimuleExponentiel(double k, double Tdeb, double Tfin, double facteur, int N, int nbPoints){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;		
		this.N = N;
		this.compteur = 1;
		this.facteur = facteur;//Multiplication de la température par facteur(inférieur à 1)
		this.nbPoints = nbPoints;
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	public void initT(){
		// Ne fait rien, T = Tdeb
	}
	
	public boolean incrT(){
		this.nbPoints--;
		// exponentiel
		if(this.compteur==N){
			this.T = (this.T-this.Tfin)*this.facteur + this.Tfin;
			this.compteur = 1;
		}
		else{
			this.compteur++;
		}
		// condition d'arrêt: T<Tfin ou T<0.
		if( this.T<this.Tfin || this.nbPoints == 0){
			return false;
		}
		else{
			return true;
		}
	}
}
