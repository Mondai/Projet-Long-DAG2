package solver;

import solverCommun.Etat;
import solverCommun.IMutation;
import solverCommun.MutationElementaire;
import solverCommun.Probleme;

// Implementation de l'interface IMutation sous la forme d'une mutation aleatoire de coloriage
// Premiere ebauche avec le noeud pris aleatoirement et la couleur aussi.
public class MutationAleatoireColoriage implements IMutation {

	
	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;

		// Determination aleatoire d'une mutation elementaire a effectuer.
		int noeudAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.graphe.getNombreNoeuds());
		int couleurNoeud = coloriage.couleurs[noeudAleatoire];
		int couleurAleatoire = couleurNoeud;

		while (couleurAleatoire == couleurNoeud) {
			couleurAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.k);
		}
		
		return new MutationElementaireNoeud(noeudAleatoire, couleurAleatoire);
	}


	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {

		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud];
		coloriage.couleurs[m.noeud] = m.couleur;
		coloriage.updateLocal(m.noeud, couleurPrec);
		
	}
	
}