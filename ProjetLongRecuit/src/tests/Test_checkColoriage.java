package tests;

import java.io.IOException;
import java.util.LinkedList;

import solver.Conflits;
import solver.ConflitsCinetiques;
import solver.Graphe;
import solver.GrapheColorie;
import solver.GrapheColorieParticule;
import solver.MutationConflitsAleatoire;
import solver.Traducteur;
import solverCommun.Etat;
import solverSimuleParametrable.ConstanteKConstant;
import solverSimuleParametrable.RecuitQuantiqueParametrableAccelere;
import solverSimuleParametrable.TemperatureLineaire;

public class Test_checkColoriage {

	public static void main(String[] args) throws IOException {
		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		Graphe graphe = Traducteur.traduire("data/dsjc250.5.col");
		int nbNoeuds = 250;
		int nbCouleurs = 28;
		double k = 1;
		int M = 4 * nbNoeuds * nbCouleurs;
		double G0 = 0.75;
		int P = 10;
		double T = 0.35/P;
		int maxSteps = (int) Math.pow(10,4);
		int seed = 1;
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep, mutation, Ec, nbCouleurs , P, graphe, seed);
		coloriage.initialiser();
		TemperatureLineaire Tparam = new TemperatureLineaire(G0,0,maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantiqueParametrableAccelere recuit = new RecuitQuantiqueParametrableAccelere(Tparam,Kparam, M, T);
		
		long startTime = System.nanoTime();
		System.out.println(recuit.lancer(coloriage));
		long endTime = System.nanoTime();
		
		// check s'il n'y a pas de conflits sur l'etat resolu
		int num = 0 ;
		int erreurs = 0;
		boolean check = false;
		sortie:
		for (Etat etat : coloriage.getEtats()){
			num++;
			GrapheColorie g = (GrapheColorie) etat;
			if(g.nombreNoeudsEnConflit()==0){
				check = true;
				System.out.println("Etat Resolu num " + num);
				int[] couleurs = g.getCouleurs();
				LinkedList<Integer>[] connexions = graphe.getConnexions();
				for(int noeud1 = 0 ; noeud1 < connexions.length ; noeud1++ ){
					for(int noeud2 : connexions[noeud1]){
						if(couleurs[noeud1]==couleurs[noeud2]){
							erreurs++;
							check=false;
							//break sortie;
						}
					}
				}
			}			
		}
		System.out.println("check: "+check);
		System.out.println("erreurs: "+erreurs);
		System.out.println("duree = "+(endTime-startTime)/1000000000+" s");
	}

}
