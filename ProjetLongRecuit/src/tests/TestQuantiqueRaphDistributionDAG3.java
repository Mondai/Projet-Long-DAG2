package tests;

import java.io.IOException;

import mutation.IMutation;
import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;
import parametrage.Temperature;
import dag3.Conflits;
import dag3.ConflitsCinetiques;
import dag3.Graphe;
import dag3.MutationConflitsAleatoire;
import Lanceurs.LanceurDAG3;
import Lanceurs.LanceurDAG3Graphique;


public class TestQuantiqueRaphDistributionDAG3 {

	public static void main(String[] args) throws IOException {
	
		LanceurDAG3Graphique launcher = new LanceurDAG3Graphique();
		
		// Paramètres généraux
		launcher.setEc(new ConflitsCinetiques());
		launcher.setEp(new Conflits());
		launcher.setSeed(1645); // Génère aussi le Gen
		launcher.setMutation(new MutationConflitsAleatoire());
		
		// Paramètres vertex coloring
		launcher.setNbNoeuds(450);
		launcher.setNbCouleurs(15);
		launcher.setNomGraphe("data/le450_15d.col");
		
		// Paramètres du recuit
		launcher.setK(1);
		launcher.setM(4*launcher.getNbNoeuds()*launcher.getNbCouleurs());
		launcher.setG0(0.75);
		launcher.setP(10);
		launcher.setMaxSteps((int) Math.pow(10,4));
		launcher.setT(new Temperature(0.35/launcher.getP()));
		
		//Paramètres graphiques
		launcher.setTailleDuSet(100);
		launcher.setEchantillonage(5000);
		launcher.setNomFichier("SortiesGraphiques/Comparaisons/DAG3EnergieFinalePourNombreIterationDonneLe450_15d");
		
		// Lancement
		launcher.lancerNombreIterationNecessairePourAtteindre0();
		//launcher.lancerEnergieFinalePourNombreIterationDonne();
		
		
	}

}
