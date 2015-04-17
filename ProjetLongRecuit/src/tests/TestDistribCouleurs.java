package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantiqueAccelere;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorie;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

public class TestDistribCouleurs {

	public static void main(String[] args) throws IOException {

		int[] seeds = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		
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
		String nomFichier = "SortiesGraphiques/DistribCouleurs/dsjc250_5.txt";
		File file = new File(nomFichier);

		// initialisation du PrintWriter: on ouvre et ferme les flux a chaque fois que l'on souhaite ecrire, pour eviter la perte d'information en cas de coupure.
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
		pw.close();		// fermeture flux
			
		for(int s=0; s<seeds.length ;s++){

			int seed = seeds[s];

			System.out.println("seed "+seed);	// affichage terminal

			GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
			coloriage.initialiser();
			FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
			ConstanteKConstant Kparam = new ConstanteKConstant(k);
			RecuitQuantiqueAccelere recuit = new RecuitQuantiqueAccelere(Tparam,Kparam, M, T);

			recuit.lancer(coloriage);

			// Distribution des couleurs
			int[] distrib = new int[nbCouleurs];
			// recherche de l'état qui a atteint le résultat
			int n = 0;
			boolean t = true;
			while(n<P && t){
				GrapheColorie etat = (GrapheColorie) coloriage.etats[n];
				if(etat.Ep.calculer(etat)==0){
					t = false;
					HashSet<Integer>[] colorClasses = etat.getClassesCouleurs();
					for (int l = 0; l < nbCouleurs; l++) {
						distrib[l]=colorClasses[l].size();	// nombre de noeuds de la l-ieme couleur
					}
				}
				n++;
			}
			
			Arrays.sort(distrib);	// trier distrib par ordre croissant
			
			if(!t){
				// Ecriture flux
				pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
				pw.println("seed "+seed);
				pw.print("[");
				System.out.print("[");
				for (int l = 0; l < nbCouleurs; l++) {
					pw.print(distrib[l]+" ");
					System.out.print(distrib[l]+" ");
				}
				pw.println("]");
				System.out.println("]");
				pw.close();		// fermeture flux
			}else{
				System.out.println("Pas de résultat pour ce seed.");
			}

		}

	}

}