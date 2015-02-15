package vertexColoring;

import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.MutationElementaire;

/**
 * Décrit l'énergie potentielle pour le problème de coloriage de graphes.
 * Aucun calcul ici, seulement une récupération d'attribut d'instances GrapheColorie.
 */
public class Conflits extends EnergiePotentielle {
	
	/**
	 * Ici, la fonction qui calcule l'énergie potentielle de l'état en question va chercher le nombre d'arêtes
	 * d'un coloriage(état) et le renvoie.
	 */
	public double calculer(Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		return coloriage.getNombreConflitsAretes();

	}

	/**
	 * Ici, la fonction calcule la différence d'énergie potentielle qu'induirait la mutation donnée à l'état en question
	 * en utilisant l'attribut F de coloriage.
	 */
	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		return coloriage.F[m.noeud][m.couleur] - coloriage.F[m.noeud][coloriage.couleurs[m.noeud]];
	}

}
