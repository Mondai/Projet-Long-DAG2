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
import solver.RecuitSimule;
import solver.RecuitSimuleExponentiel;
import solver.RecuitSimuleExponentielK;
import solver.RecuitSimuleLineaire;
import solver.RecuitSimuleLineaireK;
import solver.Traducteur;
import solverCommun.IMutation;


public class TestGenerationCourbes {


	public static void main(String[] args) throws IOException {

		String nomFichier = "SortiesGraphiques/Courbes2";
		String nomFichierProbas = "SortiesGraphiques/Courbes2Probas";

		// Parametres principaux

		
		double Tdebut=7000;
		double Tfin = 2;  // Très important !!!
		int kinit = 1000;

		int echantillonnage=200;

		int tailleEchantillon = 50;
		double facteur = 0.99;
		int N=10;
		int tailleFenetre = 100;

			// Nombre de points important car on veux comparer pour le meme nombre d'itérations

		int nbPoints = 50000;  // nombre de points au total sur palier et changement palier
		double pas = N*((Tdebut-Tfin+1)/nbPoints);
		System.out.println(pas);
		
		
		
		// Initialisation de la matrice de couleurs
		
		String[][] matriceCouleurs = new String[17][7];
		String[] couleursBasiques = {"k","b","r","c","g","m","y"};
		String[] lineSpecBasiques = {"-","--",":","-.","o","+","*",".","x","s","d","^","v",">","<","p","h"};
		
		for (int q=0;q<17;q++) {
			for (int s=0;s<7;s++) {
				matriceCouleurs[q][s]=couleursBasiques[s].concat(lineSpecBasiques[q]);
			}
		}
		
		//Initialisation des listes à itérer
		// Liste des benchmarks
		LinkedList<String> listBenchmarks = new LinkedList();
		listBenchmarks.add("data/le450_25a.col");
		//listBenchmarks.add("data/le450_25b.col");
		//listBenchmarks.add("le450_25c.col");
		//listBenchmarks.add("le450_25d.col");


		// Liste des recuits
		LinkedList<RecuitSimule> listRecuits = new LinkedList();
		listRecuits.add(new RecuitSimuleExponentielK());
		listRecuits.add(new RecuitSimuleExponentiel());
		
		
		// Liste des Mutations
		LinkedList<IMutation> listMutations = new LinkedList();
		listMutations.add( new MutationAleatoireColoriage());

		// Liste des k
		LinkedList<Double> listK = new LinkedList();
		listK.add(1.);
		//listK.add(100.);
		//listK.add(10000.);
		//listK.add(0.01);
		listK.add(0.001);
		//listK.add(0.0000001);
		

		// Création Fichier Texte

		try {
			File f = new File (""+nomFichier);
			File fProbas = new File (""+nomFichierProbas);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			PrintWriter pw2 = new PrintWriter (new BufferedWriter (new FileWriter (fProbas)));
			
			pw.println("%Sortie Graphique : ");
			pw.println("%Taille Echantillon : " + tailleEchantillon);
			pw.println("%Echantillonnage : " + echantillonnage);
			pw.println("%Température de départ : " + Tdebut);
			pw.println("%Température de fin : " + Tfin);
			
			pw2.println("%Sortie Graphique : ");
			pw2.println("%Taille Echantillon : " + tailleEchantillon);
			pw2.println("%Echantillonnage : " + echantillonnage);
			pw2.println("%Température de départ : " + Tdebut);
			pw2.println("%Température de fin : " + Tfin);

			// Itérations

			for (String benchmark : listBenchmarks) {
				
				
				
				
				// Réinitialisation Matice Couleurs
				
				int compteurCouleur1 = 0;
				int compteurCouleur2 = 0;
				
				LinkedList legende = new LinkedList();
				LinkedList legende2 = new LinkedList();
				
				
				
				Graphe graphe = Traducteur.traduire(""+benchmark);
				pw.println();
				pw.println();
				pw.println("%---------------------------------------------------------------------");
				pw.println("%Benchmark :"+ benchmark);
				pw.println("clear all;");
				pw.println("figure;");
				
				pw2.println();
				pw2.println();
				pw2.println("%---------------------------------------------------------------------");
				pw2.println("%Benchmark :"+ benchmark);
				pw2.println("clear all;");
				pw2.println("figure;");
				

				for (IMutation mutation : listMutations) {
					
					for (RecuitSimule recuit : listRecuits) {
						
						pw.println("");
						pw2.println("");
						
						
						
						
						//	for (double k : listK) {  // Disjonction de cas K ou pas K
						Iterator it = listK.iterator();
						Boolean bool = true;
						
						while (it.hasNext() && bool) {
						double k=(double) it.next();
								pw.println("			%Constante k : " + k);
								pw2.println("			%Constante k : " + k);

							// Initialisation du Problème
							Conflits Ep = new Conflits();
							EnergieCinetiqueVide Ec = new EnergieCinetiqueVide();
							ListEnergie listEnergie = new ListEnergie(echantillonnage,tailleFenetre); 
							ListEnergie listProba = new ListEnergie(echantillonnage,1); // taille de la fenetre non utile ici
							GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, 25 , 1, graphe);
							
							// Paramétrisation du recuit demandé
						
							if (recuit.toString() == "Recuit Simulé Exponentiel") {
								recuit = new RecuitSimuleExponentiel( k, Tdebut, Tfin, facteur, N, nbPoints,listEnergie,listProba);
							}
							else if (recuit.toString() == "Recuit Simulé Linéaire") {
								recuit = new RecuitSimuleLineaire( k, Tdebut, Tfin, pas, N, listEnergie,listProba);
							}
							else if (recuit.toString() == "Recuit Simulé Exponentiel avec k l energie moyenne") {
								recuit = new RecuitSimuleExponentielK( k, Tdebut, Tfin, facteur, N, nbPoints,listEnergie,listProba);
								bool = false;
							}
							else if (recuit.toString() == "Recuit Simulé Linéaire avec k l énergie moyenne") {
								recuit = new RecuitSimuleLineaireK( k, Tdebut, Tfin, pas, N, listEnergie,listProba);
								bool = false;
							}


							// Présentation Texte résultats

							pw.println("");
							pw.println("");
							pw.println("%Seed du coloriage : " + coloriage.getSeed());
							pw.println("%"+recuit.toString());
							pw.println("%Nombre d'itération : " + recuit.getNbPoints());
							pw.println("");
							
							pw2.println("");
							pw2.println("");
							pw2.println("%Seed du coloriage : " + coloriage.getSeed());
							pw2.println("%"+recuit.toString());
							pw2.println("%Nombre d'itération : " + recuit.getNbPoints());
							pw2.println("");



							// Itération de ce recuit un nombre TailleEchantillon de fois

							for (int i=0; i<tailleEchantillon ; i++) {
								// Lancement du programme
								coloriage.initialiser();
								recuit.lancer(coloriage);


								// Ecriture des vecteurs résultats
								pw.print("u"+i+"=");
								List<Double> list = recuit.getListEnergie().getlistEnergie();
								pw.print(list.toString());
								System.out.println(list.get(list.size()-1));
								pw.print(";");
								pw.println("");
								
								pw2.print("u"+i+"=");
								List<Double> list2 = recuit.getListProba().getlistEnergie();
								pw2.print(list2.toString());
								System.out.println(list2.get(list.size()-1));
								pw2.print(";");
								pw2.println("");
							}

							pw.print("u=[");
							pw2.print("u=[");
							for (int j=0; j< tailleEchantillon;j++) {
								pw.print("u"+j+"; ");
								pw2.print("u"+j+"; ");
							}
							pw.println("];");
							pw.println("vect="+echantillonnage+"*(1:length(u0));");
							pw.println("taille = length(u0);");
							pw.println("umean=zeros(taille,1);");
							pw.println("uintervalleincertitude = zeros(taille,2);");
							pw.println("for i = 1:taille;");
									  pw.println("			umean(i) = mean(u(:,i));");
									  pw.println("			nombredeVecteurs=length(u(:,1));");
									  pw.println( "			uincertitude(i) = std(u(:,i))/sqrt(nombredeVecteurs);");
									  pw.println("			uintervalleincertitude(i,1) = umean(i) + uincertitude(i)*1.96;");
									  pw.println("			uintervalleincertitude(i,2) = umean(i) - uincertitude(i)*1.96;");
									  pw.println("			end;");
									  
									  
						pw.println("plot(vect,uintervalleincertitude,'"+ matriceCouleurs[compteurCouleur2][compteurCouleur1]+"');");
						pw.println("hold on;");
						
						legende.add("['"+recuit.toString()+", k="+k+" up']");
						legende.add("['"+recuit.toString()+", k="+k+" down']");
						
						
						pw2.println("];");
						pw2.println("vect="+echantillonnage+"*(1:length(u0));");
						pw2.println("taille = length(u0);");
						pw2.println("umean=zeros(taille,1);");
						pw2.println("uintervalleincertitude = zeros(taille,2);");
						pw2.println("for i = 1:taille;");
								  pw2.println("			umean(i) = mean(u(:,i));");
								  pw2.println("			nombredeVecteurs=length(u(:,1));");
								  pw2.println( "			uincertitude(i) = std(u(:,i))/sqrt(nombredeVecteurs);");
								  pw2.println("			uintervalleincertitude(i,1) = umean(i) + uincertitude(i)*1.96;");
								  pw2.println("			uintervalleincertitude(i,2) = umean(i) - uincertitude(i)*1.96;");
								  pw2.println("			end;");
								  
								  
					pw2.println("plot(vect,uintervalleincertitude,'"+ matriceCouleurs[compteurCouleur2][compteurCouleur1]+"');");
					pw2.println("hold on;");
					
					legende2.add("[' proba "+recuit.toString()+", k="+k+" up']");
					legende2.add("[' proba "+recuit.toString()+", k="+k+" down']");
						
						
						
						
						
						
						
						
						
						if (compteurCouleur1==7) {
							compteurCouleur1=0;
						} else { 
							compteurCouleur1++;
						}
						
						System.out.println(compteurCouleur1);
						System.out.println(compteurCouleur2);
						}
							
							
						System.out.println("fin résultats affichage");
						if (compteurCouleur2==17) {
							compteurCouleur2=0;
						} else { 
							compteurCouleur2++;
						}
						
						
						
						
					}	
					
				}
				// Energie potentielle
				pw.println("title('"+benchmark+"')");
				pw.println("xlabel('Nombre d iterations');");
				pw.println("ylabel('Energie');");
				
				int u = legende.toString().length();
				
				pw.print( "legend({" );
				pw.print(""+legende.toString().subSequence(1, u-1));
				System.out.println(legende.toString());
				pw.print("});");
				
				// probas
				pw2.println("title('"+benchmark+"')");
				pw2.println("xlabel('Nombre d iterations');");
				pw2.println("ylabel('Proba');");
				
				int u2 = legende.toString().length();
				
				pw2.print( "legend({" );
				pw2.print(""+legende.toString().subSequence(1, u-1));
				System.out.println(legende.toString());
				pw2.print("});");
				
			}
			
			
			//legend({'recuit1'});
			pw.close();
			pw2.close();
			System.out.println("fin ecriture");
		} catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
}











