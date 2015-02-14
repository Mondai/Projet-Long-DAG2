package vertexColoring;

import solver.commun.Etat;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;


/**
 * Implémentation de l'interface IMutation sous la forme d'une mutation aléatoire de coloriage.
 * Le noeud et la couleur sont pris aléatoirement.
 */
public class MutationAleatoireColoriage implements IMutation {

	/**
	 * Fonction qui retourne une MutationElementaireNoeud possible, avec un noeud puis une couleur aléatoire
	 * (différente de celle précédente du noeud).
	 */
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

	/**
	 * Fonction qui modifie l'état, ici un GrapheColorié, pour prendre en compte la mutation effectuée.
	 */
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {

		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementaireNoeud m = (MutationElementaireNoeud) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud];
		coloriage.couleurs[m.noeud] = m.couleur;
		coloriage.updateLocal(m.noeud, couleurPrec);
		
	}
	
}