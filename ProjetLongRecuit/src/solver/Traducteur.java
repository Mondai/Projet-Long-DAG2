package solver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Traducteur {
	
	public static Graphe traduire(String lien) throws IOException {

		int nbNoeuds = 0;
		LinkedList<Integer>[] connexions = null;
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(lien));
		String line = null;
		String[] temp = null;
		
		// saute les lignes commençant par un "c"
		while ((line = reader.readLine()) != null && line.startsWith("c")) {
		}
		
		// lignes commençant par "p": p edge nbNoeuds nbArrêtes 
		if (line.startsWith("p")) {
			nbNoeuds = Integer.parseInt(line.split("\\s")[2]);
			connexions = new LinkedList[nbNoeuds];
		}
		// initialise le tableau
		for (int i = 0; i < nbNoeuds; i++) {
			connexions[i] = new LinkedList<Integer>();
		}
		// lignes commençant par "e": e noeud1 noeud2
		// décalage des indices (1er indice 1 devient 0)
		// chaque arrête est présente une fois dans les données. Et deux fois dans notre tableau.
		while ((line = reader.readLine()) != null) {
			temp = line.split("\\s");
			connexions[Integer.parseInt(temp[1]) - 1].add(Integer.parseInt(temp[2]) - 1);
			connexions[Integer.parseInt(temp[2]) - 1].add(Integer.parseInt(temp[1]) - 1);
		}
		reader.close();
		return new Graphe(nbNoeuds, connexions);
	}
	
}
