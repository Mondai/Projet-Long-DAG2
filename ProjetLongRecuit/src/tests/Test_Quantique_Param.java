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

		int[] seeds = {100};//, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
		
		double[] T0s = {0.35};
		//double[] G0s = {0.52,0.54,0.55,0.48,0.65,0.68,0.70,0.72,0.74,0.75,0.76,0.78,0.80,0.82,0.85,0.90,0.95,1.00,0.60,0.55,0.50};
		//iter0.3 = [ 1.034547029E9, 1.026778267E9, 1.204911054E9, 1.07531364E9, 1.30201659E9, 1.253589002E9, 9.75908088E8, 1.027336896E9, 1.145317323E9, 1.171000001E9]
		//iter0.4 = [ 1.24704142E8, 6.21217713E8, 1.94841376E8, 2.32373741E8, 1.35808144E8, 5.2548318E7, 2.12908422E8, 1.91056528E8, 8.337329E7, 2.12882765E8]

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
				//double G0 = 0.60;
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
