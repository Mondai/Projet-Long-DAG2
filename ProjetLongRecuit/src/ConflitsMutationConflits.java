// classe implementant l'energie dans le probleme du coloriage


public class ConflitsMutationConflits implements IEnergie {

	public double calculer(Probleme probleme) {
		
		Coloriage coloriage = (Coloriage)	probleme;
		double conflits = 0;
		
		for (int j = 0; j < coloriage.graphe.nombreNoeuds; j++) { // parcours de tous les noeuds du graphe
			int couleurNoeudActuel = coloriage.couleurs[j]; // couleur du noeud actuel
			coloriage.conflits[j] = 0;// on initialise en supposat l'absence de conflit
			for (int noeudAdjacent : coloriage.graphe.connexions[j]) { // parcours des voisins du noeud
				if (coloriage.couleurs[noeudAdjacent]==couleurNoeudActuel) {
					conflits++; // incrementation de conflits si meme couleur
					coloriage.conflits[j] = 1;// s'il y a un conflit on met 1 dans le tableau de conflits
				}
		
			}
		}
		
		coloriage.nombreNoeudsConflit = (int) conflits; // sauvegarde le nombre de noeuds en conflit
		conflits /=2; // chaque conflit est compt� deux fois
		
		return conflits;

	}

}