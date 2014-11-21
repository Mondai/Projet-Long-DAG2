package solver;

import java.util.HashSet;

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
	
	int[] couleurs; //Tableau des noeuds repertoriant leurs couleurs
	private int[] meilleuresCouleurs; // Tableau des noeuds repertoriant la meilleure repartition des couleurs trouvee jusqu'ici
	Graphe graphe;
	
	int k; // nombre de couleurs pour le coloriage
	int[][] conflitsConnexions; //Tableau contenant 1 si les noeuds de l'arete sont en conflit et 0 sinon
	private int[] noeudsConflit;// tableau avec 1 si noeud en conflit et 0 sinon
	private int nombreConflitsAretes;
	
	// Sauvegarde de la derniere modification effectuee
	public Modification derniereModif;
	
	public GrapheColorie(EnergiePotentielle Ep, int k, Graphe graphe, int seed) {
		
		this.derniereModif = null;
		this.Ep = Ep;
		
		this.k = k; 
		this.couleurs = new int[graphe.getNombreNoeuds()];
		this.setMeilleuresCouleurs(new int[graphe.getNombreNoeuds()]);
		this.graphe = graphe;
		
		this.setSeed(seed);
		this.gen = new HighQualityRandom(seed);
		
		this.setNoeudsConflit(new int[graphe.getNombreNoeuds()]); // pas sur si vraiment nécessaire
		this.conflitsConnexions = new int[graphe.getNombreNoeuds()][graphe.getNombreNoeuds()];
		this.setNombreConflitsAretes(0);
	}
	
	public GrapheColorie(EnergiePotentielle Ep, int k, Graphe graphe) {
		this(Ep, k, graphe, new HighQualityRandom().nextInt()); 
	}
	
	// Initialisation de l'etat: affectation de couleurs aleatoires
	public void initialiserSansSeed(){
		
		/* Non nécessaire tant que toutes les couleurs après sont initialisées à 0
		for (int i : this.noeudsConflit){
			this.noeudsConflit[i] = 0;
		}*/
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {
			// mettre à jour tous les conflits initiaux 
			// et rajouter tous les noeuds à la liste des noeuds en conflit			
			this.couleurs[j] = 0;
			this.getNoeudsConflit()[j] = 1;
			for (int i = 0; i < this.graphe.getNombreNoeuds(); i++){
				this.conflitsConnexions[j][i] = 0;
			}
			for (int i : graphe.connexions[j]){
				this.conflitsConnexions[j][i] = 1;
			}

		}
		this.setNombreConflitsAretes((int) (new Conflits().calculer(this)));
	}
	
	public void initialiser(){
		this.initialiserSansSeed();
		this.setSeed(this.getSeed() + 1);
		this.gen = new HighQualityRandom(this.getSeed());
	}
	
	public void updateLocal(int noeud, int prevColor){
		
		for (int j : graphe.connexions[noeud]){
			if (this.couleurs[j] == prevColor){
				this.conflitsConnexions[noeud][j] = 0;
				this.conflitsConnexions[j][noeud] = 0;
				this.setNombreConflitsAretes(this.getNombreConflitsAretes() - 1);
				if(!enConflit(j)) {
					this.getNoeudsConflit()[j] = 0;
					//System.out.println("removed");
					// Fonctions debug
				}
			}
			else if (this.couleurs[j] == this.couleurs[noeud]){
				this.conflitsConnexions[noeud][j] = 1;
				this.conflitsConnexions[j][noeud] = 1;
				this.setNombreConflitsAretes(this.getNombreConflitsAretes() + 1);
				this.getNoeudsConflit()[j] = 1;
				//System.out.println("added");
			}
		}
		if (!enConflit(noeud)){
			this.getNoeudsConflit()[noeud] = 0;
			//System.out.println("removed");
		}
	}
	
	public boolean enConflit(int noeud){
		int l = 0;
		for (int j : graphe.connexions[noeud]){
			if (this.couleurs[noeud] == this.couleurs[j]){
				l++;
			}
		}
		if (l > 0) return true;
		else return false;
	}
	
	//debug purposes
	public int nombreNoeudsEnConflit(){
		int k = 0;
		for (int i : this.getNoeudsConflit()){
			if (i == 1) k++;
		}
		return k;
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

	public int[] getNoeudsConflit() {
		return noeudsConflit;
	}

	public void setNoeudsConflit(int[] noeudsConflit) {
		this.noeudsConflit = noeudsConflit;
	}

	public int getNombreConflitsAretes() {
		return nombreConflitsAretes;
	}

	public void setNombreConflitsAretes(int nombreConflitsAretes) {
		this.nombreConflitsAretes = nombreConflitsAretes;
	}
	

}
