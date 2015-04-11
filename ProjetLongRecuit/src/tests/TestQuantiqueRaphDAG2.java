package tests;
import java.io.IOException;

import Lanceurs.LanceurDAG2;


import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;

import vertexColoring.MutationConflitsAleatoire;


public class TestQuantiqueRaphDAG2 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		
		LanceurDAG2 launcherDAG2 = new LanceurDAG2();
		
		// Paramètres généraux
		launcherDAG2.setEc(new ConflitsCinetiques());
		launcherDAG2.setEp(new Conflits());
		launcherDAG2.setSeed(22);
		launcherDAG2.setMutation(new MutationConflitsAleatoire());
		
		// Paramètres vertex coloring
		launcherDAG2.setNbNoeuds(250);
		launcherDAG2.setNbCouleurs(28);
		launcherDAG2.setNomGraphe("data/dsjc250.5.col");
		
		// Paramètres du recuit
		launcherDAG2.setK(1);
		launcherDAG2.setM(4*launcherDAG2.getNbNoeuds()*launcherDAG2.getNbCouleurs());
		launcherDAG2.setG0(0.75);
		launcherDAG2.setP(10);
		launcherDAG2.setMaxSteps((int) Math.pow(10,4));
		launcherDAG2.setT(0.035);
		
		// Lancement
		launcherDAG2.lancer();
		
}
}
