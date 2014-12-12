package solverCommun;

public abstract class EnergieCinetique{

	abstract public double calculer(Probleme probleme);
	abstract public double calculerDeltaE(Etat etat, Etat prev, Etat next, MutationElementaire mutation);
}
