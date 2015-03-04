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
import vertexColoring.RecuitQuantiqueAccelereVC;
import vertexColoring.Traducteur;

public class TestQuantique {

	public static void main(String[] args) throws IOException {
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");

		int nbNoeuds = 250;
		int nbCouleurs = 28;
		double k = 1;
		int M = 4 * nbNoeuds * nbCouleurs;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,3);
		int seed = 354;
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
		coloriage.initialiser();
		FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		//RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);
		RecuitQuantiqueAccelere recuit = new RecuitQuantiqueAccelere(Tparam,Kparam, M, T);
		//RecuitQuantiqueAccelereVC recuit = new RecuitQuantiqueAccelereVC(Tparam,Kparam, M, T);
		
		long startTime = System.nanoTime();
		recuit.lancer(coloriage);
		long endTime = System.nanoTime();
		
		// affichage du resultat
		for (Etat etat : coloriage.getEtats()){
			GrapheColorie g = (GrapheColorie) etat;
			/*
			for (int i = 0; i < graphe.getNombreNoeuds(); i++) {
				System.out.println(i + " -> couleur "
				+ g.getMeilleuresCouleurs()[i]);
				if (g.getNoeudsConflitList().contains(i)) System.out.println("Dessus En conflit");
			}
			*/
			System.out.println("Energie de l'état : " + g.Ep.calculer(g));
			System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
			System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
		}
		//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
	}

}
