package tests;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import solver.Conflits;
import solver.EnergieCinetiqueVide;
import solver.Graphe;
import solver.GrapheColorieParticule;
import solver.ListEnergie;
import solver.MutationAleatoireColoriage;
import solver.MutationConflitsAleatoire;
import solver.RecuitSimule;
import solver.RecuitSimuleExponentiel;
import solver.RecuitSimuleExponentielK;
import solver.RecuitSimuleLineaire;
import solver.RecuitSimuleLineaireK;
import solver.Traducteur;
import solverCommun.IMutation;


public class TestDistribution {


	public static void main(String[] args) throws IOException {

		String nomFichier = "SortiesGraphiques/Distribution";
		

		// Parametres principaux
		int nbCouleurs = 25;
		double Tdebut=0.35;
		double Tfin = 0.0001;  // Tr�s important !!!
		int kinit = 1;
		double facteur = 0.991;
		int N = 4;
		int tailleFenetre = 100;
		int nbPoints = 300000;  // nombre de points au total sur palier et changement palier
		double pas = N*((Tdebut-Tfin)/nbPoints);
		String benchmark = "data/le450_15b.col";
		IMutation mutation = new MutationConflitsAleatoire();
		double k=1.5;
		
		// Recuit
		RecuitSimule recuit = new RecuitSimuleLineaire(k, Tdebut, Tfin, pas, N);
		
		// Parametres distribution
		int nbPointsHistogramme = 50;
		int tailleEchantillon = 1000;
		int echantillonnage=1;


		// Cr�ation Fichier Texte

		try {
			File f = new File (""+nomFichier);
			File fProbas = new File (""+nomFichier);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			pw.println("%Sortie Graphique : ");
			pw.println("%Taille Echantillon : " + tailleEchantillon);
			pw.println("%Echantillonnage : " + echantillonnage);
			pw.println("%Temp�rature de d�part : " + Tdebut);
			pw.println("%Temp�rature de fin : " + Tfin);
			
			// It�rations
			
			
		
				Graphe graphe = Traducteur.traduire(""+benchmark);
				pw.println();
				pw.println();
				pw.println("%---------------------------------------------------------------------");
				pw.println("%Benchmark :"+ benchmark);
				pw.println("clear all;");
				pw.println("figure;");	
						pw.println("");
	
							// Initialisation du Probl�me
							Conflits Ep = new Conflits();
							EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();
							GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , 1, graphe); 
	
							// Pr�sentation Texte r�sultats

							pw.println("");
							pw.println("");
							pw.println("%Seed du coloriage : " + coloriage.getSeed());
							pw.println("%"+recuit.toString());
							pw.println("%Nombre d'it�ration : " + recuit.getNbPoints());
							pw.println("");
						
							// It�ration de ce recuit un nombre TailleEchantillon de fois

							for (int i=0; i<tailleEchantillon ; i++) {
								// Lancement du programme
								coloriage.initialiser();
								ListEnergie listEnergie = new ListEnergie(echantillonnage,tailleFenetre); 
								ListEnergie listProba = new ListEnergie(echantillonnage,tailleFenetre); // taille de la fenetre non utile ici
								recuit.lancer(coloriage,listEnergie,listProba);

								// Ecriture des vecteurs r�sultats
						
								pw.println("v"+i+"="+listEnergie.getTaille()+";");

								}
							pw.print("v=[");
							for (int j=0; j< tailleEchantillon;j++) {
								pw.print("v"+j+"; ");
								
							}
							pw.println("];");
							//pw.println("v=v./length(v);");
							pw.println("pas=(max(v)-min(v))/"+nbPointsHistogramme);
							pw.println("vect=(min(v):pas:max(v))");
							pw.println("hist("+N+"*v,"+N+"*vect);");
									  
									  

						pw.println("hold on;");

						

						System.out.println("fin r�sultats affichage");

					
					
				
				pw.println("title('"+benchmark+"')");
				pw.println("xlabel('effectif');");
				pw.println("ylabel('nombre d iteration');");
				
				
				
				
			pw.close();
			
			System.out.println("fin ecriture");
		} catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
}










