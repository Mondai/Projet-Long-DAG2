package dag3;

import java.util.ArrayList;

import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;
import parametrage.ParametreGamma;
import parametrage.Temperature;
import solver.commun.HighQualityRandom;
import modele.Etat;
import modele.Probleme;
import mutation.IMutation;

/**
 * Particule Quantique représentée par un certains nombres de répliques, c'est-à-dire des GrapheColorie.
 * Elle hérite de Probleme.
 *@see GrapheColorie,Graphe,HighQualityRandom
 */
public class GrapheColorieParticule extends Probleme {
	/**
	 * Nombre de couleurs pour le coloriage.
	 */
	int k;
	/**
	 * Graphe sous-jacent.
	 */
	Graphe graphe;
	HighQualityRandom gen;
	
	public GrapheColorieParticule(ArrayList<Etat> etat, Temperature T, int seed, EnergieCinetique energiecin, EnergiePotentielle energiepot, ParametreGamma gamma, Graphe graphe, int k) {
		super(etat, T, seed, energiecin, energiepot, gamma);
		
		this.graphe = graphe;
		this.gen = new HighQualityRandom(seed);
		this.k = k;
	}
	
	public double calculerEnergiePotentielle(){
		double compteur=0;
		for (Etat i:this.getEtat()){
			compteur += Conflits.calculer((GrapheColorie)i);
		}
		return compteur/this.getEtat().size();
	}
		
	public Etat creeEtatAleatoire() {
		GrapheColorie etat =  new GrapheColorie(this.getEpot(), this.k, this.graphe, this.gen.nextInt());
		etat.initialiser();
		etat.setnext(this.getEtat().get(0));
		etat.setprevious(this.getEtat().get(this.getEtat().size()-1));
		return etat;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
	
	public Graphe getGraphe() {
		return this.graphe;
	}
	
	public double calculerEnergieCinetique(){
		return this.calculerCompteurCinetique();
	}
	
	public double calculerCompteurCinetique(){
		return ConflitsCinetiques.calculerCompteurSpinique(this);
	}
	
	public int differenceSpins(Etat e, IMutation m){
		return ((GrapheColorie)e).calculerDeltaEc(m);
	}
	
	public Temperature getTemperature() {
		return this.getT();
	}
	
	public void majgamma(){
		this.getGamma().refroidissementLin();
	}		
		
}
	



