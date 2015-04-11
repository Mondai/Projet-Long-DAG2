package tests;

import java.io.IOException;


import Lanceurs.LanceurDAG2Graphique;



import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;

import vertexColoring.MutationConflitsAleatoire;



public class TestQuantiqueRaphDistributionDAG2 {

	public static void main(String[] args) throws IOException {
	
		LanceurDAG2Graphique launcher = new LanceurDAG2Graphique();
		
		// Paramètres généraux
		launcher.setEc(new ConflitsCinetiques());
		launcher.setEp(new Conflits());
		launcher.setSeed(22); // Génère aussi le Gen
		launcher.setMutation(new MutationConflitsAleatoire());
		
		// Paramètres vertex coloring
		launcher.setNbNoeuds(250);
		launcher.setNbCouleurs(28);
		launcher.setNomGraphe("data/dsjc250.5.col");
		
		// Paramètres du recuit
		launcher.setK(1);
		launcher.setM(4*launcher.getNbNoeuds()*launcher.getNbCouleurs());
		launcher.setG0(0.75);
		launcher.setP(10);
		launcher.setMaxSteps((int) Math.pow(10,1));
		launcher.setT(0.035);
		
		//Paramètres graphiques
		launcher.setTailleDuSet(10);
		launcher.setEchantillonage(5000);
		launcher.setNomFichier("SortiesGraphiques/Comparaisons/DAG2EnergieFinalePourNombreIterationDonne");
		
		// Lancement
		launcher.lancerNombreIterationNecessairePourAtteindre0();
		//launcher.lancerEnergieFinalePourNombreIterationDonne();
		
		
	}

}
