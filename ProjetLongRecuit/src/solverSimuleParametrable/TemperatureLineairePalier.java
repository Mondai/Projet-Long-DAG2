package solverSimuleParametrable;

public class TemperatureLineairePalier extends Temperature {
	
	double pasLineaire;
	int palier;
	int compteurPalier;
	
	public TemperatureLineairePalier(double tdebut, double tfinal,int nbIteration, int palier) {
		super(tdebut, tfinal, nbIteration);
		this.pasLineaire = (this.Tfinal-this.Tdebut)/this.nbIteration ;
		this.palier = palier;
		this.compteurPalier = 0;
		// TODO Auto-generated constructor stub
	}
	
	public boolean modifierT() { 			// modif de T
		if (this.t < this.Tfinal) {
			return false;
		} else if(this.compteurPalier == palier) {
			this.t += this.pasLineaire; 	// le pas linearie est negatif :)
			this.compteurPalier = 0;
			return true;
		}else{
			this.compteurPalier++;
			return true;
		}
	}

	// a completer !
}
