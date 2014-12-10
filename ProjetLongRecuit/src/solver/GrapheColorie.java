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
	
	int[] couleurs; //Tableau des noeuds repertoriant leurs couleurs
	private int[] meilleuresCouleurs; // Tableau des noeuds repertoriant la meilleure repartition des couleurs trouvee jusqu'ici
	Graphe graphe;
	
	int k; // nombre de couleurs pour le coloriage
	boolean[][] conflitsConnexions; //Tableau contenant True si les noeuds de l'arete sont en conflit et False sinon (peut être non nécessaire de stocker les spins)
	private boolean[] noeudsConflit;// tableau avec True si noeud en conflit et False sinon
	private int nombreConflitsAretes;
	
	// F[Noeuds][Couleurs] inspire de l'algo Tabucol. Permet de calculer rapidement le DelatE d'une mutation
	// F[v][c] = nombre de voisins de v ayant pour couleur c.
	// Propriete: DelatE = F[v][cSuiv] - F[v][cPrec]
	int[][] F; 
	
	// Sauvegarde de la derniere modification effectuee
	public Modification derniereModif;
	
	public GrapheColorie(EnergiePotentielle Ep, int k, Graphe graphe, int seed) {
		
		this.derniereModif = null;
		this.Ep = Ep;
		
		this.k = k; 
		this.couleurs = new int[graphe.getNombreNoeuds()];
		this.meilleuresCouleurs = new int[graphe.getNombreNoeuds()];
		this.graphe = graphe;
		
		this.seed = seed;
		this.gen = new HighQualityRandom(seed);
		
		this.noeudsConflit = new boolean[graphe.getNombreNoeuds()];
		this.conflitsConnexions = new boolean[graphe.getNombreNoeuds()][graphe.getNombreNoeuds()];
		this.nombreConflitsAretes = 0;
		
		this.F = new int[graphe.getNombreNoeuds()][this.k];
	}
	
	public GrapheColorie(EnergiePotentielle Ep, int k, Graphe graphe) {
		this(Ep, k, graphe, new HighQualityRandom().nextInt()); 
	}
	
	// Initialisation de l'etat: affectation de couleurs aleatoires
	public void initialiserSansSeed(){
		
		// Affectation des couleurs
		// mettre à jour tous les conflits initiaux 
		// et rajouter tous les noeuds à la liste des noeuds en conflit
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {			
			this.couleurs[j]=(int)(this.gen.nextDouble()*this.k);  // affectation couleurs aleatoires
		}
		
		//calcul du nombre initial de conflits
		int conflits = 0;
		for (int noeudActuel = 0; noeudActuel < this.graphe.getNombreNoeuds(); noeudActuel++) {
			for (int noeudAdjacent : graphe.connexions[noeudActuel]){
				if(this.couleurs[noeudAdjacent]==this.couleurs[noeudActuel]){
					this.conflitsConnexions[noeudActuel][noeudAdjacent] = true;
					this.noeudsConflit[noeudActuel] = true;
					conflits++;
				}
				this.F[noeudActuel][this.couleurs[noeudAdjacent]] ++ ; // initialisation F. F(v,c)= nb de voisins de v a la couleur c.
			}
		}
		
		this.nombreConflitsAretes = (conflits/2); // chaque conflit est compte deux fois
	}
	
	public void initialiser(){
		this.initialiserSansSeed();
		this.setSeed(this.getSeed() + 1);
		this.gen = new HighQualityRandom(this.getSeed());
	}
	
	public void updateLocal(int noeud, int prevColor){
		
		for (int j : graphe.connexions[noeud]){
			// mise a jour F
			this.F[j][this.couleurs[noeud]] ++;
			this.F[j][prevColor] --;
			
			// mise a jour conflits
			if (this.couleurs[j] == prevColor){
				this.conflitsConnexions[noeud][j] = false;
				this.conflitsConnexions[j][noeud] = false;
				this.nombreConflitsAretes --;
				if(!enConflit(j)) {
					this.noeudsConflit[j] = false;
					//System.out.println("removed");
				}
			}
			else if (this.couleurs[j] == this.couleurs[noeud]){
				this.conflitsConnexions[noeud][j] = true;
				this.conflitsConnexions[j][noeud] = true;
				this.nombreConflitsAretes ++;
				this.noeudsConflit[j] = true;
				//System.out.println("added");
			}
		}
		if (!enConflit(noeud)){
			this.noeudsConflit[noeud] = false;
			//System.out.println("removed");
		}
	}
	
	public boolean enConflit(int noeud){
		
		boolean res = false;
		
		for (int j : graphe.connexions[noeud]){
			if (this.couleurs[noeud] == this.couleurs[j]){
				res = true;
				break;
			}
		}
		return res;
	}

	// Sauvegarde du coloriage actuel dans une variable
	public void sauvegarderSolution(){
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {
			this.meilleuresCouleurs[j] = this.couleurs[j];
		}
	}
	
	// retourne le nombre de noeuds en conflit
	public int nombreNoeudsEnConflit() {
		int res = 0;
		for(boolean i : this.noeudsConflit){
			if(i) res ++;
		}
		return res;
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

	public boolean[] getNoeudsConflit() {
		return noeudsConflit;
	}

	public int getNombreConflitsAretes() {
		return nombreConflitsAretes;
	}

	public void setNombreConflitsAretes(int nombreConflitsAretes) {
		this.nombreConflitsAretes = nombreConflitsAretes;
	}

}
