package tests;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import solver.Conflits;
import solver.EnergieCinetiqueVide;
import solver.Graphe;
import solver.GrapheColorieParticule;
import solver.ListEnergie;
import solver.MutationConflitsAleatoire;
import solver.RecuitSimule;
import solver.RecuitSimuleLineaire;
import solver.Traducteur;
import solverSimuleParametrable.ConstanteKConstant;
import solverSimuleParametrable.RecuitSimuleParametrable;
import solverSimuleParametrable.TemperatureLineaire;
import solverSimuleParametrable.TemperatureLineairePalier;

public class TestComparaison {
	
public static void main(String[] args) throws IOException {
		
	int[] seeds = {4,5,6,7}; //,8,9,10,100,1000,10000,100000,1000000};
	
	for(int i=0; i<seeds.length;i++){
	
	Conflits Ep = new Conflits();
	EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();	
	
	MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
	
	Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
	int nbNoeuds = 450;
	int nbCouleurs = 28;
	double k = 1;
	int M =  (nbNoeuds * nbCouleurs)/100;
	double T0 = 0.6;
	int maxSteps = (int) Math.pow(10,6);
	int seed = seeds[i];
		
	GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs, 1, graphe, seed);
	
	// Ancien RecuitSimule
	coloriage.initialiser();
	RecuitSimule recuit = new RecuitSimuleLineaire(k,T0,0,T0/maxSteps,M);						
	//
	
    ThreadMXBean temp = ManagementFactory.getThreadMXBean( );  // recuperer temps cpu
	long startTime = System.nanoTime();
	long startCpu = temp.getCurrentThreadCpuTime();	
	//recuit.lancer(coloriage);
	
	
	// Avec une proba limite
	int echantillonnage=1000;
	int tailleFenetre=20;
	ListEnergie listEnergie = new ListEnergie(echantillonnage,tailleFenetre); 
	ListEnergie listProba = new ListEnergie(echantillonnage,tailleFenetre); // taille de la fenetre non utile ici
	recuit.lancer(coloriage,listEnergie,listProba,Math.pow(10, -3));
	
	
	long endCpu = temp.getCurrentThreadCpuTime();
	long endTime = System.nanoTime();
	
	System.out.println("seed = "+seed +".  Nombre de conflits : "+recuit.getMeilleureEnergie()+", Duree = "+(endTime-startTime)/1000000000+" s"+", Duree CPU = "+(endCpu-startCpu)/1000000000+" s");
	
	}
	/*
	// Nouveau RecuitSimuleParametrable
	TemperatureLineairePalier Tparam = new TemperatureLineairePalier(T0,0,maxSteps,M);
	ConstanteKConstant Kparam = new ConstanteKConstant(k);
	coloriage.initialiser();
	RecuitSimuleParametrable recuitParam = new RecuitSimuleParametrable(Tparam,Kparam);
	//

	ThreadMXBean temp1 = ManagementFactory.getThreadMXBean(); // recuperer
	// temps cpu
	long startTime1 = System.nanoTime();
	long startCpu1 = temp1.getCurrentThreadCpuTime();
	recuitParam.lancer(coloriage);
	long endCpu1 = temp1.getCurrentThreadCpuTime();
	long endTime1 = System.nanoTime();

	System.out.println("seed = " + seed + ".  Nombre de conflits : "+ recuitParam.meilleureEnergie + ", Duree = "
			+ (endTime1 - startTime1) / 1000000000 + " s" + ", Duree CPU = "+ (endCpu1 - startCpu1) / 1000000000 + " s");
	*/
}

}
