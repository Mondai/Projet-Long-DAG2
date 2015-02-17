package tests;

import java.io.IOException;

import solver.commun.Etat;
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

public class Test_Quantique_Param1 {

	public static void main(String[] args) throws IOException {

		int[] seeds = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		//int[] seeds = {6435, 613467, 500, 5000, 50000, 500000, 5000000, 20000000, 200000000, 2000000000};
		
		int[] Ps = {5,7};
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();

		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();

		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");

		for(int t = 0 ; t < Ps.length ; t++ ){
			
			int P = Ps[t];
			
			System.out.print("iter"+P+" = [ ");
			
			for(int s=0; s<seeds.length ;s++){
				
				int seed = seeds[s];
				int nbNoeuds = 250;
				int nbCouleurs = 28;
				double k = 1;
				int M = 4 * nbNoeuds * nbCouleurs;
				double G0 = 0.75;
				//int P = 10;
				double T = 0.35/P;
				int maxSteps = ((int) Math.pow(10,4))*10/P;

				GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
				coloriage.initialiser();
				FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
				ConstanteKConstant Kparam = new ConstanteKConstant(k);
				//RecuitQuantiqueParametrable recuit = new RecuitQuantiqueParametrable(Tparam,Kparam, M, T);
				RecuitQuantiqueAccelere recuit = new RecuitQuantiqueAccelere(Tparam,Kparam, M, T);

				/*
				double iter = recuit.lancer(coloriage);
				
				if(s!=seeds.length-1){
					System.out.print(iter+", ");
				}else{
					System.out.print(iter);
				}*/
				recuit.lancer(coloriage);
			}

			System.out.println("]");

		}
	}

}
