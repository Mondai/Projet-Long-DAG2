import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;


public class TestGenerationCourbes {


	public static void main(String[] args) throws IOException {

		String nomFichier = "SortiesGraphiques/Courbes2";

		// Parametres principaux

		
		double Tdebut=7000;
		double Tfin = 0;
		int kinit = 1000;

		int echantillonnage=200;

		int tailleEchantillon = 2;
		double facteur = 0.993;
		int N=10;

			// Nombre de points important car on veux comparer pour le meme nombre d'it�rations

		int nbPoints = 5000;  // nombre de points au total sur palier et changement palier
		double pas = N*((Tdebut-Tfin+1)/nbPoints);
		System.out.println(pas);
		
		
		
		// Initialisation de la matrice de couleurs
		
		String[][] matriceCouleurs = new String[17][7];
		String[] couleursBasiques = {"k","b","c","r","g","m","y"};
		String[] lineSpecBasiques = {"-","--",":","-.","o","+","*",".","x","s","d","^","v",">","<","p","h"};
		
		for (int q=0;q<17;q++) {
			for (int s=0;s<7;s++) {
				matriceCouleurs[q][s]=couleursBasiques[s].concat(lineSpecBasiques[q]);
			}
		}
		int compteurCouleur1 = 0;
		int compteurCouleur2 = 0;
		
		LinkedList legende = new LinkedList();
		
		//Initialisation des listes � it�rer
		// Liste des benchmarks
		LinkedList<String> listBenchmarks = new LinkedList();
		listBenchmarks.add("data/le450_25a.col");
		//listBenchmarks.add("data/le450_25b.col");
		
		//listBenchmarks.add("le450_25c.col");
		//listBenchmarks.add("le450_25d.col");


		// Liste des recuits
		LinkedList<RecuitSimule> listRecuits = new LinkedList();
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
		//listK.add(0.001);


		// Cr�ation Fichier Texte

		try {
			File f = new File (""+nomFichier);
			PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
			pw.println("%Sortie Graphique : ");
			pw.println("%Taille Echantillon : " + tailleEchantillon);
			pw.println("%Echantillonnage : " + echantillonnage);
			pw.println("%Temp�rature de d�part : " + Tdebut);
			pw.println("%Temp�rature de fin : " + Tfin);

			// It�rations

			for (String benchmark : listBenchmarks) {
				Graphe graphe = Traducteur.traduire(""+benchmark);
				pw.println();
				pw.println();
				pw.println("%---------------------------------------------------------------------");
				pw.println("%Benchmark :"+ benchmark);
				pw.println("clear all;");
				pw.println("figure;");
				pw.println("title('"+benchmark+",  k ="+listK.toString()+"');");
				pw.println("xlabel('Nombre d iterations');");
				pw.println("ylabel('Energie');");

				for (IMutation mutation : listMutations) {
					for (double k : listK) {
						pw.println("			%Constante k : " + k);
						pw.println("");
						int y=0;
						for (RecuitSimule recuit : listRecuits) {



							// Initialisation du Probl�me
							Conflits energie = new Conflits();
							ListEnergie listEnergie = new ListEnergie(echantillonnage); 
							Coloriage coloriage = new Coloriage(energie, mutation, 25 ,graphe);

							// Param�trisation du recuit demand�


							if (recuit.toString() == "Recuit Simul� Exponentiel") {
								recuit = new RecuitSimuleExponentiel( k, Tdebut, Tfin, facteur, N, nbPoints,listEnergie);
							}
							else if (recuit.toString() == "Recuit Simul� Lin�aire") {
								recuit = new RecuitSimuleLineaire( k, Tdebut, Tfin, pas, N, listEnergie);
							}
							else if (recuit.toString() == "Recuit Simul� Exponentiel avec k l'energie moyenne") {
								recuit = new RecuitSimuleExponentielK( k, Tdebut, Tfin, facteur, N, nbPoints,listEnergie);
							}
							else if (recuit.toString() == "Recuit Simul� Lin�aire avec k l'�nergie moyenne") {
								recuit = new RecuitSimuleLineaire( k, Tdebut, Tfin, pas, N, listEnergie);
							}


							// Pr�sentation Texte r�sultats

							pw.println("");
							pw.println("");
							pw.println("%Seed du coloriage : " + coloriage.seed);
							pw.println("%"+recuit.toString());
							pw.println("%Nombre d'it�ration : " + recuit.nbPoints);
							pw.println("");



							// It�ration de ce recuit un nombre TailleEchantillon de fois

							for (int i=0; i<tailleEchantillon ; i++) {
								// Lancement du programme
								coloriage.initialiser();
								recuit.lancer(coloriage);


								// Ecriture des vecteurs r�sultats
								pw.print("u"+i+"=");
								List<Double> list = recuit.listEnergie.getlistEnergie();
								pw.print(list.toString());
								System.out.println(list.get(list.size()-1));
								pw.print(";");
								pw.println("");
							}

							pw.print("u=[");
							for (int j=0; j< tailleEchantillon;j++) {
								pw.print("u"+j+"; ");
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
									  
									  
						pw.println("plot(vect(taille/2:taille),uintervalleincertitude(taille/2:taille,:),'"+ matriceCouleurs[compteurCouleur2][compteurCouleur1]+"');");
						pw.println("hold on;");
						
						legende.add("['"+recuit.toString()+", k="+k+" up']");
						legende.add("['"+recuit.toString()+", k="+k+" down']");
						
						if (compteurCouleur1==17) {
							compteurCouleur1=0;
						} else { 
							compteurCouleur1++;
						}
						
						}
						System.out.println("fin r�sultats affichage");
						if (compteurCouleur2==7) {
							compteurCouleur2=0;
						} else { 
							compteurCouleur2++;
						}
						
						
						
						
					}	
					
				}
			}
			int u = legende.toString().length();
			
			pw.print( "legend({" );
			pw.print(""+legende.toString().subSequence(1, u-1));
			System.out.println(legende.toString());
			pw.print("});");
			
			//legend({'recuit1'});
			pw.close();
			System.out.println("fin ecriture");
		} catch (IOException exception)
		{
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
}











