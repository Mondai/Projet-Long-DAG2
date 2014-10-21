import java.util.ArrayList;


public class RecuitSimuleLineaire extends RecuitSimule{
	
	// décroissance de T entre Tdeb et Tfin avec un pas linéaire et reste N itérations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;
	double pas; 	// pas de l'incrémentation de T
	int N;			// nombre d'itération par palier.
	int compteur; 	//compteur sur N
	
	public RecuitSimuleLineaire(double k, double Tdeb, double Tfin, double pas, int N,int echantillonage){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;
		this.pas = pas;
		this.N = N;
		this.compteur = 1;
		this.echantillonage=echantillonage;
		this.listEnergie= new ListEnergie();
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	public void initT(){
		this.T = this.Tdeb ;
		this.compteur = 1;
		this.listEnergie.init();
	}
	
	public boolean incrT(){
		// linéaire
		if(this.compteur==N){
			this.T = this.T - pas;
			this.compteur = 1;
		}
		else{
			this.compteur++;
		}
		// condition d'arrêt: T<Tfin ou T<0.
		if( this.T<this.Tfin || this.T<0){
			return false;
		}
		else{
			return true;
		}
	}
}
