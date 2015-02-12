package solver.parametres;

public class FonctionLineairePalier extends Fonction {
	
	double pasLineaire;
	int palier;
	int compteurPalier;
	
	public void init(){
		super.init();
		this.compteurPalier=1;
	}
	
	public FonctionLineairePalier(double tdebut, double tfinal,int nbIteration, int palier) {
		super(tdebut, tfinal, nbIteration);
		this.pasLineaire = (this.Tfinal-this.Tdebut)/this.nbIteration ;
		this.palier = palier;
		this.compteurPalier = 1;
		// TODO Auto-generated constructor stub
	}
	
	public boolean modifierT() { 			// modif de T
		if (this.t < this.Tfinal) {
			return false;
		} else if(this.compteurPalier == palier) {
			this.t += this.pasLineaire; 	// le pas linearie est negatif :)
			this.compteurPalier = 1;
			return true;
		}else{
			this.compteurPalier++;
			return true;
		}
	}

	// a completer !
}
