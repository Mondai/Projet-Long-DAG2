
public class RecuitSimuleExponentielPalier extends RecuitSimule{
	
	// décroissance de T entre Tdeb et Tfin avec un pas linéaire et reste N itérations sur chaque palier. k est constant. 
	
	double Tdeb;
	double Tfin;			
	int compteur; 	//compteur sur N
	double facteur;
	double palierMin;	// nombre d'itération par palier variant entre min et max.
	double palierMax;
	double palierPas;
	double palier;
	
	public RecuitSimuleExponentielPalier(double k, double Tdeb, double Tfin, double facteur, double palierMin, double palierMax, double palierPas){
		this.k = k ;
		this.Tdeb = Tdeb ;
		this.T = Tdeb ;
		this.Tfin = Tfin ;		
		this.compteur = 1;
		this.facteur = facteur;//Multiplication de la température par facteur(inférieur à 1)
		this.palierMin = palierMin;
		this.palierMax = palierMax;
		this.palier = palierMin;
		this.palierPas = palierPas;
	}
	
	public void calculerK(){
		// Ne fait rien, k est constant
	}
	
	public void initT(){
		this.T = this.Tdeb ;
		this.compteur = 1 ;
		this.palier = this.palierMin;
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
		// condition d'arrêt: T<Tfin ou T<0 ou (palier depasse palierMax).
		if( this.T<this.Tfin || this.T<0 || (this.palierMax-this.palierMin)*(this.palierMax-this.palier)<0){
			return false;
		}
		else{
			return true;
		}
	}
}
