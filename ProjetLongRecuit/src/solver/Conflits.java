package solver;

import solverCommun.EnergiePotentielle;
import solverCommun.Etat;
// classe implementant l'energie dans le probleme du coloriage
import solverCommun.Probleme;


public class Conflits extends EnergiePotentielle {

	public double calculer(Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		return coloriage.getNombreConflitsAretes();

	}

}
