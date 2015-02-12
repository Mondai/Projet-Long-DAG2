package tests;

import java.io.IOException;

import solver.commun.Etat;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantiqueParametrableAccelere;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorie;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

public class Test_Quantique_Param {

	public static void main(String[] args) throws IOException {

		int[] seeds = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		
		double[] T0s = {0.35};
		//double[] G0s = {0.52,0.54,0.55,0.48,0.65,0.68,0.70,0.72,0.74,0.75,0.76,0.78,0.80,0.82,0.85,0.90,0.95,1.00,0.60,0.55,0.50};
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();

		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();

		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");

		for(int t = 0 ; t < T0s.length ; t++ ){
			
			double T0 = T0s[t];
			
			System.out.print("iter"+T0+" = [ ");
			
			for(int s=0; s<seeds.length ;s++){
				
				int seed = seeds[s];
				int nbNoeuds = 250;
				int nbCouleurs = 28;
				double k = 1;
				int M = 4 * nbNoeuds * nbCouleurs;
				double G0 = 0.75;
				int P = 10;
				//double T = 0.35/P;
				double T = T0/P;
				int maxSteps = (int) Math.pow(10,4);

				GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
				coloriage.initialiser();
				FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
				ConstanteKConstant Kparam = new ConstanteKConstant(k);
				//RecuitQuantiqueParametrable recuit = new RecuitQuantiqueParametrable(Tparam,Kparam, M, T);
				RecuitQuantiqueParametrableAccelere recuit = new RecuitQuantiqueParametrableAccelere(Tparam,Kparam, M, T);

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