import java.io.IOException;


public class Test {

	public static void main(String[] args) throws IOException {
		
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		
		int echantillonage=200;
		
		/* test trivial: k=1, Tdeb=1000, Tfin=1, pas=1, N=10.
		Graphe graphe = Traducteur.traduire("data/test_cycle5.col");
		Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
		coloriage.initialiser();
		RecuitSimule recuit = new RecuitSimuleLineaire(1,1000,1,1,10);
		recuit.lancer(coloriage);
		*/
		
		// test avec decroissance de T lineaire: k=7000, Tdeb=1000, Tfin=1, pas=1, N=100.
		// Pour le450_250a: nombre de couleurs theorique 25 donne 2 ou 3 conflits. 26 donne 0 conflit.
		Graphe graphe = Traducteur.traduire("data/le450_25a.col");
		Coloriage coloriage = new Coloriage(energie, mutation, 25 ,graphe);
		coloriage.initialiser();
		ListEnergie listEnergie = new ListEnergie(echantillonage);
		RecuitSimule recuit = new RecuitSimuleExponentielPalier(7000,1000,0.0000001,0.99,447,1,-1,listEnergie);		// graphe d, min couleurs = 30
		// RecuitSimule recuit = new RecuitSimuleExponentielPalier(7000,1000,0.0000001,0.99,1,447,1);	// graphe d, min couleurs = 30
		// RecuitSimule recuit = new RecuitSimuleExponentiel(7000,1000,0.0000001,0.99,10,1000000); 		// graphe d, min couleurs = 33
		// RecuitSimule recuit = new RecuitSimuleLineaire(7000,1000,0.01,0.01,10);						// graphe d, min couleurs = 33 .
		
		long startTime = System.nanoTime();
		recuit.lancer(coloriage);
		long endTime = System.nanoTime();
		
		// affichage du r�sultat
		
		for (int i = 0; i < graphe.nombreNoeuds; i++) {
			System.out.println(i + " -> couleur "
					+ coloriage.meilleuresCouleurs[i]);
		}
		System.out.println("Nombre de conflits : "+recuit.meilleureEnergie);
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
	}

}
