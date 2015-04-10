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
		//System.out.println("CEA");
		GrapheColorie etat =  new GrapheColorie(this.getEpot(), this.k, this.graphe, this.gen.nextInt());
		etat.initialiser();
		etat.setnext(this.getEtat().get(0));
		etat.setprevious(this.getEtat().get(this.getEtat().size()-1));
		return etat;
	}
	/*
	public GrapheColorieParticule clone() {
		System.out.println("Clone GCP");
		
		int n = this.getEtat().size();
		ArrayList<Etat> r = new ArrayList<Etat>(n);
		for(int i=0; i<n; i++){
			r.add( ( (GrapheColorie) this.getEtat().get(i)).clone());
		}*/
		
		/* inutile d'après l'exemple TSP
		int P = n;
		ArrayList<Etat> etats = this.getEtat();
	
		
		etats.get(0).setprevious(etats.get(P-1));
		etats.get(0).setnext(etats.get(1));
		for(int i =1; i<P-1;i++){
			etats.get(i).setprevious(etats.get(i-1));
			etats.get(i).setnext(etats.get(i+1));
		}
		etats.get(P-1).setprevious(etats.get(P-2));
		etats.get(P-1).setnext(etats.get(0));*/
		
		/*
		GrapheColorieParticule p = new GrapheColorieParticule(r,this.getT(),this.getSeed(),this.getEcin(),this.getEpot(),this.getGamma(), this.graphe, this.k);
		p.setT(this.getT()); //utilisé dans TSP exemple, je ne sais pas si c'est nécessaire....
		p.setK(k);
		return p;
	}*/

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}
	
	
	// ajout pour essayer d'avoir un comportement normal
	/*
	 * 
	 * 
	 */
	
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
	



