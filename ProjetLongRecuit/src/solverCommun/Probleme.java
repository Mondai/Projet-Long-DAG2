package solverCommun;

import solver.HighQualityRandom;
/*
 * Classe abstraite représentant un problème générique soluble par recuit simulé
 * Pour résoudre un problème grâce au recuit il faut faire une classe fille qui implémente
 * les différentes méthodes abstraites utilisées par l'algorithme
 */

public abstract class Probleme extends Particule{
	
	public IMutation mutation;
	private int seed = new HighQualityRandom().nextInt();
	public HighQualityRandom gen = new HighQualityRandom(getSeed());

	public abstract void initialiser();
	public abstract void sauvegarderSolution(); // sauvegarde la solution actuelle dans une variable
	
	// Calcule et retourne l'énergie (ex: la latence)
	public double calculerEnergie(){
		return ( this.Ec.calculer(this) + calculerEnergiePotentielle() );
	}
	
	//calcule l'énergie potentielle de chaque etat et les somme
	public double calculerEnergiePotentielle(){
		double energiePotentiel = 0;
		for (Etat etat : this.etats){
			energiePotentiel += etat.Ep.calculer(etat);
		}
		return energiePotentiel;
	}
	
	// Effectue une mutation élémentaire du problème
	public void modifElem(){
		this.mutation.faire(this);
	}
	
	// Annule la dernière mutation élémentaire effectuée
	public void annulerModif(){
		this.mutation.defaire(this);
	}
	
	public void modifElem(Etat etat){
		this.mutation.faire(this, etat);
	}
	
	public void annulerModif(Etat etat){
		this.mutation.defaire(this, etat);
	}
	
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}
