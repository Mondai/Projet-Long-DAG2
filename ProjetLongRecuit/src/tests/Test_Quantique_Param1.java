package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import solver.commun.Etat;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.MutableDouble;
import solver.quantique.RecuitQuantiqueAccelere;
import solver.quantique.RecuitQuantiqueAccelere_Iter;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorie;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

public class Test_Quantique_Param1 {

	public static void main(String[] args) throws IOException {

		int[] seeds = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		
		int[] Ps = {20,25,30,40,50};
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
		
		// parametres fixes
		int nbNoeuds = 250;
		int nbCouleurs = 28;
		double k = 1;
		int M = 4 * nbNoeuds * nbCouleurs;
		// parametres de l'article
		double G0 = 0.75;
		double T0 = 0.35;
		
		// fichier txt
		String nomFichier = "SortiesGraphiques/Iter_Quantique/iter_P.txt";
		File file = new File(nomFichier);
		
		// G0 fixe. Boucle sur T0.
		for(int t = 0 ; t < Ps.length ; t++ ){

			int P = Ps[t];
			int maxSteps = ((int) Math.pow(10,4))*10/P;
			double T = T0/P;
			
			System.out.print("iter_"+G0+"_"+T0+" = [ ");	// affichage terminal
			
			// initialisation du PrintWriter: on ouvre et ferme les flux a chaque fois que l'on souhaite ecrire, pour eviter la perte d'information en cas de coupure.
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
			pw.print("iter_"+G0+"_"+T0+" = [ ");			// ecriture dans le fichier txt
			pw.close();		// fermeture flux
			
			for(int s=0; s<seeds.length ;s++){
				
				int seed = seeds[s];
				
				GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
				coloriage.initialiser();
				FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
				ConstanteKConstant Kparam = new ConstanteKConstant(k);
				RecuitQuantiqueAccelere_Iter recuit = new RecuitQuantiqueAccelere_Iter(Tparam,Kparam, M, T);

				MutableDouble mutationsTentees = new MutableDouble(0);
				MutableDouble mutationsAccepteesUB = new MutableDouble(0);
				MutableDouble mutationsAcceptees = new MutableDouble(0);
				recuit.lancer(coloriage, mutationsTentees, mutationsAccepteesUB, mutationsAcceptees);
				
				System.out.print(mutationsTentees.getValue()+" ");
				pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
				pw.print(mutationsTentees.getValue()+" ");
				pw.close();		// fermeture flux
			}

			System.out.println("]");
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); // true -> append
			pw.println("]");
			pw.close();		// fermeture flux
		}
		
	}

}