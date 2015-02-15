package vertexColoring;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Permet de traduire des fichiers .col en instances de graphe.
 * Implémente une fonction statique pour cela.
 *
 */
public class Traducteur {
	
	/**
	 * Traduit un fichier(.col) en un graphe.
	 * @param lien
	 * Lien faisant référence au fichier texte dans le projet java.
	 * @return Le graphe décrit par le fichier texte envoyé.
	 * @throws IOException
	 */
	public static Graphe traduire(String lien) throws IOException {

		int nbNoeuds = 0;
		LinkedList<Integer>[] connexions = null;
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(lien));
		String line = null;
		String[] temp = null;
		
		// saute les lignes commencant par un "c"
		while ((line = reader.readLine()) != null && line.startsWith("c")) {
		}
		
		// lignes commencant par "p": p edge nbNoeuds nbAretes 
		if (line.startsWith("p")) {
			nbNoeuds = Integer.parseInt(line.split("\\s")[2]);
			connexions = new LinkedList[nbNoeuds];
		}
		// initialise le tableau
		for (int i = 0; i < nbNoeuds; i++) {
			connexions[i] = new LinkedList<Integer>();
		}
		// lignes commencant par "e": e noeud1 noeud2
		// decalage des indices (1er indice 1 devient 0)
		// chaque arete est presente une fois dans les donnees. Et deux fois dans notre tableau.
		while ((line = reader.readLine()) != null) {
			temp = line.split("\\s");
			connexions[Integer.parseInt(temp[1]) - 1].add(Integer.parseInt(temp[2]) - 1);
			connexions[Integer.parseInt(temp[2]) - 1].add(Integer.parseInt(temp[1]) - 1);
		}
		reader.close();
		return new Graphe(nbNoeuds, connexions);
	}
	
}
