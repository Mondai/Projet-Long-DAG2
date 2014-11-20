package solver;

import solverCommun.EnergiePotentielle;
import solverCommun.Etat;

/*
Classe qui contient son propre seed et generateur random
Les couleurs actuelles de l'etat, les meilleurs couleurs et le graphe
le nombre de couleurs, de conflits et de noeuds en conflit
Contient une sauvegarde de la dernière modification, propre à chaque état
*/

public class GrapheColorie extends Etat{
	
	private int seed = new HighQualityRandom().nextInt();
	HighQualityRandom gen = new HighQualityRandom(getSeed());
	
	int[] couleurs; //Tableau des noeuds reertoriant leurs couleurs
	private int[] meilleuresCouleurs; // Tableau des noeuds repertoriant la meilleure repartition des couleurs trouvee jusqu'ici
	Graphe graphe;
	
	int k; // nombre de couleurs pour le coloriage
	int[] conflits; //Tableau contenant 0 si le noeud n'est pas en conflit et 1 sinon
	int nombreNoeudsConflit;// Sauvegarde le nombre de noeuds en conflit
	
	// Sauvegarde de la derniere modification effectuee
	public Modification derniereModif;
	
	public GrapheColorie(EnergiePotentielle E, int k, Graphe graphe, int seed) {
		
		this.derniereModif = null;
		this.E = E;
		this.k = k; 
		this.couleurs = new int[graphe.getNombreNoeuds()];
		this.setMeilleuresCouleurs(new int[graphe.getNombreNoeuds()]);
		this.graphe = graphe;
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		this.nombreNoeudsConflit = graphe.getNombreNoeuds();
		this.conflits = new int[graphe.getNombreNoeuds()];
		
	}
	
	public GrapheColorie(EnergiePotentielle E, int k, Graphe graphe) {
		this(E, k, graphe, new HighQualityRandom().nextInt()); 
	}
	
	// Initialisation de l'etat: affectation de couleurs aleatoires
	public void initialiserSansSeed(){
		
		this.nombreNoeudsConflit = graphe.getNombreNoeuds();
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {
			this.couleurs[j] = 0;
			this.conflits[j] = 1;
		}
	}
	
	public void initialiser(){
		this.initialiserSansSeed();
		this.setSeed(this.getSeed() + 1);
		this.gen = new HighQualityRandom(this.getSeed());
	}
	

	// Sauvegarde du coloriage actuel dans une variable
	public void sauvegarderSolution(){
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {
			this.getMeilleuresCouleurs()[j] = this.couleurs[j];
		}
	}

	public int[] getMeilleuresCouleurs() {
		return meilleuresCouleurs;
	}

	public void setMeilleuresCouleurs(int[] meilleuresCouleurs) {
		this.meilleuresCouleurs = meilleuresCouleurs;
	}
	
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	

}
