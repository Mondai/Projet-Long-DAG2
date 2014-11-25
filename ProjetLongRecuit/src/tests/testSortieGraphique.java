package tests;
import java.io.IOException;

import solver.Conflits;
import solver.EnergieCinetiqueVide;
import solver.Graphe;
import solver.GrapheColorieParticule;
import solver.ListEnergie;
import solver.MutationAleatoireColoriage;
import solver.RecuitSimule;
import solver.RecuitSimuleExponentielK;
import solver.RecuitSimuleLineaireK;
import solver.Traducteur;


public class testSortieGraphique {

	public static void main(String[] args) throws IOException {
		
		double Tdebut=3000;
		double Tfin = 0;
		//double facteur = 0.99;
		int nombreIterationsParPalliers = 1;
		int kinit = 1000;
		int nombrePointsRecuit=100000;
		int echantillonnage=300;
		int nombreEchantillons = 10;
		
		Conflits Ep = new Conflits();
		EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		Graphe graphe = Traducteur.traduire("data/le450_15b.col");
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, 25 , 1, graphe);
		//Coloriage coloriage = new Coloriage(energie, mutation, 15 ,graphe);
		
		ListEnergie listEnergie = new ListEnergie(echantillonnage, 1000);
		ListEnergie listProba = new ListEnergie(echantillonnage, 1);
		RecuitSimuleExponentielK recuit = new RecuitSimuleExponentielK(kinit,Tdebut,Tfin,0.99,nombreIterationsParPalliers,nombrePointsRecuit);
		RecuitSimuleLineaireK recuitLin = new RecuitSimuleLineaireK(kinit,Tdebut,Tfin,1,nombreIterationsParPalliers);	
		
		SortieGraphique albert = new SortieGraphique(recuit,nombreEchantillons,coloriage,Tdebut,Tfin,kinit, echantillonnage, listEnergie, listProba);
		albert.SortieTexte("SortiesGraphiques/test5");
	
		
	}
	
	}