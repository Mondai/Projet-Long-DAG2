package vertexColoring;

import solver.commun.MutationElementaire;

public class MutationElementaireNoeud extends MutationElementaire{
	
	int noeud;
	int couleur;
	
	public MutationElementaireNoeud(int noeud, int couleur){
		this.noeud=noeud;
		this.couleur=couleur;
	}
	
}
