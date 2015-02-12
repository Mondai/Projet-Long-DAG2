package solver.quantique;

import solver.commun.EnergieCinetique;
import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

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

	@Override
	public double calculerDeltaEUB(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		// TODO Auto-generated method stub
		return 0;
	}
}
