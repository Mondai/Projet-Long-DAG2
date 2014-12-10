package solverQuantique;

public abstract class EnergieCinetique{

	
	abstract public double calculer(ParticuleQuantique particuleQuantique);
	abstract public double calculerDeltaE(ParticuleQuantique particuleQuantique, MutationElementaire mutation);
}
