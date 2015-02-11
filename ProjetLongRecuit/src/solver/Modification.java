package solver;
/* Une modification est une mutation élémentaire de la situation du problème
 * Un noeud de la route de l'utilisateur est remplacé par un autre, ce qui revient
 * à supprimer 2 arêtes consécutives de sa route et en rajouter 2 autres 
 */
public class Modification {

	// Noeud supprimé de la route
	public int noeudModifie;
	// Noeud ajouté à la route
	public int couleurPrecedente;
	
	/**
	 * Creation et Stockage en mémoire d'une modification de la couleur d'un noeud
	 * @param noeudModifie
	 * Noeud dont on modifie la couleur
	 * @param couleurPrecedente
	 * Couleur précédente du noeud à garder en mémoire
	 */
	public Modification(int noeudModifie,int couleurPrecedente) {
		
		this.noeudModifie = noeudModifie;
		this.couleurPrecedente = couleurPrecedente;
		
	}

}
