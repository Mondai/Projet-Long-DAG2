package tests;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
import solverSimuleParametrable.TemperatureLineaire;
import solverSimuleParametrable.TemperatureLineairePalier;
import solverSimuleParametrable.RecuitQuantiqueParametrable_Graphique;

public class Test_Comparaison_QuantiqueSimule {

	public static void main(String[] args) throws IOException {

		// Paramètres généraux
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();

		// Traduction et paramètres du graphe
		String benchmark = "data/dsjc250.5.col";
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
		int nbNoeuds = 250;
		int nbCouleurs = 28;

		//Paramètres du recuit quantique
		int M = 4 * nbNoeuds * nbCouleurs;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,1);

		// Paramètres recuit simulé
		double Tdebut=0.35;
		double Tfin = 0.02;  
		int k = 1;
		int N = M;
		int nbPoints = 300000;  // nombre de points au total sur palier et changement palier
		// 100 fois moins de points que ce qu'il devrait y avoir
		double pas = N*((Tdebut-Tfin)/nbPoints);




		// Paramètres graphiques
		int echantillonage=1000;
		String nomFichier= "SortiesGraphiques/Courbes_Quantique/Comparaison";
		int tailleEchantillon=2;



		try {
			File f = new File (""+nomFichier);

			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));


			pw.println("%Sortie Graphique : ");
			pw.println("%Echantillonnage : " + echantillonage);
			pw.println();
			pw.println();
			pw.println("%---------------------------------------------------------------------");
			pw.println("%Benchmark :"+ benchmark);
			pw.println("close all;");
			pw.println("clear all;");
			pw.println("figure;");



			// Initialisation des listes


			ListEnergie[] uBest=new ListEnergie[tailleEchantillon];
			ListEnergie[] vBest=new ListEnergie[tailleEchantillon];

			
			for (int i=0; i<tailleEchantillon ; i++) {

				// Initialisation recuit
				TemperatureLineaire Tparam = new TemperatureLineaire(G0,0,maxSteps);
				ConstanteKConstant Kparam = new ConstanteKConstant(k);
				RecuitQuantiqueParametrable_Graphique recuit = new RecuitQuantiqueParametrable_Graphique(Tparam,Kparam, M, T);
				ListEnergie listeMeilleureEnergie = new ListEnergie(echantillonage,1);

				//Initialisation coloriage
				int seed = (int) (10000*Math.random());
				System.out.println("seed :"+seed);
				GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
				coloriage.initialiser();

				// Time et launch
				long startTime = System.nanoTime();
				recuit.lancer(coloriage,listeMeilleureEnergie);
				long endTime = System.nanoTime();



				pw.println("'duree = "+(endTime-startTime)/1000000000+" s'");


				// On trouve la longueur maximale


				// Plot de la meilleure courbe
				System.out.println("c'est l chantillon numéro"+i);
				uBest[i]=listeMeilleureEnergie;
				System.out.println(listeMeilleureEnergie.getlistEnergie().toString());


				System.out.println("fin d'un echantillon");
			}
			System.out.println("fin recuits quantiques");
			

			for (int i=0; i<tailleEchantillon ; i++) {

				// Initialisation recuit simulé
				RecuitSimuleLineaire recuitSimule = new RecuitSimuleLineaire( k, Tdebut, Tfin, pas, N);
				ListEnergie listeMeilleureEnergie = new ListEnergie(echantillonage,1);
				ListEnergie listeProba = new ListEnergie(echantillonage,1);
				
				//Initialisation coloriage
				int seed = (int) (10000*Math.random());
				System.out.println("seed :"+seed);
				GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
				coloriage.initialiser();

				// Time et launch
				long startTime = System.nanoTime();
				recuitSimule.lancer(coloriage,listeMeilleureEnergie);
				long endTime = System.nanoTime();

				pw.println("'duree = "+(endTime-startTime)/1000000000+" s'");


				// On trouve la longueur maximale


				// Plot de la meilleure courbe
				System.out.println("c'est l chantillon numéro"+i);
				vBest[i]=listeMeilleureEnergie;
				System.out.println(listeMeilleureEnergie.getlistEnergie().toString());


				System.out.println("fin d'un echantillon");
			}

			System.out.println("fin recuits simulés");
			
			
			

			// -----------------------------------------------------------------------------------------------------------------------
			// Partie Matlab
			// -----------------------------------------------------------------------------------------------------------------------

			
			
			// Adaptation taille
			int tailleMax=0;
			
			// On trouve la longueur maximale de recuit et quantique

			for (int j=0; j<tailleEchantillon;j++) {
				int longueur=uBest[j].getlistEnergie().size();
				System.out.println("la longueur est"+longueur);
				if (longueur>tailleMax) { tailleMax=longueur; }
			}
			System.out.println("taille max que quantique"+tailleMax);
			
			for (int j=0; j<tailleEchantillon;j++) {
				int longueur=vBest[j].getlistEnergie().size();
				System.out.println("la longueur est"+longueur);
				if (longueur>tailleMax) { tailleMax=longueur; }
			}
			System.out.println("taille max avec simulé"+tailleMax);
			
			
			
			// On remplit les autres du quantique pour harmoniser les longueurs
			for (int j=0; j<tailleEchantillon;j++) {
				int tailleListeActuelle=uBest[j].getlistEnergie().size();
				if (tailleListeActuelle<tailleMax) { 
					System.out.println(""+j);
					System.out.println(""+uBest[j].getlistEnergie().get(uBest[j].getlistEnergie().size()-1));
					double temp = uBest[j].getlistEnergie().get(uBest[j].getlistEnergie().size()-1);

					for (int z=0;z<tailleMax-tailleListeActuelle;z++) {
						uBest[j].getlistEnergie().add(temp);
					}
				}	
			}



			// On remplit les autres du simulé pour harmoniser les longueurs
			for (int j=0; j<tailleEchantillon;j++) {
				int tailleListeActuelle=vBest[j].getlistEnergie().size();
				if (tailleListeActuelle<tailleMax) { 
					System.out.println(""+j);
					System.out.println(""+vBest[j].getlistEnergie().get(vBest[j].getlistEnergie().size()-1));
					double temp = vBest[j].getlistEnergie().get(vBest[j].getlistEnergie().size()-1);

					for (int z=0;z<tailleMax-tailleListeActuelle;z++) {
						vBest[j].getlistEnergie().add(temp);
					}
				}	
			}







			// Print des u pour représenter le recuit quantique
			for (int i=0;i<tailleEchantillon;i++) {

				pw.println("uBest"+i+"="+uBest[i].getlistEnergie().toString()+";");
			}


			pw.print("u=[");

			for (int j=0; j< tailleEchantillon;j++) {
				pw.print("uBest"+j+"; ");

			}
			pw.println("];");
			pw.println("vect="+echantillonage+"*(1:"+tailleMax+");");
			pw.println("taille = "+tailleMax+";");
			pw.println("umean=zeros(taille,1);");
			pw.println("uintervalleincertitude = zeros(taille,2);");
			pw.println("for i = 1:taille;");
			pw.println("			umean(i) = mean(u(:,i));");
			pw.println("			nombredeVecteurs=length(u(:,1));");
			pw.println( "			uincertitude(i) = std(u(:,i))/sqrt(nombredeVecteurs);");
			pw.println("			uintervalleincertitude(i,1) = umean(i) + uincertitude(i)*1.96;");
			pw.println("			uintervalleincertitude(i,2) = umean(i) - uincertitude(i)*1.96;");
			pw.println("			end;");


			pw.println("plot(vect,uintervalleincertitude,'blue');");
			pw.println("hold on;");

			
			// Print des v pour représenter le recuit simulé
			for (int i=0;i<tailleEchantillon;i++) {

				pw.println("vBest"+i+"="+vBest[i].getlistEnergie().toString()+";");
			}


			pw.print("v=[");

			for (int j=0; j< tailleEchantillon;j++) {
				pw.print("vBest"+j+"; ");

			}
			pw.println("];");
			pw.println("vect="+echantillonage+"*(1:"+tailleMax+");");
			pw.println("taille = "+tailleMax+";");
			pw.println("umean=zeros(taille,1);");
			pw.println("uintervalleincertitude = zeros(taille,2);");
			pw.println("for i = 1:taille;");
			pw.println("			umean(i) = mean(v(:,i));");
			pw.println("			nombredeVecteurs=length(v(:,1));");
			pw.println( "			uincertitude(i) = std(v(:,i))/sqrt(nombredeVecteurs);");
			pw.println("			uintervalleincertitude(i,1) = umean(i) + uincertitude(i)*1.96;");
			pw.println("			uintervalleincertitude(i,2) = umean(i) - uincertitude(i)*1.96;");
			pw.println("			end;");


			pw.println("plot(vect,uintervalleincertitude,'blue');");


			// Legende
			pw.println("title('meilleure énergie sur le "+benchmark+"')");
			pw.println("xlabel('Nombre d iterations');");
			pw.println("ylabel('Energie');");


			pw.close();
			// On ferme
		} catch (IOException exception) {

		}



		//System.out.println("Nombre de conflits : "+recuit.getMeilleureEnergie());

	}





}


