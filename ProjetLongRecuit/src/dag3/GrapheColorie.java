package dag3;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import parametrage.EnergiePotentielle;
import solver.commun.HighQualityRandom;
import modele.Etat;
import mutation.IMutation;


/**
 * Classe qui repr�sente un coloriage des noeuds d'un graphe.
 * <p>
 * Elle contient son propre seed et generateur al�atoire, ainsi que les couleurs 
 * actuelles, les meilleures couleurs trouv�es dans le pass�, un lien vers le graphe,
 * les classes de couleurs et le nombre d'ar�tes en conflits pour un calcul plus rapide de l'�nergie potentielle.
 * @see Graphe,HighQualityRandom
 */
public class GrapheColorie extends Etat{
	
	/**
	 * Seed du g�n�rateur al�atoire.
	 */
	private int seed = new HighQualityRandom().nextInt();
	/**
	 * G�n�rateur al�atoire.
	 */
	HighQualityRandom gen = new HighQualityRandom(getSeed());
	
	/**
	 * Tableau des noeuds r�pertoriant leurs couleurs.
	 */
	int[] couleurs;
	/**
	 * Tableau des noeuds r�pertoriant la meilleure r�partition des couleurs trouv�e jusqu'ici.
	 */
	private int[] meilleuresCouleurs;
	/**
	 * Graphe sous-jacent.
	 */
	Graphe graphe;
	
	/**
	 * Nombre de couleurs pour le coloriage.
	 */
	int k;

	/**
	 * Liste des noeuds en conflit.
	 */
	private LinkedList<Integer> noeudsConflitList;
	/**
	 * Nombre d'ar�tes en conflit.
	 */
	private int nombreConflitsAretes;
	/**
	 * Classes de couleur.
	 */
	private HashSet<Integer>[] colorClasses;
	

	/**
	 * F[Noeuds][Couleurs] inspire de l'algo Tabucol. Permet de calculer rapidement la diff�rence d'�nergie(deltaE) d'une mutation.
	 * F[v][c] = nombre de voisins de v ayant pour couleur c.
	 * Propri�t�: DeltaE = F[v][couleurSuivante] - F[v][couleurPr�c�dente]
	 */
	int[][] F; 
	
	public GrapheColorie(EnergiePotentielle Ep, int k, Graphe graphe, int seed) {
		super(Ep);
		
		this.k = k; 
		this.couleurs = new int[graphe.getNombreNoeuds()];
		this.meilleuresCouleurs = new int[graphe.getNombreNoeuds()];
		this.graphe = graphe;
		
		this.seed = seed;
		this.gen = new HighQualityRandom(seed);
		

		this.noeudsConflitList= new LinkedList<Integer>();

		this.nombreConflitsAretes = 0;
		
		this.F = new int[graphe.getNombreNoeuds()][this.k];
		
		this.colorClasses = new HashSet[this.k];
		
		for (int i = 0; i < this.k; i++){
			this.colorClasses[i] = new HashSet<Integer>(); //besoin d'essayer avec initialCapacity != 16, du style getNombreNoeuds
		}
	}
	
	public GrapheColorie(EnergiePotentielle Ep, int k, Graphe graphe) {
		this(Ep, k, graphe, new HighQualityRandom().nextInt()); 
	}
	
	/**
	 * Initialisation de l'�tat: affectation de couleurs aleatoires.
	 */
	public void initialiserSansSeed(){
		
		// Affectation des couleurs
		// Mettre � jour tous les conflits initiaux 
		// et rajouter tous les noeuds � la liste des noeuds en conflit
		
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {			
			this.couleurs[j]=(int)(this.gen.nextDouble()*this.k);  // affectation couleurs aleatoires
			this.colorClasses[this.couleurs[j]].add(j); //partie qui initialise les classes couleurs
		}
		
		//Calcul du nombre initial de conflits
		int conflits = 0;
		for (int noeudActuel = 0; noeudActuel < this.graphe.getNombreNoeuds(); noeudActuel++) {
			for (int noeudAdjacent : graphe.connexions[noeudActuel]){
				if(this.couleurs[noeudAdjacent]==this.couleurs[noeudActuel]){
					if (!this.noeudsConflitList.contains(noeudActuel)) this.noeudsConflitList.add(noeudActuel);
					conflits++;
				}
				this.F[noeudActuel][this.couleurs[noeudAdjacent]] ++ ; // initialisation F. F(v,c)= nb de voisins de v a la couleur c.
			}
		}
		
		this.nombreConflitsAretes = (conflits/2); // chaque conflit est compte deux fois
	}
	
	/**
	 * R�initialisation. Le seed est chang� � la valeur pr�c�dente plus un.
	 */
	public void initialiser(){
		this.initialiserSansSeed();
		this.setSeed(this.getSeed() + 1);
		this.gen = new HighQualityRandom(this.getSeed());
	}
	
	@Override
	public int distanceIsing(Etat e){
		
		int E = 0;
		int spin1 = 1;
		int spin2 = 1;
		
		GrapheColorie[] v = {(GrapheColorie) e.getPrevious(),(GrapheColorie) e.getNext()};
		
		//calcul des diff�rences spins entre e et ses voisins
		for (int p = 0; p < 2 ; p++){
			GrapheColorie etat = (GrapheColorie) e;
			GrapheColorie etatNext = (GrapheColorie) v[p];
			for (int i = 0; i < etat.getCouleurs().length; i++){
				for (int j = 0; j < i; j++){							// les noeuds j < i
					spin1 = 1;
					spin2 = 1;
					if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
						spin1 = -1;
					}
					if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
						spin2 = -1;
					}
					E += spin1*spin2;
				}
				for (int j = i+1; j < etat.getCouleurs().length; j++){	// les noeuds j > i
					spin1 = 1;
					spin2 = 1;
					if (etat.getCouleurs()[i] == etat.getCouleurs()[j]){
						spin1 = -1;
					}
					if (etatNext.getCouleurs()[i] == etatNext.getCouleurs()[j]){
						spin2 = -1;
					}
					E += spin1*spin2;
				}
			}
		}
		
		E /= 2; // Tous les spins sont compt�s deux fois dans les calculs
		
		return E;
	}
	
	public int calculerDeltaEc(IMutation mutation) {
		
		MutationConflitsAleatoire m = (MutationConflitsAleatoire) mutation;
		GrapheColorie coloriage = (GrapheColorie)	this;
		GrapheColorie coloriageNext = (GrapheColorie)	this.getNext();
		GrapheColorie coloriagePrev = (GrapheColorie)	this.getPrevious();
		int deltaE = 0;

		HashSet<Integer> Valpha = coloriage.getClassesCouleurs()[coloriage.getCouleurs()[m.noeud]];
		HashSet<Integer> Vbeta = coloriage.getClassesCouleurs()[m.couleur];

		Valpha.remove(m.noeud);	// le calcul suivant requiert d'exclure v de Valpha 

		for (int u : Valpha){
			deltaE += 2*(coloriageNext.spinConflit(u, m.noeud) + coloriagePrev.spinConflit(u, m.noeud));
		}
		
		for (int u : Vbeta){
			deltaE -= 2*(coloriageNext.spinConflit(u, m.noeud) + coloriagePrev.spinConflit(u, m.noeud));
		}
		
		Valpha.add(m.noeud);	// rajouter v dans le classe de couleur (vu qu'on l'a enleve avant)
		
		return deltaE;
	}
	
	/**
	 * Mise � jour des classes de couleurs et de F � appeller � chaque fois qu'un noeud change de couleur.
	 * Les changements sont locaux, centr�s sur le noeud en question.
	 * @param noeud
	 * @param prevColor
	 */
	public void updateLocal(int noeud, int prevColor){
		
		// Met � jour l'appartenance du noeud en question par rapport aux classes de couleur
		this.colorClasses[prevColor].remove(noeud);
		this.colorClasses[this.couleurs[noeud]].add(noeud);
		
		for (int j : graphe.connexions[noeud]){
			// Mise a jour de F
			this.F[j][this.couleurs[noeud]] ++;
			this.F[j][prevColor] --;
			
			// Mise a jour des conflits
			if (this.couleurs[j] == prevColor){
				this.nombreConflitsAretes --;
				if(!enConflit(j)) {
					this.noeudsConflitList.removeFirstOccurrence(j);
				}
			}
			else if (this.couleurs[j] == this.couleurs[noeud]){
				this.nombreConflitsAretes ++;
				if (! this.noeudsConflitList.contains(j)) this.noeudsConflitList.add(j);
			}
		}
		if (!enConflit(noeud)){
			this.noeudsConflitList.removeFirstOccurrence(noeud);
		}
	}
	
	public double getEnergie(){
		return this.getNombreConflitsAretes();
	}
	
	/**
	 * V�rifie si le noeud est en conflit ou pas.
	 * @param noeud
	 * @return True si le noeud est en conflit, false sinon.
	 */
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
	
	/**
	 * Donne le spin de l'ar�te entre deux noeuds.
	 * @param noeud
	 * @param noeud2
	 * @return 1 si les deux noeuds n'ont pas la m�me couleur, -1 sinon.
	 */
	public int spinConflit(int noeud, int noeud2){
		
		if (couleurs[noeud] == couleurs[noeud2]){
			return -1;
		}
		return 1;
	}


	/**
	 * Sauvegarde du coloriage actuel dans couleurs.
	 */
	public void sauvegarderSolution(){
		System.out.println("SS");
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.getNombreNoeuds(); j++) {
			this.meilleuresCouleurs[j] = this.couleurs[j];
			
		}
		
	}
	
	/**
	 * 
	 * @return Nombre de noeuds en conflit.
	 */
	public int nombreNoeudsEnConflit() {
		return this.noeudsConflitList.size();
	}
	
	public int[] getCouleurs() {
		return couleurs;
	}
	
	public HashSet<Integer>[] getClassesCouleurs() {
		return colorClasses;
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
	
	public LinkedList<Integer> getNoeudsConflitList() {
		return noeudsConflitList;
	}

	public int getNombreConflitsAretes() {
		return nombreConflitsAretes;
	}

	public void setNombreConflitsAretes(int nombreConflitsAretes) {
		this.nombreConflitsAretes = nombreConflitsAretes;
	}
	
	
	public HighQualityRandom getGen() {
		return gen;
	}

	public HashSet<Integer>[] getColorClasses() {
		return colorClasses;
	}

	public int[][] getF() {
		return F;
	}

	
	
	
	public void setGen(HighQualityRandom gen) {
		this.gen = gen;
	}

	public void setCouleurs(int[] couleurs) {
		this.couleurs = couleurs;
	}

	public void setNoeudsConflitList(LinkedList<Integer> noeudsConflitList) {
		this.noeudsConflitList = noeudsConflitList;
	}

	public void setColorClasses(HashSet<Integer>[] colorClasses) {
		this.colorClasses = colorClasses;
	}

	public void setF(int[][] f) {
		F = f;
	}

	
	public Etat clone(){
		
		GrapheColorie e = new GrapheColorie(this.getE(),this.k,this.graphe,this.getSeed());
		
		// Reclonage
		
		e.setGen(gen);
		e.setColorClasses(colorClasses.clone());
		e.setMeilleuresCouleurs(meilleuresCouleurs.clone());
		int[][] F2 = F.clone();
		for (int i = 0; i < F2.length; i++){
			F2[i] = F[i].clone();
		}
		e.setF(F2);
		e.setCouleurs(couleurs.clone());
		
		
		
		
		int nb = nombreConflitsAretes;
		e.setNombreConflitsAretes(nb);
		
		LinkedList<Integer> listeConflitsNoeuds = new LinkedList<Integer>(noeudsConflitList);
		Collections.copy(listeConflitsNoeuds, (LinkedList<Integer>) noeudsConflitList);
		e.setNoeudsConflitList(listeConflitsNoeuds);
		
		e.setnext(this.getNext());
		e.setprevious(this.getPrevious());
		
		
		return e;
	}
	

}
