public class RecuitSimuleLineaire extends RecuitSimule{
	
	// d�croissance de T entre Tdeb et Tfin avec un pas lin�aire et reste N it�rations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;
	double pas; 	// pas de l'incr�mentation de T
	int N;			// nombre d'it�ration par palier.
	int compteur; 	//compteur sur N
	
	public RecuitSimuleLineaire(double k, double Tdeb, double Tfin, double pas, int N, IListEnergie listEnergie){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;
		this.pas = pas;
		this.N = N;
		this.compteur = 1;
		this.listEnergie= listEnergie;
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	public void init(){
		this.T = this.Tdeb ;
		this.compteur = 1;
		this.listEnergie.init();
		this.listEnergie.initTaille();
	}
	
	public boolean incrT(){
		// lin�aire
		if(this.compteur==N){
			this.T = this.T - pas;
			this.compteur = 1;
		}
		else{
			this.compteur++;
		}
		// condition d'arr�t: T<Tfin ou T<0.
		if( this.T<this.Tfin || this.T<0){
			return false;
		}
		else{
			return true;
		}
	}
}
