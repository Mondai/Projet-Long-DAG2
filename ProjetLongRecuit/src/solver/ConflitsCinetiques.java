package solver;

import solverCommun.EnergieCinetique;
import solverCommun.Etat;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;


public class ConflitsCinetiques extends EnergieCinetique {

	public double calculer(Probleme probleme) {
		
		GrapheColorieParticule coloriage = (GrapheColorieParticule)	probleme;
		
		return coloriage.calculerEnergie();

	}

	public double calculerDeltaE(Etat etat, MutationElementaire mutation) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		return coloriage.F[m.noeud][m.couleur] - coloriage.F[m.noeud][coloriage.couleurs[m.noeud]];
	}

}
