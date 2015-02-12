package vertexColoring;

import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.MutationElementaire;


public class Conflits extends EnergiePotentielle {
	
	public double calculer(Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie)	etat;
		
		return coloriage.getNombreConflitsAretes();

	}

	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		return coloriage.F[m.noeud][m.couleur] - coloriage.F[m.noeud][coloriage.couleurs[m.noeud]];
	}

}
