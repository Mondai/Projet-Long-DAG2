package dag3;

import modele.Etat;
import modele.Probleme;
import parametrage.EnergieCinetique;
import parametrage.Ponderation;


/**
 * D�crit l'�nergie cin�tique pour le probl�me de coloriage de graphes.
 *
 */
public class ConflitsCinetiques extends EnergieCinetique {

	/**
	 * Calcule l'�nergie cin�tique totale de la particule quantique.
	 */
	public static double calculer(Probleme probleme) {
		
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
		
		E /= 2; // Tous les spins sont compt�s deux fois dans les calculs
		
		return E;

	}
	
	public static double calculer(GrapheColorieParticule p, Ponderation J){
		int n = p.getEtat().size();
		return J.calcul(p.getT(), n)*calculer(p);
	}

}
