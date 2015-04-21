package vertexColoring;

import solver.commun.MutationElementaire;

/**
 * Repr�sente la mutation qui �change la couleur des deux noeuds.
 */
public class MutationElementairePermutation extends MutationElementaire {
	/**
	 * Un des noeuds permut�s.
	 */
	int noeud1;
	
	/**
	 * L'autre noeud permut�.
	 */
	int noeud2;

	public MutationElementairePermutation(int noeud1, int noeud2) {
		this.noeud1 = noeud1;
		this.noeud2 = noeud2;
	}

}
