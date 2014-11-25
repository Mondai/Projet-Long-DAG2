package solver;

import java.util.List;
// Sous-classe de RecuitSimuleLineaire implementant un k variable


public class RecuitSimuleLineaireK extends RecuitSimuleLineaire {

	double kConstant;
	
	public RecuitSimuleLineaireK(double k, double Tdeb, double Tfin, double pas, int N) {
		super(k, Tdeb, Tfin, pas, N);
		this.kConstant = k;
	}
	
	public RecuitSimuleLineaireK() { // Pour création et ensuite seulement paramétrisation
	}
	
	public void init(){
		super.init();
		this.k = this.kConstant;
	}
	
	public void calculerK(){
		/*
		int taille = this.getListEnergie().getTaille();
		List<Double> list = this.getListEnergie().getlistEnergieTotale();
		int tailleL = list.size();
		
		if (taille == 1){
			this.k = this.energiePrec;
		}
		else if (taille <= 10){
			this.k = (this.k*(taille-1)+ Math.abs(list.get(tailleL-1) - list.get(tailleL-2)))/taille;  // moyenne des différences des energies
			//this.k = (this.k*(taille-1)+ list.get(tailleL-1))/taille;
		}
		else{
			this.k = (this.k*10 - Math.abs(list.get(1) - list.get(0))
						+ Math.abs(list.get(tailleL-1) - list.get(tailleL-2))) / 10;
			//this.k = (this.k*10 - list.get(0)
			//		+ list.get(tailleL-1)) / 10;
		}
		*/
		this.k = 1;
	}
	
	public String toString(){
		return "Recuit Simulé Linéaire avec k l énergie moyenne";
	}
	
}
