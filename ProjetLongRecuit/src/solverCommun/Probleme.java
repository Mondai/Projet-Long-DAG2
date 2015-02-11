package solverCommun;

import solver.HighQualityRandom;
/*
 * Classe abstraite repr�sentant un probl�me g�n�rique soluble par recuit simul�
 * Pour r�soudre un probl�me gr�ce au recuit il faut faire une classe fille qui impl�mente
 * les diff�rentes m�thodes abstraites utilis�es par l'algorithme
 */

public abstract class Probleme extends Particule{
	
	public IMutation mutation;
	private int seed = new HighQualityRandom().nextInt();
	public HighQualityRandom gen = new HighQualityRandom(getSeed());
	
	/**
	 * Initialise le probl�me
	 */
	public abstract void initialiser();
	
	/**
	 * sauvegarde la solution actuelle dans une variable
	 */
	public abstract void sauvegarderSolution();
	
	/**
	 * Calcule et retourne l'�nergie totale
	 * @return Energie totale de la particule (Energie cin�tique + �nergie potentielle)
	 */
	public double calculerEnergie(){
		return ( this.Ec.calculer(this) + calculerEnergiePotentielle() );
	}
	
	/**
	 * Calcule l'�nergie potentielle de chaque etat et les somme
	 * @return Energie potentielle de la particule
	 */
	public double calculerEnergiePotentielle(){
		double energiePotentiel = 0;
		for (Etat etat : this.etats){
			energiePotentiel += etat.Ep.calculer(etat);
		}
		return energiePotentiel;
	}
	

	/**
	 * Calcule deltaEp � partir de la mutation propos�e (sans effectuer la mutation)
	 * @param etat
	 * Etat que l'on veut muter
	 * @param mutation
	 * Mutation � effectuer
	 * @return La diff�rence d'�nergie potentielle apr�s que la mutation affecte l'�tat
	 */
	public double calculerDeltaEp(Etat etat, MutationElementaire mutation){
		return etat.Ep.calculerDeltaE(etat, mutation);
	}
	
	/**
	 * Calcule deltaEc � partir de la mutation propos�e (sans effectuer la mutation)
	 * @param etat
	 * Etat que l'on veut muter
	 * @param previous
	 * Etat pr�c�dent sur la liste repr�sentant les int�ractions quantiques des spins entre particules
	 * @param next
	 * Etat suivant sur la liste repr�sentant les int�ractions quantiques des spins entre particules
	 * @param mutation
	 * Mutation � effectuer
	 * @return La diff�rence d'�nergie cin�tique � un coefficient proportionnel JGamma pr�s apr�s que la mutation affecte l'�tat
	 */
	public double calculerDeltaEc(Etat etat, Etat previous, Etat next, MutationElementaire mutation){
		return this.Ec.calculerDeltaE(etat, previous, next, mutation);
	}
	
	/**
	 * Calcule une borne sup�rieure de deltaEc � partir de la mutation propos�e (sans effectuer la mutation). Celle ci d�pend
	 * des tailles de la classe de coloriage pr�c�dant la mutation et de celle suivant la mutation.
	 * @param etat
	 * Etat que l'on veut muter
	 * @param previous
	 * Etat pr�c�dent sur la liste repr�sentant les int�ractions quantiques des spins entre particules
	 * @param next
	 * Etat suivant sur la liste repr�sentant les int�ractions quantiques des spins entre particules
	 * @param mutation
	 * Mutation � effectuer
	 * @return La borne sup�rieure de la diff�rence d'�nergie cin�tique � un coefficient proportionnel JGamma pr�s apr�s que la mutation affecte l'�tat
	 */
	public double calculerDeltaEcUB(Etat etat, Etat previous, Etat next, MutationElementaire mutation) {
		return this.Ec.calculerDeltaEUB(etat, previous, next, mutation); // � voir si cette fonction doit �tre conforme (elle sera h�rit�e et chang�e normalement)
	}
	
	/**
	 * Effectue une mutation �l�mentaire du probl�me
	 * @param etat
	 * Etat sur lequel on veut effectuer une MutationElementaire
	 * @param mutation
	 * MutationElementaire � effectuer
	 */
	public void modifElem(Etat etat, MutationElementaire mutation){
		this.mutation.faire(this, etat, mutation);
	}

	/**
	 * Renvoie une mutation �l�mentaire de l'�tat
	 * @param etat
	 * Etat sur lequel on veut obtenir une MutationElementaire
	 * @return Une MutationElementaire possible
	 */
	public MutationElementaire getMutationElementaire(Etat etat){
		return this.mutation.getMutationElementaire(this,etat);
	}
	
	/**
	 * 
	 * @return Seed du probl�me
	 */
	public int getSeed() {
		return seed;
	}
	/**
	 * Change le seed du g�n�rateur al�atoire du probl�me
	 * @param seed
	 * Seed du g�n�rateur al�atoire du probl�me
	 */
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
}
