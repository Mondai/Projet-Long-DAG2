import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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
	
	// Sauvegarde de la derni�re modification effectu�e
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
	
	
	// Initialisation du probl�me: affectation de couleurs al�atoires
	public void initialiser(){
		
		//Affectation des couleurs
		for (int j = 0; j < this.graphe.nombreNoeuds; j++) {
			this.couleurs[j] = 0;
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
