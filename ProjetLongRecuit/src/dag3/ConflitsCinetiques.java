package dag3;

import java.util.HashSet;

import modele.Etat;
import modele.Probleme;
import parametrage.EnergieCinetique;


/**
 * Décrit l'énergie cinétique pour le problème de coloriage de graphes.
 *
 */
public class ConflitsCinetiques extends EnergieCinetique {

	/**
	 * Calcule l'énergie cinétique totale de la particule quantique.
	 */
	public double calculer(Probleme probleme) {
		
		GrapheColorieParticule coloriageParticule = (GrapheColorieParticule)	probleme;
		int E = 0;
		int spin1 = 1;
		int spin2 = 1;

		
		for (Etat e : probleme.getEtat()){
			GrapheColorie etat = (GrapheColorie) e;
			GrapheColorie etatNext = (GrapheColorie) e.getNext();
			for (int i = 0; i < etat.getCouleurs().length; i++){
				for (int j = 0; j < i; j++){							// les noeuds j < i
					spin1 = 1;
					spin2 = 1;
					if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
						spin1 = -1;
					}
					if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
						spin2 = -1;
					}
					E += spin1*spin2;
				}
				for (int j = i+1; j < etat.getCouleurs().length; j++){	// les noeuds j > i
					spin1 = 1;
					spin2 = 1;
					if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
						spin1 = -1;
					}
					if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
						spin2 = -1;
					}
					E += spin1*spin2;
				}
			}
		}
		
		E /= 2; // Tous les spins sont comptés deux fois dans les calculs
		
		return E;

	}

}
