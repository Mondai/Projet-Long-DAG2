import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class TESTTEST {

	public static void main(String[] args) throws IOException {
		
		double Tdebut=7000;
		double Tfin = 1; // ne marche pas avec 0
		//double facteur = 0.99;
		int N = 10; // nombre it�ration par pallier de T
		int kb=17000;
		int nombrePalliers=1000;
		int echantillonage=300;
		double pas =((double)(Tdebut-Tfin)/((double)nombrePalliers));
		
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		Graphe graphe = Traducteur.traduire("data/le450_25a.col");
		Coloriage coloriage = new Coloriage(energie, mutation, 25 ,graphe);
		//RecuitSimuleExponentiel recuit = new RecuitSimuleExponentiel(kb,Tdebut,Tfin,N,nombrePalliers,echantillonage);
		
		ListEnergie listEnergie = new ListEnergie(echantillonage);
		RecuitSimuleLineaire recuit = new RecuitSimuleLineaire(kb,Tdebut,Tfin,1,N,listEnergie);
		
			
		// SortieGraphique(nombrePalliers,recuit,tailleEchantillon,coloriage,mutation,N,tdebut,tfin, kb)
		SortieGraphique albert = new SortieGraphique(1000,recuit,10,coloriage,10,1000,1,0.3, echantillonage);
		albert.SortieTexte("SortiesGraphiques/test2");
		
		System.out.println(Math.pow(Tfin/Tdebut, 1./nombrePalliers));
		
	}
	
	}