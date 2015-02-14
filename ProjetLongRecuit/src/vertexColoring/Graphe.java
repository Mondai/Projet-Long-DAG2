package vertexColoring;
import java.util.LinkedList;

/**
 * Classe qui représente la structure sous-jacente du graphe.
 * <p>
 * Elle dispose d'un tableau pour les connexions. Pour chaque noeud, ce tableau va disposer d'une liste chaînée
 * avec tous les autres noeuds connectés.
 * Les noeuds sont représentés par des entiers.
 */
public class Graphe {
	protected LinkedList<Integer>[] connexions; // représente les connexions entre noeuds du graphe
	private int nombreNoeuds;

	public Graphe(int nbNoeuds, LinkedList<Integer>[] connexions) {
		this.setNombreNoeuds(nbNoeuds);
		this.connexions = connexions;
	}

	/**
	 * Renvoie le nombre de noeuds du graphe
	 */
	public int getNombreNoeuds() {
		return nombreNoeuds;
	}

	/**
	 * Fixe le nombre de noeuds du graphe
	 */
	//Vraiment utile?? Fonction cohérente??
	public void setNombreNoeuds(int nombreNoeuds) {
		this.nombreNoeuds = nombreNoeuds;
	}
	
	/**
	 * Calcule l'adjacence maximum du graphe
	 * @return Adjacence Maximum
	 */
	public int getAdjacenceMax(){
		int adjMax = 0;
		for (int i = 0; i < nombreNoeuds; i++){
			if (connexions[i].size() > adjMax){
				adjMax = connexions[i].size();
			}
		}
		return adjMax;
	}
	
	/**
	 * Renvoie les connexions représentant le graphe
	 * @return Une liste chaînée d'Entiers
	 */
	public LinkedList<Integer>[] getConnexions(){
		return this.connexions;
	}
}