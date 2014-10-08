import java.io.IOException;


public class Test {

	public static void main(String[] args) throws IOException {
		
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		
		/* test trivial: k=1, Tdeb=1000, Tfin=1, pas=1, N=10.
		Graphe graphe = Traducteur.traduire("data/test_cycle5.col");
		Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
		coloriage.initialiser();
		RecuitSimule recuit = new RecuitSimuleLineaire(1,1000,1,1,10);
		recuit.lancer(coloriage);
		*/
		
		// test avec decroissance de T lineaire: k=7000, Tdeb=1000, Tfin=1, pas=1, N=100.
		// Resultat super!! 
		// nombre de couleurs theorique 25 donne 2 ou 3 conflits. 26 donne 0 conflit.
		Graphe graphe = Traducteur.traduire("data/le450_25a.col");
		Coloriage coloriage = new Coloriage(energie, mutation, 25 ,graphe);
		coloriage.initialiser();
		RecuitSimule recuit = new RecuitSimuleLineaire(7000,1000,1,1,100);
		recuit.lancer(coloriage);
		
		// affichage du résultat
		
		System.out.println("Nombre de conflits : "+recuit.meilleureEnergie);
		for (int i = 0; i < graphe.nombreNoeuds; i++) {
			System.out.println(i + " -> couleur "
					+ coloriage.meilleuresCouleurs[i]);
		}
	}

}
