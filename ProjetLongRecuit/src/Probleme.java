/*
 * Classe abstraite représentant un problème générique soluble par recuit simulé
 * Pour résoudre un problème grâce au recuit il faut faire une classe fille qui implémente
 * les différentes méthodes abstraites utilisées par l'algorithme
 */

public abstract class Probleme {
	
	public IEnergie E;
	public IMutation mutation;
	int seed = new HighQualityRandom().nextInt();
	HighQualityRandom gen = new HighQualityRandom(seed);

	public abstract void initialiser();
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
