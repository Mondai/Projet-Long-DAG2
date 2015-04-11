package Lanceurs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import dag3.RecuitTruanderie2Graphique;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;
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


public class LanceurDAG2Graphique {

	// Paramètres généraux
	MutationConflitsAleatoire mutation;
	Conflits Ep;
	ConflitsCinetiques Ec;
	int seed;
	HighQualityRandom gen;

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

	//parametres graphiques
	int echantillonage;
	int tailleDuSet;
	String nomFichier;


	public LanceurDAG2Graphique() {
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

	public void lancerNombreIterationNecessairePourAtteindre0() throws IOException {


		ArrayList<Double> liste = new ArrayList<Double>();
		ArrayList<Integer> listeSucces = new ArrayList<Integer>();

		File f = new File (""+nomFichier);
		PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));

		pw.println("NombreIterationNecessairePourAtteindre0 et fréquence de réussite");
		pw.println();
		pw.println();
		pw.println("%---------------------------------------------------------------------");
		pw.println("taille du set : "+tailleDuSet);
		pw.println("seed : "+seed);
		pw.println("");
		pw.println("paramètres vertex Coloring");
		pw.println("	nom du graphe : "+nomGraphe);
		pw.println("	M : "+M);
		pw.println("	G0 : "+G0);
		pw.println("	maxSteps: "+maxSteps);
		pw.println("	k : "+k);
		pw.println("	P : "+P);
		pw.println();
		pw.println();
		pw.println();
		pw.print("VectNombreIterations =[");
		pw.close();


		for (int i=0;i<tailleDuSet;i++) {

			// Initialisation problème et recuit
			GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
			coloriage.initialiser();
			FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
			ConstanteKConstant Kparam = new ConstanteKConstant(k);
			RecuitQuantique recuit = new RecuitQuantique(Tparam,Kparam, M, T);



			long startTime = System.nanoTime();
			recuit.lancer(coloriage);
			long endTime = System.nanoTime();
			
			liste.add((double) ((endTime-startTime)/1000000000));

			if (recuit.getMeilleureEnergie()==0) {
				listeSucces.add(0);
			} else {
				listeSucces.add(1);
			}


			System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
			System.out.println("============================================================");
			System.out.println("EnergieFinale :"+recuit.getMeilleureEnergie());
			System.out.println("============================================================");
			System.out.println("============================================================");


			pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
			pw.print(recuit.getMutationTentess()+", ");
			pw.close();


		}
		pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
		pw.println("];");
		pw.println("");
		pw.println("temps = "+liste.toString());

		int sum=0;
		for (int i=0;i<listeSucces.size();i++) {
			sum=+listeSucces.get(i);
		}

		pw.println("frequence réussite = "+sum/listeSucces.size());
		pw.close();
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

	public int getEchantillonage() {
		return echantillonage;
	}

	public int getTailleDuSet() {
		return tailleDuSet;
	}

	public String getNomFichier() {
		return nomFichier;
	}

	public void setEchantillonage(int echantillonage) {
		this.echantillonage = echantillonage;
	}

	public void setTailleDuSet(int tailleDuSet) {
		this.tailleDuSet = tailleDuSet;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	




}
