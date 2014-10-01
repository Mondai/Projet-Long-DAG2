// classe implementant l'energie dans le probleme du coloriage

import java.util.Iterator;


public class Conflits implements IEnergie {

	public double calculer(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage)	probleme;
		double conflits = 0;
		
		for (int j = 0; j < coloriage.graphe.nombreNoeuds; j++) { // parcours de tous les noeuds du graphe
			int couleurNoeudActuel = coloriage.couleurs[j]; // couleur du noeud actuel
			for (int noeudAdjacent : coloriage.graphe.connexions[j]) { // parcours des voisins du noeud
				if (coloriage.couleurs[noeudAdjacent]==couleurNoeudActuel) conflits++; // incrementation de conflits si meme couleur
		
			}
		}
		
		conflits /=2; // chaque conflit est compt� deux fois
		
		return conflits;

	}

}
