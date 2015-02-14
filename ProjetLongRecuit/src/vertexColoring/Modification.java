package vertexColoring;

/* Une modification est une mutation �l�mentaire de la situation du probl�me
 * Un noeud de la route de l'utilisateur est remplac� par un autre, ce qui revient
 * � supprimer 2 ar�tes cons�cutives de sa route et en rajouter 2 autres 
 */

/**
 * Une modification est une mutation �l�mentaire de la situation du probl�me.
 * Un noeud change de couleur, on stocke ici le noeud et sa couleur pr�c�dente pour pouvoir
 * inverser la modification si besoin.
 */
public class Modification {

	/**
	 * Noeud dont la couleur est modifi�e.
	 */
	public int noeudModifie;
	/**
	 * Couleur pr�c�dente du noeud.
	 */
	public int couleurPrecedente;
	
	/**
	 * Creation et Stockage en m�moire d'une modification de la couleur d'un noeud
	 * @param noeudModifie
	 * Noeud dont on modifie la couleur
	 * @param couleurPrecedente
	 * Couleur pr�c�dente du noeud � garder en m�moire
	 */
	public Modification(int noeudModifie,int couleurPrecedente) {
		
		this.noeudModifie = noeudModifie;
		this.couleurPrecedente = couleurPrecedente;
		
	}

}
