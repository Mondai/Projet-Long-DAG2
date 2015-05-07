package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.MutableDouble;
import solver.quantique.RecuitQuantiqueAccelere_Iter;
import solver.quantique.RecuitQuantiqueExpf_Iter;
import solver.quantique.RecuitQuantique_Iter;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

public class Test_Iter_QA {

	public static void main(String[] args) throws IOException {

		int[] seeds = {150,10,100,1000,10000,100000,1000000,10000000,100000000,1000000000,
				300,20,200,2000,20000,200000,2000000,20000000,200000000,2000000000};
		
		// initialisation des classes
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
		
		// parametres fixes
		int nbNoeuds = 250;
		int nbCouleurs = 28;
		double k = 1;
		int M = 4 * nbNoeuds * nbCouleurs;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,4);
		
		// fichier txt
		// Nom du fichier à modifier en fonction du recuit utilisé!!! TODO
		String nomFichier = "SortiesGraphiques/Test_Iter/dsjc250_5_"+"QAAccExpf"+".txt";
		File file = new File(nomFichier);

		System.out.println("seed, mutationsTentees, mutationsAccepteesUB, mutationsAcceptees, temps [s]");	// affichage terminal

		// initialisation du PrintWriter: on ouvre et ferme les flux a chaque fois que l'on souhaite ecrire, pour eviter la perte d'information en cas de coupure.
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
		pw.println();
		pw.println("seed, mutationsTentees, mutationsAccepteesUB, mutationsAcceptees, temps [s]");// ecriture dans le fichier txt
		pw.close();		// fermeture flux

		for(int s=0; s<seeds.length ;s++){

			int seed = seeds[s];

			GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
			coloriage.initialiser();
			FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
			ConstanteKConstant Kparam = new ConstanteKConstant(k);
			
			// type du recuit à modifier d'un run à l'autre!!! TODO
			RecuitQuantiqueAccelere_Iter recuit = new RecuitQuantiqueAccelere_Iter(Tparam,Kparam, M, T);
			//RecuitQuantique_Iter recuit = new RecuitQuantique_Iter(Tparam,Kparam, M, T);
			//RecuitQuantiqueExpf_Iter recuit = new RecuitQuantiqueExpf_Iter(Tparam,Kparam, M, T);
			
			MutableDouble mutationsTentees = new MutableDouble(0);
			MutableDouble mutationsAccepteesUB = new MutableDouble(0);
			MutableDouble mutationsAcceptees = new MutableDouble(0);
			
			long startTime = System.nanoTime();
			recuit.lancer(coloriage, mutationsTentees, mutationsAccepteesUB, mutationsAcceptees);
			long endTime = System.nanoTime();
			
			System.out.println(seed+","+mutationsTentees.getValue()+","+mutationsAccepteesUB.getValue()+","+mutationsAcceptees.getValue()+","+(endTime-startTime)/1000000000);
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
			pw.println(seed+","+mutationsTentees.getValue()+","+mutationsAccepteesUB.getValue()+","+mutationsAcceptees.getValue()+","+(endTime-startTime)/1000000000);
			pw.close();		// fermeture flux
		}

		System.out.println("Fin");
		pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
		pw.println("Fin");
		pw.close();		// fermeture flux
	}

}