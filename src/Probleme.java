import java.util.Random;

/*
 * Classe abstraite représentant un problème générique soluble par recuit simulé
 * Pour résoudre un problème grâce au recuit il faut faire une classe fille qui implémente
 * les différentes méthodes abstraites utilisées par l'algorithme
 */

public abstract class Probleme {
	
	public IEnergie E;
	public IMutation mutation;

	public abstract void initialiser(Random random); // initialisation du problème: attribution de routes aléatoires	 
	public abstract void sauvegarderSolution(); // sauvegarde la solution actuelle dans une variable
	
	// Calcule et retourne l'énergie (ex: la latance)
	public double calculerEnergie(){
		return this.E.calculer(this);
	}
	
	// Effectue une mutation élémentaire du problème
	public void modifElem(){
		this.mutation.faire(this);
	}
	
	// Annule la dernière mutation élémentaire effectuée
	public void annulerModif(){
		this.mutation.defaire(this);
	}
	
}
