package tests;

import java.io.IOException;

import solver.Conflits;
import solver.ConflitsCinetiques;
import solver.Graphe;
import solver.GrapheColorie;
import solver.GrapheColorieParticule;
import solver.MutationConflitsAleatoire;
import solver.Traducteur;
import solverCommun.Etat;
import solverSimuleParametrable.ConstanteKConstant;
import solverSimuleParametrable.RecuitQuantiqueParametrableAccelere;
import solverSimuleParametrable.TemperatureLineaire;

public class Test_Quantique_Param {

	public static void main(String[] args) throws IOException {

		int[] seeds = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		
		double[] T0s = {0.35,0.30,0.40,0.25,0.45,0.20,0.50,0.15,0.55,0.10,0.60,0.05,0.70,0.01,0.80,0.90,1.00};
		//double[] G0s = {0.52,0.54,0.55,0.48,0.65,0.68,0.70,0.72,0.74,0.75,0.76,0.78,0.80,0.82,0.85,0.90,0.95,1.00,0.60,0.55,0.50};
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();

		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();

		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");

		for(int t = 0 ; t < T0s.length ; t++ ){
			
			double T0 = T0s[t];
			
			String res = "iter"+T0+" = [ ";
			
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
				TemperatureLineaire Tparam = new TemperatureLineaire(G0,0,maxSteps);
				ConstanteKConstant Kparam = new ConstanteKConstant(k);
				//RecuitQuantiqueParametrable recuit = new RecuitQuantiqueParametrable(Tparam,Kparam, M, T);
				RecuitQuantiqueParametrableAccelere recuit = new RecuitQuantiqueParametrableAccelere(Tparam,Kparam, M, T);

				double iter = recuit.lancer(coloriage);
				
				res+=iter+", ";

			}
		
			res = res.substring(0, res.length()-2);
			res+= "]";
			System.out.println(res);

		}
	}

}
