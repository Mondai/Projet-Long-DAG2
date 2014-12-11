package solverCommun;

import java.util.Arrays;
import java.util.Collections;

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
	
	// Calcule l'énergie potentielle de chaque etat et les somme
	public double calculerEnergiePotentielle(){
		double energiePotentiel = 0;
		for (Etat etat : this.etats){
			energiePotentiel += etat.Ep.calculer(etat);
		}
		return energiePotentiel;
	}
	
	// Calcule deltaE à partir de la mutation proposée (sans effectuer la mutation)
	public double calculerDeltaEp(Etat etat, MutationElementaire mutation){
		return etat.Ep.calculerDeltaE(etat, mutation);
	}
	
	// Effectue une mutation élémentaire du problème
	public void modifElem(Etat etat, MutationElementaire mutation){
		this.mutation.faire(this, etat, mutation);
	}
	
	// renvoie une mutation elementaire possible
	public MutationElementaire getMutationElementaire(Etat etat){
		return this.mutation.getMutationElementaire(this,etat);
	}

	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public void shuffleEtats() {
		Collections.shuffle(Arrays.asList(etats), gen);
		
	}
	
}
