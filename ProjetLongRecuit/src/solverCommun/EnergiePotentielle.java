package solverCommun;

public abstract class EnergiePotentielle{
	
	/**
	 * 
	 * @param etat
	 * Etat modifi� par la mutation.
	 * @return L'�nergie potentielle de l'�tat modifi�.
	 */
	abstract public double calculer(Etat etat);
	
	/**
	 * 
	 * @param etat
	 * Etat modifi� par la mutation.
	 * @param mutation
	 * Mutation affectant l'�tat en question.
	 * @return La diff�rence d'�nergie potentielle sur l'�tat modifi� par la mutation donn�e.
	 */
	abstract public double calculerDeltaE(Etat etat, MutationElementaire mutation);
	
}
