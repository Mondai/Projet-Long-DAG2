package solver;

import solverCommun.EnergiePotentielle;
import solverCommun.Etat;
import solverCommun.MutationElementaire;
// classe implementant l'energie dans le probleme du coloriage
import solverCommun.Probleme;


public class Conflits extends EnergiePotentielle {

	public double calculer(Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		return coloriage.getNombreConflitsAretes();

	}

	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		int deltaE = 0;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		if(coloriage.couleurs[m.noeud]==m.couleur){
			return 0;		// pas de changement
		} else {
			for (int i : coloriage.graphe.connexions[m.noeud]) {
				if (coloriage.couleurs[i] == coloriage.couleurs[m.noeud]) {
					deltaE--; // si la couleur du voisin == ancienne couleur du noeud
				} else if (coloriage.couleurs[i] == m.couleur) {
					deltaE++; // si la couleur du voisin == nouvelle couleur du noeud
				}
			}
			return deltaE;
		}
	}

}
