package solver;

import java.util.List;

// Sous-classe de RecuitSimuleExponentiel implementant un k variable


public class RecuitSimuleExponentielK extends RecuitSimuleExponentiel {

	double kConstant;
	
	public RecuitSimuleExponentielK(double k, double Tdeb, double Tfin, double facteur, int N, int nbPoints, IListEnergie listEnergie) {
		super(k, Tdeb, Tfin, facteur, N, nbPoints, listEnergie);
		this.kConstant = k;
	}
	
	public RecuitSimuleExponentielK() {
		// placeholder constructeur
	}

	public void init(){
		super.init();
		this.k = this.kConstant;
	}
	
	public void calculerK(){
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
			this.k = (this.k*10- this.getListEnergie().getlistEnergieTotale().get(0)
						+ Math.abs(list.get(tailleL-1) - list.get(tailleL-2))) / 10;
			//this.k = (this.k*10- this.getListEnergie().getlistEnergieTotale().get(0)
			//		+ list.get(tailleL-1)) / 10;
		}
	}
	
	public String toString(){
		return "Recuit Simulé Exponentiel avec k l'energie moyenne";
	}
	
}