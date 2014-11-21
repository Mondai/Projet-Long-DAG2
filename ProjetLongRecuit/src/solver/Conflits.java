package solver;

import solverCommun.EnergiePotentielle;
import solverCommun.Etat;
// classe implementant l'energie dans le probleme du coloriage
import solverCommun.Probleme;


public class Conflits extends EnergiePotentielle {

	public double calculer(Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie)	etat;
		double conflits = 0;
		
		for (int j = 0; j < coloriage.graphe.getNombreNoeuds(); j++) { // parcours de tous les noeuds du graphe
			int couleurNoeudActuel = coloriage.couleurs[j]; // couleur du noeud actuel
			for (int noeudAdjacent : coloriage.graphe.connexions[j]) { // parcours des voisins du noeud
				if (coloriage.couleurs[noeudAdjacent] == couleurNoeudActuel) conflits++; // incrementation de conflits si meme couleur
		
			}
		}
		
		conflits /=2; // chaque conflit est compte deux fois
		
		return conflits;

	}

}
