import java.io.IOException;


public class Test {

	public static void main(String[] args) throws IOException {
		
		// initialisation des variables
		
		int N = 10; // nombre de palliers
		int M = 1; // nombre d'it廨ations �chaque pallier
		int k = 3; // nombre de couleurs pour le coloriage
		String lien = "lien"; // On ne sait pas comment recuperer le fichier
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		
		// cr嶧tion des classes utiles
		
		Graphe graphe = Traducteur.traduire("data/test_cycle5.col");
		Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
		Recuit recuit = new Recuit(N,M);
		
		// iteration du recuit
		
		recuit.iterer(coloriage);
		
		// affichage du r廥ultat
		
		System.out.println("Nombre de conflits : "+recuit.meilleureEnergie);
		for (int i = 0; i < graphe.nombreNoeuds; i++) {
			System.out.println(i + " -> couleur "
					+ coloriage.meilleuresCouleurs[i]);
		}
	}

}
