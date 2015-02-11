package algoDeLaMire;

import java.io.IOException;
import java.util.Arrays;

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
import solverSimuleParametrable.RecuitQuantiqueParametrableAccelere;
import solverSimuleParametrable.TemperatureLineaire;
import solverSimuleParametrable.TemperatureLineairePalier;

public class Mire {
	
	double G0;
	double deltaG0;
	double T;
	double deltaT;
	Graphe graphe;
	int pointCentral ;  //c'est la somme des nombres d'it�ration pour 5 recuits
	
	//les variables suivantes d�pendent du graphe
	int nbNoeuds = 250;
	int nbCouleurs = 28;
	double k = 1;
	int M = 4 * nbNoeuds * nbCouleurs;
	int P = 10;
	int maxSteps = 1000;
	int seed = 745267;
	

	public static void main(String[] args) throws IOException {
		
		// ici le main qui lance l'algo
		
		Mire mire = new Mire();
		mire.init(10,0.35); //param�tre initiaux � changer
		mire.lancerMire();

	}
	
	public  Mire() {} ;
	
	// la cr�ation de mire d�pend actuellement des caract�ristique de "data/dsjc250.5.col"
	
	public void init(double G0init, double Tinit) throws IOException {
		this.graphe = Traducteur.traduire("data/dsjc250.5.col");
		this.G0=G0init;
		this.T=Tinit; 
		this.deltaT = Tinit*2/3; 
		this.deltaG0 = G0init*2/3;
		this.pointCentral = testerParam(G0,T);
		
		
		}

	public int testerParam(double g0, double t) {

		Conflits Ep = new Conflits();
		ConflitsCinetiques Ec = new ConflitsCinetiques();
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep,
				mutation, Ec, nbCouleurs, P, graphe, seed);
		coloriage.initialiser();
		TemperatureLineaire Tparam = new TemperatureLineaire(g0, 0, maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantiqueParametrableAccelere recuit = new RecuitQuantiqueParametrableAccelere(
				Tparam, Kparam, M, t);
		double somme = 0. ;
		for (int i = 0 ; i < 5 ; i++) {
			somme = somme + recuit.lancer(coloriage) ;
		}
		
			return (int) somme ;

	}
	
	/* c'est juste une mire en 2D
	 * on consid�re g0 en axe des x et t en axe des y
	 * note perso : v�rifier que g0 et t restent positifs
	 */
	public void  UneEtape() {
		int haut = testerParam(G0,T+deltaT);
		int gauche = testerParam(G0-deltaG0,T);
		int droit = testerParam(G0+deltaG0,T);
		int bas = testerParam(G0,T-deltaT);
		
		int[] tab = new int[] {this.pointCentral, haut, gauche, droit, bas} ;
		Arrays.sort(tab);
		
		if (tab[0] == this.pointCentral ) {
		this.deltaG0 = this.deltaG0 / 2 ;
		this.deltaT = this.deltaT / 2 ;
		}
		else if (tab[0] == haut) {
			this.pointCentral=haut;
			this.T=T+deltaT;
		} else if (tab[0] == bas) {
			this.pointCentral=bas;
			this.T=T-deltaT;
			if (T-deltaT < 0) {
				deltaT=0.9*T;
			}
		} else if (tab[0] == gauche) {
			this.pointCentral=gauche;
			this.G0=G0-deltaG0;
			if (G0-deltaG0 < 0) {
				deltaG0=0.9*G0;
			}
		} else if (tab[0] == droit) {
			this.pointCentral=droit;
			this.G0=G0+deltaG0;
		} 
				
	}
	
	public void lancerMire () {

		int compteur = 0 ;
		while (compteur < 10) {  // changer la valeur max pour augmenter la dur�e de la mire
			this.UneEtape();
			System.out.println("G0 actuel "+this.G0+" et T actuel "+this.T);
			compteur ++ ;
		}
	}
	
}
