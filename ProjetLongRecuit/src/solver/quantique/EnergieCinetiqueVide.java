package solver.quantique;

import solver.commun.EnergieCinetique;
import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;


/**
 * Classe d'énergie cinétique nulle. Elle renvoie toujours 0 pour que l'énergie ne soit pas prise en compte dans le
 * recuit quantique.
 *
 */
public class EnergieCinetiqueVide extends EnergieCinetique {

	/**
	 * Renvoie une énergie cinétique nulle.
	 */
	public double calculer(Probleme probleme) {
		return 0;
	}

	/**
	 * Renvoie une différence d'énergie cinétique locale nulle.
	 */
	public double calculerDeltaE(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		return 0;
	}

	/**
	 * Renvoie une limite supérieure d'énergie cinétique locale nulle.
	 */
	public double calculerDeltaEUB(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		return 0;
	}
}
