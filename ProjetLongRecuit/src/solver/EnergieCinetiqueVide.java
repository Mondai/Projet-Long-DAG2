package solver;

import solverCommun.EnergieCinetique;
import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;

public class EnergieCinetiqueVide extends EnergieCinetique {

	@Override
	public double calculer(Probleme probleme) {
		return 0;
	}

	@Override
	public double calculerDeltaE(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		// TODO Auto-generated method stub
		return 0;
	}
}
