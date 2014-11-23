package tests;
import java.io.IOException;

import solver.Conflits;
import solver.EnergieCinetiqueVide;
import solver.Graphe;
import solver.GrapheColorieParticule;
import solver.ListEnergieVide;
import solver.MutationAleatoireColoriage;
import solver.MutationConflitsAleatoire;
import solver.RecuitSimule;
import solver.RecuitSimuleLineaire;
import solver.Traducteur;

public class TestComparaison {
	
public static void main(String[] args) throws IOException {
		
	Conflits Ep = new Conflits();
	EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();	
	
	MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
	//MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
	
	Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
	int nbNoeuds = 250;
	int nbCouleurs = 28;
	double k = 1;
	int M = 4 * nbCouleurs ;// * nbNoeuds * nbCouleurs;
	double T0 = 0.35;
	double maxSteps = Math.pow(10,5);
	int seed = 1;
		
	GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs, 1, graphe, seed);
	coloriage.initialiser();
	ListEnergieVide vide = new ListEnergieVide();
	RecuitSimule recuit = new RecuitSimuleLineaire(k,T0,0,T0/maxSteps,M,vide,vide);						
		
	long startTime = System.nanoTime();
	recuit.lancer(coloriage);
	long endTime = System.nanoTime();
	
	System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie()+", Duree = "+(endTime-startTime)/1000000000+" s");
	}
	
}
