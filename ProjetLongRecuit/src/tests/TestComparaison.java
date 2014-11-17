package tests;
import java.io.IOException;

import solver.Coloriage;
import solver.ConflitsMutationConflits;
import solver.Graphe;
import solver.ListEnergie;
import solver.ListEnergieVide;
import solver.MutationConflitsAleatoire;
import solver.RecuitSimule;
import solver.RecuitSimuleLineaire;
import solver.Traducteur;

public class TestComparaison {
	
public static void main(String[] args) throws IOException {
		
		ConflitsMutationConflits energie = new ConflitsMutationConflits();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();

		Graphe graphe = Traducteur.traduire("data/le450_15c.col");
		int k = 1;
		int M = 4;
		double T0 = 0.6;
		double maxSteps = Math.pow(10,6);
		
		Coloriage coloriage = new Coloriage(energie, mutation, 15, graphe);
		coloriage.initialiser();
		ListEnergieVide vide = new ListEnergieVide();
		RecuitSimule recuit = new RecuitSimuleLineaire(k,T0,0,T0/maxSteps,M,vide);						

		long startTime = System.nanoTime();
		recuit.lancer(coloriage);
		long endTime = System.nanoTime();
		
		// affichage du r�sultat
		/*
		for (int i = 0; i < graphe.getNombreNoeuds(); i++) {
			System.out.println(i + " -> couleur "
					+ coloriage.getMeilleuresCouleurs()[i]);
		}
		*/
		System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie()+", Duree = "+(endTime-startTime)/1000000000+" s");
	}
	
}
