package solver;

import java.util.List;



public class RecuitSimuleExponentiel extends RecuitSimule{
	
	// dï¿½croissance de T entre Tdeb et Tfin avec un pas linï¿½aire et reste N itï¿½rations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;
	int N;			// nombre d'itï¿½ration par palier.
	int compteur; 	//compteur sur N
	double facteur;
	int nbPointsCourant;
	
	public RecuitSimuleExponentiel(double k, double Tdeb, double Tfin, double facteur, int N, int nbPoints){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;		
		this.N = N;
		this.compteur = 1;
		this.facteur = facteur;//Multiplication de la tempï¿½rature par facteur(infï¿½rieur ï¿½ 1)
		this.setNbPoints(nbPoints);
		this.nbPointsCourant = nbPoints;
	}
	
	public RecuitSimuleExponentiel() { // Pour création et ensuite seulement paramétrisation
	}

	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	

	
	
	public void init(){
		this.T = this.Tdeb ;
		this.compteur = 1 ;
		this.nbPointsCourant = this.getNbPoints();
	}
	
	public boolean incrT(){
		this.nbPointsCourant--;
		// exponentiel
	
		if(this.compteur==N){
			this.T = (this.T-this.Tfin)*this.facteur + this.Tfin;
			this.compteur = 1;
		}
		else{
			this.compteur++;
			
		}
		// condition d'arret: T<Tfin ou T<0.
		if( this.T < this.Tfin || this.nbPointsCourant == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public String toString(){
		return "Recuit Simulé Exponentiel";
	}
}
