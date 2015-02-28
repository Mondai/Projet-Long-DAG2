package tests;

import java.io.IOException;

import solver.commun.Etat;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantique;
import solver.quantique.RecuitQuantiqueAccelere;
import solver.quantique.RecuitQuantiqueExpf;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorie;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.RecuitQuantiqueAccelereVC;
import vertexColoring.Traducteur;

public class TestDistributionTemps {

	public static void main(String[] args) throws IOException {

		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();

		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");

		int nbNoeuds = 250;
		int nbCouleurs = 27;
		double k = 1;
		int M =  8*nbNoeuds * nbCouleurs;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,2);
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe);

		FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);

		//RecuitQuantiqueParametrableAccelereVC recuit = new RecuitQuantiqueParametrableAccelereVC(Tparam,Kparam, M, T);

		int nombre = 1;

		

		for (int j=0;j<nombre;j++) {

			RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);
			coloriage.initialiser();
			long startTime = System.nanoTime();
			recuit.lancer(coloriage);
			long endTime = System.nanoTime();

			// affichage du resultat
			for (Etat etat : coloriage.getEtats()){
				GrapheColorie g = (GrapheColorie) etat;

				System.out.println("Energie de l'état : " + g.Ep.calculer(g));

			}

			System.out.println("duree du "+j+"ème recuit non accéléré = "+(endTime-startTime)/1000000000+" s");
		}
		
		for (int i=0;i<nombre;i++) {

			RecuitQuantiqueExpf recuitExpf = new RecuitQuantiqueExpf(Tparam,Kparam, M, T);
			coloriage.initialiser();
			long startTime = System.nanoTime();
			recuitExpf.lancer(coloriage);
			long endTime = System.nanoTime();

			// affichage du resultat
			for (Etat etat : coloriage.getEtats()){
				GrapheColorie g = (GrapheColorie) etat;

				System.out.println("Energie de l'état : " + g.Ep.calculer(g));

			}

			System.out.println("duree du "+i+"ème recuit accéléré = "+(endTime-startTime)/1000000000+" s");
		}


	}

}
