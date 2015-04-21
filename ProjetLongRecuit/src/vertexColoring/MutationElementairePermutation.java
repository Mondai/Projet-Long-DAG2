package vertexColoring;

import solver.commun.MutationElementaire;

/**
 * Représente la mutation qui échange la couleur des deux noeuds.
 */
public class MutationElementairePermutation extends MutationElementaire {
	/**
	 * Un des noeuds permutés.
	 */
	int noeud1;
	
	/**
	 * L'autre noeud permuté.
	 */
	int noeud2;

	public MutationElementairePermutation(int noeud1, int noeud2) {
		this.noeud1 = noeud1;
		this.noeud2 = noeud2;
	}

}
