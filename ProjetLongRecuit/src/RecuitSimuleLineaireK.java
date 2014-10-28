// Sous-classe de RecuitSimuleLineaire implementant un k variable


public class RecuitSimuleLineaireK extends RecuitSimuleLineaire {

	double kConstant;
	
	public RecuitSimuleLineaireK(double k, double Tdeb, double Tfin, double pas, int N, IListEnergie listEnergie) {
		super(k, Tdeb, Tfin, pas, N, listEnergie);
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
	
	public String toString(){
		return "Recuit Simulé Linéaire avec k l'énergie moyenne";
	}
	
}
