package Lanceurs;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import dag3.GrapheColorie;
import modele.*;
import parametrage.*;
import mutation.*;
import dag3.Conflits;
import dag3.ConflitsCinetiques;
import dag3.Graphe;
import dag3.GrapheColorieParticule;
import dag3.MutationConflitsAleatoire;
import dag3.RecuitTruanderie2;
import dag3.Traducteur;

public class LanceurDAG3 {



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
	Temperature T;




	// Tout sera initialisé dans la classe de test
	public LanceurDAG3() {
	}

	// Une fois l'initialisation faite on lance

	public void lancer() throws IOException {

		// Initialisation des états car bizzare dans leur recuit
		ArrayList<Etat> etats = new ArrayList<Etat>();
		for(int i=0;i<P;i++){
			GrapheColorie e = new GrapheColorie(Ep, nbCouleurs, graphe, seed);
			e.initialiser();
			etats.add(e);
		}
		etats.get(0).setprevious(etats.get(P-1));
		etats.get(0).setnext(etats.get(1));
		for(int i =1; i<P-1;i++){
			etats.get(i).setprevious(etats.get(i-1));
			etats.get(i).setnext(etats.get(i+1));
		}
		etats.get(P-1).setprevious(etats.get(P-2));
		etats.get(P-1).setnext(etats.get(0));

		// fin construire liste etats

		ParametreGamma gamma = new ParametreGamma(G0, G0/maxSteps, 0) ;
		GrapheColorieParticule coloriage = new GrapheColorieParticule(etats, T, seed, Ec, Ep, gamma, graphe, nbCouleurs);
		RecuitTruanderie2 recuit = new RecuitTruanderie2();

		//TODO
		System.out.println();
		System.out.println("seed "+seed);

		long startTime = System.nanoTime();
		try {
			recuit.solution(coloriage, mutation, maxSteps, seed, M);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.nanoTime();

	}

	public IMutation getMutation() {
		return mutation;
	}

	public EnergiePotentielle getEp() {
		return Ep;
	}

	public EnergieCinetique getEc() {
		return Ec;
	}

	public int getSeed() {
		return seed;
	}

	public int getNbNoeuds() {
		return nbNoeuds;
	}

	public int getNbCouleurs() {
		return nbCouleurs;
	}

	public String getNomGraphe() {
		return nomGraphe;
	}

	public int getK() {
		return k;
	}

	public int getM() {
		return M;
	}

	public double getG0() {
		return G0;
	}

	public int getP() {
		return P;
	}

	public int getMaxSteps() {
		return maxSteps;
	}

	public Temperature getT() {
		return T;
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
		this.graphe= Traducteur.traduire(nomGraphe);
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

	public void setT(Temperature t) {
		T = t;
	}







}
