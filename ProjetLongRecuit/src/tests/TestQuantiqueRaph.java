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


public class TestQuantiqueRaph {

	public static void main(String[] args) throws IOException {
	
		LanceurDAG3 launcher = new LanceurDAG3();
		
		// Paramètres généraux
		launcher.setEc(new ConflitsCinetiques());
		launcher.setEp(new Conflits());
		launcher.setSeed(22);
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
		launcher.setMaxSteps((int) Math.pow(10,4));
		launcher.setT(new Temperature(0.35/launcher.getP()));
		
		// Lancement
		launcher.lancer();
		
		
	}

}
