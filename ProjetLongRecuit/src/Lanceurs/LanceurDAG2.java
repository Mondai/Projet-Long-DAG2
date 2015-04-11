package Lanceurs;

import java.io.IOException;
import java.util.ArrayList;
import solver.commun.Etat;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantique;
import solver.quantique.RecuitQuantiqueAccelere;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorie;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.RecuitQuantiqueAccelereVC;
import vertexColoring.Traducteur;


public class LanceurDAG2 {

	// Paramètres généraux
		MutationConflitsAleatoire mutation;
		Conflits Ep;
		ConflitsCinetiques Ec;
		int seed;

		// Paramètres vertex Coloring
		int nbNoeuds;
		int nbCouleurs;
		String nomGraphe;
		Graphe graphe;

		//Paramètres du recuit
		int k;
		int M;
		double G0;
		int P;
		int maxSteps;
		double T;
	
		
		
		public LanceurDAG2() {
		}

		// Une fois l'initialisation faite on lance

		public void lancer() throws IOException {

			GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
			coloriage.initialiser();
			FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
			ConstanteKConstant Kparam = new ConstanteKConstant(k);
			RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);
			
			long startTime = System.nanoTime();
			recuit.lancer(coloriage);
			long endTime = System.nanoTime();
			
			// affichage du resultat
			for (Etat etat : coloriage.getEtats()){
				GrapheColorie g = (GrapheColorie) etat;
				System.out.println("Energie de l'état : " + g.Ep.calculer(g));
				System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
				System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
			}
			//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());
			System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
		}

		public void setMutation(MutationConflitsAleatoire mutation) {
			this.mutation = mutation;
		}

		public void setEp(Conflits ep) {
			Ep = ep;
		}

		public void setEc(ConflitsCinetiques ec) {
			Ec = ec;
		}

		public void setSeed(int seed) {
			this.seed = seed;
		}

		public void setNbNoeuds(int nbNoeuds) {
			this.nbNoeuds = nbNoeuds;
		}

		public void setNbCouleurs(int nbCouleurs) {
			this.nbCouleurs = nbCouleurs;
		}

		public void setNomGraphe(String nomGraphe) throws IOException {
			this.nomGraphe = nomGraphe;
			this.graphe=Traducteur.traduire(nomGraphe);
		}

		public void setGraphe(Graphe graphe) {
			this.graphe = graphe;
		}

		public void setK(int k) {
			this.k = k;
		}

		public void setM(int m) {
			M = m;
		}

		public void setG0(double g0) {
			G0 = g0;
		}

		public void setP(int p) {
			P = p;
		}

		public void setMaxSteps(int maxSteps) {
			this.maxSteps = maxSteps;
		}

		public void setT(double t) {
			T = t;
		}

		public int getNbNoeuds() {
			return nbNoeuds;
		}

		public int getNbCouleurs() {
			return nbCouleurs;
		}

		public int getP() {
			return P;
		}

	
		
		
		

}
