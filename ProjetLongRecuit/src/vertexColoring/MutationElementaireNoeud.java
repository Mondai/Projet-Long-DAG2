package vertexColoring;

import solver.commun.MutationElementaire;

/**
 * Repr�sente la mutation d'un noeud � une autre couleur.
 */
public class MutationElementaireNoeud extends MutationElementaire{
	
	/**
	 * Un des noeuds du graphe.
	 */
	int noeud;
	/**
	 * La nouvelle couleur du noeud
	 */
	int couleur;
	
	public MutationElementaireNoeud(int noeud, int couleur){
		this.noeud=noeud;
		this.couleur=couleur;
	}
	
}
