import java.util.LinkedList;

public class Graphe {
	LinkedList<Integer>[] connexions; // repr�sente les connexions entre noeuds du graphe
	int nombreNoeuds;

	Graphe(int nbNoeuds, LinkedList<Integer>[] connexions) {
		this.nombreNoeuds = nbNoeuds;
		this.connexions = connexions;
	}
}