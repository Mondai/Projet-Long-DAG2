package vertexColoring;

import solver.commun.EnergieCinetique;
import solver.commun.EnergiePotentielle;
import solver.commun.Etat;
import solver.commun.HighQualityRandom;
import solver.commun.IMutation;
import solver.commun.Probleme;

/**
 * Particule Quantique repr�sent�e par un certains nombres de r�pliques, c'est-�-dire des GrapheColorie.
 * Elle h�rite de Probleme.
 *@see GrapheColorie,Graphe,HighQualityRandom
 */
public class GrapheColorieParticule extends Probleme {
	/**
	 * Nombre de couleurs pour le coloriage.
	 */
	int k;
	/**
	 * Nombre de r�pliques(possibilit�s quantiques).
	 */
	int replique;
	/**
	 * Graphe sous-jacent.
	 */
	Graphe graphe;
	
	public GrapheColorieParticule(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe, int seed) {
		
		this.Ec = Ec;
		this.k = k; 
		this.graphe = graphe;
		this.mutation = mutation;
		this.replique = replique;
		
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		
		this.etats = new Etat[replique];
		for (int i = 0; i < this.replique; i++){
			this.etats[i] = new GrapheColorie(Ep, k, graphe, this.gen.nextInt());
		}
		
	}
	
	public GrapheColorieParticule(EnergiePotentielle Ep, IMutation mutation, EnergieCinetique Ec, int k, int replique, Graphe graphe) {
		this(Ep, mutation, Ec, k, replique, graphe, new HighQualityRandom().nextInt()); 
	}
	
	/**
	 * Initialisation de l'�tat: affectation de couleurs aleatoires.
	 */
	public void initialiserSansSeed(){
		EnergiePotentielle Ep = this.etats[0].Ep;
		
		this.etats = new Etat[replique];
		
		for (int i = 0; i < this.replique; i++){
			GrapheColorie etat =  new GrapheColorie(Ep, k, graphe, this.gen.nextInt());
			etat.initialiser();
			this.etats[i] = etat;
		}
		
	}
	
	/**
	 * R�initialisation. Le seed est chang� � la valeur pr�c�dente plus cent.
	 */
	public void initialiser(){
		this.setSeed(this.getSeed() + 100);
		this.gen = new HighQualityRandom(this.getSeed());
		this.initialiserSansSeed();
	}

	/**
	 * Sauvegarde du coloriage actuel de chaque r�plique.
	 */
	public void sauvegarderSolution() {
		for (Etat grapheColorie : etats){
			( (GrapheColorie) grapheColorie).sauvegarderSolution();
		}
		
	}
	
	public Etat[] getEtats(){
		return this.etats;
	}
	
	/**
	 * @return Adjacence Maximum du graphe sous-jacent
	 */
	public int getAdjacenceMax(){
		return this.graphe.getAdjacenceMax();
	}

}
