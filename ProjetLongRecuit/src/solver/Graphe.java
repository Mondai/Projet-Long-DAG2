package solver;
import java.util.LinkedList;

public class Graphe {
	protected LinkedList<Integer>[] connexions; // représente les connexions entre noeuds du graphe
	private int nombreNoeuds;

	Graphe(int nbNoeuds, LinkedList<Integer>[] connexions) {
		this.setNombreNoeuds(nbNoeuds);
		this.connexions = connexions;
	}

	public int getNombreNoeuds() {
		return nombreNoeuds;
	}

	public void setNombreNoeuds(int nombreNoeuds) {
		this.nombreNoeuds = nombreNoeuds;
	}
	
	public int getAdjacenceMax(){
		int adjMax = 0;
		for (int i = 0; i < nombreNoeuds; i++){
			if (connexions[i].size() > adjMax){
				adjMax = connexions[i].size();
			}
		}
		return adjMax;
	}
}