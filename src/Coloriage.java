import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/*
 * Problème du routage
 * Cette classe implémente la classe abstraite Probleme
 */

public class Coloriage extends Probleme {
	
	// Paramètres du problème
	int[] couleurs; //Tableau des noeuds répertoriant leurs couleurs
	int[] meilleuresCouleurs; // Tableau des noeuds répertoriant la meilleure répartition des couleurs trouvée jusqu'ici
	Graphe graphe;
	int k; // nombre de couleurs pour le coloriage
	
	// Sauvegarde de la dernière modification effectuée
	public Modification derniereModif;


	public Coloriage(IEnergie E, IMutation mutation, int k, Graphe graphe) {
		
		this.derniereModif = null;
		this.E = E;
		this.mutation = mutation;
		this.k = k; 
		this.couleurs = new int[graphe.nombreNoeuds];
		this.meilleuresCouleurs = new int[graphe.nombreNoeuds];
		this.graphe = graphe;
		
		
	}
	
	
	// Initialisation du problème: affectation de couleurs aléatoires
	public void initialiser(Random random){
		
		//Affectation des couleurs
		for (int j = 1; j <= this.graphe.nombreNoeuds; j++) {
			this.couleurs[j] = 1;
		}
		
	}
	

	// Sauvegarde du coloriage actuel dans une variable
	public void sauvegarderSolution(){
		
		//Affectation des couleurs
		for (int j = 1; j <= this.graphe.nombreNoeuds; j++) {
			this.meilleuresCouleurs[j] = this.couleurs[j];
		}
	}

}
