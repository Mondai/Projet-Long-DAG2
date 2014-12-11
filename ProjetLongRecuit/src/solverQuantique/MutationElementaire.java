package solverQuantique;

public abstract class MutationElementaire {
	
	abstract void trouverMutation (ParticuleQuantique particuleQuantique, int numeroReplique) ;
	abstract void effectuerMutation(ParticuleQuantique particuleQuantique, int numeroReplique) ;
}
