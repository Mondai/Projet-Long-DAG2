package solver;

public class RecuitSimuleLineaire extends RecuitSimule{
	
	// d�croissance de T entre Tdeb et Tfin avec un pas lin�aire et reste N it�rations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;
	double pas; 	// pas de l'incr�mentation de T
	int N;			// nombre d'it�ration par palier.
	int compteur; 	//compteur sur N
	
	
	public RecuitSimuleLineaire(double k, double Tdeb, double Tfin, double pas, int N){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;
		this.pas = pas;
		this.N = N;
		this.compteur = 1;
		this.setNbPoints((int) (N*((Tdeb-Tfin)/pas)));  // Nombre de changement de pallier
	}

	public RecuitSimuleLineaire() { // Pour cr�ation et ensuite seulement param�trisation
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	
	
	
	public void init(){
		this.T = this.Tdeb ;
		this.compteur = 1;
	}
	
	public boolean incrT(){
		if (this.T < this.Tfin || this.T<0 || this.probaMoyenne<this.probaMoyenneLimite) {
			return false;
		} else if(this.compteur == this.N) {
			this.T -= this.pas;
			this.compteur = 1;
			return true;
		}else{
			this.compteur++;
			return true;
		}
	}
	
	public String toString(){
		return "Recuit Simul� Lin�aire";
	}
}
