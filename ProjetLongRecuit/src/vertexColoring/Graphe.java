package vertexColoring;
import java.util.LinkedList;

/**
 * Classe qui repr�sente la structure sous-jacente du graphe.
 * <p>
 * Elle dispose d'un tableau pour les connexions. Pour chaque noeud, ce tableau va disposer d'une liste cha�n�e
 * avec tous les autres noeuds connect�s.
 * Les noeuds sont repr�sent�s par des entiers.
 */
public class Graphe {
	protected LinkedList<Integer>[] connexions; // repr�sente les connexions entre noeuds du graphe
	private int nombreNoeuds;

	public Graphe(int nbNoeuds, LinkedList<Integer>[] connexions) {
		this.nombreNoeuds = nbNoeuds;
		this.connexions = connexions;
	}

	/**
	 * Renvoie le nombre de noeuds du graphe
	 * @return nombre de noeuds du graphe
	 */
	public int getNombreNoeuds() {
		return nombreNoeuds;
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
	 * Renvoie les connexions repr�sentant le graphe
	 * @return Une liste cha�n�e d'Entiers
	 */
	public LinkedList<Integer>[] getConnexions(){
		return this.connexions;
	}
}