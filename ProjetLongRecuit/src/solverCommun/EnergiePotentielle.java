package solverCommun;

public abstract class EnergiePotentielle{
	
	/**
	 * 
	 * @param etat
	 * Etat modifié par la mutation.
	 * @return L'énergie potentielle de l'état modifié.
	 */
	abstract public double calculer(Etat etat);
	
	/**
	 * 
	 * @param etat
	 * Etat modifié par la mutation.
	 * @param mutation
	 * Mutation affectant l'état en question.
	 * @return La différence d'énergie potentielle sur l'état modifié par la mutation donnée.
	 */
	abstract public double calculerDeltaE(Etat etat, MutationElementaire mutation);
	
}
