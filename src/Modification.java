/* Une modification est une mutation élémentaire de la situation du problème
 * Un noeud de la route de l'utilisateur est remplacé par un autre, ce qui revient
 * à supprimer 2 arêtes consécutives de sa route et en rajouter 2 autres 
 */
public class Modification {

	// Noeud supprimé de la route
	public int noeudSup;
	// Noeud ajouté à la route
	public int noeudAj;
	public int couleurAdj; // couleur auquel le deuxième noeud va être changé
	
	public Modification(int noeudSup,int noeudAj) {
		
		this.noeudSup = noeudSup;
		this.noeudAj = noeudAj;
		this.couleurAdj = couleurAdj;
		
	}

}
