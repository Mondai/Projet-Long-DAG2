package solver;
import java.util.LinkedList;

public class Graphe {
	protected LinkedList<Integer>[] connexions; // repr�sente les connexions entre noeuds du graphe
	private int nombreNoeuds;

	Graphe(int nbNoeuds, LinkedList<Integer>[] connexions) {
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
	//Vraiment utile?? Fonction coh�rente??
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
	 * Renvoie les connexions repr�sentant le graphe
	 * @return Une liste cha�n�e d'Entiers
	 */
	public LinkedList<Integer>[] getConnexions(){
		return this.connexions;
	}
}