package dag3;

import vertexColoring.GrapheColorie;
import vertexColoring.MutationElementaireNoeud;
import modele.Etat;
import modele.Probleme;
import mutation.IMutation;


/**
 * Implémentation de l'interface IMutation sous la forme d'une mutation aléatoire de coloriage.
 * Le noeud et la couleur sont pris aléatoirement.
 */
public class MutationAleatoireColoriage implements IMutation {

	@Override
	public double calculer(Probleme probleme) {
		// différence d'énergie totale
		return 0;
	}

	@Override
	public double calculer(Probleme probleme, Etat etat) {
		
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		GrapheColorie coloriage = (GrapheColorie)	etat;

		// Propriete: DelatE = F[v][couleurSuiv] - F[v][couleurPrec]
		return coloriage.F[m.noeud][m.couleur] - coloriage.F[m.noeud][coloriage.couleurs[m.noeud]];
		
		// différence d'énergie potentielle
	}

	@Override
	public void faire(Probleme probleme) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Etat faire(Probleme probleme, Etat etat) {
		GrapheColorie coloriage = (GrapheColorie) etat;

		// Determination aleatoire d'une mutation elementaire a effectuer.
		int noeudAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.graphe.getNombreNoeuds());
		int couleurNoeud = coloriage.couleurs[noeudAleatoire];
		int couleurAleatoire = couleurNoeud;

		while (couleurAleatoire == couleurNoeud) {
			couleurAleatoire = (int) (coloriage.gen.nextDouble() * coloriage.k);
		}
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud];
		coloriage.couleurs[m.noeud] = m.couleur;
		coloriage.updateLocal(m.noeud, couleurPrec);
	}

	@Override
	public void maj() {
		// TODO Auto-generated method stub
		
	}
	
}