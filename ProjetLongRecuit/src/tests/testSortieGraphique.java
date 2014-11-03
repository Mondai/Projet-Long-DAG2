package tests;
import java.io.IOException;

import solver.Coloriage;
import solver.Conflits;
import solver.Graphe;
import solver.ListEnergie;
import solver.MutationAleatoireColoriage;
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
		int nombrePointsRecuit=10000;
		int echantillonnage=300;
		int nombreEchantillons = 10;
		
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		Graphe graphe = Traducteur.traduire("data/le450_25a.col");
		Coloriage coloriage = new Coloriage(energie, mutation, 25 ,graphe);
		
		ListEnergie listEnergie = new ListEnergie(echantillonnage);
		RecuitSimuleExponentielK recuit = new RecuitSimuleExponentielK(kinit,Tdebut,Tfin,0.80,nombreIterationsParPalliers,nombrePointsRecuit, listEnergie);
		RecuitSimuleLineaireK recuitLin = new RecuitSimuleLineaireK(kinit,Tdebut,Tfin,1,nombreIterationsParPalliers, listEnergie);	
		
		SortieGraphique albert = new SortieGraphique(recuit,nombreEchantillons,coloriage,Tdebut,Tfin,kinit, echantillonnage);
		albert.SortieTexte("SortiesGraphiques/test5");
	
		
	}
	
	}