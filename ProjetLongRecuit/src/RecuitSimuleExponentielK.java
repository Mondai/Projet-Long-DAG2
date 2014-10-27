// Sous-classe de RecuitSimuleExponentiel implementant un k variable


public class RecuitSimuleExponentielK extends RecuitSimuleExponentiel {

	double kConstant;
	
	public RecuitSimuleExponentielK(double k, double Tdeb, double Tfin, double facteur, int N, int nbPoints, IListEnergie listEnergie) {
		super(k, Tdeb, Tfin, facteur, N, nbPoints, listEnergie);
		this.kConstant = k;
	}
	
	public void init(){
		super.init();
		this.k = this.kConstant;
	}
	
	public void calculerK(){
		int taille = this.listEnergie.getTaille();
		if (taille <= 10){
			this.k = (this.k*(taille-1)+this.energiePrec)/taille;  // moyenne des energies
		}
		else{
			this.k = (this.k*(10) - this.listEnergie.getlistEnergieTotale().get(0)
						+ this.energiePrec) / 10;
		}
	}
	
}