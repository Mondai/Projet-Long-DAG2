public class RecuitSimuleExponentielPalier extends RecuitSimule{
	
	// d�croissance de T entre Tdeb et Tfin avec un pas lin�aire et reste N it�rations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;			
	int compteur; 	//compteur sur N
	double facteur;
	double palierMin;	// nombre d'it�ration par palier variant entre min et max.
	double palierMax;
	double palierPas;
	double palier;
	
	public RecuitSimuleExponentielPalier(double k, double Tdeb, double Tfin, double facteur, double palierMin, double palierMax, double palierPas,IListEnergie listEnergie){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;		
		this.compteur = 1;
		this.facteur = facteur;//Multiplication de la temp�rature par facteur(inf�rieur � 1)
		this.palierMin = palierMin;
		this.palierMax = palierMax;
		this.palier = palierMin;
		this.palierPas = palierPas;
		this.listEnergie= listEnergie;
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	public void init(){
		this.T = this.Tdeb ;
		this.compteur = 1 ;
		this.palier = this.palierMin;
		this.listEnergie.init();
	}
	
	public boolean incrT(){
		// exponentiel
		if(this.compteur==(int)this.palier){
			this.T = (this.T-this.Tfin)*this.facteur + this.Tfin;
			this.compteur = 1;
			this.palier += this.palierPas;
		}
		else{
			this.compteur++;
		}
		// condition d'arr�t: T<Tfin ou T<0 ou (palier depasse palierMax).
		if( this.T<this.Tfin || this.T<0 || (this.palierMax-this.palierMin)*(this.palierMax-this.palier)<0){
			return false;
		}
		else{
			return true;
		}
	}
}
