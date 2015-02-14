package tests;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import solver.commun.IMutation;
import solver.graphique.ListEnergie;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.EnergieCinetiqueVide;
import solver.quantique.RecuitQuantiqueParametrable_Graphique;
import vertexColoring.Conflits;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationAleatoireColoriage;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

/// A effacer peut-être
//
//
//
//
//

public class TestDistribution {


	public static void main(String[] args) throws IOException {

		String nomFichier = "SortiesGraphiques/Distribution";
		

		// Parametres principaux
		int nbCouleurs = 15;
		double Tdebut=0.35;
		double Tfin = 0.0001;  // Très important !!!
		int kinit = 1;
		double facteur = 0.991;
		int tailleFenetre = 100;
	 
		int nbNoeuds = 250;
		
		int N = 4; // *nbNoeuds*nbCouleurs;
		System.out.println(N);
		
		String benchmark = "data/le450_15c.col";
		IMutation mutation = new MutationConflitsAleatoire();
		
		
	
		// Parametres distribution
		int nbPointsHistogramme = 4;
		int tailleEchantillon = 4;
		int echantillonage= 10000;
	
		int nbPoints = 3000000*N; // nombre de points au total sur palier et changement palier
		double pas = N*((Tdebut-Tfin)/nbPoints);
		
		// Recuit
		//RecuitSimule recuit = new RecuitSimuleLineaire(k, Tdebut, Tfin, pas, N);
		
	
		// Création Fichier Texte

		try {
			File f = new File (""+nomFichier);
			File fProbas = new File (""+nomFichier);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			pw.println("%Sortie Graphique : ");
			pw.println("%Taille Echantillon : " + tailleEchantillon);
			pw.println("%Echantillonnage : " + echantillonage);
			pw.println("%Température de départ : " + Tdebut);
			pw.println("%Température de fin : " + Tfin);
			
			// Itérations
			
			
		
				Graphe graphe = Traducteur.traduire(""+benchmark);
				pw.println();
				pw.println();
				pw.println("%---------------------------------------------------------------------");
				pw.println("%Benchmark :"+ benchmark);
				pw.println("clear all;");
				pw.println("figure;");	
						pw.println("");
	
							// Initialisation du Problème
							Conflits Ep = new Conflits();
							EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();
							GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , 1, graphe); 
	
							// Présentation Texte résultats

							pw.println("");
							pw.println("");
							pw.println("%Seed du coloriage : " + coloriage.getSeed());
							pw.println("%"+recuit.toString());
							pw.println("%Nombre d'itération : " + recuit.getNbPoints());
							pw.println("");
						
							// Itération de ce recuit un nombre TailleEchantillon de fois

							for (int i=0; i<tailleEchantillon ; i++) {
								// Lancement du programme
								coloriage.initialiser();
								ListEnergie listEnergie = new ListEnergie(echantillonage,tailleFenetre); 
								ListEnergie listProba = new ListEnergie(echantillonage,tailleFenetre); // taille de la fenetre non utile ici
								recuit.lancer(coloriage,listEnergie,listProba);

								// Ecriture des vecteurs résultats
						
								//pw.println(listEnergie.getlistEnergie().toString());   Vérification de l'atteinte de 0
								pw.println("v"+i+"="+listEnergie.getTaille()+";");
								System.out.println("fin de la "+i+" itération");
								}
							pw.print("v=[");
							for (int j=0; j< tailleEchantillon;j++) {
								pw.print("v"+j+"; ");
								
							}
							pw.println("];");
							//pw.println("v=v./length(v);");
							pw.println("pas=(max(v)-min(v))/"+nbPointsHistogramme);
							pw.println("vect=(min(v):pas:max(v))");
							pw.println("hist("+echantillonage+"*v,"+echantillonage+"*vect);");
									  
									  

						pw.println("hold on;");

						

						System.out.println("fin résultats affichage");

					
					
				
				pw.println("title('"+benchmark+"')");
				pw.println("xlabel('nombre d iteration');");
				pw.println("ylabel('effectif');");
				
				
				
				
			pw.close();
			
			System.out.println("fin ecriture");
		} catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
}











