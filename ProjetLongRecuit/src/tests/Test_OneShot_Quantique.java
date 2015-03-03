package tests;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import solver.commun.Etat;
import solver.graphique.ListEnergie;
import solver.graphique.ListEnergieVide;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.parametres.FonctionLineairePalier;
import solver.quantique.EnergieCinetiqueVide;
import solver.quantique.RecuitQuantique_Graphique;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorie;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationAleatoireColoriage;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

public class Test_OneShot_Quantique {

	public static void main(String[] args) throws IOException {

		// Paramètres généraux
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();

		// Traduction et paramètres du graphe
		String benchmark = "data/dsjc250.5.col";
		Graphe graphe = Traducteur.traduire(benchmark);
		int nbNoeuds = 250;
		int nbCouleurs = 28;

		//Paramètres du recuit
		int M = 4 * nbNoeuds * nbCouleurs;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,3);
		//int seed = 745262;
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe);
		coloriage.initialiser();
		FonctionLineaire Tparam = new FonctionLineaire(G0,0,maxSteps);
		int k=1;
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantique_Graphique recuit = new RecuitQuantique_Graphique(Tparam,Kparam, M, T);

		// Paramètres graphiques
		int echantillonage=1000;
		String nomFichier= "SortiesGraphiques/Courbes_Quantique/Performance_One_Shot";
		String nomFichierProbas="SortiesGraphiques/Courbes_Quantique/Probas_One_Shot";







		try {
			File f = new File (""+nomFichier);
			File fProbas = new File (""+nomFichierProbas);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			PrintWriter pw2 = new PrintWriter (new BufferedWriter (new FileWriter (fProbas)));

			pw.println("%Sortie Graphique : ");
			pw.println("%Echantillonnage : " + echantillonage);
			pw.println();
			pw.println();
			pw.println("%---------------------------------------------------------------------");
			pw.println("%Benchmark :"+ benchmark);
			pw.println("close all;");
			pw.println("clear all;");
			pw.println("figure;");

			pw2.println("%Sortie Graphique : ");
			pw2.println("%Echantillonnage : " + echantillonage);
			pw2.println();
			pw2.println();
			pw2.println("%---------------------------------------------------------------------");
			pw2.println("%Benchmark :"+ benchmark);
			pw.println("close all;");
			pw2.println("clear all;");
			pw2.println("figure;");


			// Initialisation des listes

			ListEnergie[] listeEnergie = new ListEnergie[P];
			ListEnergie[] listeProbas = new ListEnergie[P];
			ListEnergie listeMeilleureEnergie = new ListEnergie(echantillonage,1);
			ListEnergie listeValeursJr = new ListEnergie(echantillonage,1);
			ListEnergie listeRapport = new ListEnergie(echantillonage,1000);

			for (int j=0; j<P;j++) {
				listeEnergie[j]=new ListEnergie(echantillonage,1);
				listeProbas[j]=new ListEnergie(echantillonage,1);
			}



			// Time et launch
			long startTime = System.nanoTime();
			recuit.lancer(coloriage,listeEnergie,listeProbas,listeMeilleureEnergie,listeValeursJr,listeRapport);
			long endTime = System.nanoTime();

			
			// -----------------------------------------------------------------------------------------------------------------------
			// Partie Matlab
			// -----------------------------------------------------------------------------------------------------------------------

			
			
			pw.println("'duree = "+(endTime-startTime)/1000000000+" s'");
			pw2.println("'duree = "+(endTime-startTime)/1000000000+" s'");
			
			// On trouve la longueur maximale
			int tailleMax=0;
			for (int j=0; j<P;j++) {
				int longueur=listeEnergie[j].getlistEnergie().size();
				if (longueur>tailleMax) { tailleMax=longueur; }
			}
			
			if (tailleMax<listeMeilleureEnergie.getlistEnergie().size()) {
			tailleMax=listeMeilleureEnergie.getlistEnergie().size();
			}
			int tailleMaxProba=0;
			for (int j=0; j<P;j++) {
				int longueur=listeProbas[j].getlistEnergie().size();
				if (longueur>tailleMaxProba) { tailleMaxProba=longueur; }
			}

			// On remplit les autres pour harmoniser les longueurs
			for (int j=0; j<P;j++) {
				int tailleListeActuelle=listeEnergie[j].getlistEnergie().size();
				if (tailleListeActuelle<tailleMax) { 
					System.out.println(""+j);
					System.out.println(listeEnergie[j].getlistEnergie().toString());
					double temp = listeEnergie[j].getlistEnergie().get(listeEnergie[j].getlistEnergie().size()-1);

					for (int i=0;i<tailleMax-tailleListeActuelle;i++) {
						listeEnergie[j].getlistEnergie().add(temp);
					}
				}	
			}
			
			for (int j=0; j<P;j++) {
				int tailleListeActuelle=listeProbas[j].getlistEnergie().size();
				if (tailleListeActuelle<tailleMaxProba) { 
					double temp = listeProbas[j].getlistEnergie().get(listeProbas[j].getlistEnergie().size()-1);

					for (int i=0;i<tailleMaxProba-tailleListeActuelle;i++) {
						listeProbas[j].getlistEnergie().add(temp);
					}
				}	
			}

			// Print des u
			/*
			for (int j=0; j<P;j++) {
				pw.println("u"+j+"="+listeEnergie[j].getlistEnergie().toString()+";");
				pw2.println("u"+j+"="+listeProbas[j].getlistEnergie().toString()+";");
			}


			System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
			
			pw.print("u=[");
			pw2.print("u=[");
			for (int j=0; j< P;j++) {
				pw.print("u"+j+"; ");
				pw2.print("u"+j+"; ");
			}
			pw.println("];");
			pw.println("");
			
			pw2.println("];");
			pw2.println("");

			pw.println("vect="+echantillonage+"*(1:length(u0));");
			pw2.println("vect="+echantillonage+"*(1:length(u0));");

			pw.println("plot(vect,u.')");
			pw2.println("plot(vect,u.')");

			pw.println("title('"+benchmark+"')");
			pw.println("xlabel('Nombre d iterations');");
			pw.println("ylabel('Energie');");

			pw2.println("title('"+benchmark+"')");
			pw2.println("xlabel('Nombre d iterations');");
			pw2.println("ylabel('Proba');");


			
			
			
			// Plot d'une seule courbe
			
			pw.println("figure;");
			pw.println("plot(vect,u0)");
			
			pw.println("title('une courbe sur le "+benchmark+"')");
			pw.println("xlabel('Nombre d iterations');");
			pw.println("ylabel('Energie');");
			
			pw2.println("figure;");
			pw2.println("plot(vect,u0)");
			
			pw2.println("title('Proba d'une courbe sur le "+benchmark+"')");
			pw2.println("xlabel('Nombre d iterations');");
			pw2.println("ylabel('Proba');");
			*/
			
			
			// Plot de la meilleure courbe
			
			pw.println("figure;");
			pw.println("uBest="+listeMeilleureEnergie.getlistEnergie().toString()+";");
			pw.println("vect="+echantillonage+"*(1:length(uBest));");
			pw.println("plot(vect,uBest)");
			
			pw.println("title('meilleure énergie sur le "+benchmark+"')");
			pw.println("xlabel('Nombre d iterations');");
			pw.println("ylabel('Energie');");
			
			// Plot du Jr
			pw.println("figure;");
			pw.println("uJr="+listeValeursJr.getlistEnergie().toString()+";");
			pw.println("vect="+echantillonage+"*(1:length(uBest));");
			pw.println("plot(vect,uJr)");
			
			pw.println("title('valeurs Jr sur le "+benchmark+"')");
			pw.println("xlabel('Nombre d iterations');");
			pw.println("ylabel('Jr');");
			
			// Plot Rapport
			pw.println("figure;");
			pw.println("uRapport="+listeRapport.getlistEnergie().toString()+";");
			pw.println("vect="+echantillonage+"*(1:length(uRapport));");
			pw.println("plot(vect,uRapport)");
			
			pw.println("title('valeurs de deltaEc/deltaEp sur le "+benchmark+"')");
			pw.println("xlabel('Nombre d iterations');");
			pw.println("ylabel('rapport');");
			
			// On ferme
			pw.close();
			pw2.close();
		} catch (IOException exception) {

		}


		// affichage du resultat
		for (Etat etat : coloriage.getEtats()){
			GrapheColorie g = (GrapheColorie) etat;

			System.out.println("Energie de l'état : " + g.Ep.calculer(g));
			System.out.println("Nombre de noeuds en conflits : " + g.nombreNoeudsEnConflit());
			System.out.println("Nombre d'arêtes en conflits : " + g.getNombreConflitsAretes());
		}
		//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());

	}





}


