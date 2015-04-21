package vertexColoring;

import solver.commun.Etat;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

/**
 * Implémentation de l'interface IMutation sous la forme d'une permutation aléatoire de deux noeuds.
 * Deux noeuds sont aléatoirement choisis parmi les noeuds du graphe, et leur couleur sont échangées.
 */
public class MutationPermutation implements IMutation {

	/**
	 * Fonction qui retourne une MutationElementairePermutation possible, avec deux noeuds pris parmis les noeuds du graphe.
	 */
	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		
		int nombreNoeuds = coloriage.graphe.getNombreNoeuds();
		
		int noeud1 = (int) (coloriage.gen.nextInt(nombreNoeuds));

		int noeud2 = (int) (coloriage.gen.nextInt(nombreNoeuds));
		//nextInt donne le même élément que le choix précédent?
		while (noeud2 == noeud1){
			noeud2 = (int) (coloriage.gen.nextInt(nombreNoeuds));
		}
		
		return new MutationElementairePermutation(noeud1, noeud2);
	}

	/**
	 * Fonction qui modifie l'état, ici un GrapheColorié, pour prendre en compte la mutation effectuée.
	 */
	public void faire(Probleme probleme, Etat etat, MutationElementaire mutation) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
		MutationElementairePermutation m = (MutationElementairePermutation) mutation;
		
		int couleurPrec = coloriage.couleurs[m.noeud1];
		coloriage.couleurs[m.noeud1] = coloriage.couleurs[m.noeud2];
		coloriage.updateLocal(m.noeud1, couleurPrec);
		
		coloriage.couleurs[m.noeud2] = couleurPrec;
		coloriage.updateLocal(m.noeud2, coloriage.couleurs[m.noeud1]);
		
		
	}

}