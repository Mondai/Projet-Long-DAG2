

/*
 * Probl�me du routage
 * Cette classe impl�mente la classe abstraite Probleme
 */

public class Coloriage extends Probleme {
	
	// Param�tres du probl�me
	int[] couleurs; //Tableau des noeuds r�pertoriant leurs couleurs
	int[] meilleuresCouleurs; // Tableau des noeuds r�pertoriant la meilleure r�partition des couleurs trouv�e jusqu'ici
	Graphe graphe;
	int k; // nombre de couleurs pour le coloriage
	int[] conflits; //Tableau contenant 0 si le noeud n'est pas en conflit et 1 sinon
	int nombreNoeudsConflit;// Sauvegarde le nombre de noeuds ne conflit
	
	
	// Sauvegarde de la derni�re modification effectu�e
	public Modification derniereModif;


	public Coloriage(IEnergie E, IMutation mutation, int k, Graphe graphe, int seed) {
		
		this.derniereModif = null;
		this.E = E;
		this.mutation = mutation;
		this.k = k; 
		this.couleurs = new int[graphe.nombreNoeuds];
		this.meilleuresCouleurs = new int[graphe.nombreNoeuds];
		this.graphe = graphe;
		this.seed = seed;
		this.gen = new HighQualityRandom(seed);
		this.nombreNoeudsConflit = graphe.nombreNoeuds;
		this.conflits = new int[graphe.nombreNoeuds];
		
	}
	
	public Coloriage(IEnergie E, IMutation mutation, int k, Graphe graphe) {
		this(E, mutation, k, graphe, new HighQualityRandom().nextInt()); 
	}
	
	
	// Initialisation du probl�me: affectation de couleurs al�atoires
	public void initialiser(){
		
this.nombreNoeudsConflit = graphe.nombreNoeuds;
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.nombreNoeuds; j++) {
			this.couleurs[j] = 0;
			this.conflits[j] = 1;
		}
	}
	

	// Sauvegarde du coloriage actuel dans une variable
	public void sauvegarderSolution(){
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.nombreNoeuds; j++) {
			this.meilleuresCouleurs[j] = this.couleurs[j];
		}
	}

}
