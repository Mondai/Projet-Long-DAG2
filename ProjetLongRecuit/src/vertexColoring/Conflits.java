package vertexColoring;

import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.MutationElementaire;

/**
 * D�crit l'�nergie potentielle pour le probl�me de coloriage de graphes.
 * Aucun calcul ici, seulement une r�cup�ration d'attribut d'instances GrapheColorie.
 */
public class Conflits extends EnergiePotentielle {
	
	/**
	 * Ici, la fonction qui calcule l'�nergie potentielle de l'�tat en question va chercher le nombre d'ar�tes
	 * d'un coloriage(�tat) et le renvoie.
	 */
	public double calculer(Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		return coloriage.getNombreConflitsAretes();

	}

	/**
	 * Ici, la fonction calcule la diff�rence d'�nergie potentielle qu'induirait la mutation donn�e � l'�tat en question
	 * en utilisant l'attribut F de coloriage.
	 */
	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		return coloriage.F[m.noeud][m.couleur] - coloriage.F[m.noeud][coloriage.couleurs[m.noeud]];
	}

}
