package solver;


/*
 * Problï¿½me du routage
 * Cette classe implï¿½mente la classe abstraite Probleme
 */

public class Coloriage extends Probleme {
	
	// Paramï¿½tres du problï¿½me
	int[] couleurs; //Tableau des noeuds rï¿½pertoriant leurs couleurs
	private int[] meilleuresCouleurs; // Tableau des noeuds rï¿½pertoriant la meilleure rï¿½partition des couleurs trouvï¿½e jusqu'ici
	Graphe graphe;
	int k; // nombre de couleurs pour le coloriage
	int[] conflits; //Tableau contenant 0 si le noeud n'est pas en conflit et 1 sinon
	int nombreNoeudsConflit;// Sauvegarde le nombre de noeuds en conflit
	
	
	// Sauvegarde de la derniï¿½re modification effectuï¿½e
	public Modification derniereModif;


	public Coloriage(IEnergie E, IMutation mutation, int k, Graphe graphe, int seed) {
		
		this.derniereModif = null;
		this.E = E;
		this.mutation = mutation;
		this.k = k; 
		this.couleurs = new int[graphe.getNombreNoeuds()];
		this.setMeilleuresCouleurs(new int[graphe.getNombreNoeuds()]);
		this.graphe = graphe;
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		this.nombreNoeudsConflit = graphe.getNombreNoeuds();
		this.conflits = new int[graphe.getNombreNoeuds()];
		
	}
	
	public Coloriage(IEnergie E, IMutation mutation, int k, Graphe graphe) {
		this(E, mutation, k, graphe, new HighQualityRandom().nextInt()); 
	}
	
	
	// Initialisation du problï¿½me: affectation de couleurs alï¿½atoires
	public void initialiserSansSeed(){
		
		this.nombreNoeudsConflit = graphe.getNombreNoeuds();
		
		//A changer en une initialisation des divers états pour etre
		// compatible avec le nouveau diagramme de classes
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

}
