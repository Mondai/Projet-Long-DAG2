package solverQuantique;

public class MutationElementaireGraphColoring extends MutationElementaire {

	public int noeud;
	public int couleurNoeud;
	public int nbMaxCouleur;

	public MutationElementaireGraphColoring(int nbMaxCouleur) {
		this.noeud = 0;
		this.couleurNoeud = 0;
		this.nbMaxCouleur = nbMaxCouleur;
	}

	public void trouverMutation(ParticuleQuantique particuleQuantique,
			int numeroReplique) {

		// Determination aleatoire d'une mutation elementaire a effectuer, ie changer la couleur d un seul noeud
		this.noeud = (int) (Math.random() * ((Graphe) particuleQuantique.dataProblem)
				.getNombreNoeuds());
		int couleurActuelle = ((RepresentationGraphColoring) particuleQuantique.representationsEtat[numeroReplique]).colors[this.noeud];
		this.couleurNoeud = couleurActuelle;
		// on verifie quand même que c'est une autre couleur 
		do  {
			this.couleurNoeud = (int) (Math.random() * this.nbMaxCouleur);
		} while (couleurActuelle == couleurNoeud) ;

	}

	public void effectuerMutation(ParticuleQuantique particuleQuantique,
			int numeroReplique) {
		((RepresentationGraphColoring) particuleQuantique.representationsEtat[numeroReplique]).colors[this.noeud] = this.couleurNoeud;

	}

}

/* cette classe fonctionne avec le type de probleme GraphColoring, 
 * donc s'attend bien a trouver des RepresentationGraphColoring
 */
