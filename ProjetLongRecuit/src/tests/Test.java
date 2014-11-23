package tests;
import java.io.IOException;

import solver.EnergieCinetiqueVide;
import solver.GrapheColorie;
import solver.GrapheColorieParticule;
import solver.Conflits;
import solver.Graphe;
import solver.ListEnergie;
import solver.MutationAleatoireColoriage;
import solver.MutationConflitsAleatoire;
import solver.RecuitSimule;
import solver.RecuitSimuleExponentiel;
import solver.RecuitSimuleExponentielK;
import solver.Traducteur;
import solverCommun.Etat;


public class Test {

	public static void main(String[] args) throws IOException {
		
		Conflits Ep = new Conflits();
		EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();
		//MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		
		int echantillonnage=200;
		
		/* test trivial: k=1, Tdeb=1000, Tfin=1, pas=1, N=10.
		Graphe graphe = Traducteur.traduire("data/test_cycle5.col");
		Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
		coloriage.initialiser();
		RecuitSimule recuit = new RecuitSimuleLineaire(1,1000,1,1,10);
		recuit.lancer(coloriage);
		*/
		
		// test avec decroissance de T lineaire: k=7000, Tdeb=1000, Tfin=1, pas=1, N=100.
		// Pour le450_250a: nombre de couleurs theorique 25 donne 2 ou 3 conflits. 26 donne 0 conflit.
		Graphe graphe = Traducteur.traduire("data/le450_25b.col");
		//GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, 25 , 1, graphe);
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, 25 , 1, graphe);
		coloriage.initialiser();
		ListEnergie listEnergie = new ListEnergie(echantillonnage, 1000);
		ListEnergie listProba = new ListEnergie(echantillonnage, 1);
		// RecuitSimule recuit = new RecuitSimuleExponentielPalier(1,0.01,0,0.99,447,1,-1,listEnergie);		
		// RecuitSimule recuit = new RecuitSimuleExponentielPalier(1,0.01,0,0.99,1,447,1,listEnergie);	
		RecuitSimule recuit = new RecuitSimuleExponentielK(1,1000,2,0.99,10,200000, listEnergie, listProba);
		// RecuitSimule recuit = new RecuitSimuleExponentiel(1,10000,0,0.99,10,1000000, listEnergie); // a->0, c->22
		//RecuitSimule recuit = new RecuitSimuleExponentielK(1,10000,0,0.99,10,1000000, listEnergie);  // a->0, c->26
		// RecuitSimule recuit = new RecuitSimuleLineaire(1,1000,0.01,0.1,10, listEnergie);						
		// RecuitSimule recuit = new RecuitSimuleLineaireK(1,1000,0.01,0.1,10, listEnergie);
		long startTime = System.nanoTime();
		recuit.lancer(coloriage);
		long endTime = System.nanoTime();
		
		// affichage du resultat
		for (Etat etat : coloriage.getEtats()){
			GrapheColorie g = (GrapheColorie) etat;
			for (int i = 0; i < graphe.getNombreNoeuds(); i++) {
				System.out.println(i + " -> couleur "
						+ g.getMeilleuresCouleurs()[i]);
				if (g.getNoeudsConflit()[i] == 1) System.out.println("Dessus En conflit");
			}
			System.out.println("Energie de l'état : " + g.Ep.calculer(g));
			System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
			System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
		}
		//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
	}

}
