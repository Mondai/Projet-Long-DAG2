import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class TestScriptResultat {

	public static void main(String[] args) throws IOException {
		
		int echantillonage = 200;
		
		ecrire("resultats avec environ 10^6 iterations pour chaque algo");
		ecrire("(0) => RecuitSimuleLineaire(7000,1000,0.01,0.01,10)");
		ecrire("(1) => RecuitSimuleExponentiel(7000,1000,0.0000001,0.99,10,1000000)");
		ecrire("(2) => RecuitSimuleExponentielPalier(7000,1000,0.0000001,0.99,1,1414,1)");
		ecrire("(3) => RecuitSimuleExponentielPalier(7000,1000,0.0000001,0.99,1414,1,-1)");
		
		Conflits energie = new Conflits();
		MutationAleatoireColoriage mutation = new MutationAleatoireColoriage();
		RecuitSimule[] recuits = new RecuitSimule[4]; 
		recuits[0] = new RecuitSimuleLineaire(7000,1000,0.01,0.01,10,echantillonage);
		recuits[1] = new RecuitSimuleExponentiel(7000,1000,0.0000001,0.99,10,1000000,echantillonage);
		recuits[2] = new RecuitSimuleExponentielPalier(7000,1000,0.0000001,0.99,1,1414,1,echantillonage);
		recuits[3] = new RecuitSimuleExponentielPalier(7000,1000,0.0000001,0.99,1414,1,-1,echantillonage);
		String[] lettres = {"a","b","c","d"};
		int[] kDeb = {25,25,29,29};
		
		for(int data = 0; data < 4 ;data++){  // for sur les 4 donnees
		
		Graphe graphe = Traducteur.traduire("data/le450_25"+lettres[data]+".col");
		ecrire("");
		ecrire("data/le450_25"+lettres[data]+".col, nombre couleurs optimal = " + 25 + " :");
		System.out.println("data/le450_25"+lettres[data]+".col, nombre couleurs optimal = " + 25 + " :");
		for( int algo = 0; algo < 4 ; algo++){
			boolean t = true ;
			int k = kDeb[data] ;
			while(t){		// recherche de la solution la plus proche de 25 possible
				System.out.println("k = "+k);
				Coloriage coloriage = new Coloriage(energie, mutation, k ,graphe);
				coloriage.initialiser();
				long startTime = System.nanoTime();  // compter la duree d'execution
				recuits[algo].lancer(coloriage);
				long endTime = System.nanoTime();   // idem
				if(recuits[algo].meilleureEnergie==0){
					t = false ;
					ecrire("	("+algo+") => "+k+" ,  duree = "+(endTime-startTime)/1000000000+" s");
					System.out.println("	("+algo+") => "+k+" ,  duree = "+(endTime-startTime)/1000000000+" s");
				}
				k++;
			}
		}
		}
		
	}
	
	public static void ecrire(String s){
		
		BufferedWriter bw = null;
		 
	      try {
	         bw = new BufferedWriter(new FileWriter("RecapitulatifResultats10^6.txt", true));
	     bw.write(s);
	     bw.newLine();
	     bw.flush();
	      } catch (IOException ioe) {
	     ioe.printStackTrace();
	      } finally {                       // toujours fermer le fichier
	     if (bw != null) try {
	        bw.close();
	     } catch (IOException ioe2) {
	        // vide
	     }
	      } // end try/catch/finally
	}

}
