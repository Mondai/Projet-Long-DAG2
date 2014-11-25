package solver;

import java.util.List;

// Sous-classe de RecuitSimuleExponentiel implementant un k variable


public class RecuitSimuleExponentielK extends RecuitSimuleExponentiel {

	double kConstant;
	
	public RecuitSimuleExponentielK(double k, double Tdeb, double Tfin, double facteur, int N, int nbPoints) {
		super(k, Tdeb, Tfin, facteur, N, nbPoints);
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
		/*
		int taille = this.getListEnergie().getTaille();
		List<Double> list = this.getListEnergie().getlistEnergieTotale();
		int tailleL = list.size();
		int fenetreK = this.getListEnergie().getFenetreK();
		//System.out.println(list);
		
		if (taille == 1){
			this.k = 1;
			//this.k = this.energiePrec;
		}
		else if (taille <= fenetreK ){
			this.k = (this.k*(tailleL-1)+ Math.abs(list.get(tailleL-1) - list.get(tailleL-2)))/taille;  // moyenne des différences des energies

		}
		else{
			// marche parce que listEnergie garde les 11 dernieres energies, et utilisent 10 differences d'energies
			this.k = (this.k*fenetreK - Math.abs(list.get(1) - list.get(0))
						+ Math.abs(list.get(tailleL-1) - list.get(tailleL-2))) / fenetreK;

		}
		System.out.println("k : " + this.k);*/
		this.k = 1;
	}
	
	
	
	
	
	
	
	
	
	public String toString(){
		return "Recuit Simulé Exponentiel avec k l energie moyenne";
	}
	
}
