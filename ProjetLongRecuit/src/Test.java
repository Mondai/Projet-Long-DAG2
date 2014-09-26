import java.io.IOException;


public class Test {

	public static void main(String[] args) {
		
		// initialisation des variables
		
		int N = 10; // nombre de palliers
		int M = 1; // nombre d'itérations à chaque pallier
		int k = 1; // nombre de couleurs pour le coloriage
		String lien = "lien"; // On ne sait pas comment recuperer le fichier
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		
		// création des classes utiles
		
		Graphe graphe = Traducteur.traduire(lien);
		Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
		Recuit recuit = new Recuit(N,M);
		
		// iteration du recuit
		
		recuit.iterer(coloriage);
		
		// affichage du résultat
		
		System.out.println("Nombre de conflits : "+energie.calculer(coloriage));
		System.out.println("Graphe final : "+coloriage.meilleuresCouleurs);
	}

}
