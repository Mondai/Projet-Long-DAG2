package solverCommun;

public abstract class EnergiePotentielle{

	abstract public double calculer(Etat etat);
	abstract public double calculerDeltaE(Etat etat, MutationElementaire mutation);
}
