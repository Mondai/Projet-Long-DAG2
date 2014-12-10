package solverQuantique;

public abstract class EnergiePotentielle{

	abstract public double calculer(ParticuleQuantique particuleQuantique, int numeroReplique);
	abstract public double calculerDeltaE(ParticuleQuantique particuleQuantique, int numeroReplique, MutationElementaire mutation);
}
