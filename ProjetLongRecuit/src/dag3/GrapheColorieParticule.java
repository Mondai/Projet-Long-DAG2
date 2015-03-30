package dag3;

import parametrage.EnergieCinetique;
import parametrage.EnergiePotentielle;
import parametrage.ParametreGamma;
import parametrage.Temperature;
import vertexColoring.GrapheColorie;
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
	
	public GrapheColorieParticule(java.util.ArrayList<Etat> etat, Temperature T, int seed, EnergieCinetique energiecin, EnergiePotentielle energiepot, ParametreGamma gamma, Graphe graphe) {
		super(etat, T, seed, energiecin, energiepot, gamma);
		this.graphe = graphe;
	}
	
	public Etat creeEtatAleatoire() {
		GrapheColorie etat =  new GrapheColorie(energiepot, k, graphe, this.gen.nextInt());
		etat.initialiser();
		return etat;
	}
	


}
