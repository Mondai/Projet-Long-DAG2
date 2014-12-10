package solverQuantique;

import java.io.IOException;
import java.util.LinkedList;

public class Graphe extends RepresentationGraphique {
	
	private int nombreNoeuds;
	protected LinkedList<Integer>[] connexions; // représente les connexions
												// entre noeuds du graphe
	

	Graphe(int nbNoeuds, LinkedList<Integer>[] connexions) {
		this.setNombreNoeuds(nbNoeuds);
		this.connexions = connexions;
	}

	Graphe(String lien) throws IOException {
		Traducteur.traduire(lien);
	}

	public int getNombreNoeuds() {
		return nombreNoeuds;
	}

	public void setNombreNoeuds(int nombreNoeuds) {
		this.nombreNoeuds = nombreNoeuds;
	}

}