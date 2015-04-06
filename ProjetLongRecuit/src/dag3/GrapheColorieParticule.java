package dag3;

import java.util.ArrayList;

import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;
import parametrage.ParametreGamma;
import parametrage.Temperature;
import modele.Etat;
import modele.Probleme;

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
	 * Nombre de répliques(possibilités quantiques).
	 */
	int replique;
	/**
	 * Graphe sous-jacent.
	 */
	Graphe graphe;
	
	public GrapheColorieParticule(ArrayList<Etat> etat, Graphe graphe) {
		this.etat = etat;
		this.graphe = graphe;
	}
	
	public GrapheColorieParticule(ArrayList<Etat> etat, Temperature T, int seed, EnergieCinetique energiecin, EnergiePotentielle energiepot, ParametreGamma gamma, Graphe graphe) {
		super(etat, T, seed, energiecin, energiepot, gamma);
		this.graphe = graphe;
	}
	
	public Etat creeEtatAleatoire() {
		GrapheColorie etat =  new GrapheColorie(this.getEpot(), k, graphe, this.getSeed());
		etat.initialiser();
		return etat;
	}
	
	public GrapheColorieParticule clone() {
		int n = this.getEtat().size();
		ArrayList<Etat> r = new ArrayList<Etat>(n);
		for(int i=0; i<n; i++){
			r.add(( (this.getEtat().get(i)).clone()));
		}
		
		int P = n;
		java.util.ArrayList<Etat> etats = this.getEtat();
	
		
		etats.get(0).setprevious(etats.get(P-1));
		etats.get(0).setnext(etats.get(1));
		for(int i =1; i<P-1;i++){
			etats.get(i).setprevious(etats.get(i-1));
			etats.get(i).setnext(etats.get(i+1));
		}
		etats.get(P-1).setprevious(etats.get(P-2));
		etats.get(P-1).setnext(etats.get(0));
		
		
		GrapheColorieParticule p = new GrapheColorieParticule(r,this.getT(),this.getSeed(),this.getEcin(),this.getEpot(),this.getGamma(), this.graphe);
		
		p.setReplique(P);
		p.setK(k);
		return p;
	}

	public int getK() {
		return k;
	}

	public int getReplique() {
		return replique;
	}
	
	public Graphe getGraphe() {
		return this.graphe;
	}

	public void setK(int k) {
		this.k = k;
	}

	public void setReplique(int replique) {
		this.replique = replique;
	}
	
		
		
}
	



