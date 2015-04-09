package tests;

import java.io.IOException;
import java.util.ArrayList;

import modele.Etat;
import parametrage.ParametreGamma;
import parametrage.Temperature;
import recuit.Recuit;
import dag3.Conflits;
import dag3.ConflitsCinetiques;
import dag3.Graphe;
import dag3.GrapheColorie;
import dag3.GrapheColorieParticule;
import dag3.MutationConflitsAleatoire;
import dag3.RecuitTruanderie;
import dag3.Traducteur;



public class TestQuantiqueDag3 {

	public static void main(String[] args) throws IOException {
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
		
		int nbNoeuds = 250;
		int nbCouleurs = 28;
		int k = 1;
		int M = 1;
		double G0 = 0.75;
		int P = 10;
		int maxSteps = (int) Math.pow(10,3);
		int seed = 22;
		Temperature T = new Temperature(0.35/P);
		// construire liste d'etats
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
		ParametreGamma gamma = new ParametreGamma(G0, 0.01, 0) ; // TODO gamma lineaire, car decroissance exponentielle ici
		GrapheColorieParticule coloriage = new GrapheColorieParticule(etats, T, seed, Ec, Ep, gamma, graphe, nbCouleurs);
		System.out.println(coloriage.calculerCompteurCinetique());
		//RecuitTruanderie recuit = new RecuitTruanderie();
		Recuit recuit = new Recuit();
		mutation.maj(coloriage, coloriage.getEtat().get(0));
		System.out.println(coloriage.getEtat().get(0).getEnergie());
		System.out.println("Mutation : " + mutation.calculer(coloriage, coloriage.getEtat().get(0)) );
		mutation.faire(coloriage, coloriage.getEtat().get(0));
		System.out.println(coloriage.getEtat().get(0).getEnergie());
		
		
		long startTime = System.nanoTime();
		try {
			recuit.solution(coloriage, mutation, maxSteps, seed, M);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		
		// affichage du resultat
		
		for (Etat etat : coloriage.getEtat()){
			GrapheColorie g = (GrapheColorie) etat;
			/*
			for (int i = 0; i < graphe.getNombreNoeuds(); i++) {
				System.out.println(i + " -> couleur "
				+ g.getMeilleuresCouleurs()[i]);
				if (g.getNoeudsConflitList().contains(i)) System.out.println("Dessus En conflit");
			}*/
			
			System.out.println("Energie de l'état : " + Ep.calculer(g));
			System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
			System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
		}
		//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
	}

}