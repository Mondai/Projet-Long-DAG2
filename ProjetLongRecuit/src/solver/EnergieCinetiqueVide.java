package solver;

import solverCommun.EnergieCinetique;
import solverCommun.Probleme;

public class EnergieCinetiqueVide extends EnergieCinetique {

	@Override
	public double calculer(Probleme probleme) {
		return 0;
	}
}
