package probleme2D;

import solver.commun.EnergieCinetique;
import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;
import solver.commun.IMutation;
import solver.commun.Probleme;
import vertexColoring.GrapheColorie;

public class Position2DParticule extends Probleme{

	int replique;
	Relief2D relief;
	
	

	public Position2DParticule(EnergiePotentielle Ep, EnergieCinetique Ec, int replique, Relief2D relief,IMutation mutation) {
		this.Ec=Ec;
		this.replique = replique;
		this.relief = relief;
		this.mutation = mutation;
		
		this.etats = new Etat[replique];
		for (int i = 0; i < this.replique; i++){
			this.etats[i] = new Position2D(Ep,relief, this.gen.nextInt(),0,0,0,0 );
		}
		
	}


	public void initialiser() {
		
		EnergiePotentielle Ep = this.etats[0].Ep;
		this.etats = new Etat[replique];
		
			
			for (int i = 0; i < this.replique; i++){
				Position2D etat =  new Position2D(Ep, relief, this.gen.nextInt(),0,0,0,0);
				etat.initialiser();
				this.etats[i] = etat;
			}
		}
	

	
	public void sauvegarderSolution() {
		for (Etat position2D : etats){
			( (Position2D) position2D).sauvegarderSolution();
		}
		
	}
	

}
