package solver;

public class RecuitSimuleLineaire extends RecuitSimule{
	
	// dï¿½croissance de T entre Tdeb et Tfin avec un pas linï¿½aire et reste N itï¿½rations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;
	double pas; 	// pas de l'incrï¿½mentation de T
	int N;			// nombre d'itï¿½ration par palier.
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

	public RecuitSimuleLineaire() { // Pour création et ensuite seulement paramétrisation
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	
	
	
	public void init(){
		this.T = this.Tdeb ;
		this.compteur = 1;
	}
	
	public boolean incrT(){
		
		// linï¿½aire
		if(this.compteur==this.N){
			this.T = this.T - this.pas;
			this.compteur = 1;
			
		}
		else{
			this.compteur++;
			
		}
		// condition d'arrï¿½t: T<Tfin ou T<0.
		if( this.T<this.Tfin || this.T<0 || this.probaMoyenne<this.probaMoyenneLimite){
			//System.out.println("probaMoyenne "+ this.probaMoyenne);
			return false;
		}
		else{
			return true;
		}
	}
	
	public String toString(){
		return "Recuit Simulé Linéaire";
	}
}
