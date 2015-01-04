package tests;
import java.io.IOException;

import solver.ConflitsCinetiques;
import solver.EnergieCinetiqueVide;
import solver.GrapheColorie;
import solver.GrapheColorieParticule;
import solver.Conflits;
import solver.Graphe;
import solver.ListEnergie;
import solver.ListEnergieVide;
import solver.MutationAleatoireColoriage;
import solver.MutationConflitsAleatoire;
import solver.RecuitSimule;
import solver.RecuitSimuleExponentiel;
import solver.RecuitSimuleExponentielK;
import solver.RecuitSimuleLineaire;
import solver.Traducteur;
import solverCommun.Etat;
import solverSimuleParametrable.ConstanteKConstant;
import solverSimuleParametrable.RecuitQuantiqueParametrable;
import solverSimuleParametrable.TemperatureLineairePalier;


public class TestQuantique {

	public static void main(String[] args) throws IOException {
		
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		//MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		
		//int echantillonnage=200;
		
		/* test trivial: k=1, Tdeb=1000, Tfin=1, pas=1, N=10.
		Graphe graphe = Traducteur.traduire("data/test_cycle5.col");
		Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
		coloriage.initialiser();
		RecuitSimule recuit = new RecuitSimuleLineaire(1,1000,1,1,10);
		recuit.lancer(coloriage);
		*/
		
		// test avec decroissance de T lineaire: k=7000, Tdeb=1000, Tfin=1, pas=1, N=100.
		// Pour le450_250a: nombre de couleurs theorique 25 donne 2 ou 3 conflits. 26 donne 0 conflit.
		Graphe graphe = Traducteur.traduire("data/le450_25a.col");
		//GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, 25 , 1, graphe);
		//GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, 28 , 5, graphe);
		//coloriage.initialiser();
		//ListEnergie listEnergie = new ListEnergie(echantillonnage, 1000);
		//ListEnergie listProba = new ListEnergie(echantillonnage, 1);
		//ListEnergieVide vide = new ListEnergieVide();
		// RecuitSimule recuit = new RecuitSimuleExponentielPalier(1,0.01,0,0.99,447,1,-1,listEnergie);		
		// RecuitSimule recuit = new RecuitSimuleExponentielPalier(1,0.01,0,0.99,1,447,1,listEnergie);	
		//double pas = 40*((0.5-0.0001)/100000000);
		//RecuitSimule recuit = new RecuitSimuleLineaire(1,0.5,0.0001,pas,40);
		// RecuitSimule recuit = new RecuitSimuleExponentiel(1,10000,0,0.99,10,1000000, listEnergie); // a->0, c->22
		//RecuitSimule recuit = new RecuitSimuleExponentielK(1,10000,0,0.99,10,1000000, listEnergie);  // a->0, c->26
		// RecuitSimule recuit = new RecuitSimuleLineaire(1,1000,0.01,0.1,10, listEnergie);						
		// RecuitSimule recuit = new RecuitSimuleLineaireK(1,1000,0.01,0.1,10, listEnergie);
		int M = 4 ;
		double T0 = 10;
		int maxSteps = (int) Math.pow(10,6); //(int) Math.pow(10,6)
		int k = 1;
		int nbCouleurs = 25;
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , 5, graphe);
		coloriage.initialiser();
		TemperatureLineairePalier Tparam = new TemperatureLineairePalier(T0,0,maxSteps,M);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantiqueParametrable recuit = new RecuitQuantiqueParametrable(Tparam,Kparam, M, 280);
		
		long startTime = System.nanoTime();
		recuit.lancer(coloriage);
		
		//recuit.lancer(coloriage, listEnergie, listProba);
		long endTime = System.nanoTime();
		
		// affichage du resultat
		
		int etati = 1;
		for (Etat etat : coloriage.getEtats()){
			System.out.println("Etat numéro : " + etati);
			etati ++;
			GrapheColorie g = (GrapheColorie) etat;
			/*for (int i = 0; i < graphe.getNombreNoeuds(); i++) {
				/*System.out.println(i + " -> couleur "
						+ g.getMeilleuresCouleurs()[i]);
				System.out.println(i + " -> couleur "
						+ g.getCouleurs()[i]);
				if (g.getNoeudsConflitList().contains(i)) System.out.println("Dessus En conflit");
			}*/
			System.out.println("Energie Potentielle de l'état : " + g.Ep.calculer(g));
			System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
			System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
		}
		//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
		
	}

}
