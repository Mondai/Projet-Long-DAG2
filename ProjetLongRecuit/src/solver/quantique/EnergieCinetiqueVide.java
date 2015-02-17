package solver.quantique;

import solver.commun.EnergieCinetique;
import solver.commun.Etat;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;


/**
 * Classe d'�nergie cin�tique nulle. Elle renvoie toujours 0 pour que l'�nergie ne soit pas prise en compte dans le
 * recuit quantique.
 *
 */
public class EnergieCinetiqueVide extends EnergieCinetique {

	/**
	 * Renvoie une �nergie cin�tique nulle.
	 */
	public double calculer(Probleme probleme) {
		return 0;
	}

	/**
	 * Renvoie une diff�rence d'�nergie cin�tique locale nulle.
	 */
	public double calculerDeltaE(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		return 0;
	}

	/**
	 * Renvoie une limite sup�rieure d'�nergie cin�tique locale nulle.
	 */
	public double calculerDeltaEUB(Etat etat, Etat prev, Etat next,
			MutationElementaire mutation) {
		return 0;
	}
}
