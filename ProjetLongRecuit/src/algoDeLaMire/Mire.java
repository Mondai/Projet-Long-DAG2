package algoDeLaMire;

import java.io.IOException;

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
	int pointCentral ;  //c'est le nombre d'itération pour un recuit ( voir si fut en prendre plus ! )
	
	int nbNoeuds = 250;
	int nbCouleurs = 28;
	double k = 1;
	int M = 4 * nbNoeuds * nbCouleurs;
	int P = 10;
	int maxSteps = 1000;
	int seed = 745267;

	public static void main(String[] args) throws IOException {
		
		Mire mire = new Mire();
		mire.init(10,0.35); 

	}
	
	Mire() {} 
	
	// la création de mire dépend actuellement des caractéristique de "data/dsjc250.5.col"
	
	public void init(double G0init, double Tinit) throws IOException {
		this.graphe = Traducteur.traduire("data/dsjc250.5.col");
		this.G0=G0init;
		this.T=Tinit; 
		this.pointCentral = testerParam(G0,T);
		//les deltas deplacements à changer aussi
		
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
		
			return recuit.lancer(coloriage);

	}
	
	/* c'est juste une mire en 2D
	 * on considère g0 en axe des x et t en axe des y
	 * note perso : vérifier que g0 et t restent positifs
	 */
	public void  UneEtape() {
		int haut = testerParam(G0,T+deltaT);
		int gauche = testerParam(G0-deltaG0,T);
		int droit = testerParam(G0+deltaG0,T);
		int bas = testerParam(G0,T-deltaT);
		
		/*
		 * comparaison avec le point central puis
		 * modification du point central
		 * et eventuellement des deltas deplacements
		 */
				
	}
	
	public void lancerMire () {
		/* il reste à :
		 * trouver un critère d'arret et donc modifier le true
		 * par exemple : terminer en 10 étapes YOLO / stationnarité / delta < ?
		 */
		while (true) {
			this.UneEtape();
			System.out.println("G0 actuel "+this.G0+" et T actuel "+this.T);
		}
	}
	
}
