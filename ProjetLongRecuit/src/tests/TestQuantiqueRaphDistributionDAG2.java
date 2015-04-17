package tests;

import java.io.IOException;


import Lanceurs.LanceurDAG2Graphique;



import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;

import vertexColoring.MutationConflitsAleatoire;



public class TestQuantiqueRaphDistributionDAG2 {

	public static void main(String[] args) throws IOException {
	
		LanceurDAG2Graphique launcher = new LanceurDAG2Graphique();
		
		// Param�tres g�n�raux
		launcher.setEc(new ConflitsCinetiques());
		launcher.setEp(new Conflits());
		launcher.setSeed(250); // G�n�re aussi le Gen
		launcher.setMutation(new MutationConflitsAleatoire());
		
		// Param�tres vertex coloring
		launcher.setNbNoeuds(450);
		launcher.setNbCouleurs(15);
		launcher.setNomGraphe("data/le450_15d.col");
		
		// Param�tres du recuit
		launcher.setK(1);
		launcher.setM(4*launcher.getNbNoeuds()*launcher.getNbCouleurs());
		launcher.setG0(1.8);
		launcher.setP(10);

		launcher.setMaxSteps((int) Math.pow(10,5));
		launcher.setT(0.06);
		
		//Param�tres graphiques
		launcher.setTailleDuSet(100);
		launcher.setEchantillonage(5000);
		launcher.setNomFichier("SortiesGraphiques/Comparaisons/DAG2EnergieFinalePourNombreIterationDonneDonneLe450_15d");
		
		// Lancement
		launcher.lancerNombreIterationNecessairePourAtteindre0();
		//launcher.lancerEnergieFinalePourNombreIterationDonne();
		
		
	}

}
