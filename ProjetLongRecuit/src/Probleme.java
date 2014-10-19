/*
 * Classe abstraite repr�sentant un probl�me g�n�rique soluble par recuit simul�
 * Pour r�soudre un probl�me gr�ce au recuit il faut faire une classe fille qui impl�mente
 * les diff�rentes m�thodes abstraites utilis�es par l'algorithme
 */

public abstract class Probleme {
	
	public IEnergie E;
	public IMutation mutation;
	int seed = new HighQualityRandom().nextInt();
	HighQualityRandom gen = new HighQualityRandom(seed);

	public abstract void initialiser();
	public abstract void sauvegarderSolution(); // sauvegarde la solution actuelle dans une variable
	
	// Calcule et retourne l'�nergie (ex: la latance)
	public double calculerEnergie(){
		return this.E.calculer(this);
	}
	
	// Effectue une mutation �l�mentaire du probl�me
	public void modifElem(){
		this.mutation.faire(this);
	}
	
	// Annule la derni�re mutation �l�mentaire effectu�e
	public void annulerModif(){
		this.mutation.defaire(this);
	}
	
}
