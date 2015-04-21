package vertexColoring;

import solver.commun.Etat;
import solver.commun.IMutation;
import solver.commun.MutationElementaire;
import solver.commun.Probleme;

/**
 * Implémentation de l'interface IMutation sous la forme d'une permutation aléatoire de deux noeuds en conflit.
 * Deux noeuds sont aléatoirement choisis parmi les noeuds en conflit, et leur couleur sont échangées.
 */
public class MutationPermutationConflits implements IMutation {

	/**
	 * Fonction qui retourne une MutationElementairePermutation possible, avec deux noeuds pris parmis les noeuds en conflit.
	 */
	public MutationElementaire getMutationElementaire(Probleme probleme, Etat etat) {
		
		GrapheColorie coloriage = (GrapheColorie) etat;
	
		// Determination aleatoire d'une mutation a effectuer parmi les noeuds en conflit.
		int noeudsEnConflits = coloriage.nombreNoeudsEnConflit();
		
		int compteur = (int) (coloriage.gen.nextInt(noeudsEnConflits));

		int noeud1 = 0;

		try {
			noeud1=coloriage.getNoeudsConflitList().get(compteur);
		}
		catch (IndexOutOfBoundsException e){
			System.out.println(e);
		}
		
		compteur = (int) (coloriage.gen.nextInt(noeudsEnConflits));

		int noeud2 = 0;

		try {
			noeud2=coloriage.getNoeudsConflitList().get(compteur);
		}
		catch (IndexOutOfBoundsException e){
			System.out.println(e);
		}
		
		// noeud2 != noeud1, sinon il ne reste qu'un seul noeud en conflit (impossible)
		
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
