package algoDeLaMire;

import java.io.IOException;
import java.util.Arrays;

import solver.parametres.ConstanteKConstant;
import solver.parametres.FonctionLineaire;
import solver.quantique.RecuitQuantiqueParametrableAccelere;
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
	int pointCentral ;  //c'est la somme des nombres d'itération pour 1 recuits
	
	//les variables suivantes dépendent du graphe
	int nbNoeuds = 250;
	int nbCouleurs = 28;
	double k = 1;
	int M = 4 * nbNoeuds * nbCouleurs;
	int P = 10;
	int maxSteps = 1000;
	
	

	public static void main(String[] args) throws IOException {
		
		// ici le main qui lance l'algo
		
		Mire mire = new Mire();
		mire.init(10,0.35); //paramètre initiaux à changer
		mire.lancerMire();

	}
	
	public  Mire() {} ;
	
	// la création de mire dépend actuellement des caractéristique de "data/dsjc250.5.col"
	
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
		int seed = (int) (500000 + Math.random()*250000 + Math.random()*100000 + Math.random()*50000) ;
		MutationConflitsAleatoire mutation = new MutationConflitsAleatoire();
		GrapheColorieParticule coloriage = new GrapheColorieParticule(Ep,
				mutation, Ec, nbCouleurs, P, graphe, seed);
		coloriage.initialiser();
		FonctionLineaire Tparam = new FonctionLineaire(g0, 0, maxSteps);
		ConstanteKConstant Kparam = new ConstanteKConstant(k);
		RecuitQuantiqueParametrableAccelere recuit = new RecuitQuantiqueParametrableAccelere(
				Tparam, Kparam, M, t);
		
		/*double somme = 0. ;
		for (int i = 0 ; i < 5 ; i++) {
			somme = somme + recuit.lancer(coloriage) ;
		}
			return (int) somme ;
		 */
		return (int) recuit.lancer(coloriage) ;
	}
	
	/* c'est juste une mire en 2D
	 * on considère g0 en axe des x et t en axe des y
	 * note perso : vérifier que g0 et t restent positifs
	 */
	public void  UneEtape() {
		int haut = testerParam(G0,T+deltaT);
		System.out.print("en haut : "+haut+" / ");
		int gauche = testerParam(G0-deltaG0,T);
		System.out.print("en gauche : "+gauche+" / ");
		int droit = testerParam(G0+deltaG0,T);
		System.out.print("en droit : "+droit+" / ");
		int bas = testerParam(G0,T-deltaT);
		System.out.print("en bas : "+bas+" / ");
		
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
		while (compteur < 10) {  // changer la valeur max pour augmenter la durée de la mire
			this.UneEtape();
			System.out.println("G0 actuel "+this.G0+" et T actuel "+this.T);
			compteur ++ ;
		}
	}
	
}
