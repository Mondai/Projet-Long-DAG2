package algoDeLaMire;

import java.io.IOException;
import java.util.Arrays;
import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import vertexColoring.Conflits;
import vertexColoring.ConflitsCinetiques;
import vertexColoring.Graphe;
import vertexColoring.GrapheColorieParticule;
import vertexColoring.MutationConflitsAleatoire;
import vertexColoring.Traducteur;

public class Mire {

	double G0;
	double deltaG0;
	double T;
	double deltaT;
	Graphe graphe;
	int pointCentral; // c'est la somme des nombres d'itération pour 1 recuits
	int duree;

	// les variables suivantes dépendent du graphe
	int nbNoeuds = 250;
	int nbCouleurs = 28;
	double k = 1;
	int M = 4 * nbNoeuds * nbCouleurs;
	int P = 10;
	int maxSteps = 1000;

	public static void main(String[] args) throws IOException {

		/*
		 *  allo ici le main qui lance l'algo
		 * paramètre initiaux à changer
		 */
		System.out.println("début de la mire !");
		Mire mire = new Mire();
		System.out.println("mire créée !");
		mire.init(10, 0.35, 2); 
		System.out.println("mire initialisée !");
		mire.lancerMire();

	}

	public Mire() { };

	// la création de mire dépend actuellement des caractéristique de
	// "data/dsjc250.5.col"

	public void init(double G0init, double Tinit, int duree) throws IOException {
		this.graphe = Traducteur.traduire("data/dsjc250.5.col");
		this.G0 = G0init;
		this.T = Tinit;
		this.deltaT = Tinit * 2 / 5;
		this.deltaG0 = G0init * 2 / 5;
		this.pointCentral = testerParam(G0, T);
		this.duree = duree;
		
		System.out.println("1er point central"+ this.pointCentral + " //");
	}

	public int testerParam(double g0, double t) {

		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		int seed = (int) (500000 + Math.random() * 250000 + Math.random()
				* 100000 + Math.random() * 50000);
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep,
				mutation, Ec, nbCouleurs, P, graphe, seed);
		coloriage.initialiser();
		FonctionLineaire Tparam = new FonctionLineaire(g0, 0, maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantiqueAccelere recuit = new RecuitQuantiqueAccelere(Tparam,
				Kparam, M, t);

		/*
		 * SI SUR 5 RECUITS
		 * 
		 * double somme = 0. ; for (int i = 0 ; i < 5 ; i++) { somme = somme +
		 * recuit.lancer(coloriage) ; } return (int) somme ;
		 */
		return (int) recuit.resoudre(coloriage);
	}

	/*
	 * c'est  une mire 2D 
	 * on considère que g0 est en abscisse et t en ordonné
	 */
	public void UneEtape() {
		int haut = testerParam(G0, T + deltaT);
		// System.out.print("en haut : " + haut + " / ");
		int gauche = testerParam(G0 - deltaG0, T);
		// System.out.print("en gauche : " + gauche + " / ");
		int droit = testerParam(G0 + deltaG0, T);
		// System.out.print("en droit : " + droit + " / ");
		int bas = testerParam(G0, T - deltaT);
		// System.out.print("en bas : " + bas + " / ");
		System.out.println("Au centre : " + this.pointCentral + ", en haut : "
				+ haut + ", en bas : " + bas + ", à gauche : " + gauche
				+ ", à droite : " + droit + " // ");

		int[] tab = new int[] { this.pointCentral, haut, gauche, droit, bas };
		Arrays.sort(tab);
		System.out.println("Point choisi : " + tab[0]) ;

		if (tab[0] == this.pointCentral) {
			this.deltaG0 = this.deltaG0 / 2;
			this.deltaT = this.deltaT / 2;
		} else if (tab[0] == haut) {
			this.pointCentral = haut;
			this.T = T + deltaT;
		} else if (tab[0] == bas) {
			this.pointCentral = bas;
			if (T - deltaT < 0) {
				deltaT = 0.9 * T;
			}
			this.T = T - deltaT;
		} else if (tab[0] == gauche) {
			this.pointCentral = gauche;
			if (G0 - deltaG0 < 0) {
				deltaG0 = 0.9 * G0;
			}
			this.G0 = G0 - deltaG0;
		} else if (tab[0] == droit) {
			this.pointCentral = droit;
			this.G0 = G0 + deltaG0;
		}

	}

	public void lancerMire() {
		int compteur = 0;
		while (compteur < duree) {
			System.out.println(compteur+") : G0 actuel " + this.G0 + " et T actuel "
					+ this.T +" //");
			this.UneEtape();
			compteur++;
		}
	}

}
